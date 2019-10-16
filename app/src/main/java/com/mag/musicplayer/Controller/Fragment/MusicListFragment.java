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
public class MusicListFragment extends Fragment {


    public static MusicListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MusicListFragment fragment = new MusicListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MusicListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_list, container, false);
    }

}
