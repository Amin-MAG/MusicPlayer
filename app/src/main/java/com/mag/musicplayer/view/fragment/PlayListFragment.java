package com.mag.musicplayer.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.mag.musicplayer.R;
import com.mag.musicplayer.databinding.FragmentPlayListBinding;

public class PlayListFragment extends Fragment {

    private FragmentPlayListBinding binding;

    public static PlayListFragment newInstance() {

        Bundle args = new Bundle();

        PlayListFragment fragment = new PlayListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PlayListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_play_list, container, false);
        return binding.getRoot();
    }

}
