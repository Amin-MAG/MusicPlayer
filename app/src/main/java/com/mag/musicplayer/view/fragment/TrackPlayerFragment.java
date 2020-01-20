package com.mag.musicplayer.view.fragment;


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

        binding.setTrackViewModel(viewModel);

        seekbarInitialization();

        setEvents();

        setOnChangeEvents();
    }

    private void setEvents() {

        binding.trackPlayerActivityPlayPause.setOnClickListener(btnView -> viewModel.onPlayPauseBtnClicked());

        binding.trackPlayerActivitySkipPrevious.setOnClickListener(previousBtnView -> viewModel.onPreviousBtnClicked());

        binding.trackPlayerActivitySkipNext.setOnClickListener(nextBtnView -> viewModel.onNextBtnClicked());


    }

    private void setOnChangeEvents() {

        viewModel.isPlayingMusic().observe(this, playing -> binding.trackPlayerActivityPlayPause.setImageDrawable(getResources().getDrawable(playing ? R.drawable.ic_pause : R.drawable.ic_play)));

        viewModel.getPlayingTrack().observe(this, track -> {
            binding.setTrackViewModel(viewModel);
            Picasso.get().load(viewModel.getPlayingCoverSrc()).placeholder(getResources().getDrawable(R.drawable.music_icon)).into(binding.trackPlayerActivityCover);
        });

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
                this.progressChangedValue = progress;
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
