package com.raul.macias.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.raul.macias.wikiheroes.databinding.HomeItemComicBinding
import com.raul.macias.wikiheroes.models.Detail
import com.raul.macias.wikiheroes.view.ui.home.comics.HomeComicUIViewModel

class HomeComicsViewHolder (view: View, val delegate: Delegate) : BaseViewHolder(view) {

    private val binding by lazy { DataBindingUtil.bind<HomeItemComicBinding>(view) }

    private lateinit var item: Detail


    override fun bindData(data: Any?) {
        if (data is Detail) {
            item = data
            drawUI()
        }
    }

    private fun drawUI() {
        binding?.viewModel = HomeComicUIViewModel(item)
        binding?.executePendingBindings()
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(item, itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    interface Delegate {
        fun onItemClick(item: Detail, view: View)
    }
}