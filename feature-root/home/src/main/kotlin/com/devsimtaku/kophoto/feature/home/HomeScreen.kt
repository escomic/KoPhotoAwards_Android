package com.devsimtaku.kophoto.feature.home

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.devsimtaku.kophoto.core.domain.model.PhotoDetail
import com.devsimtaku.kophoto.core.navigation.rememberNavigationState
import com.devsimtaku.kophoto.core.navigation.toBackStack
import com.devsimtaku.kophoto.feature.photos.navigation.PhotosNavKey
import com.devsimtaku.kophoto.feature.photos.navigation.photosEntry
import com.devsimtaku.kophoto.feature.rewards.navigation.RewardsNavKey
import com.devsimtaku.kophoto.feature.rewards.navigation.rewardsEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
    onPhotoClick: (PhotoDetail) -> Unit
) {
    val navigationState = rememberNavigationState(
        startKey = PhotosNavKey,
        topLevelKeys = remember {
            setOf(
                PhotosNavKey,
                RewardsNavKey
            )
        }
    )
    val backStack = navigationState.toBackStack()
    val navigator = rememberHomeNavigator(navigationState)

    NavigationSuiteScaffold(
        modifier = modifier,
        navigationSuiteItems = {
            HomeDestination.entries.forEach { destination ->
                item(
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = destination.label
                        )
                    },
                    label = { Text(text = destination.label) },
                    selected = backStack.last() == destination.navKey,
                    onClick = {
                        navigator.navigate(destination.navKey)
                    }
                )
            }
        }
    ) {
        val entryProvider = remember(onPhotoClick) {
            entryProvider {
                photosEntry(onPhotoClick = onPhotoClick)
                rewardsEntry(onPhotoClick = onPhotoClick)
            }
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "KoPhoto")
                    },
                    actions = {
                        IconButton(onClick = onSearchClick) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavDisplay(
                modifier = Modifier.padding(innerPadding),
                backStack = backStack,
                onBack = {
                    navigator.goBack()
                },
                entryProvider = entryProvider,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                popTransitionSpec = { fadeIn() togetherWith fadeOut() },
                predictivePopTransitionSpec = { fadeIn() togetherWith fadeOut() }
            )
        }
    }
}

private enum class HomeDestination(
    val label: String,
    val icon: ImageVector,
    val navKey: NavKey
) {
    Photos(
        label = "갤러리",
        icon = Icons.Default.PhotoLibrary,
        navKey = PhotosNavKey
    ),
    Rewards(
        label = "수상작",
        icon = Icons.Default.CardGiftcard,
        navKey = RewardsNavKey
    )
}
