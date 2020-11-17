package com.raul.macias.wikiheroes.view.ui.home.desk

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.raul.macias.wikiheroes.models.Item

class CreatorUIViewModel(val item : Item) : BaseObservable() {

    @Bindable
    fun getImageCreator() : String {
        if(!item.image.isNullOrEmpty()) {
            return item.image!!
        }

        return ""
    }

    @Bindable
    fun getCreatorName() = item.name
}