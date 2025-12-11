package com.spcoding.foodiebuddy.recipe.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false) val id : Int,
    val name : String,
    val films : List<String>,

    val shortFilms : List<String>,

    val tvShows : List<String>,

    val videoGames : List<String>,
    val imageUrl : String?
)
