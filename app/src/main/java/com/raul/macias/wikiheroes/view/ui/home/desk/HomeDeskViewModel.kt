package com.raul.macias.wikiheroes.view.ui.home.desk

import androidx.lifecycle.ViewModel
import com.raul.macias.wikiheroes.models.Item
import com.raul.macias.wikiheroes.repository.CharactersRepository
import com.raul.macias.wikiheroes.repository.ComicsRepository
import com.raul.macias.wikiheroes.repository.CreatorsRepository
import com.raul.macias.wikiheroes.repository.SeriesRepository
import com.raul.macias.wikiheroes.utils.Utils
import com.raul.macias.wikiheroes.view.adapter.FavCharacterAdapter
import com.raul.macias.wikiheroes.view.adapter.DeskComicsAdapter
import com.raul.macias.wikiheroes.view.adapter.DeskSeriesAdapter
import com.raul.macias.wikiheroes.view.adapter.FavCreatorAdapter
import javax.inject.Inject

class HomeDeskViewModel @Inject
constructor(private val comicsRepository: ComicsRepository,
            private val charactersRepository: CharactersRepository ,
            private val seriesRepository: SeriesRepository,
            private val creatorsRepository: CreatorsRepository) : ViewModel() {

    lateinit var adapter : DeskComicsAdapter

    lateinit var adapterSeries : DeskSeriesAdapter

    lateinit var adapterCharacters : FavCharacterAdapter

    lateinit var adapterPeople : FavCreatorAdapter

    fun getComicsFromShop() = comicsRepository.getItemsFromShop()

    fun getFavCharacters() = charactersRepository.getFavCharacters()

    fun getSeriesFromDesk() = seriesRepository.getSeriesFromDesk()

    fun getPeopleFromDesk() = creatorsRepository.getItems()

    fun getThisWeekDate() = comicsRepository.getThisWeekDate()

    fun getCreatorDetail( creator: Item) = creatorsRepository.getCreatorDetailById(Utils.getIdByResourceURI(creator.resourceURI))
}