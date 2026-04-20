package com.devsimtaku.kophoto.feature.rewards.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.devsimtaku.kophoto.feature.rewards.RewardsScreen

fun EntryProviderScope<NavKey>.rewardsEntry(
    onPhotoClick: (String) -> Unit
) {
    entry<RewardsNavKey> {
        RewardsScreen(
            onPhotoClick = onPhotoClick
        )
    }
}
