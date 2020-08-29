package com.seadev.aksi

import android.util.Log
import com.seadev.aksi.model.dataapi.DataHarian
import com.seadev.aksi.model.requestbody.ItemDataHarian
import com.seadev.aksi.rest.ApiClientNasional
import com.seadev.aksi.rest.ApiInterfaceNasional
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    private var dataHarianList: List<DataHarian>? = null

    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2.toLong())
    }

    @Test
    fun testDate() {
        val date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(java.lang.Long.valueOf("1592265600000")),
                ZoneId.systemDefault()
        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        println(date)
        val date1 = Date(java.lang.Long.valueOf("1592265600000"))
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val mDate = formatter.format(date1)
        val now = formatter.format(Date())
        println(now)
        println(mDate)
        if (now == date) {
            println("Same day")
        }
    }

    @Test
    fun testData() {
        val apiServiceNasional = ApiClientNasional.getClientNasional().create(ApiInterfaceNasional::class.java)
        val call = apiServiceNasional.dataHarian
        call.enqueue(object : Callback<ItemDataHarian?> {
            override fun onResponse(call: Call<ItemDataHarian?>, response: Response<ItemDataHarian?>) {
                assert(response.body() != null)
                val dataHarianList = response.body()!!.dataHarianList
                var index = 0
                for (dataHarian in dataHarianList) {
                    val date1 = Date(dataHarian.attributes.tgl)
                    val formatter = SimpleDateFormat("yyyy-MM-dd")
                    val mDate = formatter.format(date1)
                    val now = formatter.format(Date())
                    println("mDate: $mDate")
                    println("Now: $now")
                    if (now == mDate) {
                        println("Same day")
                        if (dataHarian.attributes.kasusTotal != 0) {
                            index++
                            break
                        } else {
                            break
                        }
                    } else {
                        index++
                    }
                }
                val dataHarian = dataHarianList.get(index - 1)
                println("Total: " + dataHarian.attributes.kasusTotal)
            }

            override fun onFailure(call: Call<ItemDataHarian?>, t: Throwable) {
                Log.d("MainActivity", "error: $t")
            }
        })
    }

    @Test
    fun testPrecentace() {
        val inCare = (22797.toDouble() / 1000)
        val cured = (72881.toDouble() / 1000)
        val die = (4322.toDouble() / 1000)
        val newincare = BigDecimal(inCare).setScale(2, RoundingMode.HALF_EVEN)
        val newcured = BigDecimal(cured).setScale(2, RoundingMode.HALF_EVEN)
        val newdie = BigDecimal(die).setScale(2, RoundingMode.HALF_EVEN)

        Assert.assertEquals("In Care", 22.80.toFloat(), newincare.toFloat())
        Assert.assertEquals("Recovered", 72.88.toFloat(), newcured.toFloat())
        Assert.assertEquals("Die", 4.32.toFloat(), newdie.toFloat())
        val total = newincare + newcured + newdie
        Assert.assertEquals("Total Percentage", 100, total.toInt())
    }
}