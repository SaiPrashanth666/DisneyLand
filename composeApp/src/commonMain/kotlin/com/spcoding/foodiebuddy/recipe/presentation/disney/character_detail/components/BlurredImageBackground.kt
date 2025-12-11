package com.spcoding.foodiebuddy.recipe.presentation.disney.character_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradient
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.spcoding.foodiebuddy.core.presentation.PulseAnimation
import foodiebuddy.composeapp.generated.resources.Res
import foodiebuddy.composeapp.generated.resources.not_found
import org.jetbrains.compose.resources.painterResource

@Composable
fun BlurredImageBackground(
    imageUrl : String?,
    isFavorite : Boolean,
    onBackClick : () -> Unit,
    onFavoriteClick : () -> Unit,
    modifier : Modifier = Modifier,
    content : @Composable () -> Unit
) {
    var imageLoadResult by remember { mutableStateOf<Result<Painter>?>(null) }
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageLoadResult = if(size.height > 1 && size.width > 1){
                Result.success(it.painter)
            }else{
                Result.failure(Exception("Invalid painter size"))
            }
        },
        onError = {
            it.result.throwable.printStackTrace()
            imageLoadResult = Result.failure(it.result.throwable)
        }
    )

    Box(modifier = modifier){
        Column(modifier = modifier
            .fillMaxHeight()){

            Box(modifier = Modifier.weight(0.3f)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
                ){
                imageLoadResult?.getOrNull()?.let { painter ->
                    Image(
                        painter = painter,
                        contentDescription = "characterCover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                            .blur(20.dp)
                    )
                }
            }

            Box(
                modifier = Modifier.weight(0.7f)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface)
            )
        }

        IconButton(
            onClick = {
                onBackClick()
            },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .padding(top = 16.dp,
                    start = 16.dp)
                .statusBarsPadding()
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "ArrowBack",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))

            ElevatedCard(
                modifier = Modifier.height(250.dp)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.Transparent
                ),
                elevation = CardDefaults.elevatedCardElevation(8.dp)
            ){
                AnimatedContent(
                    targetState = imageLoadResult
                ){ result ->
                    when(result){
                        null -> Box(contentAlignment = Alignment.Center){
                            PulseAnimation()
                        }
                        else -> {
                            Box {
                                Image(
                                    painter = if(result.isSuccess) painter else painterResource(Res.drawable.not_found),
                                    contentDescription = "characterCover",
                                    contentScale = if(result.isSuccess)ContentScale.Crop else ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize()
                                        .padding(16.dp)
                                )

                                IconButton(
                                    onClick = {
                                        onFavoriteClick()
                                    },
                                    modifier = Modifier.align(alignment = Alignment.BottomEnd)
                                        .background(brush = Brush.radialGradient(
                                            colors = listOf(MaterialTheme.colorScheme.tertiaryContainer,
                                                Color.Transparent),
                                            radius = 70f
                                        )
                                    )
                                        .padding(end = 8.dp,
                                            bottom = 8.dp)
                                ){
                                    Icon(
                                        imageVector = if(isFavorite)Icons.Filled.Favorite
                                        else Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Add/RemoveFavoriteButton",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            content()
        }
    }
}