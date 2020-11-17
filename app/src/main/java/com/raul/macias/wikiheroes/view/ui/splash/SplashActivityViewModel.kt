package com.raul.macias.wikiheroes.view.ui.splash

import androidx.lifecycle.ViewModel
import com.raul.macias.wikiheroes.repository.CharactersRepository
import com.raul.macias.wikiheroes.repository.ComicsRepository
import javax.inject.Inject

class SplashActivityViewModel @Inject
constructor(private val comicsRepository: ComicsRepository , private val charactersRepository: CharactersRepository) : ViewModel() {

    fun checkDateSyncComics() = comicsRepository.checkSyncComics()

    fun checkDateSynchCharacters() = charactersRepository.checkSyncCharacters()
}