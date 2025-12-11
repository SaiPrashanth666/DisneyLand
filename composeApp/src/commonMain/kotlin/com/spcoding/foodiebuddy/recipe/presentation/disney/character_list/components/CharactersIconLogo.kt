package com.spcoding.foodiebuddy.recipe.presentation.disney.character_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.spcoding.foodiebuddy.core.presentation.theme.FoodieTheme
import foodiebuddy.composeapp.generated.resources.Res
import foodiebuddy.composeapp.generated.resources.disney_land
import foodiebuddy.composeapp.generated.resources.disney_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DisneyLand(
    modifier : Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.primaryContainer),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
        Image(
            painter = painterResource(Res.drawable.disney_logo),
            contentDescription = "Disney logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = stringResource(Res.string.disney_land),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}


@Composable
@Preview
fun DisneyLandPreview() {
    FoodieTheme{
        DisneyLand()
    }
}