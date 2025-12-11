package com.spcoding.foodiebuddy.recipe.presentation.products.product_list

import com.spcoding.foodiebuddy.recipe.domain.Product

data class ProductListState(
    val products : List<Product> = emptyList(),
    val searchQuery : String = "",
    val selectedTabIndex : Int = 0,
    val isLoading: Boolean = false,
    val errorMessage : String? = null
)


