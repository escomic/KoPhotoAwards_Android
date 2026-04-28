package com.devsimtaku.kophoto.feature.photodetail.contract

sealed interface PhotoDetailUiEffect {
    data class NavigateToSearch(val keyword: String) : PhotoDetailUiEffect
    data class ShowErrorDialog(val throwable: Throwable) : PhotoDetailUiEffect
}
