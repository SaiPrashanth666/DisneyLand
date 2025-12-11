package com.spcoding.foodiebuddy.recipe.domain

data class Product(
    val id : Int,
    val title : String,
    val imageUrl : List<String>,
    val category : String
)
