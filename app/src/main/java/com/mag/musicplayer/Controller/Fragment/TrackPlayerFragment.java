package com.mag.musicplayer.Controller.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mag.musicplayer.R;
import com.mag.musicplayer.data.var.Constants;
import com.mag.musicplayer.databinding.FragmentTrackPlayerBinding;
import com.mag.musicplayer.util.MusicPlayer;
import com.mag.musicplayer.viewmodel.TrackViewModel;
import com.squareup.picasso.Picasso;

public class TrackPlayerFragment extends Fragment {

    private FragmentTrackPlayerBinding binding;

    private TrackViewModel viewModel;

    private TextView trackTime;
    private SeekBar trackSeekBar;

    public static TrackPlayerFragment newInstance() {

        Bundle args = new Bundle();

        TrackPlayerFragment fragment = new TrackPlayerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TrackPlayerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_track_player, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(TrackViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        findComponents();


        binding.trackPlayerActivityTrackTitle.setText(viewModel.getTrackTitle());
        binding.trackPlayerActivityTrackArtist.setText(viewModel.getArtistName());
        binding.trackPlayerActivityTrackLength.setText(MusicPlayer.getStringTime(viewModel.getTrackLength() / 1000));
        // Track Cover
        Picasso.get().load(viewModel.getCoverSrc()).placeholder(getResources().getDrawable(R.drawable.music_icon)).into(binding.trackPlayerActivityCover);
        binding.trackPlayerActivityPlayPause.setImageDrawable(getResources().getDrawable(viewModel.isPlayingMusic() ? R.drawable.ic_pause : R.drawable.ic_play));


        seekbarInitialization();

        setEvents();

    }

    private void setEvents() {
        binding.trackPlayerActivityPlayPause.setOnClickListener(btnView -> viewModel.onPausePlayBtnClicked());

        binding.trackPlayerActivitySkipPrevious.setOnClickListener(previousBtnView -> viewModel.onPreviousBtnClicked());

        binding.trackPlayerActivitySkipNext.setOnClickListener(nextBtnView -> viewModel.onNextBtnClicked());
    }

    private void seekbarInitialization() {

        Constants.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                if (!trackSeekBar.isPressed()) {
                    trackSeekBar.setProgress(viewModel.getPlayedPercentage());
                    trackTime.setText(viewModel.getPlayedTime());
                }
                Constants.HANDLER.postDelayed(this, 100);
            }
        });

        trackSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trackTime.setText(viewModel.getProgressTime(progress));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                viewModel.seekPlayingMusicTo(progressChangedValue);
            }

        });

    }

    private void findComponents() {
        trackTime = binding.trackPlayerActivityTrackTime;
        trackSeekBar = binding.trackPlayerActivitySeekbar;
    }


}
