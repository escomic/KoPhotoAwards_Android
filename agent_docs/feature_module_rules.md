# Feature Module Creation Rules (KoPhotos Project)

이 문서는 새로운 Feature 모듈을 추가할 때 AI 에이전트와 개발자가 준수해야 할 핵심 규칙을 정의합니다.

## 1. 모듈 구조 및 명명 규칙
- **위치**: `:feature:name` 또는 `:feature-root:name`
- **패키지**: `com.devsimtaku.kophoto.feature.name`
- **MVI 패턴 준수**: `contract/` 패키지 내에 `UiState`, `UiEvent`, `UiEffect`를 정의합니다.

## 2. Navigation 3 구현 규칙
모든 내비게이션 관련 파일은 해당 모듈의 `navigation` 패키지 내에 위치해야 합니다.

### A. NavKey 정의
- `NavKey` 인터페이스를 상속받는 `@Serializable` data class를 생성합니다.
- 데이터 전달이 필요한 경우 파라미터를 추가합니다.
```kotlin
@Serializable
data class ExampleNavKey(val id: String) : NavKey
```

### B. EntryProvider 정의
- `EntryProviderScope<NavKey>`의 확장 함수로 정의합니다.
- `entry<T>` 함수를 사용하여 해당 Key에 대응하는 Screen을 연결합니다.
```kotlin
fun EntryProviderScope<NavKey>.exampleEntry(
    onAction: () -> Unit
) {
    entry<ExampleNavKey> { key ->
        ExampleScreen(id = key.id, onAction = onAction)
    }
}
```

## 3. 의존성 주입 (Hilt)
- **ViewModel**: `@HiltViewModel`을 사용합니다.
- **Assisted Injection**: 내비게이션 인자(`NavKey`의 데이터)를 ViewModel에 전달할 때는 반드시 `@AssistedInject`와 `Factory`를 사용합니다.
- **Compose**: `hiltViewModel(creationCallback = ...)`을 통해 인자를 주입합니다.

## 4. App 통합 (KoPhotoApp.kt)
새로운 Feature를 추가한 후에는 반드시 `:app` 모듈의 `KoPhotoApp.kt`에 등록해야 합니다.

1. `app/build.gradle.kts`의 `dependencies`에 `implementation(projects.feature.name)`를 추가합니다.
2. `entryProvider` 블록 내에 정의한 `EntryProvider` 확장 함수를 추가합니다.
3. 화면 이동이 필요한 경우 `Navigator` 확장 함수(`NavigatorExt.kt`)를 정의하고 호출합니다.

## 5. Summary Checklist
- [ ] Module created and added to `settings.gradle.kts`
- [ ] `build.gradle.kts` configured with Hilt, Compose, and Nav3
- [ ] `NavKey` defined with `@Serializable`
- [ ] `EntryProvider` extension created
- [ ] MVI Contract (UiState, UiEvent, UiEffect) implemented
- [ ] `ViewModel` with `@AssistedInject` and `AssistedFactory`
- [ ] `Screen` with `hiltViewModel(creationCallback = ...)`
- [ ] Dependency added to `:app` module
- [ ] Entry registered in `KoPhotoApp.kt`

## 6. UI 및 리소스
- 모든 Compose UI는 `Preview`를 포함해야 합니다.
- **문자열 리소스 관리 (Hybrid)**:
    - **공통 문자열**: `Confirm`, `Cancel`, `Back` 등 앱 전역 공용어는 `:core:ui`의 `strings.xml`에 정의하고 `core_` 접두어를 사용한다.
    - **피처 전용 문자열**: 각 화면의 타이틀이나 고유 문구는 해당 모듈 내에 정의하고 `feature_{name}_` 접두어를 사용한다.
- 다국어(기본/KO) 대응을 위해 `values/strings.xml`과 `values-ko/strings.xml`을 모두 생성한다.

---
**주의**: 새로운 모듈 생성 시 이 문서를 먼저 읽고 구조를 설계하십시오.
