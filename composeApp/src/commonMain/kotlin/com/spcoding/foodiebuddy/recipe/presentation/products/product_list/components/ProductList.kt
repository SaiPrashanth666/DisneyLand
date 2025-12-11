package com.spcoding.foodiebuddy.recipe.presentation.products.product_list.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spcoding.foodiebuddy.core.presentation.theme.FoodieTheme
import com.spcoding.foodiebuddy.recipe.domain.Product
import com.spcoding.foodiebuddy.recipe.presentation.products.product_list.ProductsListAction
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductList(
    products : List<Product>,
    onProductClick : (Product) -> Unit,
    modifier: Modifier,
    scrollState : LazyListState
){
    LazyColumn(
        state = scrollState,
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        items(
            items = products,
            key = {
                it.id
            }
        ){
            ProductListItem(
                product = it,
                onClick = {
                    onProductClick(it)
                },
                modifier = Modifier.fillParentMaxWidth()
            )
        }
    }
}

private val products = (1..10).map {
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

//@Preview
//@Composable
//fun ProductsListPreview(){
//    FoodieTheme {
//        ProductList(
//            products,
//            modifier = Modifier,
//            scrollState = rememberLazyListState()
//        )
//    }
//}