package com.spcoding.foodiebuddy.recipe.presentation.disney.character_list

import com.spcoding.foodiebuddy.core.presentation.UiText
import com.spcoding.foodiebuddy.recipe.domain.Character

data class CharacterListState(
    val searchQuery : String = "",
    val characters : List<Character> = emptyList(),
    val favoriteCharacters : List<Character> = emptyList(),
    val isLoading : Boolean = true,
    val errorMessage : UiText? = null,
    val selectedTabIndex : Int = 0
    )