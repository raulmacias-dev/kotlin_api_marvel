package com.raul.macias.wikiheroes.view.adapter

import android.view.View
import com.raul.macias.wikiheroes.R
import com.raul.macias.wikiheroes.models.Character
import com.raul.macias.wikiheroes.models.Detail
import com.raul.macias.wikiheroes.models.SearchObjectItem
import com.raul.macias.wikiheroes.view.viewholder.*

class SearchAdapter ( val delegateComic : SearchObjectComicViewHolder.Delegate ,
                      val delegateCharacter : SearchObjectCharacterViewHolder.Delegate,
                      val delegateSeries : SearchObjectSeriesViewHolder.Delegate,
                      val delegateCreator : SearchObjectCreatorViewHolder.Delegate) : BaseAdapter() {


    init {
        addItems(ArrayList<SearchObjectItem>())
    }

    fun createList( newItems : List<SearchObjectItem>) {
        clearItems()
        addItems(newItems)
        notifyDataSetChanged()
    }

    fun addNewItems(newItems : List<SearchObjectItem>) {
        addItems(newItems)
        notifyItemInserted(items.size)
    }


    override fun layout(item: Any?): Int {
        if( item is Character) {
            return R.layout.item_character
        }

        if(item is Detail && item.getType() == SearchObjectItem.TYPE_COMIC) {
            return R.layout.home_item_comic
        }

        if(item is Detail && item.getType() == SearchObjectItem.TYPE_SERIES) {
            return R.layout.item_series
        }

        if(item is Detail && item.getType() == SearchObjectItem.TYPE_PERSON) {
            return R.layout.item_creator
        }

        return R.layout.home_item_comic
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return when (layout) {
            R.layout.item_character -> SearchObjectCharacterViewHolder(view , delegateCharacter)
            R.layout.home_item_comic -> SearchObjectComicViewHolder(view, delegateComic)
            R.layout.item_creator -> SearchObjectCreatorViewHolder(view , delegateCreator)
            else -> SearchObjectSeriesViewHolder(view, delegateSeries)
        }
    }
}