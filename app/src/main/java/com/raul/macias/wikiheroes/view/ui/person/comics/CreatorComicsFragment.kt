package com.raul.macias.wikiheroes.view.ui.person.comics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.raul.macias.wikiheroes.R
import com.raul.macias.wikiheroes.api.ApiResponse
import com.raul.macias.wikiheroes.databinding.FragmentCreatorComicsBinding
import com.raul.macias.wikiheroes.factory.AppViewModelFactory
import com.raul.macias.wikiheroes.models.Detail
import com.raul.macias.wikiheroes.models.DetailResponse
import com.raul.macias.wikiheroes.models.Item
import com.raul.macias.wikiheroes.utils.Utils
import com.raul.macias.wikiheroes.view.adapter.HomeComicsAdapter
import com.raul.macias.wikiheroes.view.ui.detail_items.detail_comic.DetailItemActivity
import com.raul.macias.wikiheroes.view.viewholder.HomeComicsViewHolder
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.raul.macias.wikiheroes.view.ui.creator.CreatorDetailActivity


class CreatorComicsFragment : Fragment(), HomeComicsViewHolder.Delegate {

    lateinit var binding: FragmentCreatorComicsBinding

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(CreatorFragmentsViewModel::class.java) }

    companion object {

        private const val CREATOR = "creator"

        fun newInstance(creator: Item): CreatorComicsFragment {
            val args = Bundle()
            val fragment = CreatorComicsFragment()
            fragment.arguments = args
            args.putParcelable(CREATOR, creator)
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_creator_comics, container, false)

        viewModel.creator = arguments?.getParcelable(CREATOR)!!

        initView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun initView() {
        viewModel.adapter = HomeComicsAdapter(this)
        binding.rvCreatorComics.adapter = viewModel.adapter

        binding.txtSeeAll.setOnClickListener {
            val intent = Intent( activity , CreatorDetailActivity::class.java)
            intent.putExtra(CreatorDetailActivity.CREATOR , viewModel.creator)
            intent.putExtra(CreatorDetailActivity.TITLE_SECTION , "Comics")
            startActivity(intent)
        }


        //botto view animation
        // Prepare the View for the animation
        /*binding.txtSeeAll.visibility = View.VISIBLE;
        binding.txtSeeAll.setAlpha(0.0f);

        // Start the animation
        binding.txtSeeAll.animate()
                .translationY(binding.txtSeeAll.height.toFloat())
                .alpha(1.0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.txtSeeAll.visibility = View.GONE
                    }
                })*/
    }

    private fun observeViewModel() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getComicsByCreatorId(viewModel.getCreatorId(viewModel.creator)).observe(this, Observer { response ->
            processResponse(response)
        })
    }

    private fun processResponse(response: ApiResponse<DetailResponse>) {
        if (response.isSuccessful) {
            renderDataState(response.body?.data!!.results)
        } else {
            renderErrorState(response.error)
        }
    }

    private fun renderDataState(items: List<Detail>) {
        binding.progressBar.visibility = View.GONE


        if (items.isNotEmpty()) {
            val newItems: MutableList<Detail> = mutableListOf()
            items.forEach {
                it.week = Utils.WEEK.none
                newItems.add(it)
            }
            viewModel.adapter.updateList(newItems)
        } else {
            // no items found
            binding.txtNoItems.visibility = View.VISIBLE
        }


    }

    private fun renderErrorState(throwable: Throwable?) {
        binding.progressBar.visibility = View.GONE
        throwable?.message?.let { Utils.showAlert(activity, it) }
    }

    override fun onItemClick(item: Detail, view: View) {
        val intent = Intent(activity, DetailItemActivity::class.java)
        intent.putExtra(DetailItemActivity.INTENT_ITEM, item as Parcelable)
        intent.putExtra(DetailItemActivity.INTENT_SECTION, "Comics")
        startActivity(intent)
    }

}