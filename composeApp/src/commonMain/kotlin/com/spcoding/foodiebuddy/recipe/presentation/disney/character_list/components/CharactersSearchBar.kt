package com.spcoding.foodiebuddy.recipe.presentation.disney.character_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.spcoding.foodiebuddy.core.presentation.theme.FoodieTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharactersSearchBar(
    searchQuery : String,
    onSearchQueryChanged : (String) -> Unit,
    placeholder: String,
    onImeSearch : () -> Unit,
    modifier : Modifier = Modifier
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(16.dp),
        keyboardActions = KeyboardActions(
            onSearch = {
                onImeSearch()
            }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        trailingIcon = {
            AnimatedVisibility(
                visible = searchQuery.isNotBlank()
            ){
                IconButton(
                    onClick = {
                        onSearchQueryChanged("")
                    }
                ){
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        placeholder = {
            Text(
                text = placeholder,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
    )
}


@Composable
@Preview
fun CharactersSearchBarPreview() {
    FoodieTheme{
        CharactersSearchBar(
            searchQuery = "",
            onSearchQueryChanged = {

            },
            onImeSearch = {},
            placeholder = "Type something"
        )
    }
}