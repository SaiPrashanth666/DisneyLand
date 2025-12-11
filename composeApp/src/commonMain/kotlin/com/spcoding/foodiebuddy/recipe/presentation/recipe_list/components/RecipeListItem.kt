package com.spcoding.foodiebuddy.recipe.presentation.recipe_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.spcoding.foodiebuddy.recipe.domain.MealType
import com.spcoding.foodiebuddy.recipe.domain.Recipe
import foodiebuddy.composeapp.generated.resources.Res
import foodiebuddy.composeapp.generated.resources.meal
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RecipeListItem(
    recipe : Recipe,
    onClick : () -> Unit,
    modifier : Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.clickable(
            onClick = onClick
        ),
        color = MaterialTheme.colorScheme.surface
    ){
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            Box(
                modifier = Modifier.padding(8.dp)
                    .height(100.dp),
                contentAlignment = Alignment.Center){
                var imageLoadResult by remember { mutableStateOf<Result<Painter>?>(null) }
                val painter = rememberAsyncImagePainter(
                    model = recipe.imageUrl,
                    onSuccess = {
                        val painterSize = it.painter.intrinsicSize
                        imageLoadResult = if(painterSize.height > 1 && painterSize.width > 1){
                            Result.success(it.painter)
                        }else{
                            Result.failure(IllegalArgumentException("Invalid image size"))
                        }
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(it.result.throwable)
                    }
                )

                when(val result = imageLoadResult){
                    null -> CircularProgressIndicator()
                    else -> {
                        Image(
                            painter = if(result.isSuccess) painter else {
                                painterResource(Res.drawable.meal)
                            },
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.aspectRatio(0.66f,
                                matchHeightConstraintsFirst = true)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = recipe.region,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = if(recipe.isFavorite) Icons.Default.Favorite else Icons.Outlined.Favorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
            )
        }
    }
}

private val dummyRecipe = Recipe(
    id = 52785,
    name = "Dal fry",
    mealType = MealType.VEG,
    region = "India",
    instructions = "Just fry it",
    imageUrl = "https://www.themealdb.com/images/media/meals/wuxrtu1483564410.jpg",
    ingredients = listOf("Toor dal","Water","salt","turmeric","Ghee"),
    correspondingQuantity = listOf("1 cup", "2 cups", "1 tsp", "1/4 tsp", "3 tbs",),
    tutorial = "https://www.youtube.com/watch?v=J4D855Q9-jg",
    isFavorite = false
)

@Composable
@Preview
fun RecipeListItemPreview()
{
    MaterialTheme{
        RecipeListItem(
            recipe = dummyRecipe,
            onClick = {}
        )
    }
}