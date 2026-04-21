package com.devsimtaku.kophoto.ui

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import com.devsimtaku.kophoto.feature.home.navigation.HomeNavKey
import com.devsimtaku.kophoto.feature.home.navigation.homeEntry
import com.devsimtaku.kophoto.navigation.navigateToPhotoDetail
import com.devsimtaku.kophoto.photodetail.navigation.photoDetailEntry

@PreviewScreenSizes
@Composable
fun KoPhotoApp() {
    val navigationState = rememberNavigationState(
        startKey = HomeNavKey,
        topLevelKeys = remember {
            setOf(
                HomeNavKey
            )
        }
    )
    val backStack = navigationState.toBackStack()
    val navigator = remember { Navigator(navigationState) }
    val entryProvider = entryProvider {
        homeEntry(
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
            entryProvider = entryProvider,
            transitionSpec = {
                slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
            },
            popTransitionSpec = { defaultPopTransform() },
            predictivePopTransitionSpec = { _ -> defaultPopTransform() }
        )
    }
}


/**
 * 공통적으로 사용되는 팝(뒤로 가기) 애니메이션 변환 로직
 */
private fun defaultPopTransform(): ContentTransform {
    return (slideInHorizontally { -it } togetherWith slideOutHorizontally { it }).apply {
        targetContentZIndex = -1f
    }
}