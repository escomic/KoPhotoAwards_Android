package com.devsimtaku.kophoto.feature.photodetail.contract

sealed interface PhotoDetailUiEvent {
    data class OnKeywordClick(val keyword: String) : PhotoDetailUiEvent
    data class OnImageClick(val imageUrl: String, val title: String?) : PhotoDetailUiEvent
}
