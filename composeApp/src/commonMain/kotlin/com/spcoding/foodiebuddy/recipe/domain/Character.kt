package com.spcoding.foodiebuddy.recipe.domain

data class Character(
    val id : Int,
    val name : String,
    val films : List<String>,
    val shortFilms : List<String>,
    val tvShows : List<String>,
    val videoGames : List<String>,
    val imageUrl : String?
)
