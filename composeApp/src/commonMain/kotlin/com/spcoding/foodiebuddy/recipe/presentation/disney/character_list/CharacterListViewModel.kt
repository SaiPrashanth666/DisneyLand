package com.spcoding.foodiebuddy.recipe.presentation.disney.character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spcoding.foodiebuddy.core.domain.onError
import com.spcoding.foodiebuddy.core.domain.onSuccess
import com.spcoding.foodiebuddy.core.presentation.toUiText
import com.spcoding.foodiebuddy.recipe.domain.Character
import com.spcoding.foodiebuddy.recipe.domain.disney.CharacterRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private var cachedCharacters = emptyList<Character>()

    private var searchJob : Job? = null
    private var favoriteCharactersJob : Job? = null

    private val _state = MutableStateFlow(CharacterListState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                if(cachedCharacters.isEmpty()){
                    observeSearchQuery()
                }
                hasLoadedInitialData = true
            }
            observeFavoriteCharacters()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CharacterListState()
        )

    fun onAction(action: CharacterListAction) {
        when (action) {
            is CharacterListAction.OnSearchQueryChanged -> {
                _state.update {
                    it.copy(
                        searchQuery = action.query
                    )
                }
            }
            is CharacterListAction.OnSelectedTabChanged -> {
                _state.update {
                    it.copy(
                        selectedTabIndex = action.index
                    )
                }
            }
            else -> Unit
        }
    }

    fun observeSearchQuery(){
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when{
                    query.isBlank() -> {
                        if(cachedCharacters.isEmpty()){
                            searchDisneyCharacters()
                            cachedCharacters = state.value.characters
                        }else{
                            _state.update {
                                it.copy(
                                    characters = cachedCharacters,
                                    errorMessage = null
                                )
                            }
                        }
                    }
                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchDisneyCharacters(
                            query = query
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun searchDisneyCharacters(query : String? = null, pageNumber : Int? = null) = viewModelScope.launch{
            _state.update { it.copy( isLoading = true) }
            characterRepository
                .searchCharacters(
                    query = query,
                    pageNumber = pageNumber
                )
                .onSuccess { initialSearchResults ->
                    _state.update {
                        it.copy(
                            characters = initialSearchResults,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            errorMessage = error.toUiText(),
                            isLoading = false,
                            characters = emptyList()
                        )
                    }
                }
        }

    private fun observeFavoriteCharacters() {
        favoriteCharactersJob?.cancel()
        favoriteCharactersJob = characterRepository
            .getFavoriteCharacters()
            .onEach { favoriteCharacters ->
                _state.update {
                    it.copy(
                        favoriteCharacters = favoriteCharacters
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}