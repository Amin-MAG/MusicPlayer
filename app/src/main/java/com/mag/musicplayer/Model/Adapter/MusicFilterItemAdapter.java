package com.mag.musicplayer.Model.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class MusicFilterItemAdapter extends RecyclerView.Adapter<MusicFilterItemAdapter.MusicListViewHolder> {

    private Activity activity;
    private List<String> items;

    public MusicFilterItemAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MusicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_filter_task_item, parent, false);
        return new MusicListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListViewHolder holder, int position) {
        String item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MusicListViewHolder extends RecyclerView.ViewHolder {

        public MusicListViewHolder(@NonNull View view) {
            super(view);

        }

        public void bind(String item) {

        }

    }

}
