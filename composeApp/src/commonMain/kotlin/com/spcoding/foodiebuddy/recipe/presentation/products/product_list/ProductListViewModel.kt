package com.spcoding.foodiebuddy.recipe.presentation.products.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spcoding.foodiebuddy.recipe.domain.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel()
{
    private val _state = MutableStateFlow(ProductListState())
    val state = _state
        .onStart {
            loadProducts()
        }
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value)

    fun onAction(action : ProductsListAction){
        when(action) {
            is ProductsListAction.onSearchQueryChanged -> {
                _state.value = _state.value.copy(
                    searchQuery = action.query
                )
            }
            is ProductsListAction.onSelectedTabChanged -> {
                _state.update { it.copy(
                    selectedTabIndex = action.index
                    )
                }
            }
        }
    }

    private fun loadProducts(){
        val products = (1..10).map {
            Product(
                id = it,
                title = "Product $it",
                imageUrl = listOf(
                    "https://i.dummyjson.com/data/products/61/1.jpg",
                    "https://i.dummyjson.com/data/products/61/2.png",
                    "https://i.dummyjson.com/data/products/61/3.jpg"
                ),
                category = "mens-watches"
            )
        }
        viewModelScope.launch {
            _state.update {
                it.copy(
                    products = products
                )
            }
        }
    }
}
