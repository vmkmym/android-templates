package com.example.composeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeproject.ui.theme.ComposeProjectTheme
import com.example.composeproject.utils.generateLottoNumbers
import com.example.composeproject.utils.random
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GenerateLottoButton()
                }
            }
        }
    }
}


@Composable
fun GenerateLottoButton() {
    var lottoNumbers by remember { mutableStateOf(listOf<Int>()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            lottoNumbers.forEach { number -> LottoBall(number) }
        }
        Button(
            modifier = Modifier
                .padding(bottom = 40.dp)
                .width(140.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                lottoNumbers = generateLottoNumbers()
            }
        ) {
            Text("로또 번호 생성")
        }
    }
}

@Composable
fun LottoBall(number: Int) {
    var animateValue by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(number) {
        animateValue = 0f
        animateValue = 0.3f
        animateValue = 0.6f
        animateValue = 0.9f
        animateValue = 1f
    }

    val animatedScale by animateFloatAsState(
        label = "animatedScale",
        targetValue = animateValue,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .background(Color.random(), CircleShape)
            .scale(animatedScale)
    ) {
        Text(
            text = number.toString(),
            color = Color.White,
            fontSize = 20.sp
        )
    }
}