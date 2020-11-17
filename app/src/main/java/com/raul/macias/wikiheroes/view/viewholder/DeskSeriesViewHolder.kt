package com.raul.macias.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.raul.macias.wikiheroes.databinding.DeskSeriesRowBinding
import com.raul.macias.wikiheroes.models.Detail

class DeskSeriesViewHolder (view: View, val delegate: Delegate) : BaseViewHolder(view) {

    private val binding by lazy { DataBindingUtil.bind<DeskSeriesRowBinding>(view) }


    lateinit var series : Detail


    override fun bindData(data: Any?) {
        if(data is Detail) {
            series = data
            drawUI()
        }
    }

    private fun drawUI() {
        binding?.detail = series
        binding?.url = series.thumbnail?.path + "." + series.thumbnail?.extension
        binding?.executePendingBindings()
    }

    override fun onClick(v: View?) {
        delegate.onSeriesClicked(series, itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    interface Delegate {
        fun onSeriesClicked(item: Detail, view: View)
    }
}