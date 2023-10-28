package me.taste2plate.app.customer.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val md_theme_light_primary = Color(0xFFC00017)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFFFDAD6)
val md_theme_light_onPrimaryContainer = Color(0xFF410003)
val md_theme_light_secondary = Color(0xFF775653)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFFFDAD6)
val md_theme_light_onSecondaryContainer = Color(0xFF2C1513)
val md_theme_light_tertiary = Color(0xFF725B2E)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFFEDEA6)
val md_theme_light_onTertiaryContainer = Color(0xFF261900)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF201A19)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF201A19)
val md_theme_light_surfaceVariant = Color(0xFFF5DDDB)
val md_theme_light_onSurfaceVariant = Color(0xFF534341)
val md_theme_light_outline = Color(0xFF857371)
val md_theme_light_inverseOnSurface = Color(0xFFFBEEEC)
val md_theme_light_inverseSurface = Color(0xFF362F2E)
val md_theme_light_inversePrimary = Color(0xFFFFB3AC)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFFC00017)
val md_theme_light_outlineVariant = Color(0xFFD8C2BF)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFFFB3AC)
val md_theme_dark_onPrimary = Color(0xFF680008)
val md_theme_dark_primaryContainer = Color(0xFF93000F)
val md_theme_dark_onPrimaryContainer = Color(0xFFFFDAD6)
val md_theme_dark_secondary = Color(0xFFE7BDB8)
val md_theme_dark_onSecondary = Color(0xFF442927)
val md_theme_dark_secondaryContainer = Color(0xFF5D3F3C)
val md_theme_dark_onSecondaryContainer = Color(0xFFFFDAD6)
val md_theme_dark_tertiary = Color(0xFFE1C38C)
val md_theme_dark_onTertiary = Color(0xFF3F2D04)
val md_theme_dark_tertiaryContainer = Color(0xFF584419)
val md_theme_dark_onTertiaryContainer = Color(0xFFFEDEA6)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF201A19)
val md_theme_dark_onBackground = Color(0xFFEDE0DE)
val md_theme_dark_surface = Color(0xFF201A19)
val md_theme_dark_onSurface = Color(0xFFEDE0DE)
val md_theme_dark_surfaceVariant = Color(0xFF534341)
val md_theme_dark_onSurfaceVariant = Color(0xFFD8C2BF)
val md_theme_dark_outline = Color(0xFFA08C8A)
val md_theme_dark_inverseOnSurface = Color(0xFF201A19)
val md_theme_dark_inverseSurface = Color(0xFFEDE0DE)
val md_theme_dark_inversePrimary = Color(0xFFC00017)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFFFB3AC)
val md_theme_dark_outlineVariant = Color(0xFF534341)
val md_theme_dark_scrim = Color(0xFF000000)


val seed = Color(0xFFDE2228)


val GreyLight = Color(0xFFB5B6BA)
val GreyDark = Color(0xFF60626C)

val YellowBanner = Color(0xFFfce039)
val YellowBannerDark = Color(0xFF6c5e00)

val ForestGreen = Color(0xFF176d29)
val ForestGreenDark = Color(0xFF86d988)


val forestGreen : @Composable () -> Color = {
    if(isSystemInDarkTheme())
        ForestGreenDark
    else ForestGreen
}

val yellowBannerColor : @Composable () -> Color = {
    if(isSystemInDarkTheme())
        YellowBannerDark
    else YellowBanner
}


val primaryColor: @Composable () -> Color = {
    MaterialTheme.colorScheme.primary
}

val onSecondaryColor: @Composable () -> Color = {
    MaterialTheme.colorScheme.onSecondary
}

val backgroundColor: @Composable () -> Color = {
    MaterialTheme.colorScheme.background
}

val onBackgroundColor: @Composable () -> Color = {
    MaterialTheme.colorScheme.onBackground
}

val secondaryColor: @Composable () -> Color = {
    MaterialTheme.colorScheme.secondary
}

val outlineColor: @Composable () -> Color = {
    MaterialTheme.colorScheme.outline
}

val cardContainerOnSecondaryColor: @Composable () -> CardColors = {
    CardDefaults.cardColors(
        MaterialTheme.colorScheme.onSecondary
    )
}


val whiteBackgroundButtonColor: @Composable () -> ButtonColors = {
    ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.secondary
    )
}
