package com.raul.macias.wikiheroes.view.ui.home

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.raul.macias.wikiheroes.models.Character

class ItemCharacterViewModel(private val character: Character) : BaseObservable() {

    companion object {
        val IMAGE_TYPE = "."
    }

    var imageUrl = modelImageUrl()

    fun modelImageUrl(): String = character.thumbnail.path + IMAGE_TYPE + character.thumbnail.extension

    @Bindable
    fun getCharacterName(): String = character.name
}