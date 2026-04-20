package com.devsimtaku.kophoto.feature.rewards.contract

class RewardsUiState

sealed interface RewardsUiEvent {
    data class OnPhotoClick(val id: String) : RewardsUiEvent
}

sealed interface RewardsUiEffect {
    data class NavigateToDetail(val id: String) : RewardsUiEffect
}
