package com.spcoding.foodiebuddy.recipe.presentation.recipe_list

import com.spcoding.foodiebuddy.recipe.domain.MealType
import com.spcoding.foodiebuddy.recipe.domain.Recipe

data class RecipeListState(
    val recipes : List<Recipe> = emptyList(),
    val searchQuery : String = "rice",
    val selectedTabIndex : Int = 0,
    val isLoading : Boolean = true,
    val errorMessage : String? = null
)


