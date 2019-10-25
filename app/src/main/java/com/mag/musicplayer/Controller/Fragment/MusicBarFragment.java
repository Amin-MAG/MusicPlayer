package com.mag.musicplayer.Controller.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.mag.musicplayer.Controller.Activity.TrackPlayerActivity;
import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;
import com.mag.musicplayer.Util.MusicPlayer;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MusicBarFragment extends Fragment {

    private Track barTrack;

    private ConstraintLayout mainLayout;
    private TextView trackName, trackArtist;
    private ImageView trackImage;
    private ImageButton skipPreBtn, skipNextBtn, playPauseBtn;

    private MusicBarCallback callback = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof MusicBarCallback) {
            callback = (MusicBarCallback) getActivity();
        }

    }


    public static MusicBarFragment newInstance() {

        Bundle args = new Bundle();

        MusicBarFragment fragment = new MusicBarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MusicBarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findItems(view);

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TrackPlayerActivity.newIntent(getActivity(), barTrack, new TrackPlayerActivity.TrackActivityCallback() {
                    @Override
                    public Track getTrackDistanceFromAdapter(int distance) {
                        return callback.getTrackDistance(distance);
                    }
                }));
            }
        });

        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicPlayer.getInstance().getMediaPlayer().isPlaying()) {
                    MusicPlayer.getInstance().getMediaPlayer().pause();
                    playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                } else {
                    MusicPlayer.getInstance().getMediaPlayer().start();
                    playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                }
            }
        });

        skipNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Track nextTrack = callback.getTrackDistance(+1);
                try {
                    MusicPlayer.getInstance().playMusic(nextTrack, getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                updateBar(nextTrack);
            }
        });

        skipPreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Track previousTrack = callback.getTrackDistance(-1);
                try {
                    MusicPlayer.getInstance().playMusic(previousTrack, getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                updateBar(previousTrack);
            }
        });

    }

    private void findItems(@NonNull View view) {
        trackName = view.findViewById(R.id.musicBarFragment_trackName);
        trackArtist = view.findViewById(R.id.musicBarFragment_trackArtist);
        trackImage = view.findViewById(R.id.musicBarFragment_trackImage);
        skipPreBtn = view.findViewById(R.id.trackPlayerActivity_skipPrevious);
        skipNextBtn = view.findViewById(R.id.trackPlayerActivity_skipNext);
        playPauseBtn = view.findViewById(R.id.trackPlayerActivity_play_pause);
        mainLayout = view.findViewById(R.id.musicBarFragment_mainLayout);
    }

    public void updateBar(Track track) {

        trackName.setText(track.getTrackTitle().length() > 12 ? track.getTrackTitle() + "..." : track.getTrackTitle());
        trackArtist.setText(track.getArtistName());

        updatePlayPause();

        Picasso.get().load(track.getImagePath()).placeholder(getResources().getDrawable(R.drawable.music_icon)).into(trackImage);

        barTrack = track;

    }

    public void updateBar() {
        updatePlayPause();
    }

    private void updatePlayPause() {

        if (MusicPlayer.getInstance().getMediaPlayer().isPlaying()) {
            playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
        } else {
            playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
        }

    }

    public interface MusicBarCallback {
        Track getTrackDistance(int distance);
    }

}
