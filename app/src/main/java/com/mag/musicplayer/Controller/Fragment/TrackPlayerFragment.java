package com.mag.musicplayer.Controller.Fragment;


import android.content.Context;
import android.os.Bundle;
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

import com.mag.musicplayer.Model.MusicRepository;
import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;
import com.mag.musicplayer.Util.MusicPlayer;
import com.mag.musicplayer.Util.PictureUtil;

import java.io.IOException;

public class TrackPlayerFragment extends Fragment {

    public static final String ARG_TRACK = "arg_track";
    private ImageView trackImage;
    private TextView trackTitle, trackArtist, trackLength, trackTime;
    private SeekBar trackSeekBar;
    private ImageButton playPauseBtn, skipNextBtn, skipPreviousBtn;

    private TrackPlayerCallback callback;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof TrackPlayerCallback)
            callback = (TrackPlayerCallback) getActivity();

    }

    public static TrackPlayerFragment newInstance(Track track) {

        Bundle args = new Bundle();
        args.putLong(ARG_TRACK, track.getTrackId());

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

        long trackId =  getArguments().getLong(ARG_TRACK);
        Track track = MusicRepository.getInstance().getTrackById(trackId);

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
        trackLength.setText(MusicPlayer.getLengthText(track.getTrackLength() / 1000));

        if (MusicPlayer.getInstance().getMediaPlayer().isPlaying()) {
            playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
        } else {
            playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
        }


        if (track.getImageThumbnail() == null)
            trackImage.setImageDrawable(getResources().getDrawable(R.drawable.music_icon));
        else
            trackImage.setImageBitmap(PictureUtil.getScaleBitmap(MusicPlayer.getInputStreamOfImage(getActivity().getContentResolver(), track.getImagePath()), 512, 512));


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

        skipPreviousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Track previousTrack = callback.getTrackDistance(-1);
                try {
                    MusicPlayer.getInstance().playMusic(previousTrack, getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                update(previousTrack);
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
                update(nextTrack);
            }
        });

    }

    private void update(Track track) {

        trackTitle.setText(track.getTrackTitle());
        trackArtist.setText(track.getArtistName());
        trackLength.setText(MusicPlayer.getLengthText(track.getTrackLength() / 1000));

        if (MusicPlayer.getInstance().getMediaPlayer().isPlaying()) {
            playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
        } else {
            playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
        }

        if (track.getImageThumbnail() == null) {
            trackImage.setImageDrawable(getResources().getDrawable(R.drawable.music_icon));
        } else {
            trackImage.setImageBitmap(PictureUtil.getScaleBitmap(MusicPlayer.getInputStreamOfImage(getActivity().getContentResolver(), track.getImagePath()), 512, 512));
        }


    }

    public interface TrackPlayerCallback {
        Track getTrackDistance(int distance);
    }

}
