package com.spcoding.foodiebuddy.recipe.presentation.disney.character_detail

import com.spcoding.foodiebuddy.recipe.domain.Character

data class CharacterDetailState(
    val isLoading : Boolean = true,
    val isFavorite : Boolean = false,
    val character : Character? = null
)