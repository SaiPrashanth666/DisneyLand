package com.spcoding.foodiebuddy.recipe.presentation.products.product_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.spcoding.foodiebuddy.core.presentation.theme.FoodieTheme
import com.spcoding.foodiebuddy.recipe.domain.Product
import foodiebuddy.composeapp.generated.resources.Res
import foodiebuddy.composeapp.generated.resources.meal
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductListItem(
    product : Product,
    onClick : () -> Unit,
    modifier : Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.elevatedCardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row (modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically)
        {
            Box(modifier = Modifier.height(100.dp),
                contentAlignment = Alignment.Center) {
                var imageLoadResult by remember { mutableStateOf<Result<Painter>?>(null) }
                val painter = rememberAsyncImagePainter(
                    model = product.imageUrl.firstOrNull(),
                    onSuccess = {
                        val size = it.painter.intrinsicSize
                        if (size.height > 1 && size.width > 1) {
                            imageLoadResult = Result.success(it.painter)
                        } else {
                            imageLoadResult =
                                Result.failure(IllegalArgumentException("Invalid image size"))
                        }
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(it.result.throwable)
                    }
                )

                when (val result = imageLoadResult) {
                    null -> CircularProgressIndicator()
                    else -> {
                        Image(
                            painter = if (result.isSuccess) painter else painterResource(Res.drawable.meal),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            Column(modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start){

                Text(
                    text = product.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

            }

            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

val product = Product(
    id = 1,
    category = "mens-watches",
    imageUrl = listOf(
        "https://i.dummyjson.com/data/products/61/1.jpg",
        "https://i.dummyjson.com/data/products/61/2.png",
        "https://i.dummyjson.com/data/products/61/3.jpg"
        ),
    title = "Leather Straps Wristwatch")

@Composable
@Preview
fun ProductListItemPreview() {
    FoodieTheme{
        ProductListItem(
            product,
            onClick = {}
        )
    }
}