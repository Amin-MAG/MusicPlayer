package com.mag.musicplayer.Controller.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mag.musicplayer.R;

public class SettingFragment extends Fragment {


    public static SettingFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SettingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

}
