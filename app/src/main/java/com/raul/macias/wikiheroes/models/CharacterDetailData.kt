package com.raul.macias.wikiheroes.models

data class CharacterDetailData(val offset: Int, val limit: Int, val total: Int, val count: Int,
                               var results: MutableList<Detail>)