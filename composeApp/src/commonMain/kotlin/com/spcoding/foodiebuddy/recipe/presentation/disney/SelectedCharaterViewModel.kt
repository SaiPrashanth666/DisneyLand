package com.spcoding.foodiebuddy.recipe.presentation.disney

import androidx.lifecycle.ViewModel
import com.spcoding.foodiebuddy.recipe.domain.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedCharacterViewModel : ViewModel() {

    private val _selectedCharacter = MutableStateFlow<Character?>(null)
    val SelectedCharacter = _selectedCharacter.asStateFlow()

    fun onSelectCharacter(character : Character?) {
        _selectedCharacter.value = character
    }
}