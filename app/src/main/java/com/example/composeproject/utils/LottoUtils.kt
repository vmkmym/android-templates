package com.example.composeproject.utils

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

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