package com.spcoding.foodiebuddy.recipe.domain

data class Recipe(
    val id : Int,
    val name : String,
    val mealType : MealType,
    val region : String,
    val instructions : String,
    val tutorial : String? = null,
    val imageUrl : String,
    val isFavorite : Boolean = false,
    val ingredients : List<String> = emptyList(),
    val correspondingQuantity : List<String> = emptyList()
)


enum class MealType {
    VEG, NON_VEG, CONTAINS_EGG
}