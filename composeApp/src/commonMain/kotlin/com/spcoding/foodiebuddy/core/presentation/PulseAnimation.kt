package com.spcoding.foodiebuddy.core.presentation

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.spcoding.foodiebuddy.core.presentation.theme.FoodieTheme
import io.ktor.client.plugins.logging.SIMPLE
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PulseAnimation(
    modifier : Modifier = Modifier
) {
    val transition = rememberInfiniteTransition()
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier
            .graphicsLayer{
                scaleY = progress
                scaleX = progress
                alpha = 1f - progress
            }
            .border(
                width = 5.dp,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                shape = CircleShape
            )
    )
}

@Preview(showBackground = true)
@Composable
fun PulseAnimationPreview(){
    FoodieTheme {
        Box(modifier = Modifier.size(100.dp)
            .background(color = Color.Black),
            contentAlignment = Alignment.Center){
            PulseAnimation(modifier = Modifier.size(100.dp))
        }
    }
}