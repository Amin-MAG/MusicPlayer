package com.mag.musicplayer.Controller.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mag.musicplayer.R;

public class FileExplorerFragment extends Fragment {


    public static FileExplorerFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FileExplorerFragment fragment = new FileExplorerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FileExplorerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_file_explorer, container, false);
    }

}
