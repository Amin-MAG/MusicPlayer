package com.mag.musicplayer.Controller.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mag.musicplayer.data.model.Adapter.AlbumListAdapter;
import com.mag.musicplayer.data.model.Adapter.MusicListAdapter;
import com.mag.musicplayer.data.model.Album;
import com.mag.musicplayer.data.repository.AlbumRepository;
import com.mag.musicplayer.data.repository.TrackRepository;
import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.R;

import java.util.ArrayList;

public class AlbumListFragment extends Fragment {

    private RecyclerView albumRecyclerView, musicRecyclerView;
    private AlbumListAdapter albumListAdapter;
    private MusicListAdapter musicListAdapter;
    private TextView albumName;
    private ImageView backBtn;
    private SearchView searchView;

    public static AlbumListFragment newInstance() {

        Bundle args = new Bundle();

        AlbumListFragment fragment = new AlbumListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AlbumListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBtn = view.findViewById(R.id.albumListFragment_backButton);
        albumName = view.findViewById(R.id.albumListFragment_albumName);
        searchView = view.findViewById(R.id.albumListFragment_searchview);

        albumRecyclerView = view.findViewById(R.id.albumListFragment_albumRecycler);
        int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        albumRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        albumListAdapter = new AlbumListAdapter(AlbumRepository.getInstance().getAllAlbums(), album -> {

            albumName.setText(album.getAlbumTitle().length() > 30 ? album.getAlbumTitle().substring(0,30) + "..." : album.getAlbumTitle());
            albumRecyclerView.setVisibility(View.GONE);
            searchView.setVisibility(View.GONE);
            musicListAdapter.setTracks(TrackRepository.getInstance().getTrackByAlbum(album));
            musicListAdapter.notifyDataSetChanged();
            musicRecyclerView.setVisibility(View.VISIBLE);
            albumName.setVisibility(View.VISIBLE);
            backBtn.setVisibility(View.VISIBLE);

        });
        albumRecyclerView.setAdapter(albumListAdapter);

        musicRecyclerView = view.findViewById(R.id.albumListFragment_musicRecycler);
        musicListAdapter = new MusicListAdapter(new ArrayList<>());
        musicRecyclerView.setAdapter(musicListAdapter);

        musicRecyclerView.setVisibility(View.GONE);
        albumName.setVisibility(View.GONE);
        backBtn.setVisibility(View.GONE);

        backBtn.setOnClickListener(view1 -> {

            albumRecyclerView.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE);
            musicRecyclerView.setVisibility(View.GONE);
            albumName.setVisibility(View.GONE);
            backBtn.setVisibility(View.GONE);

        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                albumListAdapter.setAlbums(AlbumRepository.getInstance().getAlbums(s.toLowerCase()));
                albumListAdapter.notifyDataSetChanged();
                return false;
            }
        });

        makeSearchViewWhite();

    }

    @SuppressLint("ResourceType")
    private void makeSearchViewWhite() {
        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextColor(Color.parseColor(getResources().getString(R.color.white)));
    }

    public MusicListAdapter getMusicListAdapter() {
        return musicListAdapter;
    }

}
