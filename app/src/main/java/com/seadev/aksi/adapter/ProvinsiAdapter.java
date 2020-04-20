package com.seadev.aksi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seadev.aksi.R;
import com.seadev.aksi.model.dataapi.DataProvinsi;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProvinsiAdapter extends RecyclerView.Adapter<ProvinsiAdapter.ProvinsiViewHolder> {
    private Context context;
    private List<DataProvinsi> provinsiList = new ArrayList<>();

    public ProvinsiAdapter(Context context) {
        this.context = context;
    }

    public List<DataProvinsi> getProvinsiList() {
        return provinsiList;
    }

    public void setProvinsiList(List<DataProvinsi> provinsiList) {
        this.provinsiList = provinsiList;
    }

    public void addDaraProvinsi(DataProvinsi dataProvinsi) {
        this.provinsiList.add(dataProvinsi);
        notifyDataSetChanged();
    }

    public void clear() {
        int size = getProvinsiList().size();
        provinsiList.clear();
        notifyItemRangeRemoved(0, size - 1);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProvinsiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_provinsi, parent, false);
        ButterKnife.bind(this, view);
        return new ProvinsiViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProvinsiViewHolder holder, int position) {
        DataProvinsi dataProvinsi = provinsiList.get(position);
        if (!dataProvinsi.getAttribute().getNamaProv().toLowerCase().equals("indonesia")) {
            holder.provinsiName.setText(dataProvinsi.getAttribute().getNamaProv());
            holder.positifCase.setText(String.valueOf(dataProvinsi.getAttribute().getKasusPositifl()));
            holder.recoverCase.setText(String.valueOf(dataProvinsi.getAttribute().getKasusSembuh()));
            holder.deathCase.setText(String.valueOf(dataProvinsi.getAttribute().getKasusKasusManinggal()));
        }
    }

    @Override
    public int getItemCount() {
        return getProvinsiList().size();
    }

    public class ProvinsiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_provinsi)
        TextView provinsiName;
        @BindView(R.id.tv_item_positif_prov)
        TextView positifCase;
        @BindView(R.id.tv_item_sembuh_prov)
        TextView recoverCase;
        @BindView(R.id.tv_item_meni_prov)
        TextView deathCase;

        public ProvinsiViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
