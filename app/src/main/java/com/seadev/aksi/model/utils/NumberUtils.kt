package com.seadev.aksi.model.utils

import android.icu.text.NumberFormat
import java.util.Locale

fun Int.formatCaseNumber(): String = NumberFormat.getInstance(Locale.getDefault()).format(this)
fun Float.formatPercentCase(): String = String.format(Locale.getDefault(), "%.1f", this * 100)