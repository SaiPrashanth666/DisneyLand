package com.spcoding.foodiebuddy.recipe.presentation.disney.character_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spcoding.foodiebuddy.core.presentation.theme.FoodieTheme
import com.spcoding.foodiebuddy.recipe.domain.Character
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharactersList(
    characters : List<Character>,
    onClick : (Character) -> Unit,
    modifier : Modifier = Modifier,
    scrollState : LazyListState
) {
    LazyColumn (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        items(
            items = characters,
            key = {
                it.id
            }
        ){
            CharactersListItem(
                character = it,
                onClick = {
                    onClick(it)
                }
            )
        }
    }
}


//@Composable
//@Preview
//fun CharactersListPreview() {
//    FoodieTheme{
//        CharactersList()
//    }
//}