package com.spcoding.foodiebuddy.recipe.presentation.recipe_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import foodiebuddy.composeapp.generated.resources.Res
import foodiebuddy.composeapp.generated.resources.meal
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FoodieLogoTitle(
    modifier : Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.primaryContainer)
        .statusBarsPadding()
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp))
    {
        Image(
            painter = painterResource(Res.drawable.meal),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(75.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        Text(
            text = "Foodie Buddy",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

    }
}


@Composable
@Preview
fun FoodieLogoTitlePreview() {
    MaterialTheme{
        FoodieLogoTitle()
    }
}