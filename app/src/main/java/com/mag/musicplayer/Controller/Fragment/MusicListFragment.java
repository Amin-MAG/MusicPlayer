package com.mag.musicplayer.Controller.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.musicplayer.Controller.Activity.TrackPlayerActivity;
import com.mag.musicplayer.data.model.Adapter.MusicListAdapter;
import com.mag.musicplayer.data.repository.TrackRepository;
import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.R;
import com.mag.musicplayer.util.MusicPlayer;

public class MusicListFragment extends Fragment {

    private RecyclerView recyclerView;
    private MusicListAdapter adapter;
    private SearchView searchView;
    private ImageButton repeatBtn, shuffleBtn;

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
                TrackRepository.getInstance().setShuffleMode(!TrackRepository.getInstance().isShuffle());
                if (TrackRepository.getInstance().isShuffle())
                    TrackRepository.getInstance().makeShuffle();
                updateOptionsBtns();
            }
        });

        repeatBtn = view.findViewById(R.id.musicListFragment_repeatButton);
        repeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrackRepository.getInstance().setRepeatingMode(!TrackRepository.getInstance().isRepeatingMode());
                updateOptionsBtns();
            }
        });

        recyclerView = view.findViewById(R.id.musicListFragment_recycler);
        adapter = new MusicListAdapter(TrackRepository.getInstance().getTracks(), new MusicListAdapter.MusicListAdapterCallback() {
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
//                if (TrackPlayerActivity.getTrackPlayerActivity() != null)
//                    TrackPlayerActivity.getTrackPlayerActivity().update(nextTrack);
            }

        });
        recyclerView.setAdapter(adapter);


        searchView = view.findViewById(R.id.musicListFragment_searchView);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatBtn.setVisibility(View.GONE);
                shuffleBtn.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                repeatBtn.setVisibility(View.VISIBLE);
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
                adapter.setTracks(TrackRepository.getInstance().getTracks(s.toLowerCase()));
                adapter.notifyDataSetChanged();
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

    @SuppressLint("ResourceType")
    private void updateOptionsBtns() {

        shuffleBtn.setBackgroundColor(Color.parseColor(TrackRepository.getInstance().isShuffle() ? getResources().getString(R.color.colorPrimaryDark) : "#00FFFFFF"));
        repeatBtn.setBackgroundColor(Color.parseColor(TrackRepository.getInstance().isRepeatingMode() ? getResources().getString(R.color.colorPrimaryDark) : "#00FFFFFF"));
        repeatBtn.setImageDrawable(TrackRepository.getInstance().isRepeatingMode() ? getResources().getDrawable(R.drawable.ic_repeat_one) : getResources().getDrawable(R.drawable.ic_repeat));

    }


    public MusicListAdapter getAdapter() {
        return adapter;
    }

    public interface MusicListUiCallback {
        void updateMusicBar(Track track);

        Track getNext();
    }

}
