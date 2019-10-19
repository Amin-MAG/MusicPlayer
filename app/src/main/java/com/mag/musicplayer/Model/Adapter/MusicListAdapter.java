package com.mag.musicplayer.Model.Adapter;

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

import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;
import com.mag.musicplayer.Util.MusicPlayer;

import java.io.IOException;
import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder> {

    private List<Track> tracks;
    private Track selectedTrack;
    private MusicListAdapterCallback callBack;

    private Activity activity;
    private CardView selectedCardView;

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

            trackName.setText(track.getTrackTitle().length() > 24 ? track.getTrackTitle() + "..." : track.getTrackTitle());
            trackArtistName.setText(track.getArtistName());
            trackLength.setText(getLengthText(track.getTrackLength() / 1000));

            if (selectedTrack != null && track.getTrackId() == selectedTrack.getTrackId()) {
                trackCardView.setBackgroundColor(Color.parseColor(activity.getString(R.color.colorPrimary)));
            } else {
                trackCardView.setBackgroundColor(Color.parseColor("#CB893CD5"));
            }

            trackCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedTrack = track;

                    try {
                        MusicPlayer.getInstance().playMusic(track, activity);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    updateUi((CardView) view);

                }
            });

        }

        private void updateUi(CardView cardView) {

            updateCardView(cardView);
            callBack.updateMusicBar(selectedTrack);

        }

        @SuppressLint("ResourceType")
        private void updateCardView(CardView view) {
            if (selectedCardView == null) {
                selectedCardView = view;
            } else {
                selectedCardView.setBackgroundColor(Color.parseColor("#CB893CD5"));
                selectedCardView = view;
            }
            selectedCardView.setBackgroundColor(Color.parseColor(activity.getString(R.color.colorPrimary)));
        }

        private String getLengthText(int seconds) {
            int minutes = seconds / 60;
            int secondReminder = seconds % 60;
            return (minutes < 10 ? "0" + minutes : minutes) + ":" + (secondReminder < 10 ? "0" + secondReminder : secondReminder);
        }

    }

    public interface MusicListAdapterCallback {
        void updateMusicBar(Track track);
    }

}
