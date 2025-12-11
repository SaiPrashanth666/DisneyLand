package com.spcoding.foodiebuddy.recipe.presentation.disney.character_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spcoding.foodiebuddy.core.presentation.theme.FoodieTheme
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_detail.components.AnimatedList
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_detail.components.BlurredImageBackground
import foodiebuddy.composeapp.generated.resources.Res
import foodiebuddy.composeapp.generated.resources.camera_movie_svgrepo_com
import foodiebuddy.composeapp.generated.resources.films
import foodiebuddy.composeapp.generated.resources.films_logo
import foodiebuddy.composeapp.generated.resources.shopping_cart
import foodiebuddy.composeapp.generated.resources.short_films
import foodiebuddy.composeapp.generated.resources.tv_logo
import foodiebuddy.composeapp.generated.resources.tv_shows
import foodiebuddy.composeapp.generated.resources.video_games
import foodiebuddy.composeapp.generated.resources.video_games_logo
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterDetailRoot(
    viewModel: CharacterDetailViewModel = viewModel(),
    onBackClick : () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CharacterDetailScreen(
        state = state,
        onAction = { action ->
            when(action){
                CharacterDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun CharacterDetailScreen(
    state: CharacterDetailState,
    onAction: (CharacterDetailAction) -> Unit,
) {
    BlurredImageBackground(
        imageUrl = state.character?.imageUrl,
        isFavorite = state.isFavorite,
        onBackClick = {
            onAction(CharacterDetailAction.OnBackClick)
        },
        onFavoriteClick = {
            onAction(CharacterDetailAction.OnFavoriteClick)
        },
        modifier = Modifier.fillMaxSize()
    ){
        if(state.character != null){
            Column(
                modifier = Modifier
                    .widthIn(700.dp)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp,
                        horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.character.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if(state.character.films.isNotEmpty()){
                    AnimatedList(
                        imageRes = Res.drawable.films_logo,
                        title = stringResource(Res.string.films),
                        content = state.character.films
                    )
                }

                if(state.character.shortFilms.isNotEmpty()){
                    AnimatedList(
                        imageRes = Res.drawable.camera_movie_svgrepo_com,
                        title = stringResource(Res.string.short_films),
                        content = state.character.shortFilms
                    )
                }

                if(state.character.tvShows.isNotEmpty()){
                    AnimatedList(
                        imageRes = Res.drawable.tv_logo,
                        title = stringResource(Res.string.tv_shows),
                        content = state.character.tvShows
                    )
                }

                if(state.character.videoGames.isNotEmpty()){
                    AnimatedList(
                        imageRes = Res.drawable.video_games_logo,
                        title = stringResource(Res.string.video_games),
                        content = state.character.videoGames
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    FoodieTheme {
        CharacterDetailScreen(
            state = CharacterDetailState(),
            onAction = {}
        )
    }
}