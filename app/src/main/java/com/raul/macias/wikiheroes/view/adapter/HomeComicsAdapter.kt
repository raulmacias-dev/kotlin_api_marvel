package com.raul.macias.wikiheroes.view.adapter

import android.view.View
import com.raul.macias.wikiheroes.R
import com.raul.macias.wikiheroes.models.Detail
import com.raul.macias.wikiheroes.view.viewholder.BaseViewHolder
import com.raul.macias.wikiheroes.view.viewholder.HomeComicsViewHolder

class HomeComicsAdapter ( val delegate : HomeComicsViewHolder.Delegate) : BaseAdapter() {

    init {
        addItems(ArrayList<Detail>())
    }

    fun addList( comics: List<Detail>) {
        clearItems()
        addItems(comics)
        notifyDataSetChanged()
    }

    fun updateList( comics : List<Detail>) {
        addItems(comics)
        notifyItemInserted(items.size)
    }


    override fun layout(item: Any?): Int {
        return R.layout.home_item_comic
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
       return HomeComicsViewHolder(view , delegate)
    }
}