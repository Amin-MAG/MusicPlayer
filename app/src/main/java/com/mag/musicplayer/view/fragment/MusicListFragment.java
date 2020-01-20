package com.mag.musicplayer.view.fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mag.musicplayer.R;
import com.mag.musicplayer.data.model.Adapter.MusicListAdapter;
import com.mag.musicplayer.data.repository.TrackRepository;
import com.mag.musicplayer.databinding.FragmentMusicListBinding;
import com.mag.musicplayer.viewmodel.TrackViewModel;

public class MusicListFragment extends Fragment {

    private FragmentMusicListBinding binding;

    private TrackViewModel viewModel;

    private MusicListAdapter adapter;
    private SearchView searchView;
    private ImageButton repeatBtn, shuffleBtn;


    public static MusicListFragment newInstance() {

        Bundle args = new Bundle();

        MusicListFragment fragment = new MusicListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MusicListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_music_list, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(TrackViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findComponents();


        adapter = new MusicListAdapter(TrackRepository.getInstance().getAllTracks());


        binding.musicListFragmentRecycler.setAdapter(adapter);


        setEvents();

        makeSearchViewWhite();

        setOnChangeEvents();

    }

    @SuppressLint("ResourceType")
    private void setOnChangeEvents() {

        viewModel.isShuffle().observe(this, isShuffle -> shuffleBtn.setBackgroundColor(Color.parseColor(isShuffle ? getResources().getString(R.color.colorPrimaryDark) : "#00FFFFFF")));

        viewModel.isRepeating().observe(this, isRepeating -> {
            repeatBtn.setBackgroundColor(Color.parseColor(isRepeating ? getResources().getString(R.color.colorPrimaryDark) : "#00FFFFFF"));
            repeatBtn.setImageDrawable(isRepeating ? getResources().getDrawable(R.drawable.ic_repeat_one) : getResources().getDrawable(R.drawable.ic_repeat));
        });

    }

    private void setEvents() {

        // Shuffle

        shuffleBtn.setOnClickListener(shuffleBtn -> TrackRepository.getInstance().switchShuffle());

        // Repeat Button

        repeatBtn.setOnClickListener(repeatBtn -> TrackRepository.getInstance().switchRepeating());

        // SearchView Events

        searchView.setOnSearchClickListener(searchViewBox -> {
            repeatBtn.setVisibility(View.GONE);
            shuffleBtn.setVisibility(View.GONE);
            searchView.setVisibility(View.VISIBLE);
        });

        searchView.setOnCloseListener(() -> {
            repeatBtn.setVisibility(View.VISIBLE);
            shuffleBtn.setVisibility(View.VISIBLE);
            return false;
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchingString) {
                adapter.setTracks(viewModel.search(searchingString));
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    private void findComponents() {
        shuffleBtn = binding.musicListFragmentShuffleButton;
        repeatBtn = binding.musicListFragmentRepeatButton;
        searchView = binding.musicListFragmentSearchView;
    }

    @SuppressLint("ResourceType")
    private void makeSearchViewWhite() {
        LinearLayout linearLayout1 = (LinearLayout) searchView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextColor(Color.parseColor(getResources().getString(R.color.white)));
    }


    public MusicListAdapter getAdapter() {
        return adapter;
    }

}
