package com.spcoding.foodiebuddy.recipe.presentation.disney.character_detail

import com.spcoding.foodiebuddy.recipe.domain.Character

sealed interface CharacterDetailAction {
    data object OnBackClick : CharacterDetailAction
    data object OnFavoriteClick : CharacterDetailAction
    data class OnSelectedCharacterChange(val character : Character) : CharacterDetailAction
}