package com.spcoding.foodiebuddy.recipe.presentation.products.product_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spcoding.foodiebuddy.core.presentation.theme.onSurfaceDark
import com.spcoding.foodiebuddy.recipe.presentation.products.product_list.components.ProductList
import com.spcoding.foodiebuddy.recipe.presentation.products.product_list.components.ProductLogoTitle
import com.spcoding.foodiebuddy.recipe.presentation.products.product_list.components.ProductSearchBar
import foodiebuddy.composeapp.generated.resources.Res
import foodiebuddy.composeapp.generated.resources.tab_favorite
import foodiebuddy.composeapp.generated.resources.tab_products
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProductListScreenRoot(
    viewModel : ProductListViewModel,
    modifier : Modifier
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProductListScreen(
        state = state,
        modifier = modifier,
        onAction = { action ->
            when(action){
                is ProductsListAction.onProductClicked -> {

                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun ProductListScreen(
    state : ProductListState,
    onAction : (ProductsListAction) -> Unit,
    modifier: Modifier
) {
    val localKeyboardController = LocalSoftwareKeyboardController.current
    val pagerState = rememberPagerState { 2 }
    val productsListState = rememberLazyListState()

    LaunchedEffect(state.products){
        productsListState.scrollToItem(0)
    }

    LaunchedEffect(state.selectedTabIndex){
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage){
        onAction(ProductsListAction.onSelectedTabChanged(pagerState.currentPage))
    }

    Column(
        modifier = modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProductLogoTitle()

        ProductSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(ProductsListAction.onSearchQueryChanged(it))
            },
            onImeSearch = {
                localKeyboardController?.hide()
            },
            modifier = modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        PrimaryTabRow(
            selectedTabIndex = state.selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier.widthIn(max = 700.dp)
                .fillMaxWidth(),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ){
            Tab(
                selected = state.selectedTabIndex == 0,
                onClick = {
                    onAction(ProductsListAction.onSelectedTabChanged(0))
                },
                modifier = Modifier.weight(1f),
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ){
                Text(
                    text = stringResource(Res.string.tab_products),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Tab(
                selected = state.selectedTabIndex == 1,
                onClick = {
                    onAction(ProductsListAction.onSelectedTabChanged(1))
                },
                modifier = Modifier.weight(1f),
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ){
                Text(
                    text = stringResource(Res.string.tab_favorite),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){ pageIndex ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                when(pageIndex) {
                    0 -> {
                        if(state.isLoading){
                            CircularProgressIndicator()
                        }
                        else{
                            when {
                                state.errorMessage != null -> {
                                    Text(
                                        text = state.errorMessage,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                                else -> {
                                    ProductList(
                                        products = state.products,
                                        onProductClick = { product ->
                                            onAction(ProductsListAction.onProductClicked(product))
                                        },
                                        modifier = Modifier.fillMaxSize(),
                                        scrollState = productsListState
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}