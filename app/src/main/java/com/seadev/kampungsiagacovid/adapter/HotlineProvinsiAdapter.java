package com.seadev.kampungsiagacovid.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.seadev.kampungsiagacovid.BuildConfig;
import com.seadev.kampungsiagacovid.R;
import com.seadev.kampungsiagacovid.model.dataapi.Hotline;
import com.seadev.kampungsiagacovid.model.dataapi.IdProvinsi;
import com.seadev.kampungsiagacovid.model.requestbody.ItemHotline;
import com.seadev.kampungsiagacovid.rest.ApiClientLokasi;
import com.seadev.kampungsiagacovid.rest.ApiInterfaceFirebase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotlineProvinsiAdapter extends RecyclerView.Adapter<HotlineProvinsiAdapter.HotlineViewHolder> {
    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();
    private Context context;
    private List<IdProvinsi> provinsiList = new ArrayList<>();

    public HotlineProvinsiAdapter(Context context) {
        this.context = context;
    }

    public List<IdProvinsi> getProvinsiList() {
        return provinsiList;
    }

    public void setProvinsiList(List<IdProvinsi> provinsiList) {
        this.provinsiList = provinsiList;
    }

    public void clear() {
        int size = getProvinsiList().size();
        provinsiList.clear();
        notifyItemRangeRemoved(0, size);
        notifyDataSetChanged();
    }

    public void addIdProvinsi(IdProvinsi idProvinsi) {
        this.provinsiList.add(idProvinsi);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HotlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotline_provinsi, parent, false);
        ButterKnife.bind(this, view);
        return new HotlineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotlineViewHolder holder, int position) {
        IdProvinsi dataProvinsi = provinsiList.get(position);
        holder.title.setText(dataProvinsi.getName());
        Glide.with(context)
                .load(BuildConfig.BASE_URL_LOKASI + context.getString(R.string.res_icon_hotline))
                .into(holder.iconCall);
        expansionsCollection.add(holder.getExpansionLayout());
        expansionsCollection.openOnlyOne(true);
        holder.initView();
        holder.loadDataHotline(dataProvinsi.getId());

    }

    @Override
    public int getItemCount() {
        return getProvinsiList().size();
    }

    public class HotlineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.expansionLayout)
        ExpansionLayout expansionLayout;
        @BindView(R.id.iv_call)
        ImageView iconCall;
        @BindView(R.id.tv_title_hotline)
        TextView title;
        @BindView(R.id.rv_rs)
        RecyclerView rvRs;
        private ApiInterfaceFirebase apiServiceLokasi = ApiClientLokasi.getClientLokasi().create(ApiInterfaceFirebase.class);
        private List<Hotline> hotlineList;
        private HotlineRsAdapter rsAdapter;

        public HotlineViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        ExpansionLayout getExpansionLayout() {
            return expansionLayout;
        }

        private void initView() {
            rsAdapter = new HotlineRsAdapter(context);
            rvRs.setHasFixedSize(true);
            rvRs.setItemAnimator(new DefaultItemAnimator());
        }

        private void loadDataHotline(String idProvinsi) {
            Log.d("HotlineViewAdapter", "loadDataHotline: run");
            hotlineList = new ArrayList<>();
            Call<ItemHotline> call = apiServiceLokasi.getDataHotline();
            call.enqueue(new Callback<ItemHotline>() {
                @Override
                public void onResponse(Call<ItemHotline> call, Response<ItemHotline> response) {
                    assert response.body() != null;
                    hotlineList.add(new Hotline("Layanan Nasional Covid-19", "119", "1"));
                    hotlineList.addAll(response.body().getHotlineList());
                    for (Hotline hotline : hotlineList) {
                        if (hotline.getIdProvinsi().equals(idProvinsi)) {
                            rsAdapter.addHotline(hotline);
                        }
                    }
                    rvRs.setAdapter(rsAdapter);
                }

                @Override
                public void onFailure(Call<ItemHotline> call, Throwable t) {
                    Log.d("HotlineViewAdapter", "error: " + t);
                }
            });
        }

    }
}
