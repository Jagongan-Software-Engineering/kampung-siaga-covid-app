package com.seadev.kampungsiagacovid.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.seadev.kampungsiagacovid.R;
import com.seadev.kampungsiagacovid.model.dataapi.IdDesa;
import com.seadev.kampungsiagacovid.model.dataapi.IdKecamatan;
import com.seadev.kampungsiagacovid.model.dataapi.IdKotaKab;
import com.seadev.kampungsiagacovid.model.dataapi.IdProvinsi;
import com.seadev.kampungsiagacovid.model.requestbody.ItemIdDesa;
import com.seadev.kampungsiagacovid.model.requestbody.ItemIdKecamatan;
import com.seadev.kampungsiagacovid.model.requestbody.ItemIdKotaKab;
import com.seadev.kampungsiagacovid.model.requestbody.ItemIdProvinsi;
import com.seadev.kampungsiagacovid.rest.ApiClientLokasi;
import com.seadev.kampungsiagacovid.rest.ApiInterfaceFirebase;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_nama_lengkap)
    EditText etNama;
    @BindView(R.id.et_alamat)
    EditText etAlamat;
    @BindView(R.id.et_rt_rw)
    EditText etRtRw;
    @BindView(R.id.et_no_telp)
    EditText etNoTelp;
    @BindView(R.id.sp_provinsi)
    AppCompatSpinner spProvinsi;
    @BindView(R.id.sp_kota_kab)
    AppCompatSpinner spKotaKab;
    @BindView(R.id.sp_kecamatan)
    AppCompatSpinner spKecamatan;
    @BindView(R.id.sp_desa)
    AppCompatSpinner spDesa;
    private ApiInterfaceFirebase apiServiceLokasi = ApiClientLokasi.getClientLokasi().create(ApiInterfaceFirebase.class);
    private List<IdProvinsi> provinsiList;
    private List<IdKotaKab> kotaKabList;
    private List<IdKecamatan> kecamatanList;
    private List<IdDesa> desaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
        loadDataProvinsi();
    }

    private void initView() {
        List<String> exampleList = new ArrayList<>();
        exampleList.add("Provinsi");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exampleList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProvinsi.setAdapter(adapter);

        exampleList = new ArrayList<>();
        exampleList.add("Kabupaten / Kota");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exampleList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKotaKab.setAdapter(adapter);

        exampleList = new ArrayList<>();
        exampleList.add("Kacamatan");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exampleList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKecamatan.setAdapter(adapter);

        exampleList = new ArrayList<>();
        exampleList.add("Desa / Kelurahan");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exampleList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDesa.setAdapter(adapter);
    }

    private void loadDataProvinsi() {
        provinsiList = new ArrayList<>();
        Call<ItemIdProvinsi> call = apiServiceLokasi.getDataProvinsi();
        call.enqueue(new Callback<ItemIdProvinsi>() {
            @Override
            public void onResponse(Call<ItemIdProvinsi> call, Response<ItemIdProvinsi> response) {
                assert response.body() != null;
                provinsiList = response.body().getProvinsiList();
                setDataSpinerProvinsi(provinsiList);
            }

            @Override
            public void onFailure(Call<ItemIdProvinsi> call, Throwable t) {
                Log.d("Register Activity", "error: " + t.getMessage());
            }
        });
    }

    private void loadDataKotaKab(String idProvinsi) {
        kotaKabList = new ArrayList<>();
        Call<ItemIdKotaKab> call = apiServiceLokasi.getDataKotaKab();
        call.enqueue(new Callback<ItemIdKotaKab>() {
            @Override
            public void onResponse(Call<ItemIdKotaKab> call, Response<ItemIdKotaKab> response) {
                assert response.body() != null;
                kotaKabList = response.body().getIdKotaKabList();
                setDataSpinerKotaKab(kotaKabList, idProvinsi);
            }

            @Override
            public void onFailure(Call<ItemIdKotaKab> call, Throwable t) {

            }
        });
    }

    private void loadDataKecamatan(String id) {
        kecamatanList = new ArrayList<>();
        Call<ItemIdKecamatan> call = apiServiceLokasi.getDataKecamatan();
        call.enqueue(new Callback<ItemIdKecamatan>() {
            @Override
            public void onResponse(Call<ItemIdKecamatan> call, Response<ItemIdKecamatan> response) {
                assert response.body() != null;
                kecamatanList = response.body().getIdKecamatanList();
                setDataSpinerKecamatan(kecamatanList, id);
            }

            @Override
            public void onFailure(Call<ItemIdKecamatan> call, Throwable t) {

            }
        });
    }

    private void loadDataDesa(String id) {
        desaList = new ArrayList<>();
        Call<ItemIdDesa> call = apiServiceLokasi.getDataDesa();
        call.enqueue(new Callback<ItemIdDesa>() {
            @Override
            public void onResponse(Call<ItemIdDesa> call, Response<ItemIdDesa> response) {
                assert response.body() != null;
                desaList = response.body().getIdDesaList();
                setDataSpinerDesa(desaList, id);

            }

            @Override
            public void onFailure(Call<ItemIdDesa> call, Throwable t) {

            }
        });
    }

    private void setDataSpinerProvinsi(List<IdProvinsi> idProvinsis) {
        List<String> mProvinsiList = new ArrayList<>();
        for (IdProvinsi provinsi : idProvinsis) mProvinsiList.add(provinsi.getName());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mProvinsiList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProvinsi.setAdapter(adapter);
        spProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadDataKotaKab(idProvinsis.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDataSpinerKotaKab(List<IdKotaKab> idKotaKabs, String idProvinsi) {
        List<String> mList = new ArrayList<>();
        List<String> mId = new ArrayList<>();
        for (IdKotaKab idKotaKab : idKotaKabs) {
            if (idKotaKab.getIdProvinsi().equals(idProvinsi)) {
                mList.add(idKotaKab.getName());
                mId.add(idKotaKab.getId());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKotaKab.setAdapter(adapter);
        spKotaKab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadDataKecamatan(mId.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDataSpinerKecamatan(List<IdKecamatan> idKecamatans, String idKotaKab) {
        List<String> mList = new ArrayList<>();
        List<String> mId = new ArrayList<>();
        for (IdKecamatan idKecamatan : idKecamatans) {
            if (idKecamatan.getIdKotaKab().equals(idKotaKab)) {
                mList.add(idKecamatan.getName());
                mId.add(idKecamatan.getId());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKecamatan.setAdapter(adapter);
        spKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadDataDesa(mId.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDataSpinerDesa(List<IdDesa> idDesas, String idKecamatan) {
        List<String> mList = new ArrayList<>();
        List<String> mId = new ArrayList<>();
        for (IdDesa idDesa : idDesas) {
            if (idDesa.getIdKecamatan().equals(idKecamatan)) {
                mList.add(idDesa.getName());
                mId.add(idDesa.getId());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDesa.setAdapter(adapter);
    }

}
