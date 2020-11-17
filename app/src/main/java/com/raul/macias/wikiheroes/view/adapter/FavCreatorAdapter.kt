package com.raul.macias.wikiheroes.view.adapter

import android.view.View
import com.raul.macias.wikiheroes.R
import com.raul.macias.wikiheroes.models.Item
import com.raul.macias.wikiheroes.view.viewholder.BaseViewHolder
import com.raul.macias.wikiheroes.view.viewholder.FavCreatorViewHolder

class FavCreatorAdapter( val delegate : FavCreatorViewHolder.Delegate) : BaseAdapter() {


    init {
        addItems(ArrayList<Item>())
    }

    fun updateItems( newItems : List<Item>) {
        clearItems()
        addItems(newItems)
        notifyDataSetChanged()
    }

    override fun layout(item: Any?): Int {
        return R.layout.fav_creator_row
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return FavCreatorViewHolder( view , delegate)
    }
}