package com.raul.macias.wikiheroes.repository

import androidx.lifecycle.LiveData
import com.raul.macias.wikiheroes.BuildConfig
import com.raul.macias.wikiheroes.api.ApiResponse
import com.raul.macias.wikiheroes.api.MarvelApi
import com.raul.macias.wikiheroes.models.DeskItem
import com.raul.macias.wikiheroes.models.DetailResponse
import com.raul.macias.wikiheroes.room.ShopDao
import com.raul.macias.wikiheroes.utils.Utils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SeriesRepository @Inject
constructor(private val marvelApi: MarvelApi , private val shopDao: ShopDao) {

    private val defaultLimit = 20

    var offset = 0

    private val timestamp = Date().time

    private val hash = Utils.md5(timestamp.toString() + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_API_KEY)

    fun getSeriesBySeriesId( id : String) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesBySeriesId(id, BuildConfig.MARVEL_API_KEY, hash, timestamp.toString())
    }

    fun getSeriesByCreatorId( id : String, offset : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesByCreatorId(id , BuildConfig.MARVEL_API_KEY, hash, timestamp.toString(), "-startYear", offset, defaultLimit)
    }

    fun getSeriesByCreatorId( id : String) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesByCreatorId(id , BuildConfig.MARVEL_API_KEY, hash, timestamp.toString(), "-startYear", 0, defaultLimit)
    }

    fun getSeriesByCharacterId(id : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesByCharacterId(id.toString(), BuildConfig.MARVEL_API_KEY, hash, timestamp.toString())
    }

    fun getSeriesByCharacterId(id : Int, offset : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesByCharacterId(id.toString(), BuildConfig.MARVEL_API_KEY, hash, timestamp.toString(),offset, defaultLimit)
    }

    fun searchSeriesNameStartsWith( nameStartWith : String) : LiveData<ApiResponse<DetailResponse>>{
        return marvelApi.searchSeriesNameStartsWith(nameStartWith , BuildConfig.MARVEL_API_KEY, hash, timestamp.toString() , "-startYear" , offset , defaultLimit)
    }

    fun getSeriesDetailById( seriesId : String) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesDetailById(seriesId , BuildConfig.MARVEL_API_KEY, hash, timestamp.toString())
    }

    fun getSeriesFromDesk() : LiveData<List<DeskItem>> {
        return shopDao.getSeriesInDesk()
    }
}