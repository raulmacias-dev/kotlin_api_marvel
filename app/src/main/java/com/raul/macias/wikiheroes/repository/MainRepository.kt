package com.raul.macias.wikiheroes.repository

import com.raul.macias.wikiheroes.utils.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject
constructor(private val preferenceManager: PreferenceManager) {

    fun saveSaveDominantColor(color : Int) {
        preferenceManager.setInt(PreferenceManager.DOMINANT_COLOR, color)
    }

    fun getDominantColor() : Int = preferenceManager.getInt(PreferenceManager.DOMINANT_COLOR, 0)
    fun getStandardColor() : Int = -13094864
}