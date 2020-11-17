package com.raul.macias.wikiheroes.view.adapter

import android.view.View
import com.raul.macias.wikiheroes.R
import com.raul.macias.wikiheroes.models.Detail
import com.raul.macias.wikiheroes.view.viewholder.BaseViewHolder
import com.raul.macias.wikiheroes.view.viewholder.DeskComicViewHolder

class DeskComicsAdapter (val delegate : DeskComicViewHolder.Delegate, private val thisWeekDate : String?) : BaseAdapter() {

    init {
        addItems(ArrayList<Detail>())
    }

    fun updateList( newItems : List<Detail>) {
        clearItems()
        addItems(newItems)
        notifyDataSetChanged()
    }


    override fun layout(item: Any?): Int {
        return R.layout.desk_comic_row
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return if(thisWeekDate == null) {
            DeskComicViewHolder( view, delegate, "")
        } else {
            DeskComicViewHolder( view, delegate, thisWeekDate)
        }
    }
}