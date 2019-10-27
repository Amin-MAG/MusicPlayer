package com.mag.musicplayer.Controller.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mag.musicplayer.Model.Adapter.AlbumListAdapter;
import com.mag.musicplayer.Model.Adapter.MusicListAdapter;
import com.mag.musicplayer.Model.Album;
import com.mag.musicplayer.Model.MusicRepository;
import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;

import java.util.ArrayList;

public class AlbumListFragment extends Fragment {

    private RecyclerView albumRecyclerView, musicRecyclerView;
    private AlbumListAdapter albumListAdapter;
    private MusicListAdapter musicListAdapter;

    private AlbumListUiCallback uiCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof AlbumListUiCallback) {
            uiCallback = (AlbumListUiCallback) getActivity();
        }

    }

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

        albumRecyclerView = view.findViewById(R.id.albumListFragment_albumRecycler);
        albumRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        albumListAdapter = new AlbumListAdapter(MusicRepository.getInstance().getAlbums(), new AlbumListAdapter.AlbumListAdapterCallback() {
            @Override
            public void updateUi(Album album) {

                albumRecyclerView.setVisibility(View.GONE);
                musicListAdapter.setTracks(MusicRepository.getInstance().getTrackByAlbum(album));
                musicRecyclerView.setVisibility(View.VISIBLE);

            }
        });

        albumRecyclerView.setAdapter(albumListAdapter);

        musicRecyclerView = view.findViewById(R.id.albumListFragment_musicRecycler);
        musicListAdapter = new MusicListAdapter(new ArrayList<Track>(), new MusicListAdapter.MusicListAdapterCallback() {
            @Override
            public void updateMusicBar(Track track) {
                if (uiCallback != null)
                    uiCallback.updateMusicBar(track);
            }
        });
        musicRecyclerView.setAdapter(musicListAdapter);
        musicRecyclerView.setVisibility(View.GONE);

    }

    public interface AlbumListUiCallback {
        void updateMusicBar(Track track);
    }

}
