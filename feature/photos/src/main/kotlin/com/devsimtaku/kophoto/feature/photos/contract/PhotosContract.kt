package com.devsimtaku.kophoto.feature.photos.contract

import androidx.annotation.StringRes
import com.devsimtaku.kophoto.core.domain.model.PhotoGallery
import com.devsimtaku.kophoto.core.ui.R

data class PhotosUiState(
    val selectedSortOption: PhotosSortOption = PhotosSortOption.ModifiedDate
)

enum class PhotosSortOption(
    val code: String,
    @param:StringRes val labelResId: Int
) {
    PhotoDate(code = "A", labelResId = R.string.core_sort_photo_date),
    Title(code = "B", labelResId = R.string.core_sort_title),
    ModifiedDate(code = "C", labelResId = R.string.core_sort_modified_date)
}

sealed interface PhotosUiEvent {
    data class OnPhotoClick(val photo: PhotoGallery) : PhotosUiEvent
    data class OnSortOptionSelected(val option: PhotosSortOption) : PhotosUiEvent
}

sealed interface PhotosUiEffect {
    data class NavigateToDetail(val photo: PhotoGallery) : PhotosUiEffect
    data class ShowErrorDialog(val throwable: Throwable) : PhotosUiEffect
}
