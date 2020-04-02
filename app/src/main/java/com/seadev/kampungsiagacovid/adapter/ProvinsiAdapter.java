package com.seadev.kampungsiagacovid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seadev.kampungsiagacovid.R;
import com.seadev.kampungsiagacovid.model.dataapi.DataProvinsi;

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
        holder.textView.setText(dataProvinsi.getAttribute().getNamaProv() + "\n" +
                "Positiv  : " + dataProvinsi.getAttribute().getKasusPositifl() + "\n" +
                "Sembuh   : " + dataProvinsi.getAttribute().getKasusSembuh() + "\n" +
                "Meninggal: " + dataProvinsi.getAttribute().getKasusKasusManinggal());
    }

    @Override
    public int getItemCount() {
        return getProvinsiList().size();
    }

    public class ProvinsiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_provinsi)
        TextView textView;

        public ProvinsiViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
