package com.spcoding.foodiebuddy.recipe.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Upsert
    suspend fun updateCharacter(character : CharacterEntity)

    @Query("SELECT * FROM CharacterEntity")
    fun getFavoriteCharacters() : Flow<List<CharacterEntity>>

    @Query("SELECT * FROM CharacterEntity WHERE id = :id")
    suspend fun getFavoriteCharacter(id : Int) : CharacterEntity?

    @Query("DELETE FROM CharacterEntity WHERE id = :id")
    suspend fun deleteFromFavorite(id : Int)
}