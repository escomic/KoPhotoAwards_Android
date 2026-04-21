package com.devsimtaku.kophoto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.devsimtaku.kophoto.core.designsystem.theme.KoPhotoTheme
import com.devsimtaku.kophoto.ui.KoPhotoApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoPhotoTheme {
                KoPhotoApp()
            }
        }
    }
}