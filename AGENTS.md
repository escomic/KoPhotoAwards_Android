# KoPhotos Android Project Guidelines

이 프로젝트는 한국 사진 공모전 당선작 및 일반 관광 사진 갤러리 정보를 제공하는 Android 애플리케이션입니다.

## 🎯 Project Overview
- **목적**: 사진 공모전 당선작 및 관광 사진 전시 및 정보 제공
- **주요 기능**: 사진 리스트(수상작/갤러리) 조회, 사진 상세 정보 확인, 이미지 뷰어(확대/축소), 사진 검색 등

## 🏗 Architecture
프로젝트는 **안드로이드 권장 앱 아키텍처**를 기반으로 하며, **MVI (Model-View-Intent)** 패턴과 **Multi-module** 구조를 채택합니다.

### Module Structure
- `:app`: 애플리케이션의 Entry Point 및 의존성 주입 설정
- `:feature-root:*`: 여러 Feature를 조합하여 최상위 UI를 구성하는 루트 모듈
    - `:feature-root:home`: 하단 탭 내비게이션을 포함한 메인 홈 화면
- `:feature:*`: 기능 단위 모듈 (UI 및 ViewModel 포함)
    - `:feature:photos`: 일반 사진 갤러리 목록 화면
    - `:feature:rewards`: 공모전 수상작 목록 화면
    - `:feature:photodetail`: 사진 상세 정보 화면
- `:core:*`: 공통 기능 모듈
    - `:core:domain`: 비즈니스 로직 및 UseCase, 도메인 모델
    - `:core:data`: 리포지토리 구현 및 데이터 소스 (Local/Remote)
    - `:core:network`: API 통신 설정 및 네트워크 관련 로직
    - `:core:designsystem`: 공통 UI 컴포넌트, 테마, 리소스
    - `:core:navigation`: **Navigation3** 기반의 화면 이동 로직
    - `:core:ui`: 공통 UI 관련 유틸리티 및 베이스 클래스

### Design Pattern: MVI
각 Feature의 상태 관리는 MVI 패턴을 따릅니다.
- **State**: UI가 표시할 단일 상태 객체
- **Intent (Event)**: 사용자의 액션 또는 시스템 이벤트
- **Effect (Side Effect)**: Toast, Navigation 등 단발성 이벤트
- 모든 `ViewModel`은 `core:ui` 모듈의 `BaseViewModel`을 상속받아 구현해야 합니다.

## 🛠 Tech Stack
- **UI**: Jetpack Compose
- **Language**: Kotlin
- **Navigation**: Navigation3
- **Dependency Injection**: Hilt (권장)
- **Asynchronous**: Coroutines & Flow
- **Network**: Retrofit2 (또는 Ktor)
- **Serialization**: KotlinX Serialization

## 📝 Development Rules
1. **Surgical Updates**: 코드 변경 시 기존 컨벤션과 스타일을 엄격히 준수합니다.
2. **State Management**: `ViewModel`에서 `StateFlow`를 사용하여 UI State를 노출하고, `collectAsStateWithLifecycle`을 사용하여 Compose에서 구독합니다.
3. **Module Independence & Hierarchy**: 
    - **Feature 간 상호 참조 금지**: `:feature:*` 모듈끼리는 절대 직접 의존하거나 참조하지 않습니다. 화면 이동은 오직 `:core:navigation` 인터페이스를 통해서만 수행합니다.
    - **Feature-Root 역할**: `:feature-root:*` 모듈은 하나 이상의 `:feature:*` 모듈을 의존하여 화면을 조합(Composition)하는 UI 레이어의 최상위 역할을 수행합니다.
    - **Core 독립성**: `:core` 모듈은 어떠한 `:feature` 모듈도 의존하지 않도록 설계합니다.
4. **Testing**: 핵심 비즈니스 로직과 ViewModel의 State 전이는 단위 테스트를 작성하여 검증합니다.
5. **Preview**: 모든 Compose UI 컴포넌트는 `Preview`를 포함하여 UI를 독립적으로 확인할 수 있도록 합니다.
6. **Dependency Management**:
    - `libs.versions.toml`에 새로운 항목 추가 시 알파벳 순서를 유지합니다.
    - `build.gradle.kts`에 의존성 추가 시 알파벳 순서로 정렬하여 가독성을 높입니다.
7. **Import Optimization**: 코드 수정 후에는 사용하지 않는 import 문을 반드시 정리(Optimize Imports)해야 합니다.

## 🗺 Roadmap
- [x] 프로젝트 모듈화 및 내비게이션 구조 설계
- [x] Open API 연동 및 기본 리스트 구현 (Photos, Rewards)
- [x] 사진 상세 정보(PhotoDetail) UI 구현
- [x] 검색 기능 구현
- [x] 이미지 뷰어 기능
- [ ] 관심 사진 저장 (Local DB)
