package com.mag.musicplayer.Controller.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mag.musicplayer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackPlayerFragment extends Fragment {


    public static TrackPlayerFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TrackPlayerFragment fragment = new TrackPlayerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TrackPlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_track_player, container, false);
    }

}
