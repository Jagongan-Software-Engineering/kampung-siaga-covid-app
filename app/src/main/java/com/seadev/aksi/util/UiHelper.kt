package com.seadev.aksi.util

import java.math.BigDecimal
import java.math.RoundingMode

object UiHelper {
    fun toTitleCase(str: String?): String? {
        if (str == null) {
            return null
        }
        var space = true
        val builder = StringBuilder(str)
        val len = builder.length
        for (i in 0 until len) {
            val c = builder[i]
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c))
                    space = false
                }
            } else if (Character.isWhitespace(c)) {
                space = true
            } else {
                builder.setCharAt(i, Character.toLowerCase(c))
            }
        }
        return builder.toString()
    }

    fun getPercentage(number: Int): Float {
        val data = number.toDouble() / 1000
        return BigDecimal(data).setScale(2, RoundingMode.HALF_EVEN).toFloat()
    }
}