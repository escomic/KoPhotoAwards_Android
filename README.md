# KoPhotoAwards (한국 사진 공모전 & 관광 갤러리) 📸

한국 관광공사 등의 사진 공모전 당선작 및 고화질 관광 사진 정보를 한눈에 확인하고 관리할 수 있는 안드로이드 애플리케이션입니다.

## 📺 데모 영상

<img src="docs/kophoto.gif" width="300" alt="KoPhotoAwards 데모 영상" />

## 🎯 프로젝트 개요
- **목적**: 한국의 아름다운 사진 공모전 수상작 및 관광지 사진 제공
- **주요 타겟**: 사진 애호가, 한국 관광 정보가 필요한 사용자
- Figma : https://www.figma.com/design/PxYJdwVGRI0FR66Z45E0XG/KoPhotos?node-id=1-2058&t=4wl4DDwkE0OvF5sE-1

## ✨ 주요 기능
- **사진 리스트**: 수상작 및 일반 관광 사진 갤러리 탐색
- **상세 정보**: 사진 제목, 장소, 촬영일, 사진가 정보 및 설명 제공
- **이미지 뷰어**: 고화질 이미지 확대/축소 및 상세 감상
- **검색**: 키워드를 활용한 통합 사진 검색 기능
- **북마크**: 관심 있는 사진을 Local DB(Room)에 저장하고 모아보기

## 🏗 아키텍처 (Architecture)
본 프로젝트는 **안드로이드 권장 앱 아키텍처**를 기반으로 하며, 확장성과 유지보수성을 고려하여 설계되었습니다.

### 🧩 Multi-module 구조
- `:app`: 애플리케이션 진입점 및 Hilt 의존성 주입 설정
- `:feature-root:home`: 하단 탭 내비게이션을 포함한 메인 홈 구성
- `:feature:*`: 기능 단위 모듈 (Photos, Rewards, Detail, Search, Bookmarks, ImageViewer)
- `:core:domain`: 순수 비즈니스 로직 및 UseCase, 도메인 모델
- `:core:data`: Repository 구현 및 Local/Remote 데이터 소스 관리
- `:core:network`: API 통신(Retrofit2) 설정 및 네트워크 로직
- `:core:designsystem`: 공통 UI 컴포넌트, 테마(Theme), 리소스
- `:core:navigation`: **Navigation3** 기반의 화면 이동 인터페이스
- `:core:ui`: 공통 UI 유틸리티 및 Base 클래스

### 📐 디자인 패턴: MVI (Model-View-Intent)
단방향 데이터 흐름(UDF)을 보장하는 MVI 패턴을 사용하여 예측 가능한 상태 관리를 수행합니다.
- **State**: UI가 표현할 단일 상태
- **Intent**: 사용자 액션 및 시스템 이벤트
- **Effect**: Navigation, Toast 등 단발성 이벤트 처리

## 🛠 기술 스택 (Tech Stack)
- **UI**: Jetpack Compose
- **Language**: Kotlin
- **Navigation**: Navigation3
- **Dependency Injection**: Hilt
- **Asynchronous**: Coroutines & Flow
- **Network**: Retrofit2 & KotlinX Serialization
- **Local DB**: Room (Kotlin Code Generation 적용)

## 🚀 시작하기
1. 프로젝트를 클론합니다.
   ```bash
   git clone https://github.com/escomic/KoPhotoAwards_Android.git
   ```
2. Android Studio (Ladybug 이상 권장)에서 프로젝트를 엽니다.
3. 필요한 경우 `local.properties`에 API 키 설정을 확인합니다.
4. 빌드 및 실행합니다.

---
