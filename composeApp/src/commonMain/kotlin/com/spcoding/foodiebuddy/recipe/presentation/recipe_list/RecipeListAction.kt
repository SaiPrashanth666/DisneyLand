package com.spcoding.foodiebuddy.recipe.presentation.recipe_list

interface RecipeListAction {
    data class OnSearchQueryChange(val searchQuery : String) : RecipeListAction
    data class OnTabSelected(val tabIndex : Int) : RecipeListAction
    data class OnRecipeClicked(val id : Int) : RecipeListAction
}