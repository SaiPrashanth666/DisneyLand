package com.spcoding.foodiebuddy.recipe.data.dto.disney

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    @SerialName("_id") val id : Int,
    val name : String,
    val films : List<String>,
    val shortFilms : List<String>,
    val tvShows : List<String>,
    val videoGames : List<String>,
    val imageUrl : String? = null
)