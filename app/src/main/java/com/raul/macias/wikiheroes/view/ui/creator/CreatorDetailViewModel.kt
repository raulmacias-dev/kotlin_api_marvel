package com.raul.macias.wikiheroes.view.ui.creator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.raul.macias.wikiheroes.api.ApiResponse
import com.raul.macias.wikiheroes.models.DetailResponse
import com.raul.macias.wikiheroes.models.Item
import com.raul.macias.wikiheroes.repository.ComicsRepository
import com.raul.macias.wikiheroes.repository.SeriesRepository
import com.raul.macias.wikiheroes.utils.Utils
import com.raul.macias.wikiheroes.view.adapter.DetailAdapter
import javax.inject.Inject

class CreatorDetailViewModel @Inject
constructor(private val comicsRepository: ComicsRepository, private val seriesRepository: SeriesRepository) : ViewModel() {

    lateinit var creator : Item

    lateinit var type : String

    private val defaultLimit = 20

    var offset = 0

    var firstTime = false

    lateinit var adapter : DetailAdapter

    var pageCounter = 0

    var itemsLiveData: LiveData<ApiResponse<DetailResponse>> = MutableLiveData()

    private val page: MutableLiveData<Int> = MutableLiveData()


    init {
        itemsLiveData = Transformations.switchMap(page) { getItemsByCreatorId(creator) }
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



    private fun getItemsByCreatorId(item : Item) : LiveData<ApiResponse<DetailResponse>> {

        val id = Utils.getIdByResourceURI(item.resourceURI)

        when (type) {
            "Comics" -> {
                return comicsRepository.getComicsByCreatorId(id, offset)
            }
            "Series" -> {
                return seriesRepository.getSeriesByCreatorId(id, offset)
            }

            "Events" -> {
                return comicsRepository.getEventsByCreatorId(id, offset)
            }

        }

        return MutableLiveData()
    }
}