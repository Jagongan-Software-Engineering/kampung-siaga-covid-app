package com.seadev.kampungsiagacovid.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.seadev.kampungsiagacovid.R;
import com.seadev.kampungsiagacovid.adapter.ProvinsiAdapter;
import com.seadev.kampungsiagacovid.model.DataHarian;
import com.seadev.kampungsiagacovid.model.DataProvinsi;
import com.seadev.kampungsiagacovid.model.ItemDataHarian;
import com.seadev.kampungsiagacovid.model.ItemDataProvinsi;
import com.seadev.kampungsiagacovid.rest.ApiClientNasional;
import com.seadev.kampungsiagacovid.rest.ApiInterfaceNasional;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_confirmed)
    TextView tvConfirm;
    @BindView(R.id.tv_in_care)
    TextView tvInCare;
    @BindView(R.id.tv_recover)
    TextView tvRecover;
    @BindView(R.id.tv_died)
    TextView tvDied;
    @BindView(R.id.rv_provinsi)
    RecyclerView rvProvinsi;
    private ProvinsiAdapter provinsiAdapter;

    private ApiInterfaceNasional apiServiceNasional = ApiClientNasional.getClientNasional().create(ApiInterfaceNasional.class);
    private List<DataHarian> dataHarianList;
    private List<DataProvinsi> provinsiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadDataNasional();
        initView();
        loadDataProvinsi();
    }

    private void initView() {
        provinsiAdapter = new ProvinsiAdapter(this);
        rvProvinsi.setHasFixedSize(true);
        rvProvinsi.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadDataProvinsi() {
        provinsiList = new ArrayList<>();
        Call<ItemDataProvinsi> call = apiServiceNasional.getDataProvinsi();
        call.enqueue(new Callback<ItemDataProvinsi>() {
            @Override
            public void onResponse(Call<ItemDataProvinsi> call, Response<ItemDataProvinsi> response) {
                assert response.body() != null;
                provinsiList = response.body().getProvinsiList();
                provinsiAdapter.setProvinsiList(provinsiList);
                rvProvinsi.setAdapter(provinsiAdapter);
            }

            @Override
            public void onFailure(Call<ItemDataProvinsi> call, Throwable t) {
                Log.d("MainActivity", "error: " + t);
            }
        });
    }

    private void loadDataNasional() {
        dataHarianList = new ArrayList<>();
        Call<ItemDataHarian> call = apiServiceNasional.getDataHarian();
        call.enqueue(new Callback<ItemDataHarian>() {
            @Override
            public void onResponse(Call<ItemDataHarian> call, Response<ItemDataHarian> response) {
                assert response.body() != null;
                dataHarianList = response.body().getDataHarianList();
                int index = 0;
                for (DataHarian dataHarian : dataHarianList) {
                    if (dataHarian.getAttributes().getKasusTotal() != 0) {
                        index++;
                    } else {
                        break;
                    }
                }
                DataHarian dataHarian = dataHarianList.get(index - 1);
                setDataHarian(dataHarian);
            }

            @Override
            public void onFailure(Call<ItemDataHarian> call, Throwable t) {
                Log.d("MainActivity", "error: " + t);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setDataHarian(DataHarian dataHarian) {
        tvConfirm.setText("Total Kasus: " + dataHarian.getAttributes().getKasusTotal());
        tvRecover.setText("Sembuh: " + dataHarian.getAttributes().getSembuh());
        tvInCare.setText("Dalam Perawatan: " + dataHarian.getAttributes().getDalamPerawatan());
        tvDied.setText("Meninggal: " + dataHarian.getAttributes().getMeninggal());
    }
}
