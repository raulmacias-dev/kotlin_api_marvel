package com.raul.macias.wikiheroes.view.ui.person.comics

import androidx.lifecycle.ViewModel
import com.raul.macias.wikiheroes.models.Item
import com.raul.macias.wikiheroes.repository.ComicsRepository
import com.raul.macias.wikiheroes.repository.SeriesRepository
import com.raul.macias.wikiheroes.utils.Utils
import com.raul.macias.wikiheroes.view.adapter.HomeComicsAdapter
import javax.inject.Inject

class CreatorFragmentsViewModel @Inject
constructor(private val comicsRepository: ComicsRepository, private val seriesRepository: SeriesRepository) : ViewModel() {

    lateinit var creator : Item

    lateinit var adapter : HomeComicsAdapter

    fun getComicsByCreatorId( id : String) = comicsRepository.getComicsByCreatorId(id)

    fun getSeriesByCreatorId( id : String) = seriesRepository.getSeriesByCreatorId(id)

    fun getCreatorId( creator : Item) : String {
        return Utils.getIdByResourceURI(creator.resourceURI)
    }
}