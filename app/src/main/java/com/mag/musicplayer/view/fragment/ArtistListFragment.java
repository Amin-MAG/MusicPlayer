package com.mag.musicplayer.view.fragment;


import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.musicplayer.R;
import com.mag.musicplayer.view.adapter.ArtistListAdapter;
import com.mag.musicplayer.view.adapter.MusicListAdapter;
import com.mag.musicplayer.databinding.FragmentArtistListBinding;
import com.mag.musicplayer.viewmodel.ArtistViewModel;
import com.mag.musicplayer.viewmodel.TrackViewModel;

import java.util.ArrayList;

public class ArtistListFragment extends Fragment {

    private FragmentArtistListBinding binding;

    private ArtistViewModel viewModel;
    private TrackViewModel trackViewModel;


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
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_artist_list, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(ArtistViewModel.class);
        trackViewModel = ViewModelProviders.of(getActivity()).get(TrackViewModel.class);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findComponents();


        setArtistAdapter();

        setMusicAdapter();

        setEvents();

        makeSearchViewWhite();

        setOnChangeEvents();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setOnChangeEvents() {

        viewModel.isArtistSelected().observe(this, selected -> {

            if (selected) {

                artistRecyclerView.setVisibility(View.GONE);
                searchView.setVisibility(View.GONE);
                musicRecyclerView.setVisibility(View.VISIBLE);
                artistName.setVisibility(View.VISIBLE);
                backBtn.setVisibility(View.VISIBLE);

                musicListAdapter.setTracks(viewModel.getTracksByArtist());
                binding.setArtistViewModel(viewModel);

            } else {

                artistRecyclerView.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.VISIBLE);
                musicRecyclerView.setVisibility(View.GONE);
                artistName.setVisibility(View.GONE);
                backBtn.setVisibility(View.GONE);

            }

        });

        trackViewModel.getPlayingTrack().observe(this, track -> {
            trackViewModel.setTrack(track);
            musicListAdapter.notifyDataSetChanged();
        });


    }

    private void setEvents() {

        backBtn.setOnClickListener(backBtnView -> viewModel.showArtistView());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String s) {
                albumListAdapter.setArtists(viewModel.search(s.toLowerCase()));
                albumListAdapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    private void setMusicAdapter() {
        musicListAdapter = new MusicListAdapter(new ArrayList<>());
        musicRecyclerView.setAdapter(musicListAdapter);
    }

    private void setArtistAdapter() {
//        int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        int spanCount = 3;
        artistRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        albumListAdapter = new ArtistListAdapter(viewModel.getArtists());
        artistRecyclerView.setAdapter(albumListAdapter);
    }

    private void findComponents() {
        backBtn = binding.artistListFragmentBackButton;
        artistName = binding.artistListFragmentArtistName;
        searchView = binding.artistListFragmentSearchview;
        artistRecyclerView = binding.artistListFragmentArtistRecycler;
        musicRecyclerView = binding.artistListFragmentMusicRecycler;
    }

    @SuppressLint("ResourceType")
    private void makeSearchViewWhite() {
        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextColor(Color.parseColor(getResources().getString(R.color.white)));
    }

}
