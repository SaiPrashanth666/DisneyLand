package com.spcoding.foodiebuddy.recipe.data.dto.disney

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("data") val data : List<CharacterDto>
)
