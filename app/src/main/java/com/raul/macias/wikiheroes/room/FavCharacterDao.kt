package com.raul.macias.wikiheroes.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raul.macias.wikiheroes.models.FavCharacter

@Dao
interface FavCharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavCharacter( favCharacter: FavCharacter)

    @Query("DELETE FROM FavCharacter WHERE FavCharacter.characterId = :id")
    fun removeFavCharacter( id : Int)

    @Query("SELECT * FROM FavCharacter")
    fun getFavCharacters() : LiveData<List<FavCharacter>>


    @Query("SELECT * FROM FavCharacter WHERE FavCharacter.characterId = :id")
    fun getFavCharacterById( id : Int) : LiveData<FavCharacter>
}