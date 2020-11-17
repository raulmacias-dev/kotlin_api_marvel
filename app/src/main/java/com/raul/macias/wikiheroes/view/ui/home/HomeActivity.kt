package com.raul.macias.wikiheroes.view.ui.home

import android.app.ActivityOptions
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.raul.macias.wikiheroes.R
import com.raul.macias.wikiheroes.api.Resource
import com.raul.macias.wikiheroes.api.Status
import com.raul.macias.wikiheroes.databinding.ActivityHomeBinding
import com.raul.macias.wikiheroes.models.Character
import com.raul.macias.wikiheroes.utils.ErrorDialog
import com.raul.macias.wikiheroes.utils.Utils
import com.raul.macias.wikiheroes.view.adapter.CharactersAdapter
import com.raul.macias.wikiheroes.view.viewholder.CharacterViewHolder
import dagger.android.AndroidInjection
import javax.inject.Inject
import android.content.Intent
import android.os.Parcelable
import com.raul.macias.wikiheroes.factory.AppViewModelFactory
import com.raul.macias.wikiheroes.view.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.item_character.view.*
import android.util.Pair as UtilPair


class HomeActivity : AppCompatActivity(), CharacterViewHolder.Delegate {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val binding by lazy { DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home) }

    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(HomeActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) //This line initialise our dependencies
        super.onCreate(savedInstanceState)

        if ( savedInstanceState == null ) {
            viewModel.firstTime = true
            initView()
            observeViewModel()
        }
    }


    private fun initView() {
        val linearLayout = androidx.recyclerview.widget.LinearLayoutManager(this)
        binding.rvCharacters.layoutManager = linearLayout
        viewModel.adapter = CharactersAdapter(this)
        binding.rvCharacters.adapter = viewModel.adapter
        binding.rvCharacters.addOnScrollListener(Utils.InfiniteScrollListener({
            viewModel.pageCounter += 1
            loadMore(viewModel.pageCounter) }, linearLayout))
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
        Log.d("HomeActivity", "call LOADING")
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
        Log.d("HomeActivity", "call ERROR response : " + throwable.toString())
        binding.progressBar.visibility = View.GONE
        ErrorDialog.show(this.supportFragmentManager.beginTransaction(), throwable.toString())
    }

    override fun onItemClick(character: Character, view: View) {


        val img = UtilPair.create(view.image as View, resources.getString(R.string.transition_character_image))

        val name = UtilPair.create(view.name as View, resources.getString(R.string.transition_character_name))

        val options = ActivityOptions.makeSceneTransitionAnimation(this, img , name)

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.INTENT_CHARACTER , character as Parcelable)
        startActivity(intent, options.toBundle())
    }

}
