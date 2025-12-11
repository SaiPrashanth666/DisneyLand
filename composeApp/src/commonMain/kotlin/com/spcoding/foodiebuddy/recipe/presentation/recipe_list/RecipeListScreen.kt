package com.spcoding.foodiebuddy.recipe.presentation.recipe_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spcoding.foodiebuddy.recipe.domain.MealType
import com.spcoding.foodiebuddy.recipe.domain.Recipe
import com.spcoding.foodiebuddy.recipe.presentation.recipe_list.components.FoodieLogoTitle
import com.spcoding.foodiebuddy.recipe.presentation.recipe_list.components.RecipeSearchBar
import com.spcoding.foodiebuddy.recipe.presentation.recipe_list.components.Recipelist
import foodiebuddy.composeapp.generated.resources.Res
import foodiebuddy.composeapp.generated.resources.empty_recipes_message
import foodiebuddy.composeapp.generated.resources.tab_favorite
import foodiebuddy.composeapp.generated.resources.tab_recipes
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RecipeListScreenRoot(
    viewModel : RecipeListViewModel,
    modifier : Modifier = Modifier
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    RecipeListScreen(
        state = state,
        onSearchQueryChange = {},
        onAction = { action->
            when(action){
                is RecipeListAction.OnTabSelected ->{

                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun RecipeListScreen(
    state : RecipeListState,
    onSearchQueryChange : (String) -> Unit,
    onAction : (RecipeListAction) -> Unit,
    modifier : Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val pagerState = rememberPagerState{2}
    val recipesListState = rememberLazyListState()

    LaunchedEffect(state.selectedTabIndex){
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage){
        onAction(RecipeListAction.OnTabSelected(pagerState.currentPage))
    }

    LaunchedEffect(state.recipes){
        recipesListState.scrollToItem(0)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FoodieLogoTitle()
        RecipeSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        PrimaryTabRow(
            selectedTabIndex = state.selectedTabIndex,
            modifier = modifier.widthIn(700.dp)
                .fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.tabIndicatorOffset(state.selectedTabIndex)
                )
            }
        ){
            Tab(
                selected = state.selectedTabIndex == 0,
                onClick = { onAction(RecipeListAction.OnTabSelected(0)) },
                modifier = modifier.weight(1f),
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ){
                Text(
                    text = stringResource(Res.string.tab_recipes),
                    modifier = Modifier.padding(8.dp)
                )
            }

            Tab(
                selected = state.selectedTabIndex == 1,
                onClick = { onAction(RecipeListAction.OnTabSelected(1)) },
                modifier = modifier.weight(1f),
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ){
                Text(
                    text = stringResource(Res.string.tab_favorite),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

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
                when(pageIndex){
                    0 -> {
                        if(state.isLoading){
                            CircularProgressIndicator()
                        }
                        else{
                            when{
                                state.errorMessage != null -> {
                                    Text(
                                        text = state.errorMessage,
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                                state.recipes.isEmpty() -> {
                                    Text(
                                        text = stringResource(Res.string.empty_recipes_message),
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.surface
                                    )
                                }
                                else -> {
                                    Recipelist(
                                        recipes = state.recipes,
                                        onRecipeClick = {

                                        },
                                        modifier = Modifier.fillMaxSize()
                                            .background(
                                                color = MaterialTheme.colorScheme.surfaceContainer
                                            ),
                                        scrollState = recipesListState
                                    )
                                }
                            }
                        }
                    }
                    1 -> {
                        Box(modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center)
                        {
                            Text(
                                text = "Yet to implement this feature"
                            )
                        }
                    }
                }
            }
        }
    }
}


private val recipes = (1..15).map {
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

@Composable
@Preview
fun RecipeListScreenPreview() {
    MaterialTheme{
        RecipeListScreen(
            state = RecipeListState(
                recipes= recipes,
                searchQuery = "rice",
                selectedTabIndex = 0,
                isLoading = false,
                errorMessage = null
            ),
            onSearchQueryChange = {},
            onAction = {}
        )
    }
}