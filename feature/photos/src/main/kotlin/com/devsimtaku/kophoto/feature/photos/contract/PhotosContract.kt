package com.devsimtaku.kophoto.feature.photos.contract

import com.devsimtaku.kophoto.core.domain.model.PhotoGallery

class PhotosUiState()

sealed interface PhotosUiEvent {
    data class OnPhotoClick(val photo: PhotoGallery) : PhotosUiEvent
}

sealed interface PhotosUiEffect {
    data class NavigateToDetail(val photo: PhotoGallery) : PhotosUiEffect
    data class ShowErrorDialog(val throwable: Throwable) : PhotosUiEffect
}
