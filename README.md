# TodoList app + DB 구현
- Live 프로젝트 관리 훈련
- 기존 프로젝트에 git을 활용, 변경사항을 어떻게 공유할지 고민하기

## Description
1. viewModel, ViewModelFactory : UI 관련 데이터 관리
2. Room Database : Android에서 제공하는 SQLite에 대한 추상화 레이어
3. Repository : 데이터 액세스 캡슐화
4. Graph : 의존성 그래프, 앱 전체에서 db, repo 접근 가능
+ todo 생성만 해놓고 update, delete는 아직 미구현!!

### 영상
[Screen_recording_20240325_184629.webm](https://github.com/vmkmym/android-templates/assets/71699054/1a56bad0-0758-447b-aea8-22fe0ac34ae9)

### Trouble Shooting (자세한 건 개인 노션에 정리)
1. 룸 버전, 코틀린 버전, 컴파일러 버전 맞추기: 프로젝트의 `build.gradle` 파일에서 각각의 버전을 확인하고 필요한 경우 업데이트를 진행했습니다. 이를 통해 라이브러리 간의 호환성 문제를 해결할 수 있었습니다.

2. 체크 버튼의 상태가 UI에 제대로 반영되지 않음: 이 문제는 `ViewModel`에서 상태를 관리하고 UI에 바인딩하는 과정에서 발생했습니다. `LiveData`나 `Flow`를 사용하여 상태를 관리하고, 이를 `observe`하거나 `collect`하여 UI에 반영하도록 수정하였습니다.

3. Repository의 역할과 ViewModel과의 차이점: `Repository`는 데이터 액세스를 캡슐화하는 역할을 합니다. 반면에, `ViewModel`은 UI와 관련된 데이터를 관리하는 역할을 합니다. 이 두 개념의 차이점을 이해하고 적절하게 사용함으로써, 코드의 가독성과 유지 관리성을 향상시킬 수 있었습니다.

4. Graph의 역할: `Graph`는 애플리케이션의 의존성 그래프를 나타냅니다. 이 프로젝트에서는 `Graph`를 통해 `TodoRepository`의 인스턴스를 제공하고 있습니다. 이 인스턴스는 `Room` 데이터베이스와 상호작용하는 데 사용됩니다.

5. LazyColumn 중첩 문제로 인한 FAB 버튼 반응x: `LazyColumn` 내부에 다른 `LazyColumn`이나 `ScrollView`를 중첩하면 스크롤 동작에 문제가 발생할 수 있습니다. 이 문제를 해결하기 위해 중첩을 제거하고 필요한 경우 `LazyColumn`의 `header`나 `footer`를 사용하도록 수정하였습니다.

6. Database 버전 변경 후 migration 이동 경로 누락 문제: `Room` 데이터베이스의 스키마가 변경되면 `Migration` 클래스를 사용하여 이전 버전의 데이터베이스에서 새 버전의 데이터베이스로 이동하는 경로를 제공해야 합니다. 이 경로가 누락되면 앱이 충돌할 수 있습니다. 이 문제를 해결하기 위해 필요한 `Migration` 클래스를 추가하고, `Room.databaseBuilder`에서 이를 설정하였습니다.
