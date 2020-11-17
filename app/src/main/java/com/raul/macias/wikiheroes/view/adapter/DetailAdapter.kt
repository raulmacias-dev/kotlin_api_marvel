package com.raul.macias.wikiheroes.view.adapter

import android.view.View
import com.raul.macias.wikiheroes.R
import com.raul.macias.wikiheroes.models.Detail
import com.raul.macias.wikiheroes.view.viewholder.BaseViewHolder
import com.raul.macias.wikiheroes.view.viewholder.DetailViewHolder

class DetailAdapter(val delegate : DetailViewHolder.Delegate) : BaseAdapter(){

    init {
        addItems(ArrayList<Detail>())
    }

    fun updateList( newItems : List<Detail>) {
        addItems(newItems)
        notifyItemInserted(items.size)
    }

    /*fun dispatch(newList: List<Detail>) {
        val userDiffCallback = Utils.DetailsDiffCallback(newList, items as List<Detail>)
        val diffResult = DiffUtil.calculateDiff(userDiffCallback)
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }*/


    override fun layout(item: Any?): Int {
        return R.layout.item_small_image
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return DetailViewHolder(view , delegate)
    }
}