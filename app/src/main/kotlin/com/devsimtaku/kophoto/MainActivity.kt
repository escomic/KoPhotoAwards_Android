package com.devsimtaku.kophoto

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.devsimtaku.kophoto.core.designsystem.theme.KoPhotoTheme
import com.devsimtaku.kophoto.ui.KoPhotoApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b),
            ),
        )
        setContent {
            KoPhotoTheme {
                KoPhotoApp()
            }
        }
    }
}
