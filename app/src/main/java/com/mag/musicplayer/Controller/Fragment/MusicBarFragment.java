package com.mag.musicplayer.Controller.Fragment;


import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicBarFragment extends Fragment {

    private TextView trackName, trackArtist;
    private ImageView trackImage;
    private MaterialButton skipPreBtn, skipNextBtn, playPauseBtn;
    private SeekBar trackSeekBar;

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

    }

    private void findItems(@NonNull View view) {
        trackName = view.findViewById(R.id.musicBarFragment_trackName);
        trackArtist = view.findViewById(R.id.musicBarFragment_trackArtist);
        trackImage = view.findViewById(R.id.musicBarFragment_trackImage);
        skipPreBtn = view.findViewById(R.id.musicBarFragment_skipPrevious);
        skipNextBtn = view.findViewById(R.id.musicBarFragment_skipNext);
        playPauseBtn = view.findViewById(R.id.musicBarFragment_play_pause);
        trackSeekBar = view.findViewById(R.id.musicBarFragment_trackSeekBar);
    }

    public void updateBar(Track track) {

        trackName.setText(track.getTrackTitle());
        trackArtist.setText(track.getArtistName());

    }

}
