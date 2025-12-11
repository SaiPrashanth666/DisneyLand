package com.spcoding.foodiebuddy.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.spcoding.foodiebuddy.core.presentation.theme.FoodieTheme
import com.spcoding.foodiebuddy.recipe.presentation.disney.SelectedCharacterViewModel
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_detail.CharacterDetailAction
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_detail.CharacterDetailRoot
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_detail.CharacterDetailViewModel
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_list.CharacterListAction
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_list.CharacterListRoot
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_list.CharacterListScreen
import com.spcoding.foodiebuddy.recipe.presentation.disney.character_list.CharacterListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    FoodieTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.CharacterGraph
        ){
            navigation<Route.CharacterGraph>(
                startDestination = Route.CharacterList
            ){
                composable<Route.CharacterList>(
                    popEnterTransition = { slideInHorizontally() },
                    exitTransition = { slideOutHorizontally() }
                ){

                    val selectedCharacterViewModel = it.sharedKoinViewModel<SelectedCharacterViewModel>(navController)
                    val viewModel = koinViewModel<CharacterListViewModel>()

                    LaunchedEffect(true){
                        selectedCharacterViewModel.onSelectCharacter(null)
                    }

                    CharacterListRoot(
                        viewModel = viewModel,
                        onCharacterClick = { character ->
                            selectedCharacterViewModel.onSelectCharacter(character)
                            navController.navigate(Route.CharacterDetail(character.id))
                        }
                    )
                }

                composable<Route.CharacterDetail>(
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    }},
                    exitTransition = { slideOutHorizontally { initalOffset ->
                        initalOffset
                    }}
                ){
                    val selectedCharacterViewModel = it.sharedKoinViewModel<SelectedCharacterViewModel>(navController)
                    val viewModel = koinViewModel<CharacterDetailViewModel>()

                    val selectedCharacter by selectedCharacterViewModel.SelectedCharacter.collectAsStateWithLifecycle()
                    LaunchedEffect(selectedCharacter){
                        selectedCharacter?.let {
                            viewModel.onAction(CharacterDetailAction.OnSelectedCharacterChange(it))
                        }
                    }

                    CharacterDetailRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private inline fun<reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController : NavController
) : T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }

    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}