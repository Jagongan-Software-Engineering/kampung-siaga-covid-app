package com.seadev.aksi.model.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.seadev.aksi.model.ui.theme.White

object SkeletonColor {
    private const val colorDefault = 0xFFE2E2EB
    val Default = Color(colorDefault)
}

@Composable
private fun SkeletonContainer(
    modifier: Modifier,
    enableShimmer: Boolean,
    shimmerColor: Color
) {
    if (enableShimmer) Box(modifier = modifier.background(shimmerColor)) {
        Box(
            modifier = modifier
                .shimmer()
                .background(SkeletonColor.Default)
        )
    } else Box(modifier = modifier.background(SkeletonColor.Default))
}

@Composable
fun CircleSkeleton(
    modifier: Modifier = Modifier, size: Dp,
    enableShimmer: Boolean = false,
    shimmerColor: Color = White
) {
    SkeletonContainer(
        modifier
            .size(size)
            .clip(CircleShape),
        enableShimmer, shimmerColor
    )
}

@Composable
fun RoundedSkeleton(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    radius: Dp,
    enableShimmer: Boolean = false,
    shimmerColor: Color = White
) {
    SkeletonContainer(
        modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(radius)),
        enableShimmer, shimmerColor
    )
}

@Composable
fun RoundedSkeleton(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    enableShimmer: Boolean = false,
    shimmerColor: Color = White
) {
    SkeletonContainer(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(height / 2)),
        enableShimmer, shimmerColor
    )
}
