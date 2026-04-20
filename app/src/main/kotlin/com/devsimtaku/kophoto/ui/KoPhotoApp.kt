package com.devsimtaku.kophoto.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.devsimtaku.kophoto.core.navigation.Navigator
import com.devsimtaku.kophoto.core.navigation.rememberNavigationState
import com.devsimtaku.kophoto.core.navigation.toBackStack
import com.devsimtaku.kophoto.feature.photos.navigation.PhotosNavKey
import com.devsimtaku.kophoto.feature.photos.navigation.photosEntry
import com.devsimtaku.kophoto.feature.rewards.navigation.rewardsEntry
import com.devsimtaku.kophoto.navigation.navigateToPhotoDetail
import com.devsimtaku.kophoto.photodetail.navigation.photoDetailEntry

@PreviewScreenSizes
@Composable
fun KoPhotoApp() {
    val navigationState = rememberNavigationState(
        startKey = PhotosNavKey,
        topLevelKeys = remember {
            setOf(
                PhotosNavKey
            )
        }
    )
    val backStack = navigationState.toBackStack()
    val navigator = remember { Navigator(navigationState) }
    val entryProvider = entryProvider {
        photosEntry(
            onPhotoClick = navigator::navigateToPhotoDetail
        )
        rewardsEntry(
            onPhotoClick = navigator::navigateToPhotoDetail
        )
        photoDetailEntry(
            onBackClick = navigator::goBack
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NavDisplay(
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
                rememberViewModelStoreNavEntryDecorator<NavKey>(),
            ),
            onBack = navigator::goBack,
            entryProvider = entryProvider
        )
    }
}