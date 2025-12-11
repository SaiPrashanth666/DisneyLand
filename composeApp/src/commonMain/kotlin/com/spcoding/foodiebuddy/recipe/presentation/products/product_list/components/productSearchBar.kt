package com.spcoding.foodiebuddy.recipe.presentation.products.product_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.spcoding.foodiebuddy.core.presentation.theme.FoodieTheme
import foodiebuddy.composeapp.generated.resources.Res
import foodiebuddy.composeapp.generated.resources.product_search_bar_placeholder
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductSearchBar(
    searchQuery : String,
    onSearchQueryChange : (String) -> Unit,
    onImeSearch : () -> Unit,
    modifier : Modifier = Modifier
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        shape = RoundedCornerShape(16.dp),
        placeholder = {
            Text(
                text = stringResource(Res.string.product_search_bar_placeholder),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onImeSearch()
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = searchQuery.isNotBlank()
            ){
                IconButton(
                    onClick = {
                        onSearchQueryChange("")
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
        modifier = modifier
            .background(shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface)
            .minimumInteractiveComponentSize()
    )
}


@Composable
@Preview
fun ProductSearchBarPreview() {
    FoodieTheme{
        ProductSearchBar(
            "kotlin",
            onSearchQueryChange = {},
            onImeSearch = {}
        )
    }
}