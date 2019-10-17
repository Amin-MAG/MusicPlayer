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
public class FilterItemsFragment extends Fragment {


    public static FilterItemsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FilterItemsFragment fragment = new FilterItemsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FilterItemsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_items, container, false);
    }

}
