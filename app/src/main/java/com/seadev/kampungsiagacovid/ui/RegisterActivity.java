package com.seadev.kampungsiagacovid.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import com.seadev.kampungsiagacovid.rest.ApiInterfaceLokasi;

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
    Button ton1;
    private EditText text1, text2, text3, text4;
    private DatabaseReference database;
    private ProgressDialog loading;

    @BindView(R.id.sp_provinsi)
    AppCompatSpinner spProvinsi;
    @BindView(R.id.sp_kota_kab)
    AppCompatSpinner spKotaKab;
    @BindView(R.id.sp_kecamatan)
    AppCompatSpinner spKecamatan;
    @BindView(R.id.sp_desa)
    AppCompatSpinner spDesa;
    private ApiInterfaceLokasi apiServiceLokasi = ApiClientLokasi.getClientLokasi().create(ApiInterfaceLokasi.class);
    private List<IdProvinsi> provinsiList;
    private List<IdKotaKab> kotaKabList;
    private List<IdKecamatan> kecamatanList;
    private List<IdDesa> desaList;

    private String lastIdProvinsi;
    private String lastIdKotaKab;
    private String lastIdKecamatan;
    private String lastIdDesa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        database = FirebaseDatabase.getInstance().getReference();
        ButterKnife.bind(this);
        initView();
        loadDataProvinsi();

        text1 = findViewById(R.id.et_nama_lengkap);
        text2 = findViewById(R.id.et_alamat);
        text3 = findViewById(R.id.et_rt_rw);
        text4 = findViewById(R.id.et_no_telp);
        ton1 = findViewById(R.id.btn_register);

        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String Stext1 = text1.getText().toString();
                String Stext2 =text2.getText().toString();
                String Stext3 =text3.getText().toString();
                String Stext4 =text4.getText().toString();

                if (Stext1.equals("")) {
                    text1.setError("Silahkan isi nama");
                    text1.requestFocus();
                } else if (Stext2.equals("")) {
                    text2.setError("Silahkan isi alamat");
                    text2.requestFocus();
                } else if (Stext3.equals("")) {
                    text3.setError("Silahkan isi RT/RW");
                    text3.requestFocus();
                } else if (Stext3.equals("")) {
                    text4.setError("Silahkan isi no.hp");
                    text4.requestFocus();
                } else {
                    loading = ProgressDialog.show(RegisterActivity.this,
                            null,
                            "please wait ....",
                            true,
                            false);

                    submitUser(new Data(
                            Stext1,
                            Stext2,
                            Stext3,
                            Stext4));


                }
            }
        });
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
                lastIdProvinsi = idProvinsis.get(position).getId();
                loadDataKotaKab(lastIdProvinsi);
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
                lastIdKotaKab = mId.get(position);
                loadDataKecamatan(lastIdKotaKab);
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
                lastIdKecamatan = mId.get(position);
                loadDataDesa(lastIdKecamatan);
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
        spDesa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lastIdDesa = mId.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void submitUser(Data data) {
        Log.d("RegisterActivity", "\nnama: " + text1.getText() + "\n" +
                "alamat: " + text2.getText() + "\n" +
                "provinsi: " + lastIdProvinsi + "\n" +
                "kota: " + lastIdKotaKab + "\n" +
                "kecamatan: " + lastIdKecamatan + "\n" +
                "desa: " + lastIdDesa + "\n" +
                "rt/rw: " + text3.getText() + "\n" +
                "no telp: " + text4.getText() + "\n");
        loading.dismiss();
        database.child("Register Warga")
                .push()
                .setValue(data)
                .addOnSuccessListener(this, aVoid -> {
                    loading.dismiss();
                    text1.setText("");
                    text2.setText("");
                    text3.setText("");
                    text4.setText("");
                    Toast.makeText(RegisterActivity.this, "Data Berhasil Ditambahkan.",
                            Toast.LENGTH_SHORT).show();
                });


    }

}
