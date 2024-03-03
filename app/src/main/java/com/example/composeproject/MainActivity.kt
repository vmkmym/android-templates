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
                    GenerateLotto()
                }
            }
        }
    }
}

@Composable
fun GenerateLotto() {
    var lottoKey by remember { mutableIntStateOf(0) }
    var lottoNumbers by remember { mutableStateOf(listOf<Int>()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            lottoNumbers.forEach { number -> LottoBall(number, lottoKey) }
        }
        Button(
            modifier = Modifier
                .width(140.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                lottoNumbers = generateLottoNumbers()
                lottoKey++
            }
        ) {
            Text("로또 번호 생성")
        }
    }
}

@Composable
fun LottoBall(number: Int, key: Int) {
    var targetValue by remember { mutableFloatStateOf(0f) }
    val value by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMedium
        ), label = ""
    )
    val currentKey by rememberUpdatedState(key)
    LaunchedEffect(currentKey) { targetValue = 1f }

    Box(
        modifier = Modifier
            .size(50.dp)
            .background(Color.random(), CircleShape)
            .scale(value),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = Color.White,
            fontSize = 20.sp
        )
    }
}

fun generateLottoNumbers(): List<Int> {
    return (1..45).shuffled().take(6)
}

fun Color.Companion.random(): Color {
    return Color(
        red = Random.nextFloat(),
        green = Random.nextFloat(),
        blue = Random.nextFloat()
    )
}