package com.spcoding.foodiebuddy.recipe.presentation.products.product_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import foodiebuddy.composeapp.generated.resources.shopping_cart
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProductLogoTitle(
    modifier: Modifier = Modifier
){
    Row(modifier = modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.primaryContainer)
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)){

        Image(
            painter = painterResource(Res.drawable.shopping_cart),
            contentScale = ContentScale.Fit,
            contentDescription = "shopping-cart",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
            modifier = Modifier.size(75.dp)
        )

        Text(
            text = "Hello Shopper!",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}