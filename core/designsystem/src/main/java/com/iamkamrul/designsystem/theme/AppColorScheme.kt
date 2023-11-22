package com.iamkamrul.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


data class AppColor(
    val white: Color = Color.Unspecified,
    val black: Color = Color.Unspecified,
    val topAppBar: Color = Color.Unspecified,
    val secondaryBackground: Color = Color.Unspecified
)
internal val LocalAppColor = compositionLocalOf { AppColor() }

val MaterialTheme.color: AppColor
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColor.current



internal val LocalLightColorScheme = AppColor(
    white = White,
    black = Black,
    topAppBar = White,
    secondaryBackground = Black95,
)

internal val LocalDarkColorScheme = AppColor(
    white = Black,
    black = White,
    topAppBar = Black10,
    secondaryBackground = Black
)

