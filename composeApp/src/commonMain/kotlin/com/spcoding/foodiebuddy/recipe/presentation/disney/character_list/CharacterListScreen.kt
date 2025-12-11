package com.spcoding.foodiebuddy.recipe.presentation.disney.character_list

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spcoding.foodiebuddy.core.presentation.theme.FoodieTheme
import com.spcoding.foodiebuddy.recipe.domain.Character
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_list.components.CharactersList
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_list.components.CharactersSearchBar
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_list.components.DisneyLand
import foodiebuddy.composeapp.generated.resources.Res
import foodiebuddy.composeapp.generated.resources.character_search_bar_placeholder
import foodiebuddy.composeapp.generated.resources.no_favorite_characters
import foodiebuddy.composeapp.generated.resources.no_search_results
import foodiebuddy.composeapp.generated.resources.tab_characters
import foodiebuddy.composeapp.generated.resources.tab_favorite
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterListRoot(
    viewModel: CharacterListViewModel,
    onCharacterClick : (Character) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CharacterListScreen(
        state = state,
        onAction = { action ->
            when(action){
                is CharacterListAction.OnCharacterClicked -> {
                    onCharacterClick(action.character)
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun CharacterListScreen(
    state: CharacterListState,
    onAction: (CharacterListAction) -> Unit,
) {
    val pagerState = rememberPagerState { 2 }
    val keyboardController = LocalSoftwareKeyboardController.current
    val charactersScrollState = rememberLazyListState()
    val favoriteCharactersScrollState = rememberLazyListState()

    LaunchedEffect(state.selectedTabIndex){
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage){
        onAction(CharacterListAction.OnSelectedTabChanged(pagerState.currentPage))
    }
    LaunchedEffect(state.characters){
        charactersScrollState.scrollToItem(0)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
            .background(color = MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        DisneyLand()
        CharactersSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChanged = { query ->
                onAction(CharacterListAction.OnSearchQueryChanged(query))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            placeholder = stringResource(Res.string.character_search_bar_placeholder),
            modifier = Modifier
                .padding(16.dp)
                .widthIn(max = 400.dp)
                .fillMaxWidth()
        )

        PrimaryTabRow(
            selectedTabIndex = state.selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .tabIndicatorOffset(state.selectedTabIndex)
                )
            },
            modifier = Modifier.padding(top = 12.dp)
                .widthIn(max = 400.dp)
                .fillMaxWidth()
        ){
            Tab(
                selected = state.selectedTabIndex == 0,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    onAction(CharacterListAction.OnSelectedTabChanged(0))
                },
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = stringResource(Res.string.tab_characters)
                )
            }
            Tab(
                selected = state.selectedTabIndex == 1,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    onAction(CharacterListAction.OnSelectedTabChanged(1))
                },
                modifier = Modifier.weight(1f)
            ){
                Text(
                    text = stringResource(Res.string.tab_favorite)
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
        ){ pageIndex ->
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center){
                when(pageIndex){
                    0 -> {
                        if(state.isLoading){
                            CircularProgressIndicator()
                        } else {
                            when {
                                state.errorMessage != null -> {
                                    Text(
                                        text = state.errorMessage.asString(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                                state.characters.isEmpty() -> {
                                    Text(
                                        text = stringResource(Res.string.no_search_results),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                else -> {
                                    CharactersList(
                                        characters = state.characters,
                                        onClick = {
                                            onAction(CharacterListAction.OnCharacterClicked(it))
                                        },
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .widthIn(max = 400.dp)
                                            .fillMaxWidth(),
                                        scrollState = charactersScrollState
                                    )
                                }
                            }
                        }
                    }
                    1 -> {
                        if(state.favoriteCharacters.isEmpty()){
                            Text(
                                text = stringResource(Res.string.no_favorite_characters),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        else {
                            CharactersList(
                                characters = state.favoriteCharacters,
                                onClick = { character ->
                                    onAction(CharacterListAction.OnCharacterClicked(character))
                                },
                                modifier = Modifier.padding(16.dp),
                                scrollState = favoriteCharactersScrollState
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    FoodieTheme {
        CharacterListScreen(
            state = CharacterListState(),
            onAction = {}
        )
    }
}
