# 4주차 과제
제출일자 : 22/02/3024

## 요구사항
'다음' 버튼을 누르면 이미지가 변경되는 동작을 Animation을 포함해 구현해주세요.

## 목적
Compose를 활용한 Animation 적용

## 필수조건
Kotlin, Compose, [Crossfade](https://developer.android.com/jetpack/compose/animation?hl=ko#crossfade)

## gif
<img src="https://github.com/vmkmym/android-templates/assets/71699054/a745fc50-fb12-4323-b19d-39c6b69fe864" width="300" />

## 구현 영상
[crossfade](https://github.com/vmkmym/android-templates/assets/71699054/6dac17da-b7e9-43d3-952c-ea0a1245c625)

## 추가
- 이전 버튼을 추가해서 이전 이미지를 볼 수 있게 함
- 앱의 생명주기 onStart를 사용함

## Crossfade
- 크로스페이드 애니메이션을 사용하여 두 레이아웃 사이의 전환을 애니메이션 처리
- current 매개변수로 전달된 값을 전환하면 콘텐츠가 크로스페이드 애니메이션을 사용하여 전환됨

```Kotlin
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <T> Crossfade(
    targetState: T,
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Float> = tween(),
    label: String = "Crossfade",
    content: @Composable (T) -> Unit
) {
    val transition = updateTransition(targetState, label)
    transition.Crossfade(modifier, animationSpec, content = content)
}
```

## 공식 문서에는 when 사용했지만 index를 이용한 이유
- 이미지 리스트에서 직접 이미지 가져올 수 있어서 코드가 간결해짐 (간결성)
- 이미지 리스트에 이미지를 추가하거나 제거해도 인덱스를 사용하는 로직은 변경할 필요 없음 (확장성)
- 인덱스를 사용하면 이미지 리스트 크기에 관계없이 일정한 시간이 걸림 (성능)
```Kotlin
var currentPage by remember { mutableStateOf("A") }
Crossfade(targetState = currentPage) { screen ->
    when (screen) {
        "A" -> Text("Page A")
        "B" -> Text("Page B")
    }
}
```
