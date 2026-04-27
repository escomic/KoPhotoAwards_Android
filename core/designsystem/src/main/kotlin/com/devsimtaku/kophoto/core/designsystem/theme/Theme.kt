package com.devsimtaku.kophoto.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


// ─────────────────────────────────────────────
// Light-only scheme (다크 모드 필요 시 darkColorScheme 추가)
// ─────────────────────────────────────────────
private val LightColorScheme = lightColorScheme(
    primary              = Primary,
    onPrimary            = OnPrimary,
    primaryContainer     = PrimaryContainer,
    onPrimaryContainer   = OnPrimaryContainer,
    inversePrimary       = InversePrimary,

    secondary            = Secondary,
    onSecondary          = OnSecondary,
    secondaryContainer   = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,

    tertiary             = Tertiary,
    onTertiary           = OnTertiary,
    tertiaryContainer    = TertiaryContainer,
    onTertiaryContainer  = OnTertiaryContainer,

    error                = Error,
    onError              = OnError,
    errorContainer       = ErrorContainer,
    onErrorContainer     = OnErrorContainer,

    background           = Background,
    onBackground         = OnBackground,

    surface              = Surface,
    onSurface            = OnSurface,
    onSurfaceVariant     = OnSurfaceVariant,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceContainerLow  = SurfaceContainerLow,
    surfaceContainerLowest = SurfaceContainerLowest,
    surfaceContainerHighest = SurfaceContainerHighest,
    surfaceVariant       = SurfaceVariant,
    surfaceTint          = SurfaceTint,
    inverseSurface       = InverseSurface,
    inverseOnSurface     = InverseOnSurface,

    outline              = Outline,
    outlineVariant       = OutlineVariant,

    scrim                = StaticBlack,
)

@Composable
fun KoPhotoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}