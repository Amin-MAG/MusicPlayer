package com.mag.musicplayer.Controller.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.musicplayer.Controller.Activity.TrackPlayerActivity;
import com.mag.musicplayer.Model.Adapter.MusicListAdapter;
import com.mag.musicplayer.Model.MusicRepository;
import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;
import com.mag.musicplayer.Util.MusicPlayer;

public class MusicListFragment extends Fragment {

    private RecyclerView recyclerView;
    private MusicListAdapter adapter;
    private SearchView searchView;
    private ImageButton reapeatBtn, shuffleBtn;

    private MusicListUiCallback uiCallback = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof MusicListUiCallback) {
            uiCallback = (MusicListUiCallback) getActivity();
        }

    }

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
        return inflater.inflate(R.layout.fragment_music_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shuffleBtn = view.findViewById(R.id.musicListFragment_shuffleButton);
        shuffleBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                MusicRepository.getInstance().setShuffleMode(!MusicRepository.getInstance().isShuffle());
                if (MusicRepository.getInstance().isShuffle())
                    MusicRepository.getInstance().makeShuffle();
                updateOptionsBtns();
            }
        });

        reapeatBtn = view.findViewById(R.id.musicListFragment_repeatButton);

        recyclerView = view.findViewById(R.id.musicListFragment_recycler);
        adapter = new MusicListAdapter(MusicRepository.getInstance().getTracks(), new MusicListAdapter.MusicListAdapterCallback() {
            @Override
            public void updateMusicBar(Track track) {
                if (uiCallback != null)
                    uiCallback.updateMusicBar(track);
            }
        });

        MusicPlayer.getInstance().setCallback(new MusicPlayer.MusicPlayerCallback() {
            private Track nextTrack;

            @Override
            public Track getNextTrack() {
                nextTrack = uiCallback.getNext();
                return nextTrack;
            }

            @Override
            public void updateUiAutoSkip() {
                uiCallback.updateMusicBar(nextTrack);
                if (TrackPlayerActivity.getTrackPlayerActivity() != null)
                    TrackPlayerActivity.getTrackPlayerActivity().update(nextTrack);
            }

        });
        recyclerView.setAdapter(adapter);


        searchView = view.findViewById(R.id.musicListFragment_searchView);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reapeatBtn.setVisibility(View.GONE);
                shuffleBtn.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                reapeatBtn.setVisibility(View.VISIBLE);
                shuffleBtn.setVisibility(View.VISIBLE);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.setTracks(MusicRepository.getInstance().getTracks(s.toLowerCase()));
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    private void updateOptionsBtns() {

        shuffleBtn.setBackgroundColor(Color.parseColor(MusicRepository.getInstance().isShuffle() ? "#E6FF1D1D" : "#00FFFFFF"));

    }


    public MusicListAdapter getAdapter() {
        return adapter;
    }

    public interface MusicListUiCallback {
        void updateMusicBar(Track track);

        Track getNext();
    }

}
