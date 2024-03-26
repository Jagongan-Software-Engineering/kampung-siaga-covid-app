package com.seadev.aksi.model.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AksiColor {
    private val PrimaryColor: @Composable () -> Color =
        { if (isSystemInDarkTheme()) BlueNight else BlueDay }
    private val SecondaryColor: @Composable () -> Color =
        { if (isSystemInDarkTheme()) PinkNight else PinkDay }
    private val AccentColor: @Composable () -> Color =
        { if (isSystemInDarkTheme()) LightPurpleNight else LightPurpleDay }
    private val TextColor: @Composable () -> Color =
        { if (isSystemInDarkTheme()) TextNight else TextDay}
    private val ContainerColor: @Composable () -> Color =
        { if (isSystemInDarkTheme()) BackgroundNight else BackgroundDay}
    private val BackgroundColor: @Composable () -> Color =
        { if (isSystemInDarkTheme()) Black else White}

    val Primary @Composable get() = PrimaryColor.invoke()
    val Secondary @Composable get() = SecondaryColor.invoke()
    val Accent @Composable get() = AccentColor.invoke()
    val Text @Composable get() = TextColor.invoke()
    val TextLight @Composable get() = if (isSystemInDarkTheme()) TextLightNight else TextLightDay

    val Container @Composable get() = ContainerColor.invoke()
    val Background @Composable get() = BackgroundColor.invoke()
    val Yellow @Composable get() = if (isSystemInDarkTheme()) ChartYellowNight else ChartYellow
    val Green @Composable get() = if (isSystemInDarkTheme()) ChartGreenNight else ChartGreen
    val Red @Composable get() = if (isSystemInDarkTheme()) ChartRedNight else ChartRed
}