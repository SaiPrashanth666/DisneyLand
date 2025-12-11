package com.spcoding.foodiebuddy.recipe.presentation.recipe_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.spcoding.foodiebuddy.recipe.domain.MealType
import com.spcoding.foodiebuddy.recipe.domain.Recipe
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Recipelist(
    recipes : List<Recipe>,
    onRecipeClick : (Recipe) -> Unit,
    modifier : Modifier = Modifier,
    scrollState : LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(
            items = recipes,
            key = { it.id }
        ){ recipe ->
            RecipeListItem(
                recipe = recipe,
                modifier = Modifier.widthIn(max = 700.dp)
                    .fillMaxWidth(),
                onClick = {
                    onRecipeClick(recipe)
                }
            )
        }
    }
}

val recipes = (1..15).map {
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
fun RecipelistPreview() {
    MaterialTheme{
        Recipelist(
            recipes = recipes,
            onRecipeClick = {}
        )
    }
}