# KoPhotos Android Project Guidelines

이 프로젝트는 한국 사진 공모전 당선작 및 일반 관광 사진 갤러리 정보를 제공하는 Android 애플리케이션입니다.

## 🎯 Project Overview
- **목적**: 사진 공모전 당선작 및 관광 사진 전시 및 정보 제공
- **주요 기능**: 사진 리스트(수상작/갤러리) 조회, 사진 상세 정보 확인, (향후) 촬영 장소 지도 표시 등

## 🏗 Architecture
프로젝트는 **안드로이드 권장 앱 아키텍처**를 기반으로 하며, **MVI (Model-View-Intent)** 패턴과 **Multi-module** 구조를 채택합니다.

### Module Structure
- `:app`: 애플리케이션의 Entry Point 및 의존성 주입 설정
- `:feature:*`: 기능 단위 모듈 (UI 및 ViewModel 포함)
    - `:feature:photos`: 사진 목록 화면
    - `:feature:photodetail`: 사진 상세 화면
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
3. **Module Independence**: 
    - 모듈 간 의존성을 최소화하고, `core` 모듈은 `feature` 모듈을 의존하지 않도록 설계합니다.
    - **Feature와 Feature 모듈 간 참조는 하지 않습니다.** (화면 이동 시 `:core:navigation` 모듈의 인터페이스를 활용합니다.)
4. **Testing**: 핵심 비즈니스 로직과 ViewModel의 State 전이는 단위 테스트를 작성하여 검증합니다.
5. **Preview**: 모든 Compose UI 컴포넌트는 `Preview`를 포함하여 UI를 독립적으로 확인할 수 있도록 합니다.
6. **Dependency Management**:
    - `libs.versions.toml`에 새로운 항목 추가 시 알파벳 순서를 유지합니다.
    - `build.gradle.kts`에 의존성 추가 시 알파벳 순서로 정렬하여 가독성을 높입니다.

## 🗺 Roadmap
- [ ] Open API 연동 및 기본 리스트 구현
- [ ] 사진 상세 정보 및 고해상도 이미지 보기
- [ ] 지도 API 연동 (사진 촬영 장소 표시)
- [ ] 관심 사진 저장 (Local DB)
