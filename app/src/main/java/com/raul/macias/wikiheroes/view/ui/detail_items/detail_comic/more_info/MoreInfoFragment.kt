package com.raul.macias.wikiheroes.view.ui.detail_items.detail_comic.more_info

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
import com.raul.macias.wikiheroes.databinding.FragmentMoreInfoBinding
import com.raul.macias.wikiheroes.factory.AppViewModelFactory
import com.raul.macias.wikiheroes.models.Item
import com.raul.macias.wikiheroes.view.adapter.CreatorRowAdapter
import com.raul.macias.wikiheroes.view.ui.person.PersonDetailActivity
import com.raul.macias.wikiheroes.view.viewholder.CreatorViewHolder
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MoreInfoFragment : Fragment() , CreatorViewHolder.Delegate {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(MoreInfoViewModel::class.java)}

    lateinit var binding : FragmentMoreInfoBinding

    lateinit var creators : List<Item>

    lateinit var adapter : CreatorRowAdapter

    companion object {

        const val CREATORS = "CREATORS"

        const val SECTION = "SECTION"

        fun newInstance(creators : ArrayList<Item>, section : String) : MoreInfoFragment {
            val args: Bundle = Bundle()
            args.putParcelableArrayList(CREATORS , creators)
            args.putString(SECTION , section)
            val fragment = MoreInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if( savedInstanceState == null ) {
            creators = arguments!!.getParcelableArrayList(CREATORS)

            viewModel.creators = creators

            viewModel.section = arguments!!.getString(SECTION)
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_more_info, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        adapter = CreatorRowAdapter(this, viewModel.getCreatorsMap(viewModel.creators))
        binding.listRowsCreators.adapter = adapter
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onItemClick(creator: Item, view: View) {

        binding.progressBar.visibility = View.VISIBLE
        viewModel.getCreatorDetail(creator).observe( this , Observer { response ->
            binding.progressBar.visibility = View.GONE
            if(response.isSuccessful && response != null && response.body!!.data.results.isNotEmpty()) {
                val detail = response.body.data.results[0]
                val intent = Intent( activity , PersonDetailActivity::class.java)
                intent.putExtra(PersonDetailActivity.CREATOR , creator as Parcelable)
                intent.putExtra(PersonDetailActivity.IMAGE , detail.thumbnail?.path + "." + detail.thumbnail?.extension)
                startActivity(intent)
            }

        })
    }

}