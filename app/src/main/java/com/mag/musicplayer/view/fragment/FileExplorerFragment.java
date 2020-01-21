package com.mag.musicplayer.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.mag.musicplayer.R;
import com.mag.musicplayer.databinding.FragmentFileExplorerBinding;

public class FileExplorerFragment extends Fragment {

    private FragmentFileExplorerBinding binding;


    public static FileExplorerFragment newInstance() {

        Bundle args = new Bundle();

        FileExplorerFragment fragment = new FileExplorerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FileExplorerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_file_explorer, container, false);
        return binding.getRoot();
    }

}
