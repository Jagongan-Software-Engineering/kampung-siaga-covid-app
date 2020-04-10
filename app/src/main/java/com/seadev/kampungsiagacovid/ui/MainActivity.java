package com.seadev.kampungsiagacovid.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.iid.FirebaseInstanceId;
import com.seadev.kampungsiagacovid.BuildConfig;
import com.seadev.kampungsiagacovid.R;
import com.seadev.kampungsiagacovid.model.Asesmen;
import com.seadev.kampungsiagacovid.model.dataapi.DataHarian;
import com.seadev.kampungsiagacovid.model.requestbody.ItemDataHarian;
import com.seadev.kampungsiagacovid.rest.ApiClientNasional;
import com.seadev.kampungsiagacovid.rest.ApiInterfaceNasional;
import com.seadev.kampungsiagacovid.room.AssesmenDatabase;
import com.seadev.kampungsiagacovid.util.DateFormater;
import com.seadev.kampungsiagacovid.util.ReportHistoryFormater;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.seadev.kampungsiagacovid.room.AsesmenContract.db;

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

    @BindView(R.id.iv_ic_data_covid)
    ImageView iconDataCovid;
    @BindView(R.id.iv_ic_penilaian_diri)
    ImageView iconPenilaianDiri;
    @BindView(R.id.iv_ic_holtine)
    ImageView iconHotline;
    @BindView(R.id.iv_ic_pencegahan)
    ImageView iconPencegahan;
    @BindView(R.id.ivCampaign)
    ImageView iconCampaign;
    @BindView(R.id.iv_history_report)

    ImageView imgReport;
    @BindView(R.id.tv_title_report)
    TextView tvTitleReport;
    @BindView(R.id.tv_desc_report)
    TextView tvDescReport;
    @BindView(R.id.tv_date_report)
    TextView tvDateReport;
    @BindView(R.id.tv_more_report)
    TextView btnMoreReport;

    @BindView(R.id.cv_campaign)
    CardView layoutCampaign;
    @BindView(R.id.layout_history_report)
    View layoutHistory;


    private ApiInterfaceNasional apiServiceNasional = ApiClientNasional.getClientNasional().create(ApiInterfaceNasional.class);
    private List<DataHarian> dataHarianList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initFirebase();
        loadDataNasional();
        fechDatabaseAsesmen();
        btnDataCovid.setOnClickListener(v ->
                startActivity(new Intent(this, DataProvinsiActivity.class))
        );
        btnLapor.setOnClickListener(v ->
                startActivity(new Intent(this, SurveyActivity.class))
        );
        btnHotline.setOnClickListener(v -> {
            startActivity(new Intent(this, HotlineActivity.class));
        });
        btnPencegahan.setOnClickListener(v -> {
            startActivity(new Intent(this, PreventionActivity.class));
        });
        layoutCampaign.setOnClickListener(v ->
                startActivity(new Intent(this, SurveyActivity.class))
        );
    }

    private void initView() {
        Glide.with(this)
                .load(BuildConfig.BASE_URL_LOKASI + getString(R.string.res_icon_data_covid))
                .into(iconDataCovid);
        Glide.with(this)
                .load(BuildConfig.BASE_URL_LOKASI + getString(R.string.res_icon_assesment))
                .into(iconPenilaianDiri);
        Glide.with(this)
                .load(BuildConfig.BASE_URL_LOKASI + getString(R.string.res_icon_hotline))
                .into(iconHotline);
        Glide.with(this)
                .load(BuildConfig.BASE_URL_LOKASI + getString(R.string.res_icon_prevention))
                .into(iconPencegahan);
        Glide.with(this)
                .load(BuildConfig.BASE_URL_LOKASI + getString(R.string.res_img_campaign))
                .into(iconCampaign);
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
            colors.add(getColor(android.R.color.holo_green_light));
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

    @SuppressLint({"SetTextI18n", "CheckResult"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void fechDatabaseAsesmen() {
        db = Room.databaseBuilder(Objects.requireNonNull(this),
                AssesmenDatabase.class, "db_asesmen").allowMainThreadQueries().build();
        List<Asesmen> asesmenList = db.asesmenDao().getDataAsesmen();
        if (!asesmenList.isEmpty()) {

            Asesmen asesmen = asesmenList.get(asesmenList.size() - 1);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String nowDate = LocalDateTime.now().format(formatter);
                LocalDate dateTime = LocalDate.parse(asesmen.getDate(), formatter);
                String date = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy").format(dateTime);
                String[] listDate = date.split(" ");
                int isToday = (nowDate.equals(asesmen.getDate())) ? 1 : 0;
                tvDateReport.setText(DateFormater.Companion.getHari(listDate[0], isToday) + ", " + listDate[1] + " " + DateFormater.Companion.getBulan(listDate[2]) + " " + listDate[3]);
            }
            tvTitleReport.setText(ReportHistoryFormater.Companion.getTitleReport(asesmen.getRisiko()));
            tvDescReport.setText(ReportHistoryFormater.Companion.getDescReport(asesmen.getRisiko()));
            Glide.with(this)
                    .load(ReportHistoryFormater.Companion.getImgReport(asesmen.getRisiko()))
                    .into(imgReport);
            imgReport.setBackgroundColor(getColor(ReportHistoryFormater.Companion.getImgReport(asesmen.getRisiko())));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String nowDate = LocalDateTime.now().format(formatter);

            if (nowDate.equals(asesmen.getDate())) {
                layoutCampaign.setVisibility(View.GONE);
            } else {
                layoutCampaign.setVisibility(View.VISIBLE);
            }

        } else {
            layoutHistory.setVisibility(View.GONE);
            Log.d("MainActivity", "room-size: " + asesmenList.size());
        }
    }

}
