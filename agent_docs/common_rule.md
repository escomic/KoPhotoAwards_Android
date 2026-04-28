# AI Agent Common Rules

이 규칙은 KoPhoto 프로젝트를 수행하는 모든 AI 에이전트가 준수해야 할 공통 가이드라인입니다. 에이전트는 작업을 시작하기 전 이 규칙을 반드시 숙지해야 합니다.

## 🧪 Unit Testing Principle (핵심 원칙)

1. **신규 기능 및 수정 시 필수 수행**:
    - 새로운 기능을 추가하거나 기존 로직을 수정한 후에는 반드시 관련 유닛 테스트를 수행하여 기능의 정상 동작을 검증해야 합니다.
    - 로직 변경으로 인해 기존 테스트가 실패할 경우, 변경된 비즈니스 로직에 맞게 테스트 코드를 반드시 업데이트해야 합니다.

2. **Git 작업 전 필수 검증**:
    - `git commit` 또는 `git push`와 같은 형상 관리 행동을 하기 전에는 항상 전체 또는 관련 모듈의 유닛 테스트를 수행하여 회귀 오류(Regression)가 없음을 증명해야 합니다.
    - 테스트가 실패하는 상태에서는 절대로 커밋을 생성하지 않습니다.

3. **테스트 코드 작성 권장**:
    - 새로운 비즈니스 로직(ViewModel, Repository, UseCase 등)을 작성할 때는 그에 대응하는 유닛 테스트 코드를 함께 작성하는 것을 원칙으로 합니다.

## 🏗 Architecture & Design Pattern

1. **MVI Pattern 준수**:
    - 모든 UI 레이어는 State, Event(Intent), Effect 구조를 가진 MVI 패턴을 엄격히 따릅니다.
    - `BaseViewModel`을 상속받아 상태 전이를 명확히 관리합니다.

2. **Multi-module 의존성 규칙**:
    - `:feature:*` 모듈 간의 직접적인 참조는 엄격히 금지됩니다.
    - 화면 이동은 반드시 `:core:navigation`에서 정의된 인터페이스와 NavKey를 통해서만 수행합니다.
    - 상위 모듈(예: `:feature-root:home`)이나 `:app` 모듈에서 하위 기능 모듈을 조합합니다.

## 📝 Coding Standards

1. **Surgical Update**: 
    - 코드 수정 시 기존의 명명 규칙, 들여쓰기, 스타일을 완벽하게 준수합니다. 불필요한 대규모 리팩토링은 지양하고 목적에 맞는 정밀한 수정을 우선합니다.
2. **Import Optimization**: 
    - 작업을 마친 후에는 사용하지 않는 import 문을 정리하고 알파벳 순서로 정렬합니다.
3. **Dependency Management**: 
    - 새로운 라이브러리 추가 시 `libs.versions.toml`을 먼저 업데이트하고, 의존성 목록은 가급적 알파벳 순으로 정렬합니다.

## 🔐 Security & Sensitive Information Management

1. **민감 정보 하드코딩 금지**:
    - API 서비스 키, 비밀번호, 클라이언트 비밀키 등 민감한 자격 증명을 소스 코드(`.kt`, `.xml`, `.gradle.kts` 등)에 직접 작성(Hardcoding)하지 않습니다.
2. **설정 파일 활용**:
    - 민감 정보는 `local.properties`나 환경 변수, 별도의 시크릿 관리 도구를 통해 주입받도록 설계하며, 해당 설정 파일이 Git에 추적되지 않도록 `.gitignore`를 철저히 관리합니다.
3. **로그 보안**:
    - 디버깅 목적으로 로그를 출력할 때 사용자 개인 정보, 인증 토큰, 비밀번호 등이 포함되지 않도록 주의합니다.
4. **Credential 누출 확인**:
    - `git commit` 전, 실수로 민감 정보가 포함된 파일이 스테이징되지 않았는지 다시 한번 검토합니다.

## 🔍 Validation Checklist

- [ ] 관련 유닛 테스트가 모두 통과되었는가?
- [ ] MVI 패턴의 상태 관리 원칙을 위배하지 않았는가?
- [ ] **API 키나 비밀번호 등 민감 정보가 소스 코드에 노출되지 않았는가?**
- [ ] 모듈 간 순환 참조나 잘못된 의존성 추가는 없는가?
- [ ] `libs.versions.toml` 및 `build.gradle.kts` 설정이 올바른가?
