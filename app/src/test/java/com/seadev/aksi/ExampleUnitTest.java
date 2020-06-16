package com.seadev.aksi;

import android.util.Log;

import com.seadev.aksi.model.dataapi.DataHarian;
import com.seadev.aksi.model.requestbody.ItemDataHarian;
import com.seadev.aksi.rest.ApiClientNasional;
import com.seadev.aksi.rest.ApiInterfaceNasional;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    private List<DataHarian> dataHarianList;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDate() {
        String date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(Long.valueOf("1592265600000")),
                ZoneId.systemDefault()
        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        System.out.println(date);

        Date date1 = new Date(Long.valueOf("1592265600000"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String mDate = formatter.format(date1);
        String now = formatter.format(new Date());

        System.out.println(now);
        System.out.println(mDate);

        if (now.equals(date)) {
            System.out.println("Same day");
        }
    }

    @Test
    public void testData() {
        dataHarianList = new ArrayList<>();
        ApiInterfaceNasional apiServiceNasional = ApiClientNasional.getClientNasional().create(ApiInterfaceNasional.class);
        Call<ItemDataHarian> call = apiServiceNasional.getDataHarian();
        call.enqueue(new Callback<ItemDataHarian>() {
            @Override
            public void onResponse(Call<ItemDataHarian> call, Response<ItemDataHarian> response) {
                assert response.body() != null;
                dataHarianList = response.body().getDataHarianList();
                int index = 0;
                for (DataHarian dataHarian : dataHarianList) {

                    Date date1 = new Date(dataHarian.getAttributes().getTgl());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String mDate = formatter.format(date1);
                    String now = formatter.format(new Date());
                    System.out.println("mDate: " + mDate);
                    System.out.println("Now: " + now);

                    if (now.equals(mDate)) {
                        System.out.println("Same day");
                        if (dataHarian.getAttributes().getKasusTotal() != 0) {
                            index++;
                            break;
                        } else {
                            break;
                        }
                    } else {
                        index++;
                    }
                }
                DataHarian dataHarian = dataHarianList.get(index - 1);
                System.out.println("Total: " + dataHarian.getAttributes().getKasusTotal());
            }

            @Override
            public void onFailure(Call<ItemDataHarian> call, Throwable t) {
                Log.d("MainActivity", "error: " + t);
            }
        });
    }
}