package com.raul.macias.wikiheroes.view.ui.home.characters

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.raul.macias.wikiheroes.R
import com.raul.macias.wikiheroes.api.Resource
import com.raul.macias.wikiheroes.api.Status
import com.raul.macias.wikiheroes.databinding.FragmentHomeCharactersBinding
import com.raul.macias.wikiheroes.factory.AppViewModelFactory
import com.raul.macias.wikiheroes.models.Character
import com.raul.macias.wikiheroes.utils.Utils
import com.raul.macias.wikiheroes.view.adapter.CharactersAdapter
import com.raul.macias.wikiheroes.view.ui.detail.DetailActivity
import com.raul.macias.wikiheroes.view.ui.home.HomeActivityViewModel
import com.raul.macias.wikiheroes.view.viewholder.CharacterViewHolder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.item_character.view.*
import javax.inject.Inject

class HomeCharactersFragment : Fragment() , CharacterViewHolder.Delegate {


    override fun onItemClick(character: Character, view: View) {

        val img = Pair.create(view.image as View, resources.getString(R.string.transition_character_image))

        val name = Pair.create(view.name as View, resources.getString(R.string.transition_character_name))

        val options = ActivityOptions.makeSceneTransitionAnimation(activity, img , name)

        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.INTENT_CHARACTER , character as Parcelable)
        startActivity(intent, options.toBundle())
    }


    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(HomeActivityViewModel::class.java) }

    lateinit var binding : FragmentHomeCharactersBinding


    companion object {
        fun newInstance() : HomeCharactersFragment {
            val args = Bundle()
            val fragment = HomeCharactersFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater ,  R.layout.fragment_home_characters, container, false)

        if ( savedInstanceState == null ) {
            viewModel.firstTime = true
        }

        initView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun initView() {
        val linearLayout = androidx.recyclerview.widget.LinearLayoutManager(context)
        binding.rvCharacters.layoutManager = linearLayout
        viewModel.adapter = CharactersAdapter(this)
        binding.rvCharacters.adapter = viewModel.adapter
        binding.rvCharacters.addOnScrollListener(Utils.InfiniteScrollListener({
            viewModel.pageCounter += 1
            loadMore(viewModel.pageCounter)
        }, linearLayout))
    }

    private fun loadMore(page : Int) {
        viewModel.postPage(page)
    }

    private fun observeViewModel() {
        viewModel.charactersLiveData.observe(this, Observer { it?.let { processResponse(it) } })
        loadMore(viewModel.pageCounter++)
    }

    private fun processResponse(response: Resource<List<Character>>) {
        when (response.status) {
            Status.LOADING -> renderLoadingState()

            Status.SUCCESS -> renderDataState(Utils.checkCharactersImages(response.data!!))

            Status.ERROR -> renderErrorState(response.error!!)
        }
    }

    private fun renderLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun renderDataState(items : List<Character>) {

        binding.progressBar.visibility = View.GONE

        viewModel.pageCounter = items[items.size-1].page

        viewModel.adapter.updateList(items)

        if(viewModel.firstTime) {
            binding.rvCharacters.scheduleLayoutAnimation()
            viewModel.firstTime = false
        }

    }

    private fun renderErrorState(throwable: Throwable) {
        binding.progressBar.visibility = View.GONE
        //ErrorDialog.show(suppo.beginTransaction(), throwable.toString())
    }

}