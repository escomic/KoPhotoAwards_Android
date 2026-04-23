package com.devsimtaku.kophoto.feature.search.contract

sealed interface SearchUiEffect {
    data class ShowToast(val message: String) : SearchUiEffect
}
