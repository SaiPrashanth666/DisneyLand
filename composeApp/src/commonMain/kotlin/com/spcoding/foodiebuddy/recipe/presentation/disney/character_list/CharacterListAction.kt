package com.spcoding.foodiebuddy.recipe.presentation.disney.character_list

import com.spcoding.foodiebuddy.recipe.domain.Character

sealed interface CharacterListAction {
    data class OnSearchQueryChanged(val query : String) : CharacterListAction
    data class OnSelectedTabChanged(val index : Int) : CharacterListAction
    data class OnCharacterClicked(val character : Character) : CharacterListAction
}