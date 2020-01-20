package com.mag.musicplayer.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mag.musicplayer.R;
import com.mag.musicplayer.databinding.FragmentMusicBarBinding;
import com.mag.musicplayer.view.activity.TrackPlayerActivity;
import com.mag.musicplayer.viewmodel.TrackViewModel;
import com.squareup.picasso.Picasso;

public class MusicBarFragment extends Fragment {

    private FragmentMusicBarBinding binding;
    private TrackViewModel viewModel;


    public static MusicBarFragment newInstance() {

        Bundle args = new Bundle();

        MusicBarFragment fragment = new MusicBarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MusicBarFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_music_bar, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(TrackViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setTrackViewModel(viewModel);

        setEvents();

        setOnChangeEvents();

    }

    private void setEvents() {


        binding.getRoot().setOnClickListener(barView -> startActivity(TrackPlayerActivity.newIntent(getActivity())));

        binding.trackPlayerActivityPlayPause.setOnClickListener(btnView -> viewModel.onPlayPauseBtnClicked());

        binding.trackPlayerActivitySkipNext.setOnClickListener(skipNextBtn -> viewModel.onNextBtnClicked());

        binding.trackPlayerActivitySkipPrevious.setOnClickListener(skipPreviousBtn -> viewModel.onPreviousBtnClicked());

    }

    private void setOnChangeEvents() {

        viewModel.isPlayingMusic().observe(this, playing -> binding.trackPlayerActivityPlayPause.setImageDrawable(getResources().getDrawable(playing ? R.drawable.ic_pause : R.drawable.ic_play)));

        viewModel.getPlayingTrack().observe(this, track -> {
            binding.setTrackViewModel(viewModel);
            Picasso.get().load(viewModel.getPlayingCoverSrc()).placeholder(getResources().getDrawable(R.drawable.music_icon)).into(binding.musicBarFragmentTrackImage);
        });

        viewModel.getPlayingTrack().observe(this, playingTrack -> {
            if (playingTrack != null) binding.getRoot().setVisibility(View.VISIBLE);
            else binding.getRoot().setVisibility(View.GONE);
        });

    }

}
