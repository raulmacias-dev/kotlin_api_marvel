package com.raul.macias.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.raul.macias.wikiheroes.databinding.ItemMoreGalleryBinding
import com.raul.macias.wikiheroes.models.Detail

class MoreImageViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {

    //here we define actions which we handle
    interface Delegate {
        fun onItemClick(item: Detail, view: View)
    }

    private lateinit var item: Detail

    private val binding by lazy { DataBindingUtil.bind<ItemMoreGalleryBinding>(view) }

    override fun bindData(data: Any?) {
        if (data is Detail) {
            item = data
            drawUI()
        }
    }

    private fun drawUI() {
        binding?.detail = item
        binding?.url = item.thumbnail?.path + "." + item.thumbnail?.extension
        binding?.executePendingBindings()
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(item, itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }
}