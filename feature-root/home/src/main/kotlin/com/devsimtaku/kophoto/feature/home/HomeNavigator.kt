package com.devsimtaku.kophoto.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import com.devsimtaku.kophoto.core.navigation.NavigationState

/**
 * HomeScreen 내부의 탭 전환 및 상위 레벨 백스택 관리를 전담하는 내비게이터.
 *
 * 이 클래스는 오직 하단 내비게이션 탭(갤러리, 수상작) 간의 이동과
 * 앱의 기본 뒤로가기 정책인 "Exit through Home" 패턴을 관리하는 데 집중합니다.
 * 탭 이동 외의 복잡한 화면 전환 기능은 포함하지 않으며, 확장 기능을 지양합니다.
 */
internal class HomeNavigator(
    private val navigationState: NavigationState
) {
    private val currentTopLevelKey: NavKey
        get() = navigationState.currentTopLevelKey

    /**
     * 탭 간 이동을 수행합니다.
     * 동일한 탭 선택 시 해당 탭의 상태를 초기화하고, 홈(시작) 탭 이동 시 다른 스택을 정리합니다.
     */
    fun navigate(navKey: NavKey) {
        val stack = navigationState.topLevelStack

        when (navKey) {
            currentTopLevelKey -> {
                // 이미 선택된 탭을 다시 누르면 해당 탭의 서브 스택을 루트까지 비워 초기 상태로 되돌림
                val subStack = navigationState.subStacks[navKey]
                while (subStack != null && subStack.size > 1) {
                    subStack.removeAt(subStack.size - 1)
                }
            }

            navigationState.startKey -> {
                // 홈(시작) 탭으로 이동 시 "Exit through Home" 패턴을 위해 상위 스택을 모두 비움
                while (stack.size > 1) {
                    stack.removeAt(stack.size - 1)
                }
                // 홈 탭의 서브 스택도 초기화하여 첫 화면 노출 보장
                val subStack = navigationState.subStacks[navKey]
                while (subStack != null && subStack.size > 1) {
                    subStack.removeAt(subStack.size - 1)
                }
            }

            else -> {
                // 타 탭으로 이동 시, 홈 탭 위에 현재 탭 하나만 유지하도록 관리
                if (stack.size > 1) {
                    stack[stack.size - 1] = navKey
                } else {
                    stack.add(navKey)
                }
            }
        }
    }

    /**
     * 뒤로가기 동작을 제어합니다.
     * 탭 내부의 상세 화면이 있다면 먼저 닫고, 그렇지 않으면 홈 탭으로 이동하거나 종료를 결정합니다.
     */
    fun goBack(): Boolean {
        val stack = navigationState.topLevelStack

        // 1. 현재 활성화된 탭 내부(서브 스택)에 상세 화면이 있다면 먼저 제거
        val currentSubStack = navigationState.subStacks[currentTopLevelKey]
        if (currentSubStack != null && currentSubStack.size > 1) {
            currentSubStack.removeAt(currentSubStack.size - 1)
            return true
        }

        // 2. 탭 수준에서의 이동: 현재 탭이 홈이 아니라면 홈 탭으로 전환하여 "Exit through Home" 유도
        if (stack.size > 1) {
            stack.removeAt(stack.size - 1)
            return true
        }

        // 3. 홈 탭의 루트인 경우 더 이상 뒤로 갈 곳이 없음 (앱 종료 로직으로 연결)
        return false
    }
}

@Composable
internal fun rememberHomeNavigator(
    navigationState: NavigationState
): HomeNavigator = remember(navigationState) {
    HomeNavigator(navigationState)
}
