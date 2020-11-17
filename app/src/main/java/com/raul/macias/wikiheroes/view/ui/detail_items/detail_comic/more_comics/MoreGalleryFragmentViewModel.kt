package com.raul.macias.wikiheroes.view.ui.detail_items.detail_comic.more_comics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raul.macias.wikiheroes.api.ApiResponse
import com.raul.macias.wikiheroes.models.DetailResponse
import com.raul.macias.wikiheroes.repository.ComicsRepository
import com.raul.macias.wikiheroes.repository.SeriesRepository
import com.raul.macias.wikiheroes.utils.Utils
import javax.inject.Inject

class MoreGalleryFragmentViewModel@Inject
constructor(private val comicsRepository: ComicsRepository, private val seriesRepository: SeriesRepository) : ViewModel() {

    lateinit var resourceURI : String

    lateinit var id : String  //id of the resourceURI

    lateinit var seriesId : String

    lateinit var section : String

    var seriesThumbnail : String = ""

    var seriesTitle : String = ""


    fun getItems(resourceUri : String, type: String) : LiveData<ApiResponse<DetailResponse>> {

        seriesId = Utils.getIdByResourceURI(resourceUri)

        when(type) {
            "Series" -> {
                return comicsRepository.getComicsBySeriesId(id!!)
            }

            "Comics" -> {
                //get the comics of the same collection
                return comicsRepository.getComicsBySeriesId(seriesId)
            }
        }

        return MutableLiveData()
    }


    fun getSeriesDetails() : LiveData<ApiResponse<DetailResponse>> = seriesRepository.getSeriesBySeriesId(seriesId)
}