package com.mag.musicplayer.Controller.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.musicplayer.Model.Adapter.MusicFilterItemAdapter;
import com.mag.musicplayer.R;

import java.util.ArrayList;

public class FilterItemsFragment extends Fragment {

    private RecyclerView recyclerView;
    private MusicFilterItemAdapter adapter;

    public static FilterItemsFragment newInstance() {

        Bundle args = new Bundle();

        FilterItemsFragment fragment = new FilterItemsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FilterItemsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new MusicFilterItemAdapter(new ArrayList<String>() {{
            add("Songs");
            add("Songs");
            add("Songs");
            add("Songs");
            add("Songs");
            add("Songs");
            add("Songs");
            add("Songs");
        }});
        recyclerView = view.findViewById(R.id.filterItemsFragment_recycler);
        recyclerView.setAdapter(adapter);


    }

}
