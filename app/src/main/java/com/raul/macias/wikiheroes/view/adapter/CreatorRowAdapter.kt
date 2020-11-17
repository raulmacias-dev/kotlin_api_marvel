package com.raul.macias.wikiheroes.view.adapter

import android.view.View
import com.raul.macias.wikiheroes.R
import com.raul.macias.wikiheroes.models.Item
import com.raul.macias.wikiheroes.view.viewholder.BaseViewHolder
import com.raul.macias.wikiheroes.view.viewholder.CreatorRowViewHolder
import com.raul.macias.wikiheroes.view.viewholder.CreatorViewHolder

class CreatorRowAdapter (val delegate : CreatorViewHolder.Delegate ,val  map : List<Pair<String? , List<Item>>>) : BaseAdapter() {

    init {
        addItems(map)
    }


    override fun layout(item: Any?): Int {
        return R.layout.creator_row
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return CreatorRowViewHolder(view , delegate)
    }


}