package com.spcoding.foodiebuddy.recipe.presentation.disney.character_list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.automirrored.outlined.ArrowRight
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.spcoding.foodiebuddy.core.presentation.PulseAnimation
import com.spcoding.foodiebuddy.core.presentation.theme.FoodieTheme
import com.spcoding.foodiebuddy.recipe.domain.Character
import foodiebuddy.composeapp.generated.resources.Res
import foodiebuddy.composeapp.generated.resources.error_404_not_found
import foodiebuddy.composeapp.generated.resources.not_found
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharactersListItem(
    character : Character,
    onClick : () -> Unit,
    modifier : Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.elevatedCardElevation(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(modifier = Modifier
                    .padding(8.dp)
                    .height(200.dp),
                contentAlignment = Alignment.Center) {
                var imageLoadResult by remember { mutableStateOf<Result<Painter>?>(null) }
                val painter = rememberAsyncImagePainter(
                    model = character.imageUrl,
                    onSuccess = {
                        val size = it.painter.intrinsicSize
                        imageLoadResult = if (size.height > 1 && size.width > 1) {
                            Result.success(it.painter)
                        } else {
                            Result.failure(Exception("Invalid image size"))
                        }
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(it.result.throwable)
                    }
                )

                val painterState by painter.state.collectAsStateWithLifecycle()
                val progress by animateFloatAsState(
                    targetValue = if(painterState is AsyncImagePainter.State.Success){
                        1f
                    }else{
                        0f
                    }
                )


                when (val result = imageLoadResult) {
                    null -> PulseAnimation(modifier = Modifier.size(150.dp))
                    else -> {
                        Image(
                            painter = if (result.isSuccess) painter else painterResource(Res.drawable.not_found),
                            contentDescription = stringResource(Res.string.error_404_not_found),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .aspectRatio(
                                    ratio = 3/2f,
                                    matchHeightConstraintsFirst = true
                                )
                                .graphicsLayer{
                                    rotationX = (1f - progress) * 30
                                    val scale = 0.8f + (0.2f * progress)
                                    scaleX = scale
                                    scaleY = scale
                                }
                        )
                    }
                }
            }


            Column(
                modifier = Modifier.weight(1f)
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = character.tvShows.joinToString(","),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
@Preview
fun CharactersListItemPreview() {
    FoodieTheme{
        CharactersListItem(
            character = queen,
            onClick = {}
        )
    }
}

private val queen = Character(
    id = 1,
    name = "Queen Arianna",
    tvShows = listOf("Once Upon a Time","Tangled: The Series"),
    imageUrl = "https://static.wikia.nocookie.net/disney/images/1/15/Arianna_Tangled.jpg/revision/latest?cb=20160715191802",
    films = emptyList(),
    shortFilms = emptyList(),
    videoGames = emptyList()
    )
