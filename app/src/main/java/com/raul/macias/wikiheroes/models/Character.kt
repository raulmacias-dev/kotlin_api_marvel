package com.raul.macias.wikiheroes.models

import androidx.room.Embedded
import androidx.room.Entity
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Entity(primaryKeys = ["id"])
@Parcelize
data class Character(val id: Int, val name: String,
                     val description: String,
                     val modified: String,
                     @Embedded(prefix = "thumbnail_")
                     val thumbnail: Thumbnail,
                     val resourceURI: String?,
                     @Embedded(prefix = "comics_")
                     val comics: CollectionItem,
                     @Embedded(prefix = "series_")
                     val series: CollectionItem,
                     @Embedded(prefix = "stories_")
                     val stories: CollectionItem,
                     @Embedded(prefix = "events_")
                     val events: CollectionItem,
                     val urls: MutableList<ItemUrl>,
                     var page : Int) : Parcelable, SearchObjectItem()  {
    override fun getType(): Int {
        return SearchObjectItem.TYPE_CHARACTER
    }

}