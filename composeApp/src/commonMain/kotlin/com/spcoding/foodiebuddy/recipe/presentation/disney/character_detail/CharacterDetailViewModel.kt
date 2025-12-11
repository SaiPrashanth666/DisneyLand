package com.spcoding.foodiebuddy.recipe.presentation.disney.character_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spcoding.foodiebuddy.app.Route
import com.spcoding.foodiebuddy.recipe.domain.disney.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val characterRepository: CharacterRepository,
    private val savedStateHandle : SavedStateHandle
) : ViewModel() {

    private val characterId = savedStateHandle.toRoute<Route.CharacterDetail>().id
    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CharacterDetailState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
            observeFavoriteStatus()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CharacterDetailState()
        )

    fun onAction(action: CharacterDetailAction) {
        when (action) {
            is CharacterDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if(state.value.isFavorite){
                        characterRepository.deleteFromFavorite(characterId)
                    }else{
                        state.value.character?.let {
                            characterRepository.markAsFavorite(it)
                        }
                    }
                }
            }
            is CharacterDetailAction.OnSelectedCharacterChange -> {
                _state.update {
                    it.copy(
                        character = action.character
                    )
                }
            }
            else -> Unit
        }
    }

    private fun observeFavoriteStatus() {
        characterRepository
            .isCharacterFavorite(characterId)
            .onEach { isFavorite ->
                _state.update {
                    it.copy(
                        isFavorite = isFavorite
                    )
                }
            }
            .launchIn(viewModelScope)
    }

}