package com.raul.macias.wikiheroes.repository

import androidx.lifecycle.LiveData
import com.raul.macias.wikiheroes.BuildConfig
import com.raul.macias.wikiheroes.api.ApiResponse
import com.raul.macias.wikiheroes.api.MarvelApi
import com.raul.macias.wikiheroes.api.Resource
import com.raul.macias.wikiheroes.models.Character
import com.raul.macias.wikiheroes.models.CharacterResponse
import com.raul.macias.wikiheroes.models.DetailResponse
import com.raul.macias.wikiheroes.models.FavCharacter
import com.raul.macias.wikiheroes.room.CharacterDao
import com.raul.macias.wikiheroes.room.FavCharacterDao
import com.raul.macias.wikiheroes.utils.PreferenceManager
import com.raul.macias.wikiheroes.utils.Utils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class CharactersRepository @Inject
constructor(val characterDao: CharacterDao, val favCharacterDao: FavCharacterDao, val marvelApi: MarvelApi , val preferenceManager: PreferenceManager) {

    val defaultLimit = 10

    var offset = 0

    val timestamp = Date().time

    val hash = Utils.md5(timestamp.toString() + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_API_KEY)


    fun getCharacters(page: Int): LiveData<Resource<List<Character>>> {

        return object : NetworkBoundResource<List<Character>, CharacterResponse>() {

            override fun saveFetchData(item: CharacterResponse) {

                offset += defaultLimit
                val newCharacters = item.data.results

                newCharacters.forEach { character -> character.page = page }

                characterDao.insertCharacters(newCharacters)
            }

            override fun shouldFetch(data: List<Character>?): Boolean {
                if(data != null && data.isNotEmpty()) {
                    offset = data.size
                }
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Character>> {
                return if(page == 0) {
                    characterDao.getCharacters()
                } else  {
                    characterDao.getCharacters(page)
                }

            }

            override fun fetchService(): LiveData<ApiResponse<CharacterResponse>> {
                return marvelApi.getCharacters("-modified", timestamp.toString(), BuildConfig.MARVEL_API_KEY, hash, offset, defaultLimit)
            }

            override fun onFetchFailed() {

            }

        }.asLiveData

    }

    fun getStoriesByCharacterId(id : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getStoriesByCharacterId(id.toString(), BuildConfig.MARVEL_API_KEY, hash, timestamp.toString())
    }

    fun getEventsByCharacterId(id : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getEventsByCharacterId(id.toString(), BuildConfig.MARVEL_API_KEY, hash, timestamp.toString())
    }

    fun checkSyncCharacters() {

        val todayDate = Utils.getCurrentDate()
        val lastSynchDate = preferenceManager.getString(PreferenceManager.LAST_DATE_SYNC, "")

        // refresh comics every 5 days
        if (lastSynchDate != null && lastSynchDate != "" && Utils.getDifferenceBetweenDates(lastSynchDate, todayDate) == 2L) {
            thread {
                characterDao.deleteCharacters()
            }

        }
    }

    fun getFavCharacters() : LiveData<List<FavCharacter>> {
        return favCharacterDao.getFavCharacters()
    }

    fun addFavCharacter( character: Character) {
        thread {
            val favCharacter = FavCharacter( character.id , character)
            favCharacterDao.insertFavCharacter(favCharacter)
        }
    }

    fun removeFavCharacter( character: Character) {
        thread {
            favCharacterDao.removeFavCharacter(character.id)
        }
    }

    fun getFavCharacterById( id : Int) : LiveData<FavCharacter> {
        return favCharacterDao.getFavCharacterById(id)
    }


    fun searchCharacterByName( nameStartsWith : String) : LiveData<ApiResponse<CharacterResponse>> {
        return marvelApi.searchCharacterNameStartsWith(nameStartsWith , BuildConfig.MARVEL_API_KEY, hash, timestamp.toString() , "name", offset, defaultLimit)
    }
}
