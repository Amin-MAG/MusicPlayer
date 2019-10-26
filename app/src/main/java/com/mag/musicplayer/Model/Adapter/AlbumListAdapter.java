package com.mag.musicplayer.Model.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.musicplayer.Model.Album;
import com.mag.musicplayer.R;

import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumListViewHolder> {

    private List<Album> albums;

    private Activity activity;

    public AlbumListAdapter(List<Album> albums) {
        this.albums = albums;
    }

    @NonNull
    @Override
    public AlbumListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_track, parent, false);
        return new AlbumListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumListViewHolder holder, int position) {
        Album album = this.albums.get(position);
        holder.bind(album);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class AlbumListViewHolder extends RecyclerView.ViewHolder {

        private ImageView albumCover;
        private TextView albumName, artistName;

        public AlbumListViewHolder(@NonNull View itemView) {
            super(itemView);

            albumCover = itemView.findViewById(R.id.albumBoxLayout_image);
            albumName= itemView.findViewById(R.id.albumBoxLayout_albumName);
            artistName = itemView.findViewById(R.id.albumBoxLayout_artsitName);

        }


        public void bind(Album album) {

        }

    }

}
