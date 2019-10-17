package com.mag.musicplayer.Controller.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mag.musicplayer.Model.Adapter.MusicListAdapter;
import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicListFragment extends Fragment {

    private RecyclerView recyclerView;
    private MusicListAdapter adapter;


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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.musicListFragment_recycler);
        adapter = new MusicListAdapter(new ArrayList<Track>(){{
            add(new Track(1,"air","david garrett 2010","david garret","somewhere", null,300, new Date()));
            add(new Track(1,"air","david garrett 2010","david garret","somewhere", null,300, new Date()));
            add(new Track(1,"air","david garrett 2010","david garret","somewhere", null,300, new Date()));
            add(new Track(1,"air","david garrett 2010","david garret","somewhere", null,300, new Date()));
            add(new Track(1,"air","david garrett 2010","david garret","somewhere", null,300, new Date()));
            add(new Track(1,"air","david garrett 2010","david garret","somewhere", null,300, new Date()));
            add(new Track(1,"air","david garrett 2010","david garret","somewhere", null,300, new Date()));
            add(new Track(1,"air","david garrett 2010","david garret","somewhere", null,300, new Date()));
            add(new Track(1,"air","david garrett 2010","david garret","somewhere", null,300, new Date()));
            add(new Track(1,"air","david garrett 2010","david garret","somewhere", null,300, new Date()));
            add(new Track(1,"air","david garrett 2010","david garret","somewhere", null,300, new Date()));
            add(new Track(1,"air","david garrett 2010","david garret","somewhere", null,300, new Date()));
            add(new Track(1,"air","david garrett 2010","david garret","somewhere", null,300, new Date()));
        }});
        recyclerView.setAdapter(adapter);

    }

}
