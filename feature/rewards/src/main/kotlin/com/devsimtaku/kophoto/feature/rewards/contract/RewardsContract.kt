package com.devsimtaku.kophoto.feature.rewards.contract

import com.devsimtaku.kophoto.core.domain.model.PhotoAward

class RewardsUiState()

sealed interface RewardsUiEvent {
    data class OnPhotoClick(val reward: PhotoAward) : RewardsUiEvent
}

sealed interface RewardsUiEffect {
    data class NavigateToDetail(val reward: PhotoAward) : RewardsUiEffect
    data class ShowErrorDialog(val throwable: Throwable) : RewardsUiEffect
}
