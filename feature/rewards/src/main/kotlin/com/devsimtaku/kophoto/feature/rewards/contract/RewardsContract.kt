package com.devsimtaku.kophoto.feature.rewards.contract

import androidx.annotation.StringRes
import com.devsimtaku.kophoto.core.domain.model.PhotoAward
import com.devsimtaku.kophoto.core.ui.R

data class RewardsUiState(
    val selectedSortOption: RewardsSortOption = RewardsSortOption.ModifiedDate
)

enum class RewardsSortOption(
    val code: String,
    @param:StringRes val labelResId: Int
) {
    Title(code = "A", labelResId = R.string.core_sort_title),
    ModifiedDate(code = "C", labelResId = R.string.core_sort_modified_date),
    CreatedDate(code = "D", labelResId = R.string.core_sort_created_date)
}

sealed interface RewardsUiEvent {
    data class OnPhotoClick(val reward: PhotoAward) : RewardsUiEvent
    data class OnSortOptionSelected(val option: RewardsSortOption) : RewardsUiEvent
}

sealed interface RewardsUiEffect {
    data class NavigateToDetail(val reward: PhotoAward) : RewardsUiEffect
    data class ShowErrorDialog(val throwable: Throwable) : RewardsUiEffect
}
