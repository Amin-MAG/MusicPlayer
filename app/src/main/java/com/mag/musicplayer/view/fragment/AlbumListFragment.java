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
import com.mag.musicplayer.view.adapter.AlbumListAdapter;
import com.mag.musicplayer.data.model.Adapter.MusicListAdapter;
import com.mag.musicplayer.databinding.FragmentAlbumListBinding;
import com.mag.musicplayer.viewmodel.AlbumViewModel;

public class AlbumListFragment extends Fragment {

    private FragmentAlbumListBinding binding;

    private AlbumViewModel viewModel;

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
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_album_list, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(AlbumViewModel.class);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findComponents();

        setAlbumAdapter();

        setMusicAdapter();

        setEvents();

        makeSearchViewWhite();

        setOnChangeEvents();

    }

    private void setEvents() {

        backBtn.setOnClickListener(backBtn -> viewModel.showAlbumView());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String searchingString) {
                albumListAdapter.setAlbums(viewModel.search(searchingString.toLowerCase()));
                albumListAdapter.notifyDataSetChanged();
                return false;
            }

        });

    }

    private void setMusicAdapter() {
        musicListAdapter = new MusicListAdapter();
        musicRecyclerView.setAdapter(musicListAdapter);
    }

    private void findComponents() {
        backBtn = binding.albumListFragmentBackButton;
        albumName = binding.albumListFragmentAlbumName;
        searchView = binding.albumListFragmentSearchview;
        albumRecyclerView = binding.albumListFragmentAlbumRecycler;
        musicRecyclerView = binding.albumListFragmentMusicRecycler;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setOnChangeEvents() {

        viewModel.isAlbumSelected().observe(this, selected -> {

            if (selected) {

                albumRecyclerView.setVisibility(View.GONE);
                searchView.setVisibility(View.GONE);
                musicRecyclerView.setVisibility(View.VISIBLE);
                albumName.setVisibility(View.VISIBLE);
                backBtn.setVisibility(View.VISIBLE);

                musicListAdapter.setTracks(viewModel.getTracksByAlbum());
                binding.setAlbumViewModel(viewModel);

            } else {

                albumRecyclerView.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.VISIBLE);
                musicRecyclerView.setVisibility(View.GONE);
                albumName.setVisibility(View.GONE);
                backBtn.setVisibility(View.GONE);

            }

        });


    }

    private void setAlbumAdapter() {

        int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        albumRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        albumListAdapter = new AlbumListAdapter(viewModel.getAlbumList());
        albumRecyclerView.setAdapter(albumListAdapter);

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
