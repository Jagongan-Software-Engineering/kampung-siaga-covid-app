package com.seadev.aksi.model

import com.google.firebase.Timestamp
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val listOne = listOf(1,0,1)
        val listTwo = listOf(-1,-1,-1)
        val size = listTwo.filterIndexed { index, i -> i == listOne[index] }.size
        println(size)
        val date = Timestamp(Date(1586538000000)).toDate()
        val sdf = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "id"))
        println(sdf.format(date))
    }
}