package com.devsimtaku.kophoto.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devsimtaku.kophoto.core.designsystem.theme.StaticWhite

@Composable
fun KPNavigationSuiteScaffold(
    navigationSuiteItems: KPNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = KPNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = KPNavigationDefaults.navigationContentColor(),
            selectedTextColor = KPNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = KPNavigationDefaults.navigationContentColor(),
            indicatorColor = KPNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = KPNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = KPNavigationDefaults.navigationContentColor(),
            selectedTextColor = KPNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = KPNavigationDefaults.navigationContentColor(),
            indicatorColor = KPNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = KPNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = KPNavigationDefaults.navigationContentColor(),
            selectedTextColor = KPNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = KPNavigationDefaults.navigationContentColor(),
        ),
    )

    NavigationSuiteScaffold(
        modifier = modifier,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            shortNavigationBarContainerColor = StaticWhite,
            navigationBarContainerColor = StaticWhite,
            navigationRailContainerColor = StaticWhite,
            navigationDrawerContainerColor = StaticWhite
        ),
        navigationSuiteItems = {
            KPNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors,
            ).run(navigationSuiteItems)
        }
    ) {
        content()
    }
}

class KPNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors,
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null,
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = {
            if (selected) {
                selectedIcon()
            } else {
                icon()
            }
        },
        label = label,
        colors = navigationSuiteItemColors,
        modifier = modifier,
    )
}

object KPNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.inversePrimary

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.primary

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
}
