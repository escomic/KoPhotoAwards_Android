package com.devsimtaku.kophoto.feature.photos.contract

import androidx.paging.PagingData
import com.devsimtaku.kophoto.core.domain.model.PhotoGallery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class PhotosUiState(
)

sealed interface PhotosUiEvent {
    data class OnPhotoClick(val id: String) : PhotosUiEvent
}

sealed interface PhotosUiEffect {
    data class NavigateToDetail(val id: String) : PhotosUiEffect
}
