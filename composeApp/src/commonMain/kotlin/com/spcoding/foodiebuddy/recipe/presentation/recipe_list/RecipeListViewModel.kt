package com.spcoding.foodiebuddy.recipe.presentation.recipe_list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spcoding.foodiebuddy.recipe.domain.MealType
import com.spcoding.foodiebuddy.recipe.domain.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeListViewModel : ViewModel(){

    private val _state = MutableStateFlow(RecipeListState())
    val state = _state
        .onStart {
            loadRecipes()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )


    private fun loadRecipes() {
        viewModelScope.launch {
            _state.update{
                it.copy(
                    recipes = recipes,
                    isLoading = false
                )
            }
        }
    }

    fun onAction(action : RecipeListAction){
        when(action){
            is RecipeListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(
                        searchQuery = action.searchQuery
                    )
                }
                loadRecipes()
            }
            is RecipeListAction.OnTabSelected -> {
                _state.update {
                    it.copy(
                        selectedTabIndex = action.tabIndex
                    )
                }
            }
        }
    }

    val recipes = (1..15).map {
        Recipe(
            id = it,
            name = "Recipe $it",
            region = "Region $it",
            mealType = MealType.VEG,
            isFavorite = it%2==1,
            instructions = "Instructions $it",
            imageUrl = "https://www.themealdb.com/images/media/meals/wuxrtu1483564410.jpg",
        )
    }
}