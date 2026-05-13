package com.devsimtaku.kophoto.feature.rewards

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.devsimtaku.kophoto.core.domain.model.PhotoAward
import com.devsimtaku.kophoto.core.domain.repository.KoPhotoRepository
import com.devsimtaku.kophoto.core.ui.mvi.BaseViewModel
import com.devsimtaku.kophoto.feature.rewards.contract.RewardsUiEffect
import com.devsimtaku.kophoto.feature.rewards.contract.RewardsUiEvent
import com.devsimtaku.kophoto.feature.rewards.contract.RewardsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

@HiltViewModel
class RewardsViewModel @Inject constructor(
    private val koPhotoRepository: KoPhotoRepository
) : BaseViewModel<RewardsUiState, RewardsUiEvent, RewardsUiEffect>(
    RewardsUiState()
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    val rewards: Flow<PagingData<PhotoAward>> = uiState
        .map { it.selectedSortOption }
        .distinctUntilChanged()
        .flatMapLatest { sortOption ->
            koPhotoRepository.getPhotoAwardList(arrange = sortOption.code)
        }
        .cachedIn(viewModelScope)

    override fun handleEvent(event: RewardsUiEvent) {
        when (event) {
            is RewardsUiEvent.OnPhotoClick -> {
                sendEffect(RewardsUiEffect.NavigateToDetail(event.reward))
            }
            is RewardsUiEvent.OnSortOptionSelected -> {
                if (uiState.value.selectedSortOption != event.option) {
                    setState { copy(selectedSortOption = event.option) }
                }
            }
        }
    }
}
