package com.raul.macias.wikiheroes.view.ui.seeall.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.raul.macias.wikiheroes.api.ApiResponse
import com.raul.macias.wikiheroes.models.DetailResponse
import com.raul.macias.wikiheroes.repository.ComicsRepository
import com.raul.macias.wikiheroes.repository.MainRepository
import com.raul.macias.wikiheroes.repository.SeriesRepository
import com.raul.macias.wikiheroes.view.adapter.DetailAdapter
import javax.inject.Inject

class SeriesSeeAllViewModel @Inject
constructor(private val comicsRepository: ComicsRepository, private val mainRepository: MainRepository, private val seriesRepository: SeriesRepository) : ViewModel() {

    lateinit var seriesId : String

    lateinit var seriesImage : String

    lateinit var seriesTitle : String


    lateinit var adapter : DetailAdapter

    var firstTime = false

    var offset = 0

    private val defaultLimit = 10

    var pageCounter = 0

    private val page : MutableLiveData<Int> = MutableLiveData()

    var itemsLiveData: LiveData<ApiResponse<DetailResponse>> = MutableLiveData()

    init {
        itemsLiveData = Transformations.switchMap(page) { getComicsBySeriesId() }
    }

    /**
     * Use this method in case of positive response
     */
    fun increaseOffset() {
        offset += defaultLimit
    }

    /**
     * Use this method to increase the page and make a call automatically
     */
    fun postPage(page: Int) { this.page.value = page }

    fun getStandardBackgroundColor() : Int = mainRepository.getStandardColor()

    fun getSeriesDetail() : LiveData<ApiResponse<DetailResponse>> = seriesRepository.getSeriesBySeriesId(seriesId)

    fun getComicsBySeriesId() : LiveData<ApiResponse<DetailResponse>> {
        return comicsRepository.getComicsBySeriesId(seriesId, offset)
    }
}