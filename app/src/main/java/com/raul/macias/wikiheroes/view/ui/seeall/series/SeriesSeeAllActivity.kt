package com.raul.macias.wikiheroes.view.ui.seeall.series

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.raul.macias.wikiheroes.R
import com.raul.macias.wikiheroes.api.ApiResponse
import com.raul.macias.wikiheroes.databinding.ActivitySeriesSeeAllBinding
import com.raul.macias.wikiheroes.factory.AppViewModelFactory
import com.raul.macias.wikiheroes.models.Detail
import com.raul.macias.wikiheroes.models.DetailResponse
import com.raul.macias.wikiheroes.utils.Utils
import com.raul.macias.wikiheroes.view.adapter.DetailAdapter
import com.raul.macias.wikiheroes.view.ui.detail_items.detail_comic.DetailItemActivity
import com.raul.macias.wikiheroes.view.viewholder.DetailViewHolder
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.item_small_image.view.*
import javax.inject.Inject

class SeriesSeeAllActivity : AppCompatActivity(), DetailViewHolder.Delegate {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(SeriesSeeAllViewModel::class.java) }

    private val binding by lazy { DataBindingUtil.setContentView<ActivitySeriesSeeAllBinding>(this, R.layout.activity_series_see_all) }

    companion object {
        const val SERIES_ID = "series_id"
        const val SERIES_TITLE = "series_title"
        const val SERIES_IMAGE = "series_image"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)


        initUI()
        observeViewModel()

    }

    private fun initUI() {

        viewModel.seriesId = intent.extras.getString(SERIES_ID)
        viewModel.seriesImage = intent.extras.getString(SERIES_IMAGE)
        viewModel.seriesTitle = intent.extras.getString(SERIES_TITLE)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        supportActionBar?.title = viewModel.seriesTitle

        binding.seriesTitle.text = viewModel.seriesTitle


        //LIST
        val layoutManager = GridLayoutManager(this, 3)
        binding.listSerieComics.layoutManager = layoutManager
        viewModel.adapter = DetailAdapter(this)
        binding.listSerieComics.adapter = viewModel.adapter

        binding.listSerieComics.addOnScrollListener(Utils.InfiniteScrollListenerGrid({
            viewModel.pageCounter += 1
            loadMore(viewModel.pageCounter)
        }, layoutManager))
    }

    private fun observeViewModel() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.itemsLiveData.observe(this, Observer { it?.let { processResponse(it) } })
        loadMore(viewModel.pageCounter++)
    }

    private fun loadMore(page: Int) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.postPage(page)
    }

    private fun processResponse(response: ApiResponse<DetailResponse>) {
        binding.progressBar.visibility = View.GONE
        if (response.isSuccessful && response.body != null) {
            viewModel.increaseOffset()
            renderDataState(response.body.data.results)
        } else {
            //show error
            //todo
        }
    }

    private fun renderDataState(items: List<Detail>) {


        items.forEach { it.week = Utils.WEEK.none }

        viewModel.adapter.updateList(items)
        if (viewModel.firstTime) {
            binding.listSerieComics.scheduleLayoutAnimation()
            viewModel.firstTime = false
        }
    }

    override fun onItemClick(item: Detail, view: View) {
        val img = Pair.create(view.image_gallery as View, resources.getString(R.string.transition_detail_image))

        val txt = Pair.create(view.title_gallery as View, resources.getString(R.string.transition_detail_title))

        val options = ActivityOptions.makeSceneTransitionAnimation(this, img, txt)

        val intent = Intent(this, DetailItemActivity::class.java)
        intent.putExtra(DetailItemActivity.INTENT_ITEM, item as Parcelable)
        intent.putExtra(DetailItemActivity.INTENT_SECTION, "Comics")
        startActivity(intent)
    }

}