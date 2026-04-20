package com.devsimtaku.kophoto.feature.rewards

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.devsimtaku.kophoto.core.domain.repository.KoPhotoRepository
import com.devsimtaku.kophoto.core.ui.mvi.BaseViewModel
import com.devsimtaku.kophoto.feature.rewards.contract.RewardsUiEffect
import com.devsimtaku.kophoto.feature.rewards.contract.RewardsUiEvent
import com.devsimtaku.kophoto.feature.rewards.contract.RewardsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RewardsViewModel @Inject constructor(
    private val koPhotoRepository: KoPhotoRepository
) : BaseViewModel<RewardsUiState, RewardsUiEvent, RewardsUiEffect>(
    RewardsUiState()
) {
    val rewards = koPhotoRepository.getPhotoAwardList()
        .cachedIn(viewModelScope)

    override fun handleEvent(event: RewardsUiEvent) {
        when (event) {
            is RewardsUiEvent.OnPhotoClick -> {
                sendEffect(RewardsUiEffect.NavigateToDetail(event.id))
            }
        }
    }
}
