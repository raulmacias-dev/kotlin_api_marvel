package com.raul.macias.wikiheroes.view.adapter

import android.view.View
import com.raul.macias.wikiheroes.R
import com.raul.macias.wikiheroes.models.Item
import com.raul.macias.wikiheroes.view.viewholder.BaseViewHolder
import com.raul.macias.wikiheroes.view.viewholder.CreatorViewHolder

class CreatorsAdapter (val delegate : CreatorViewHolder.Delegate, private val creators : List<Item>) : BaseAdapter() {

    init {
        addItems(creators)
    }


    override fun layout(item: Any?): Int {
        return R.layout.creator_item
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return CreatorViewHolder(view, delegate, creators)
    }
}