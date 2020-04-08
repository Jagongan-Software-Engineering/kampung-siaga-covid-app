package com.seadev.kampungsiagacovid.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.iid.FirebaseInstanceId;
import com.seadev.kampungsiagacovid.R;
import com.seadev.kampungsiagacovid.model.dataapi.DataHarian;
import com.seadev.kampungsiagacovid.model.requestbody.ItemDataHarian;
import com.seadev.kampungsiagacovid.rest.ApiClientNasional;
import com.seadev.kampungsiagacovid.rest.ApiInterfaceNasional;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
    @BindView(R.id.tv_new_positive)
    TextView tvNewConfirm;
    @BindView(R.id.tv_new_in_care)
    TextView tvNewInCare;
    @BindView(R.id.tv_new_recover)
    TextView tvNewRecover;
    @BindView(R.id.tv_new_died)
    TextView tvNewDied;
    @BindView(R.id.btn_menu_data_covid)
    CardView btnDataCovid;
    @BindView(R.id.btn_menu_lapor_diri)
    CardView btnLapor;
    @BindView(R.id.btn_menu_hotline)
    CardView btnHotline;
    @BindView(R.id.btn_menu_pencegahan)
    CardView btnPencegahan;
    @BindView(R.id.pie_chart)
    PieChart pieChart;
    @BindView(R.id.txt_confirmed)
    TextView tvPersenDirawat;
    @BindView(R.id.txt_recovered)
    TextView tvPersenSembuh;
    @BindView(R.id.txt_deaths)
    TextView tvPersenMeninggal;
    @BindView(R.id.txt_cases)
    TextView tvKasus;
    @BindView(R.id.layout_cases)
    LinearLayout layoutCase;


    private ApiInterfaceNasional apiServiceNasional = ApiClientNasional.getClientNasional().create(ApiInterfaceNasional.class);
    private List<DataHarian> dataHarianList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFirebase();
        loadDataNasional();
        btnDataCovid.setOnClickListener(v ->
                startActivity(new Intent(this, DataProvinsiActivity.class))
        );
        btnLapor.setOnClickListener(v ->
                startActivity(new Intent(this, SurveyActivity.class))
        );
        btnHotline.setOnClickListener(v -> {
            startActivity(new Intent(this, HotlineActivity.class));
        });
    }


    private void initFirebase() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("TAG", "getInstanceId failed", task.getException());
                return;
            }
            // Get new Instance ID token
            String token = task.getResult().getToken();
            // Log and toast
            String msg = getString(R.string.msg_token_fmt, token);
            Log.d("TAG", msg);
        });
    }


    private void loadDataNasional() {
        dataHarianList = new ArrayList<>();
        Call<ItemDataHarian> call = apiServiceNasional.getDataHarian();
        call.enqueue(new Callback<ItemDataHarian>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    private void setDataHarian(DataHarian dataHarian) {
        List<PieEntry> pieEntries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            colors.add(getColor(R.color.colorAccent));
            colors.add(getColor(android.R.color.holo_orange_light));
            colors.add(getColor(android.R.color.holo_red_light));
        }

        pieEntries.add(new PieEntry((float) dataHarian.getAttributes().getSembuh(), "Sembuh"));
        pieEntries.add(new PieEntry((float) dataHarian.getAttributes().getDalamPerawatan(), "Dirawat"));
        pieEntries.add(new PieEntry((float) dataHarian.getAttributes().getMeninggal(), "Meninggal"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(colors);
        pieDataSet.setDrawValues(false);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDescription(null);
        pieChart.setHoleRadius(70f);
        pieChart.setDrawEntryLabels(false);
        pieChart.animateY(1500, Easing.EaseInOutQuart);
        pieChart.invalidate();

        tvConfirm.setText(String.valueOf(dataHarian.getAttributes().getKasusTotal()));
        tvRecover.setText(String.valueOf(dataHarian.getAttributes().getSembuh()));
        tvInCare.setText(String.valueOf(dataHarian.getAttributes().getDalamPerawatan()));
        tvDied.setText(String.valueOf(dataHarian.getAttributes().getMeninggal()));

        tvNewConfirm.setText("(+" + dataHarian.getAttributes().getKasusBaru() + ")");
        tvNewRecover.setText("(+" + dataHarian.getAttributes().getSembuhBaru() + ")");
        tvNewInCare.setText("(+" + dataHarian.getAttributes().getDirawatBaru() + ")");
        tvNewDied.setText("(+" + dataHarian.getAttributes().getMeninggalBaru() + ")");

        layoutCase.setVisibility(View.VISIBLE);
        tvKasus.setText(String.valueOf(dataHarian.getAttributes().getKasusTotal()));

        DecimalFormat format = new DecimalFormat("#.##");
        format.setRoundingMode(RoundingMode.HALF_UP);
        tvPersenSembuh.setText(format.format(dataHarian.getAttributes().getPersenSembuh()) + "%");
        tvPersenMeninggal.setText(format.format(dataHarian.getAttributes().getPersenMeninggal()) + "%");
        tvPersenDirawat.setText(format.format(dataHarian.getAttributes().getPersenPerawatan()) + "%");


    }

}
