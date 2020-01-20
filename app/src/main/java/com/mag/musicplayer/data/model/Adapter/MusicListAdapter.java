package com.mag.musicplayer.data.model.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.R;
import com.mag.musicplayer.data.repository.MusicPlayer;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder> {

    private List<Track> tracks;
    private Track selectedTrack;
    private MusicListAdapterCallback callBack;


    private Activity activity;

    public MusicListAdapter(List<Track> tracks, MusicListAdapterCallback callBack) {
        this.tracks = tracks;
        this.callBack = callBack;
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
        private CardView trackCardView;

        public MusicListViewHolder(@NonNull View view) {
            super(view);

            trackCardView = view.findViewById(R.id.trackLayout_cardView);
            trackImage = view.findViewById(R.id.trackLayout_trackImage);
            trackName = view.findViewById(R.id.trackLayout_trackName);
            trackArtistName = view.findViewById(R.id.trackLayout_trackArtistName);
            trackLength = view.findViewById(R.id.trackLayout_trackLength);

        }

        @SuppressLint("ResourceType")
        public void bind(final Track track) {

            trackName.setText(track.getTrackTitle().length() > 30 ? track.getTrackTitle().substring(0, 30) + "..." : track.getTrackTitle());
            trackArtistName.setText(track.getArtistName());

            Picasso.get().load(track.getImagePath()).placeholder(activity.getResources().getDrawable(R.drawable.music_icon)).into(trackImage);

            trackLength.setText(MusicPlayer.getStringTime(track.getTrackLength() / 1000));

            if (selectedTrack != null && track.getTrackId() == selectedTrack.getTrackId()) {
                trackCardView.setBackgroundColor(Color.parseColor(activity.getString(R.color.colorAccent)));
            } else {
                trackCardView.setBackgroundColor(Color.parseColor(activity.getString(R.color.colorPrimaryLight)));
            }

            trackCardView.setOnClickListener(view -> {

                selectedTrack = track;

                try {
                    MusicPlayer.getInstance().playMusic(track, activity);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                updateUi();

            });

        }

    }


    public void updateUi() {

        callBack.updateMusicBar(selectedTrack);
        notifyDataSetChanged();

    }

    public int findTrackIndex(Track track) {
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).getTrackId() == track.getTrackId())
                return i;
        }
        return -1;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setSelectedTrack(Track selectedTrack) {
        this.selectedTrack = selectedTrack;
    }

    public interface MusicListAdapterCallback {
        void updateMusicBar(Track track);

    }


}
