package com.xa.trcode.transcodeexamplesdkdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xa.transcode.bean.XACatalog;
import com.xa.trcode.transcodeexamplesdkdemo.R;

import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {
    private List<XACatalog> list;
    private ItemOnClick itemOnClick;

    public CatalogAdapter(List<XACatalog> list) {
        this.list = list;
    }

    public void setNewData(List<XACatalog> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final XACatalog item = list.get(position);
        holder.catalogName.setText(item.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOnClick != null) {

                    itemOnClick.onClick(item.getTitle(),item.getUrl(),position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView catalogName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catalogName = itemView.findViewById(R.id.catalog_name);
        }
    }

    public interface ItemOnClick {
        void onClick(String name, String url, int index);
    }
}
