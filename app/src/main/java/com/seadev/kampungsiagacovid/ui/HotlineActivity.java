package com.seadev.kampungsiagacovid.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.seadev.kampungsiagacovid.R;
import com.seadev.kampungsiagacovid.adapter.HotlineProvinsiAdapter;
import com.seadev.kampungsiagacovid.model.dataapi.IdProvinsi;
import com.seadev.kampungsiagacovid.model.requestbody.ItemIdProvinsi;
import com.seadev.kampungsiagacovid.rest.ApiClientLokasi;
import com.seadev.kampungsiagacovid.rest.ApiInterfaceFirebase;

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

public class HotlineActivity extends AppCompatActivity {

    @BindView(R.id.rv_hotline)
    RecyclerView rvMain;
    @BindView(R.id.sv_hotline)
    SearchView searchView;
    @BindView(R.id.pb_data_hotline)
    ProgressBar progressBar;
    @BindView(R.id.refreshLayoutHotline)
    SwipeRefreshLayout refreshLayout;
    private HotlineProvinsiAdapter adapter;
    private ApiInterfaceFirebase apiServiceLokasi = ApiClientLokasi.getClientLokasi().create(ApiInterfaceFirebase.class);
    private List<IdProvinsi> provinsiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotline);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Hotline Covid-19 Indonesia");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        Sprite sprite = new ThreeBounce();
        sprite.setColor(getResources().getColor(R.color.colorPrimary));
        progressBar.setIndeterminateDrawable(sprite);

        initView();
        loadDataProvinsi();

        refreshLayout.setOnRefreshListener(() -> {
            adapter.clear();
            initView();
            loadDataProvinsi();
            refreshLayout.setRefreshing(false);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.clear();
                searchData(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    adapter.clear();
                    searchData(newText);
                }
                return true;
            }
        });

        searchView.setOnCloseListener(() -> {
            Log.d("HotlineActivity", "Close:True");
            return true;
        });
    }

    private void initView() {
        adapter = new HotlineProvinsiAdapter(this);
        rvMain.setHasFixedSize(true);
        rvMain.setItemAnimator(new DefaultItemAnimator());
    }

    private void loadDataProvinsi() {
        showProgressbar(true);
        provinsiList = new ArrayList<>();
        Call<ItemIdProvinsi> call = apiServiceLokasi.getDataProvinsi();
        call.enqueue(new Callback<ItemIdProvinsi>() {
            @Override
            public void onResponse(Call<ItemIdProvinsi> call, Response<ItemIdProvinsi> response) {
                assert response.body() != null;
                provinsiList.add(new IdProvinsi("1", "INDONESIA"));
                provinsiList.addAll(response.body().getProvinsiList());
                adapter.setProvinsiList(provinsiList);
                showProgressbar(false);
                rvMain.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ItemIdProvinsi> call, Throwable t) {
                Log.d("HotlineActivity", "error: " + t.getMessage());
            }
        });
    }

    private void searchData(String query) {
        showProgressbar(true);
        provinsiList = new ArrayList<>();
        Call<ItemIdProvinsi> call = apiServiceLokasi.getDataProvinsi();
        call.enqueue(new Callback<ItemIdProvinsi>() {
            @Override
            public void onResponse(Call<ItemIdProvinsi> call, Response<ItemIdProvinsi> response) {
                assert response.body() != null;
                provinsiList.add(new IdProvinsi("1", "INDONESIA"));
                provinsiList.addAll(response.body().getProvinsiList());
                for (IdProvinsi provinsi : provinsiList) {
                    if (provinsi.getName().toLowerCase().equals(query.toLowerCase()) ||
                            provinsi.getName().toLowerCase().contains(query.toLowerCase())) {
                        adapter.addIdProvinsi(provinsi);
                    }
                }
                rvMain.setAdapter(adapter);
                showProgressbar(false);
            }

            @Override
            public void onFailure(Call<ItemIdProvinsi> call, Throwable t) {
                Log.d("Register Activity", "error: " + t.getMessage());
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
