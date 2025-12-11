package com.spcoding.foodiebuddy.recipe.presentation.products.product_list

import com.spcoding.foodiebuddy.recipe.domain.Product

interface ProductsListAction {
    data class onSearchQueryChanged(val query : String) : ProductsListAction
    data class onProductClicked(val product : Product) : ProductsListAction
    data class onSelectedTabChanged(val index : Int) : ProductsListAction
}
