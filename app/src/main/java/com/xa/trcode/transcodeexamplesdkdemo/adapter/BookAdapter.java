package com.xa.trcode.transcodeexamplesdkdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xa.transcode.bean.XATransCodeBookEntity;
import com.xa.trcode.transcodeexamplesdkdemo.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<XATransCodeBookEntity> list;
    private ItemOnClick itemOnClick;

    public BookAdapter(List<XATransCodeBookEntity> list) {
        this.list = list;
    }

    public void setNewData(List<XATransCodeBookEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final XATransCodeBookEntity item = list.get(position);
        holder.bookName.setText(item.getName());
        holder.bookUrl.setText("url: " + item.getUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOnClick != null) {
                    itemOnClick.onClick(item.getUrl());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView bookName;
        public TextView bookUrl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.book_name);
            bookUrl = itemView.findViewById(R.id.book_url);
        }
    }

    public interface ItemOnClick {
        void onClick(String url);
    }
}
