package com.raul.macias.wikiheroes.view.adapter

import android.view.View
import com.raul.macias.wikiheroes.R
import com.raul.macias.wikiheroes.models.Character
import com.raul.macias.wikiheroes.view.viewholder.BaseViewHolder
import com.raul.macias.wikiheroes.view.viewholder.FavCharacterViewHolder

class FavCharacterAdapter(val delegate: FavCharacterViewHolder.Delegate) : BaseAdapter() {

    init {
        addItems(ArrayList<Character>())
    }

    fun updateList(characters: List<Character>) {
        clearItems()
        addItems(characters)
        notifyDataSetChanged()
    }

    override fun layout(item: Any?): Int {
        return R.layout.fav_character_row
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return FavCharacterViewHolder(view, delegate)
    }
}