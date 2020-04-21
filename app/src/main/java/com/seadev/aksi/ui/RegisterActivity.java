package com.seadev.aksi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.seadev.aksi.R;
import com.seadev.aksi.model.Users;
import com.seadev.aksi.model.dataapi.IdDesa;
import com.seadev.aksi.model.dataapi.IdKecamatan;
import com.seadev.aksi.model.dataapi.IdKotaKab;
import com.seadev.aksi.model.dataapi.IdProvinsi;
import com.seadev.aksi.model.requestbody.ItemIdDesa;
import com.seadev.aksi.model.requestbody.ItemIdKecamatan;
import com.seadev.aksi.model.requestbody.ItemIdKotaKab;
import com.seadev.aksi.model.requestbody.ItemIdProvinsi;
import com.seadev.aksi.rest.ApiClientLokasi;
import com.seadev.aksi.rest.ApiInterfaceFirebase;
import com.seadev.aksi.util.StringUtilKt;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    @BindView(R.id.et_nama_lengkap)
    EditText etNama;
    @BindView(R.id.et_alamat)
    EditText etAlamat;
    @BindView(R.id.et_rt)
    EditText etRt;
    @BindView(R.id.et_rw)
    EditText etRW;
    @BindView(R.id.et_nik)
    EditText etNik;
    @BindView(R.id.sp_provinsi)
    MaterialSpinner spProvinsi;
    @BindView(R.id.sp_kota_kab)
    MaterialSpinner spKotaKab;
    @BindView(R.id.sp_kecamatan)
    MaterialSpinner spKecamatan;
    @BindView(R.id.sp_desa)
    MaterialSpinner spDesa;

    @BindView(R.id.til_nama)
    TextInputLayout tilNama;
    @BindView(R.id.til_nik)
    TextInputLayout tilNik;
    @BindView(R.id.til_rt)
    TextInputLayout tilRt;
    @BindView(R.id.til_rw)
    TextInputLayout tilRw;
    @BindView(R.id.til_alamat)
    TextInputLayout tilAlamat;

    @BindView(R.id.tv_error_prov)
    TextView tvErrorProvinsi;
    @BindView(R.id.tv_error_kota)
    TextView tvErrorKotaKab;
    @BindView(R.id.tv_error_kecamatan)
    TextView tvErrorKecamatan;
    @BindView(R.id.tv_error_desa)
    TextView tvErrorDesa;

    @BindView(R.id.layout_loading_create_account)
    CardView layoutLoading;
    @BindView(R.id.layout_main_register)
    CardView layoutMain;
    @BindView(R.id.pb_loading_create_account)
    ProgressBar progressBar;

    @BindView(R.id.btn_create_account)
    Button btnCreateAccount;
    private ApiInterfaceFirebase apiServiceLokasi = ApiClientLokasi.getClientLokasi().create(ApiInterfaceFirebase.class);
    private List<IdProvinsi> provinsiList;
    private List<IdKotaKab> kotaKabList;
    private List<IdKecamatan> kecamatanList;
    private List<IdDesa> desaList;
    private Sprite sprite;

    private String mNama;
    private String mNik;
    private String mIdProvinsi;
    private String mIdKotaKab;
    private String mIdKecamatan;
    private String mIdDesa;
    private String mRtrw;
    private String mAlamat;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    private boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        sprite = new FadingCircle();
        sprite.setColor(getResources().getColor(R.color.colorPrimary));
        progressBar.setIndeterminateDrawable(sprite);

        initView();
        loadDataProvinsi();

        btnCreateAccount.setOnClickListener(v -> {
            validationData(0);
            if (isValid) {
                Log.d(TAG, "btnCreateAccount: isValid");
                layoutLoading.setVisibility(View.VISIBLE);
                layoutMain.setVisibility(View.GONE);
                createNewUser();
            }
        });
    }

    private void createNewUser() {
        mNama = etNama.getText().toString();
        mNik = etNik.getText().toString();
        int rt = Integer.parseInt(etRt.getText().toString());
        int rw = Integer.parseInt(etRW.getText().toString());
        mRtrw = new StringBuilder()
                .append(rt)
                .append("/")
                .append(rw)
                .toString();
        mAlamat = etAlamat.getText().toString();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(mNama).build();
        firebaseUser.updateProfile(profileUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "updateProfile: success");
            }
        });

        Users users = new Users(
                firebaseUser.getUid(),
                firebaseUser.getPhoneNumber(),
                mNama, mAlamat,
                mIdProvinsi, mIdKotaKab,
                mIdKecamatan, mIdDesa,
                mRtrw, mNik
        );

        databaseReference.child(users.getIdUser()).setValue(users)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "addOnSuccessListener: success");
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Log.d(TAG, "addOnFailureListener: " + e)
                );
    }

    private void validationData(int section) {
        int validation = 0;
        if (section == 1 || section == 0) {
            if (TextUtils.isEmpty(etNama.getText().toString())) {
                validation++;
                tilNama.setError("Tidak boleh kosong");
                tilNama.setErrorEnabled(true);
            } else {
                tilNama.setErrorEnabled(false);
            }
        }
        if (section == 2 || section == 0) {
            if (TextUtils.isEmpty(etNik.getText().toString())) {
                validation++;
                tilNik.setError("Tidak boleh kosong");
                tilNik.setErrorEnabled(true);
            } else {
                tilNik.setErrorEnabled(false);
            }
        }
        if (section == 3 || section == 0) {
            if (mIdProvinsi == null) {
                validation++;
                tvErrorProvinsi.setVisibility(View.VISIBLE);
            } else {
                tvErrorProvinsi.setVisibility(View.GONE);
            }
        }
        if (section == 4 || section == 0) {
            if (mIdKotaKab == null) {
                validation++;
                tvErrorKotaKab.setVisibility(View.VISIBLE);
            } else {
                tvErrorKotaKab.setVisibility(View.GONE);
            }
        }
        if (section == 5 || section == 0) {
            if (mIdKecamatan == null) {
                validation++;
                tvErrorKecamatan.setVisibility(View.VISIBLE);
            } else {
                isValid = false;
                tvErrorKecamatan.setVisibility(View.GONE);
            }
        }
        if (section == 6 || section == 0) {
            if (mIdDesa == null) {
                validation++;
                tvErrorDesa.setVisibility(View.VISIBLE);
            } else {
                tvErrorDesa.setVisibility(View.GONE);
            }
        }

        if (section == 7 || section == 0) {
            if (TextUtils.isEmpty(etRt.getText().toString())) {
                validation++;
                tilRt.setError("Tidak boleh kosong");
                tilRt.setErrorEnabled(true);
            } else {
                tilRt.setErrorEnabled(false);
            }
        }

        if (section == 8 || section == 0) {
            if (TextUtils.isEmpty(etRW.getText().toString())) {
                validation++;
                tilRw.setError("Tidak boleh kosong");
                tilRw.setErrorEnabled(true);
            } else {
                tilRw.setErrorEnabled(false);
            }
        }
        if (section == 9 || section == 0) {
            if (TextUtils.isEmpty(etAlamat.getText().toString())) {
                validation++;
                tilAlamat.setError("Tidak boleh kosong");
                tilAlamat.setErrorEnabled(true);
            } else {
                tilAlamat.setErrorEnabled(false);
            }
        }

        Log.d(TAG, "validation: " + validation);
        isValid = validation == 0;
    }

    private void initView() {
        spProvinsi.setItems("Pilih Provinsi");
        spKotaKab.setItems("Pilih Kabupaten / Kota");
        spKecamatan.setItems("Pilih Kacamatan");
        spDesa.setItems("Pilih Desa / Kelurahan");

        spKotaKab.setEnabled(false);
        spKecamatan.setEnabled(false);
        spDesa.setEnabled(false);

        etNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validationData(1);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etNik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validationData(2);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etRt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validationData(7);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etRW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validationData(8);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etAlamat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validationData(9);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
        spKotaKab.setEnabled(false);
        spKecamatan.setEnabled(false);
        spDesa.setEnabled(false);

        spKotaKab.setItems("Sedang memuat ...");
        spKecamatan.setItems("Pilih Kacamatan");
        spDesa.setItems("Pilih Desa / Kelurahan");

        mIdKotaKab = null;
        mIdKecamatan = null;
        mIdDesa = null;

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
        spKecamatan.setEnabled(false);
        spDesa.setEnabled(false);

        spKecamatan.setItems("Sedang memuat ...");
        spDesa.setItems("Pilih Desa / Kelurahan");

        mIdKecamatan = null;
        mIdDesa = null;

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
        spDesa.setEnabled(false);

        spDesa.setItems("Sedang memuat ...");
        mIdDesa = null;

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
        for (IdProvinsi provinsi : idProvinsis)
            mProvinsiList.add(StringUtilKt.toTitleCase(provinsi.getName()));
        spProvinsi.setItems(mProvinsiList);
        spProvinsi.setOnItemSelectedListener((view, position, id, item) -> {
            mIdProvinsi = idProvinsis.get(position).getId();
            validationData(3);
            loadDataKotaKab(mIdProvinsi);
        });
    }

    private void setDataSpinerKotaKab(List<IdKotaKab> idKotaKabs, String idProvinsi) {
        List<String> mList = new ArrayList<>();
        List<String> mId = new ArrayList<>();
        for (IdKotaKab idKotaKab : idKotaKabs) {
            if (idKotaKab.getIdProvinsi().equals(idProvinsi)) {
                mList.add(StringUtilKt.toTitleCase(idKotaKab.getName()));
                mId.add(idKotaKab.getId());
            }
        }
        spKotaKab.setEnabled(true);
        spKotaKab.setItems(mList);
        spKotaKab.setOnItemSelectedListener((view, position, id, item) -> {
            mIdKotaKab = mId.get(position);
            validationData(4);
            loadDataKecamatan(mIdKotaKab);
        });
    }

    private void setDataSpinerKecamatan(List<IdKecamatan> idKecamatans, String idKotaKab) {
        List<String> mList = new ArrayList<>();
        List<String> mId = new ArrayList<>();
        for (IdKecamatan idKecamatan : idKecamatans) {
            if (idKecamatan.getIdKotaKab().equals(idKotaKab)) {
                mList.add(StringUtilKt.toTitleCase(idKecamatan.getName()));
                mId.add(idKecamatan.getId());
            }
        }
        spKecamatan.setItems(mList);
        spKecamatan.setEnabled(true);
        spKecamatan.setOnItemSelectedListener((view, position, id, item) -> {
            mIdKecamatan = mId.get(position);
            validationData(5);
            loadDataDesa(mIdKecamatan);
        });
    }

    private void setDataSpinerDesa(List<IdDesa> idDesas, String idKecamatan) {
        List<String> mList = new ArrayList<>();
        List<String> mId = new ArrayList<>();
        for (IdDesa idDesa : idDesas) {
            if (idDesa.getIdKecamatan().equals(idKecamatan)) {
                mList.add(StringUtilKt.toTitleCase(idDesa.getName()));
                mId.add(idDesa.getId());
            }
        }
        spDesa.setItems(mList);
        spDesa.setEnabled(true);
        spDesa.setOnItemSelectedListener((view, position, id, item) -> {
            mIdDesa = mId.get(position);
            validationData(6);
        });
    }

}
