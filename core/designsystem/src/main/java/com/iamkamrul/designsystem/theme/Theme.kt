package com.iamkamrul.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    //The primary color could be used for the app bar, buttons, and other important UI elements.
    primary = Orange40,
    //Color used for text and icons displayed on top of the primary color.
    onPrimary = White,
    //The secondary color could be used for tabs, headers, and other secondary UI elements.
    //secondary = PurpleGrey40,
    //The onSecondary color could be used for text and icons that appear on top of secondary-colored backgrounds.
    //onSecondary = White,
    //The Background color could be used for backgrounds.
    background = Black,
    //The onBackground color could be used for text and icons that appear on top of background backgrounds.
    onBackground = White,
    //The surface color that affect surfaces of components, such as cards, sheets, and menus.
    surface = Black10,
    onSurface = White,
    surfaceVariant = Black10,
    onSurfaceVariant = White,
    //It refers to the color or style used to represent an error state in the UI. This color is typically applied to components or indicators that highlight invalid input, validation errors, or other error conditions in the application. The error color helps draw attention to and communicate the presence of errors to the user.
    error = Red,
    // It refers top of error text and icon colors
    onError = White,
    // It refers to the border or outline around a UI component. The outline is a visual representation that defines the shape and boundaries of the component. It can be used to provide visual emphasis, highlight selected or focused components, or convey interactivity.
    //outline = Pink40,
)

private val lightColorScheme = lightColorScheme(
    //The primary color could be used for the app bar, buttons, and other important UI elements.
    primary = Orange40,
    //Color used for text and icons displayed on top of the primary color.
    onPrimary = White,
    //The secondary color could be used for tabs, headers, and other secondary UI elements.
    //secondary = PurpleGrey40,
    //The onSecondary color could be used for text and icons that appear on top of secondary-colored backgrounds.
    //onSecondary = White,
    //The Background color could be used for backgrounds.
    background = White,
    //The onBackground color could be used for text and icons that appear on top of background backgrounds.
    onBackground = Black,
    //The surface color that affect surfaces of components, such as cards, sheets, and menus.
    surface = White,
    onSurface = Black,
    surfaceVariant = White,
    onSurfaceVariant = Black,
    //It refers to the color or style used to represent an error state in the UI. This color is typically applied to components or indicators that highlight invalid input, validation errors, or other error conditions in the application. The error color helps draw attention to and communicate the presence of errors to the user.
    error = Red40,
    // It refers top of error text and icon colors
    onError = White,
    // It refers to the border or outline around a UI component. The outline is a visual representation that defines the shape and boundaries of the component. It can be used to provide visual emphasis, highlight selected or focused components, or convey interactivity.
    //outline = Pink40
)

@Composable
fun JetPackComposeModularizationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val statusBarColor = if(darkTheme) Black10 else White
            window.statusBarColor = statusBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    val appColorScheme = when {
        darkTheme -> LocalDarkColorScheme
        else -> LocalLightColorScheme
    }
    CompositionLocalProvider(values = arrayOf(LocalAppColor provides appColorScheme)) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }

}