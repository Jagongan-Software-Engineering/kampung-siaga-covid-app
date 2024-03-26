package com.seadev.aksi.model.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer


private object Shimmer {
    const val DEFAULT_DURATION = 800
    const val DEFAULT_DELAY = 500
}

fun Modifier.shimmer(): Modifier = composed {
    val shimmer = rememberShimmer(
        shimmerBounds = ShimmerBounds.View,
        theme = createCustomTheme(),
    )
    shimmer(customShimmer = shimmer)
}

private fun createCustomTheme() = defaultShimmerTheme.copy(
    animationSpec = infiniteRepeatable(
        animation = tween(
            Shimmer.DEFAULT_DURATION,
            easing = LinearEasing,
            delayMillis = Shimmer.DEFAULT_DELAY,
        ),
        repeatMode = RepeatMode.Restart,
    ),
    blendMode = BlendMode.DstIn,
    rotation = 15.0f,
    shaderColors = listOf(
        Color.Unspecified.copy(alpha = 1f),
        Color.Unspecified.copy(0.0f),
        Color.Unspecified.copy(alpha = 1f),
    ),
    shaderColorStops = null,
    shimmerWidth = 80.dp,
)
