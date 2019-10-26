package com.mag.musicplayer.Model.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.musicplayer.Model.Album;
import com.mag.musicplayer.R;
import com.squareup.picasso.Picasso;

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
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_album_box, parent, false);
        return new AlbumListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumListViewHolder holder, int position) {
        Album album = albums.get(position);
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
            artistName = itemView.findViewById(R.id.albumBoxLayout_artistName);

        }

        public void bind(Album album) {

            albumName.setText(album.getAlbumTitle().length() > 24 ? album.getAlbumTitle().substring(0,24)+ "..." : album.getAlbumTitle());
            artistName.setText(album.getArtistName());
            Picasso.get().load(album.getImagePath()).placeholder(activity.getResources().getDrawable(R.drawable.music_icon)).into(albumCover);

        }

    }

}
