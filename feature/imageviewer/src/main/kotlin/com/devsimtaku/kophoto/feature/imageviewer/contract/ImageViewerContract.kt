package com.devsimtaku.kophoto.feature.imageviewer.contract

data class ImageViewerUiState(
    val imageUrl: String = "",
    val title: String? = null
)

sealed interface ImageViewerUiEvent {
    data object OnBackClick : ImageViewerUiEvent
}

sealed interface ImageViewerUiEffect {
    data object NavigateBack : ImageViewerUiEffect
}
