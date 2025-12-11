package com.spcoding.foodiebuddy.recipe.presentation.recipe_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import foodiebuddy.composeapp.generated.resources.Res
import foodiebuddy.composeapp.generated.resources.recipe_search_bar_placeholder
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RecipeSearchBar(
    searchQuery : String,
    onSearchQueryChange : (String) -> Unit,
    onImeSearch : () -> Unit,
    modifier : Modifier = Modifier
)
{
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text(
                text = stringResource(Res.string.recipe_search_bar_placeholder),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        singleLine = true,
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
                        onSearchQueryChange("")
                    },
                ){
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        modifier = modifier
            .background(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface
            )
            .minimumInteractiveComponentSize()
    )
}


@Composable
@Preview
fun RecipeSearchBarPreview()
{
    MaterialTheme{
        RecipeSearchBar(
            searchQuery = "kotlin",
            onImeSearch = {},
            onSearchQueryChange = {}
        )
    }
}