package com.spcoding.foodiebuddy.recipe.data.network

import coil3.decode.DataSource
import com.spcoding.foodiebuddy.core.domain.DataError
import com.spcoding.foodiebuddy.core.domain.Result
import com.spcoding.foodiebuddy.recipe.data.dto.disney.SearchResponseDto

interface RemoteCharacterDataSource {
    suspend fun searchCharacters(
        query : String? = null,
        pageNumber : Int? = null
    ) : Result<SearchResponseDto, DataError.Remote>
}