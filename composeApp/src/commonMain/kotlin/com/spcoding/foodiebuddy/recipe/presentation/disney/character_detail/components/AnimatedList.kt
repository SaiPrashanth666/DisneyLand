package com.spcoding.foodiebuddy.recipe.presentation.disney.character_detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.spcoding.foodiebuddy.core.presentation.theme.FoodieTheme
import foodiebuddy.composeapp.generated.resources.Res
import foodiebuddy.composeapp.generated.resources.shopping_cart
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AnimatedList(
    imageRes : DrawableResource,
    title : String,
    content : List<String>,
    modifier : Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val animatedContainerColor by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.tertiaryContainer
    )
    val animatedContentColor by animateColorAsState(
        targetValue = if(expanded) MaterialTheme.colorScheme.onPrimaryContainer
        else MaterialTheme.colorScheme.onTertiaryContainer
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .background(color = animatedContainerColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(32.dp),
                colorFilter = ColorFilter.tint(animatedContentColor)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = animatedContentColor,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    expanded = !expanded
                }
            ) {
                Icon(
                    imageVector = if(expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = "expand or collapse",
                    tint = animatedContentColor
                )
            }
        }
        if(expanded){
            AnimatedVisibility(expanded){
            Column (verticalArrangement = Arrangement.spacedBy(8.dp)){
                content.forEach { it ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FiberManualRecord,
                            contentDescription = "BulletPoint",
                            tint = animatedContentColor
                        )
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = animatedContentColor
                        )
                    }
                }
            }
                }
        }
    }
}


@Composable
@Preview
fun AnimatedListPreview() {
    FoodieTheme{
        AnimatedList(
            Res.drawable.shopping_cart,
            "Films",
            listOf("Walt Disney anthology series",
                "The Mickey Mouse Club",
                "The Mouse Factory",
                "Mickey's Fun Songs",
                "Mickey Mouse Works",
                "Mickey's Mousekersize (short series",
                "Mickey Mouse Mixed-Up Adventures",
                "uckTales (2017 series)")
        )
    }
}