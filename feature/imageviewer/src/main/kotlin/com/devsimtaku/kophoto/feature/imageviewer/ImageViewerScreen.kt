package com.devsimtaku.kophoto.feature.imageviewer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devsimtaku.kophoto.core.designsystem.KPIcon
import com.devsimtaku.kophoto.core.designsystem.theme.StaticBlack
import com.devsimtaku.kophoto.core.designsystem.theme.StaticWhite
import com.devsimtaku.kophoto.feature.imageviewer.contract.ImageViewerUiEffect
import com.devsimtaku.kophoto.feature.imageviewer.contract.ImageViewerUiEvent
import me.saket.telephoto.zoomable.coil3.ZoomableAsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageViewerScreen(
    imageUrl: String,
    title: String?,
    onBackClick: () -> Unit,
    viewModel: ImageViewerViewModel = hiltViewModel(
        creationCallback = { factory: ImageViewerViewModel.Factory ->
            factory.create(imageUrl, title)
        }
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                ImageViewerUiEffect.NavigateBack -> onBackClick()
            }
        }
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {
                    uiState.title?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleLarge,
                            color = StaticWhite,
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.sendEvent(ImageViewerUiEvent.OnBackClick) }) {
                        Icon(
                            imageVector = KPIcon.ArrowBack,
                            contentDescription = "Back",
                            tint = StaticWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = StaticBlack.copy(alpha = 0.5f)
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(StaticBlack),
            contentAlignment = Alignment.Center
        ) {
            ZoomableAsyncImage(
                model = uiState.imageUrl,
                contentDescription = uiState.title,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
