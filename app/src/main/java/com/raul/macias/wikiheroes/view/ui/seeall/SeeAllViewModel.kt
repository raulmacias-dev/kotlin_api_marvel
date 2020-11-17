package com.raul.macias.wikiheroes.view.ui.seeall

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

class SeeAllViewModel @Inject
constructor(private val comicsRepository: ComicsRepository,private val seriesRepository: SeriesRepository, private val mainRepository: MainRepository) : ViewModel() {

    lateinit var adapter : DetailAdapter

    var firstTime = false

    var offset = 0

    var pageCounter = 0

    private val page : MutableLiveData<Int> = MutableLiveData()

    var itemsLiveData: LiveData<ApiResponse<DetailResponse>> = MutableLiveData()

    lateinit var type : String //character or collection

    private val defaultLimit = 20

    var id : Int = 0

    lateinit var title : String

    lateinit var section : String

    lateinit var characterName : String

    init {
        itemsLiveData = Transformations.switchMap(page) { getItems(id) }
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


    /**
     * Use this method to get the dominant color of the current Character
     */
    fun getDominantColor() = mainRepository.getDominantColor()

    private fun getItems( id : Int) : LiveData<ApiResponse<DetailResponse>> {

        when (type) {
            "Character" -> {
                return comicsRepository.getComicsByCharacterId(id)
            }

            "Comics" -> {
                return comicsRepository.getComicsByCharacterId(id, offset)
            }

            "Series" -> {
                return seriesRepository.getSeriesByCharacterId(id, offset)
            }

        }

        return MutableLiveData()
    }
}