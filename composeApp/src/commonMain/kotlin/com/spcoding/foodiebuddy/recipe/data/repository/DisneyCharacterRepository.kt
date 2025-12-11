package com.spcoding.foodiebuddy.recipe.data.repository

import com.spcoding.foodiebuddy.core.domain.DataError
import com.spcoding.foodiebuddy.core.domain.Result
import com.spcoding.foodiebuddy.core.domain.map
import com.spcoding.foodiebuddy.recipe.data.database.CharacterDao
import com.spcoding.foodiebuddy.recipe.data.mappers.toCharacter
import com.spcoding.foodiebuddy.recipe.data.mappers.toCharacterEntity
import com.spcoding.foodiebuddy.recipe.data.network.RemoteCharacterDataSource
import com.spcoding.foodiebuddy.recipe.domain.Character
import com.spcoding.foodiebuddy.recipe.domain.disney.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class DisneyCharacterRepository(
    private val remoteCharacterDataSource: RemoteCharacterDataSource,
    private val favoriteCharacterDao: CharacterDao
) : CharacterRepository {

    override suspend fun searchCharacters(
        query: String?,
        pageNumber: Int?
    ): Result<List<Character>, DataError.Remote> {
        return remoteCharacterDataSource.searchCharacters(
            query = query,
            pageNumber = pageNumber
        ).map { dto ->
            dto.data.map { characterDto ->
                characterDto.toCharacter()
            }
        }
    }

    override fun getFavoriteCharacters(): Flow<List<Character>> {
        return favoriteCharacterDao.getFavoriteCharacters()
            .map { favoriteCharacters ->
                favoriteCharacters.map {
                    it.toCharacter()
                }
            }
    }

    override suspend fun markAsFavorite(character: Character) {
        favoriteCharacterDao.updateCharacter(character.toCharacterEntity())
    }

    override suspend fun deleteFromFavorite(id: Int) {
        favoriteCharacterDao.deleteFromFavorite(id)
    }

    override fun isCharacterFavorite(id: Int): Flow<Boolean> {
        return favoriteCharacterDao.getFavoriteCharacters()
            .map { favoriteCharacters ->
                favoriteCharacters.any { it.id == id }
            }
    }

}