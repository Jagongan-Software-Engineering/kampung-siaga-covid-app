package com.seadev.aksi.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seadev.aksi.R;
import com.seadev.aksi.model.dataapi.Hotline;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HotlineRsAdapter extends RecyclerView.Adapter<HotlineRsAdapter.HotlineViewHolder> {

    private Context context;
    private List<Hotline> hotlineList = new ArrayList<>();

    public HotlineRsAdapter(Context context) {
        this.context = context;
    }

    public List<Hotline> getHotlineList() {
        return hotlineList;
    }

    public void addHotline(Hotline hotlineList) {
        this.hotlineList.add(hotlineList);
    }

    @NonNull
    @Override
    public HotlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotline, parent, false);
        ButterKnife.bind(this, view);
        return new HotlineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotlineViewHolder holder, int position) {
        Hotline hotline = hotlineList.get(position);
        holder.tvRsName.setText(hotline.getNamaRs());
        holder.tvNoTelp.setText(hotline.getHotline());
        holder.itemView.setOnClickListener(v -> {
            context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hotline.getHotline())));
        });
    }

    @Override
    public int getItemCount() {
        return getHotlineList().size();
    }

    public class HotlineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_rs_name)
        TextView tvRsName;
        @BindView(R.id.tv_no_telp)
        TextView tvNoTelp;

        public HotlineViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
