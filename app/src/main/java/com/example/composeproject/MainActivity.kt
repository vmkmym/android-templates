package com.example.composeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeproject.ui.theme.ComposeProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onStart() {
        super.onStart()
        setContent {
            ComposeProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    ChangeImageAnimation()
}

@Composable
fun ChangeImageAnimation() {
    val images = listOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5,
        R.drawable.img6
    )
    var currentImageIndex by remember { mutableIntStateOf(0) }

    fun nextImage() {
        currentImageIndex = (currentImageIndex + 1) % images.size
    }

    fun previousImage() {
        currentImageIndex = (currentImageIndex - 1 + images.size) % images.size
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        // 3. Crossfade 컴포저를 사용하여 이미지를 교체하는 애니메이션을 적용
        Crossfade(
            targetState = currentImageIndex,
            label = "Crossfade between images"
        ) { index ->
            Image(
                painter = painterResource(id = images[index]),
                contentDescription = "Image ${index + 1} out of ${images.size}",
                modifier = Modifier.size(300.dp)
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Button(
                // 1. 버튼을 클릭하면 previousImage 함수가 호출되도록 설정
                onClick = ::previousImage,
                modifier = Modifier.width(80.dp)
            ) {
                Text("이전")
            }
            Button(
                // 2. 버튼을 클릭하면 nextImage 함수가 호출되도록 설정
                onClick = ::nextImage,
                modifier = Modifier.width(80.dp)
            ) {
                Text("다음")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeProjectTheme {
        ChangeImageAnimation()
    }
}