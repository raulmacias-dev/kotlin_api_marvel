package com.raul.macias.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.raul.macias.wikiheroes.databinding.ItemSeriesBinding
import com.raul.macias.wikiheroes.models.Detail
import com.raul.macias.wikiheroes.models.SearchObjectItem
import com.raul.macias.wikiheroes.view.ui.search.SeriesUIViewModel

class SearchObjectSeriesViewHolder (view: View, val delegate: Delegate) : BaseViewHolder(view) {

    private val binding by lazy { DataBindingUtil.bind<ItemSeriesBinding>(view) }


    lateinit var series : Detail


    override fun bindData(data: Any?) {
        if( data is Detail && data.getType() == SearchObjectItem.TYPE_SERIES) {
            series = data
            bindUI()
        }
    }

    private fun bindUI() {
        binding?.viewModel = SeriesUIViewModel(series)
        binding?.executePendingBindings()
    }

    override fun onClick(v: View?) {
        return delegate.onSeriesClicked(series, itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    interface Delegate {
        fun onSeriesClicked(item: Detail, view: View)
    }
}