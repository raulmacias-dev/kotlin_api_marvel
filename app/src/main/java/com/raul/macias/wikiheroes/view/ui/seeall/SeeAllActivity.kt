package com.raul.macias.wikiheroes.view.ui.seeall

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Build
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
import com.raul.macias.wikiheroes.databinding.ActivitySeeAllBinding
import com.raul.macias.wikiheroes.factory.AppViewModelFactory
import com.raul.macias.wikiheroes.models.Detail
import com.raul.macias.wikiheroes.models.DetailResponse
import com.raul.macias.wikiheroes.utils.Utils
import com.raul.macias.wikiheroes.view.adapter.DetailAdapter
import com.raul.macias.wikiheroes.view.ui.detail_items.detail_comic.DetailItemActivity
import com.raul.macias.wikiheroes.view.viewholder.DetailViewHolder
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.item_small_image.view.*
import org.jetbrains.anko.backgroundDrawable
import javax.inject.Inject


class SeeAllActivity : AppCompatActivity(), DetailViewHolder.Delegate {


    companion object {
        const val TITLE_SECTION = "title_section"
        const val ID = "id"
        const val SECTION = "section"
        const val CHARACTER_NAME = "character_name"
    }

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(SeeAllViewModel::class.java)}

    private val binding by lazy { DataBindingUtil.setContentView<ActivitySeeAllBinding>(this, R.layout.activity_see_all) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        if( savedInstanceState == null ) {
            viewModel.id = intent.getIntExtra(ID, 0)
            viewModel.title = intent.getStringExtra(TITLE_SECTION)
            viewModel.type = intent.getStringExtra(SECTION)
            viewModel.firstTime = true

            if(viewModel.type == "Comics" || viewModel.type == "Series") {
                viewModel.characterName = intent.getStringExtra(CHARACTER_NAME)
                viewModel.title = viewModel.characterName + " : " + viewModel.type
            }

            initUI()

            observerViewModel()
        }
    }

    private fun initUI() {

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.title.text = viewModel.title

        // set gradient background color
        setGradientBackground(viewModel.getDominantColor())

        // init list
        val layoutManager = GridLayoutManager(this , 3)
        binding.listItems.layoutManager = layoutManager
        viewModel.adapter = DetailAdapter(this)
        binding.listItems.adapter = viewModel.adapter

        binding.nested.setOnScrollChangeListener(Utils.NestedInfiniteScrollListener {
            viewModel.pageCounter += 1
            loadMore(viewModel.pageCounter)
        })

    }

    private fun observerViewModel() {
        viewModel.itemsLiveData.observe(this, Observer { it?.let { processResponse(it) } })
        loadMore(viewModel.pageCounter++)
    }

    private fun loadMore(page : Int) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.postPage(page)
    }

    private fun processResponse(response: ApiResponse<DetailResponse>) {
        binding.progressBar.visibility = View.GONE
        if(response.isSuccessful && response.body != null) {
            viewModel.increaseOffset()
            renderDataState(response.body.data.results)
        } else {
            //show error
            //todo
        }
    }

    private fun renderDataState( items : List<Detail>) {
        items.forEach {
            it.week = Utils.WEEK.none
        }
        viewModel.adapter.updateList(items)
        if(viewModel.firstTime) {
            binding.listItems.scheduleLayoutAnimation()
            viewModel.firstTime = false
        }
    }


    private fun setGradientBackground(dominantColor : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val colors: IntArray = intArrayOf(resources.getColor(R.color.black, null), dominantColor)
            val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors)
            gradientDrawable.cornerRadius = 0f
            binding.root.backgroundDrawable = gradientDrawable
        } else {

        }

    }


    override fun onItemClick(item: Detail, view: View) {
        val img = Pair.create(view.image_gallery as View, resources.getString(R.string.transition_detail_image))

        val txt = Pair.create(view.title_gallery as View, resources.getString(R.string.transition_detail_title))

        val options = ActivityOptions.makeSceneTransitionAnimation(this, img, txt)

        val intent = Intent(this, DetailItemActivity::class.java)
        intent.putExtra(DetailItemActivity.INTENT_ITEM , item as Parcelable)
        intent.putExtra(DetailItemActivity.INTENT_SECTION, viewModel.type)
        startActivity(intent)
    }

}