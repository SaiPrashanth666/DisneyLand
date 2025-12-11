package com.spcoding.foodiebuddy.recipe.domain.disney

import com.spcoding.foodiebuddy.core.domain.DataError
import com.spcoding.foodiebuddy.core.domain.Result
import com.spcoding.foodiebuddy.recipe.domain.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun searchCharacters(
        query : String? = null,
        pageNumber : Int? = null
    ) : Result<List<Character>, DataError.Remote>

    fun getFavoriteCharacters() : Flow<List<Character>>

    suspend fun markAsFavorite(character : Character)

    suspend fun deleteFromFavorite(id : Int)

    fun isCharacterFavorite(id : Int) : Flow<Boolean>
}