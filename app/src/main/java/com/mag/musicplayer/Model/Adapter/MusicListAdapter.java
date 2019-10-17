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

import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder> {

    private Activity activity;
    private List<Track> tracks;

    public MusicListAdapter(List<Track> tracks) {
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public MusicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_track, parent, false);
        return new MusicListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListViewHolder holder, int position) {
        Track track = tracks.get(position);
        holder.bind(track);
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class MusicListViewHolder extends RecyclerView.ViewHolder {

        private ImageView trackImage;
        private TextView trackName, trackArtistName, trackLength;


        public MusicListViewHolder(@NonNull View view) {
            super(view);

            trackImage = view.findViewById(R.id.trackLayout_trackImage);
            trackName = view.findViewById(R.id.trackLayout_trackName);
            trackArtistName = view.findViewById(R.id.trackLayout_trackArtistName);
            trackLength = view.findViewById(R.id.trackLayout_trackLength);

        }

        public void bind(Track track) {

            trackName.setText(track.getTrackName());
            trackArtistName.setText(track.getArtistName());

            int seconds = track.getTrackLength();
            int minutes = seconds / 60;
            trackLength.setText((minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds % 60 < 10 ? "0" + seconds % 10 : seconds % 10));

        }

    }

}
