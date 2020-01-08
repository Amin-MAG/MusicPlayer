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
import com.mag.musicplayer.R;
import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.data.repository.TrackRepository;
import com.mag.musicplayer.util.MusicPlayer;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MusicBarFragment extends Fragment {

    public static final String ON_SAVE_INSTANCE_TRACK_ID = "on_save_instance_track_id";
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (barTrack != null)
            outState.putLong(ON_SAVE_INSTANCE_TRACK_ID, barTrack.getTrackId());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            barTrack = TrackRepository.getInstance().getTrackById(savedInstanceState.getLong(ON_SAVE_INSTANCE_TRACK_ID));
        }

//        Log.d("LifeCycle", "OnViewCreated " + barTrack + " " + this);
//        Log.d("LifeCycle", getFragmentManager().getFragments().size() + "");


        findItems(view);

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (barTrack != null)
                    startActivity(TrackPlayerActivity.newIntent(getActivity()));
            }
        });

        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicPlayer.getInstance().getMediaPlayerOld().isPlaying()) {
                    MusicPlayer.getInstance().getMediaPlayerOld().pause();
                    playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                } else {
                    MusicPlayer.getInstance().getMediaPlayerOld().start();
                    playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                }
            }
        });

        skipNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Track nextTrack = callback.getTrackDistance(+1);
                callback.updateRecyclerSelectedTrack(nextTrack);
                try {
                    MusicPlayer.getInstance().playMusic(nextTrack, getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
//                updateBar(nextTrack);
            }
        });

        skipPreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Track previousTrack = callback.getTrackDistance(-1);
                callback.updateRecyclerSelectedTrack(previousTrack);
                try {
                    MusicPlayer.getInstance().playMusic(previousTrack, getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                updateBar(previousTrack);
            }
        });

        updateBar(barTrack);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//                Log.d("LifeCycle", "FragmentDestroyed" + " " + this);

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

        if (track != null) {

            trackName.setText(track.getTrackTitle().length() > 24 ? track.getTrackTitle().substring(0, 24) + "..." : track.getTrackTitle());
            trackArtist.setText(track.getArtistName());
            Picasso.get().load(track.getImagePath()).placeholder(getResources().getDrawable(R.drawable.music_icon)).into(trackImage);

            barTrack = track;
        }

        updatePlayPause();
    }

    private void updatePlayPause() {

        if (MusicPlayer.getInstance().getMediaPlayerOld().isPlaying()) {
            playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
        } else {
            playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
        }

    }

    public interface MusicBarCallback {
        Track getTrackDistance(int distance);

        void updateRecyclerSelectedTrack(Track track);
    }

}
