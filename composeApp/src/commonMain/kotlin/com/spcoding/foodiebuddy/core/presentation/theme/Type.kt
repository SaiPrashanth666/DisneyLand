package com.spcoding.foodiebuddy.core.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import foodiebuddy.composeapp.generated.resources.Cinzel_Bold
import foodiebuddy.composeapp.generated.resources.Cinzel_ExtraBold
import foodiebuddy.composeapp.generated.resources.Cinzel_Medium
import foodiebuddy.composeapp.generated.resources.Cinzel_Regular
import foodiebuddy.composeapp.generated.resources.Cinzel_SemiBold
import foodiebuddy.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

val Cinzel @Composable get() = FontFamily(
    Font(
        resource = Res.font.Cinzel_Regular,
        weight = FontWeight.Normal
    ),
    Font(
        resource = Res.font.Cinzel_Medium,
        weight = FontWeight.Medium
    ),
    Font(
        resource = Res.font.Cinzel_SemiBold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resource = Res.font.Cinzel_Bold,
        weight = FontWeight.Bold
    ),
    Font(
        resource = Res.font.Cinzel_ExtraBold,
        weight = FontWeight.ExtraBold
    )
)


val typography @Composable get()= Typography(
    titleLarge = TextStyle(
        fontFamily = Cinzel,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        lineHeight = 36.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Cinzel,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Cinzel,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Cinzel,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Cinzel,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Cinzel,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 26.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Cinzel,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Cinzel,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)