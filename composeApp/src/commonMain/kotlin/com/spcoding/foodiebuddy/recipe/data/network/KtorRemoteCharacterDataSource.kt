package com.spcoding.foodiebuddy.recipe.data.network

import com.spcoding.foodiebuddy.core.data.safeCall
import com.spcoding.foodiebuddy.core.domain.DataError
import com.spcoding.foodiebuddy.core.domain.Result
import com.spcoding.foodiebuddy.recipe.data.dto.disney.SearchResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://api.disneyapi.dev"

class KtorRemoteCharacterDataSource(
    private val httpClient : HttpClient
) : RemoteCharacterDataSource {


    override suspend fun searchCharacters(
        query: String?,
        pageNumber: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
            httpClient.get("$BASE_URL/character")
            {
                parameter("name", query)
                parameter("page", pageNumber)
            }
        }
    }
}