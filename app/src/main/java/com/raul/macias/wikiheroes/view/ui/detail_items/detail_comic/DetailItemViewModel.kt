package com.raul.macias.wikiheroes.view.ui.detail_items.detail_comic

import androidx.lifecycle.ViewModel
import com.raul.macias.wikiheroes.models.Detail
import com.raul.macias.wikiheroes.repository.ComicsRepository
import com.raul.macias.wikiheroes.repository.SeriesRepository
import javax.inject.Inject

class DetailItemViewModel @Inject
constructor( private val comicsRepository: ComicsRepository , private val seriesRepository: SeriesRepository) : ViewModel() {

    lateinit var item : Detail

    lateinit var section : String

    fun addToShop() = comicsRepository.addToShop(item)

    fun removeFromShop() = comicsRepository.removeFromShop(item)

    fun getItemFromShop() = comicsRepository.getItemFromShop(item)

    //get series details by id
    fun getSeriesDetailById(seriesId : Int) = seriesRepository.getSeriesDetailById(seriesId.toString())
}