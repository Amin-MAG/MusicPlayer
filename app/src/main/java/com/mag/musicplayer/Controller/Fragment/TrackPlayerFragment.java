package com.mag.musicplayer.Controller.Fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;
import com.mag.musicplayer.Util.MusicPlayer;
import com.mag.musicplayer.Util.PictureUtil;

public class TrackPlayerFragment extends Fragment {

    public static final String ARG_TRACK = "arg_track";
    private ImageView trackImage;
    private TextView trackTitle, trackArtist, trackLength, trackTime;
    private SeekBar trackSeekBar;
    private ImageButton playPauseBtn, skipNextBtn, skipPreviousBtn;

    public static TrackPlayerFragment newInstance(Track track) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TRACK, track);

        TrackPlayerFragment fragment = new TrackPlayerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TrackPlayerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_track_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Track track = (Track) getArguments().get(ARG_TRACK);

        trackTitle = view.findViewById(R.id.trackPlayerActivity_trackTitle);
        trackArtist = view.findViewById(R.id.trackPlayerActivity_trackArtist);
        trackLength = view.findViewById(R.id.trackPlayerActivity_trackLength);
        trackTime = view.findViewById(R.id.trackPlayerActivity_trackTime);
        trackImage = view.findViewById(R.id.trackPlayerActivity_cover);
        trackSeekBar = view.findViewById(R.id.trackPlayerActivity_seekbar);
        playPauseBtn = view.findViewById(R.id.trackPlayerActivity_play_pause);
        skipNextBtn = view.findViewById(R.id.trackPlayerActivity_skipNext);
        skipPreviousBtn = view.findViewById(R.id.trackPlayerActivity_skipPrevious);

        trackTitle.setText(track.getTrackTitle());
        trackArtist.setText(track.getArtistName());
        trackLength.setText(MusicPlayer.getLengthText(track.getTrackLength()/1000));

        Log.d("pathLog", track.getImagePath() + "") ;

        if (track.getImageThumbnail() == null)
            trackImage.setImageDrawable(getResources().getDrawable(R.drawable.music_icon));
        else
            trackImage.setImageBitmap(PictureUtil.getScaleBitmap(MusicPlayer.getInputStreamOfImage(getActivity().getContentResolver(), track.getImagePath()), 512, 512));


    }

}
