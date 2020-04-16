package com.seadev.kampungsiagacovid.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.seadev.kampungsiagacovid.R;
import com.seadev.kampungsiagacovid.adapter.ProvinsiAdapter;
import com.seadev.kampungsiagacovid.model.dataapi.DataProvinsi;
import com.seadev.kampungsiagacovid.model.requestbody.ItemDataProvinsi;
import com.seadev.kampungsiagacovid.rest.ApiClientNasional;
import com.seadev.kampungsiagacovid.rest.ApiInterfaceNasional;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataProvinsiActivity extends AppCompatActivity {

    @BindView(R.id.sv_provinsi)
    SearchView searchView;
    @BindView(R.id.rv_provinsi)
    RecyclerView rvMain;
    @BindView(R.id.pb_data_provinsi)
    ProgressBar progressBar;
    @BindView(R.id.refreshLayoutDataProvinsi)
    SwipeRefreshLayout refreshLayout;
    private List<DataProvinsi> provinsiList;
    private ProvinsiAdapter provinsiAdapter;
    private ApiInterfaceNasional apiServiceNasional = ApiClientNasional.getClientNasional().create(ApiInterfaceNasional.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_provinsi);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Data Covid Per-Provinsi");
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Sprite sprite = new ThreeBounce();
        sprite.setColor(getResources().getColor(R.color.colorPrimary));
        progressBar.setIndeterminateDrawable(sprite);

        initView();
        loadDataProvinsi();

        refreshLayout.setOnRefreshListener(() -> {
            provinsiAdapter.clear();
            initView();
            loadDataProvinsi();
            refreshLayout.setRefreshing(false);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                provinsiAdapter.clear();
                searchData(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                provinsiAdapter.clear();
                searchData(newText);
                return true;
            }
        });
        searchView.setOnCloseListener(() -> {
            provinsiAdapter.clear();
            loadDataProvinsi();
            return true;
        });
    }

    private void initView() {
        provinsiAdapter = new ProvinsiAdapter(this);
        rvMain.setHasFixedSize(true);
        rvMain.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadDataProvinsi() {
        showProgressbar(true);
        provinsiList = new ArrayList<>();
        Call<ItemDataProvinsi> call = apiServiceNasional.getDataProvinsi();
        call.enqueue(new Callback<ItemDataProvinsi>() {
            @Override
            public void onResponse(Call<ItemDataProvinsi> call, Response<ItemDataProvinsi> response) {
                assert response.body() != null;
                provinsiList = response.body().getProvinsiList();
                provinsiAdapter.setProvinsiList(provinsiList);
                rvMain.setAdapter(provinsiAdapter);
                showProgressbar(false);
            }

            @Override
            public void onFailure(Call<ItemDataProvinsi> call, Throwable t) {
                Log.d("DataProvinsiActivity", "error: " + t);
            }
        });
    }

    private void searchData(String query) {
        showProgressbar(true);
        provinsiList = new ArrayList<>();
        Call<ItemDataProvinsi> call = apiServiceNasional.getDataProvinsi();
        call.enqueue(new Callback<ItemDataProvinsi>() {
            @Override
            public void onResponse(Call<ItemDataProvinsi> call, Response<ItemDataProvinsi> response) {
                assert response.body() != null;
                provinsiList = response.body().getProvinsiList();
                for (DataProvinsi dataProvinsi : provinsiList) {
                    String mData = dataProvinsi.getAttribute().getNamaProv();
                    Log.d("DataProvinsiActivity", "query: " + query.toLowerCase());
                    Log.d("DataProvinsiActivity", "data: " + mData.toLowerCase());
                    if (mData.toLowerCase().contains(query.toLowerCase()) || mData.toLowerCase().equals(query.toLowerCase())) {
                        if (!mData.toLowerCase().equals("indonesia")) {
                            Log.d("DataProvinsiActivity", "isContain?: Yes");
                            provinsiAdapter.addDaraProvinsi(dataProvinsi);
                        }
                    }
                }
                showProgressbar(false);
                rvMain.setAdapter(provinsiAdapter);

            }

            @Override
            public void onFailure(Call<ItemDataProvinsi> call, Throwable t) {
                Log.d("DataProvinsiActivity", "error: " + t);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private void showProgressbar(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
