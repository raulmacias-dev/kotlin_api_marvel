package com.raul.macias.wikiheroes.models

import androidx.room.Embedded
import androidx.room.Entity


@Entity(primaryKeys = ["characterId"])
data class FavCharacter( val characterId : Int ,
                         @Embedded( prefix = "character_")
                         val character: Character)