package com.mag.musicplayer.Controller.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.musicplayer.data.model.Adapter.ArtistListAdapter;
import com.mag.musicplayer.data.model.Adapter.MusicListAdapter;
import com.mag.musicplayer.data.model.Artist;
import com.mag.musicplayer.data.repository.ArtistRepository;
import com.mag.musicplayer.data.repository.TrackRepository;
import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.R;

import java.util.ArrayList;

public class ArtistListFragment extends Fragment {

    private RecyclerView artistRecyclerView, musicRecyclerView;
    private ArtistListAdapter albumListAdapter;
    private MusicListAdapter musicListAdapter;
    private TextView artistName;
    private ImageView backBtn;
    private SearchView searchView;

    public static ArtistListFragment newInstance() {

        Bundle args = new Bundle();

        ArtistListFragment fragment = new ArtistListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ArtistListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBtn = view.findViewById(R.id.artistListFragment_backButton);
        artistName = view.findViewById(R.id.artistListFragment_artistName);
        searchView = view.findViewById(R.id.artistListFragment_searchview);

        artistRecyclerView = view.findViewById(R.id.artistListFragment_artistRecycler);
        int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        artistRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        albumListAdapter = new ArtistListAdapter(ArtistRepository.getInstance().getAllArtists(), artist -> {

            artistName.setText(artist.getArtistName().length() > 30 ? artist.getArtistName().substring(0, 30) + "..." : artist.getArtistName());
            artistRecyclerView.setVisibility(View.GONE);
            searchView.setVisibility(View.GONE);
            musicListAdapter.setTracks(TrackRepository.getInstance().getTrackByArtist(artist));
            musicListAdapter.notifyDataSetChanged();
            musicRecyclerView.setVisibility(View.VISIBLE);
            artistName.setVisibility(View.VISIBLE);
            backBtn.setVisibility(View.VISIBLE);

        });
        artistRecyclerView.setAdapter(albumListAdapter);

        musicRecyclerView = view.findViewById(R.id.artistListFragment_musicRecycler);
        musicListAdapter = new MusicListAdapter(new ArrayList<>());
        musicRecyclerView.setAdapter(musicListAdapter);

        musicRecyclerView.setVisibility(View.GONE);
        artistName.setVisibility(View.GONE);
        backBtn.setVisibility(View.GONE);

        backBtn.setOnClickListener(view1 -> {

            artistRecyclerView.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.VISIBLE);
            musicRecyclerView.setVisibility(View.GONE);
            artistName.setVisibility(View.GONE);
            backBtn.setVisibility(View.GONE);

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                albumListAdapter.setArtists(ArtistRepository.getInstance().getArtists(s.toLowerCase()));
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
