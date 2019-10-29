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
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.mag.musicplayer.Model.Adapter.AlbumListAdapter;
import com.mag.musicplayer.Model.Adapter.ArtistListAdapter;
import com.mag.musicplayer.Model.Adapter.MusicListAdapter;
import com.mag.musicplayer.Model.Album;
import com.mag.musicplayer.Model.Artist;
import com.mag.musicplayer.Model.MusicRepository;
import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;

import java.util.ArrayList;

public class ArtistListFragment extends Fragment {

    private RecyclerView artistRecyclerView, musicRecyclerView;
    private ArtistListAdapter albumListAdapter;
    private MusicListAdapter musicListAdapter;
    private TextView artistName;
    private ImageView backBtn;
    private SearchView searchView;

    private ArtistListUiCallback uiCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof ArtistListUiCallback) {
            uiCallback = (ArtistListUiCallback) getActivity();
        }

    }

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

        artistRecyclerView= view.findViewById(R.id.artistListFragment_artistRecycler);
        artistRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        albumListAdapter = new ArtistListAdapter(MusicRepository.getInstance().getArtists(), new ArtistListAdapter.ArtistListAdapterCallback(){
            @Override
            public void updateUi(Artist artist) {

                artistName.setText(artist.getArtistName().length() > 30 ? artist.getArtistName().substring(0,30) + "..." : artist.getArtistName());
                artistRecyclerView.setVisibility(View.GONE);
                searchView.setVisibility(View.GONE);
                musicListAdapter.setTracks(MusicRepository.getInstance().getTrackByArtist(artist));
                musicListAdapter.notifyDataSetChanged();
                musicRecyclerView.setVisibility(View.VISIBLE);
                artistName.setVisibility(View.VISIBLE);
                backBtn.setVisibility(View.VISIBLE);

            }
        });
        artistRecyclerView.setAdapter(albumListAdapter);

        musicRecyclerView = view.findViewById(R.id.artistListFragment_musicRecycler);
        musicListAdapter = new MusicListAdapter(new ArrayList<Track>(), new MusicListAdapter.MusicListAdapterCallback() {
            @Override
            public void updateMusicBar(Track track) {
                if (uiCallback != null){
                    uiCallback.updateMusicBar(track);
                }
            }
        });
        musicRecyclerView.setAdapter(musicListAdapter);

        musicRecyclerView.setVisibility(View.GONE);
        artistName.setVisibility(View.GONE);
        backBtn.setVisibility(View.GONE);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                artistRecyclerView.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.VISIBLE);
                musicRecyclerView.setVisibility(View.GONE);
                artistName.setVisibility(View.GONE);
                backBtn.setVisibility(View.GONE);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                albumListAdapter.setArtists(MusicRepository.getInstance().getArtists(s.toLowerCase()));
                albumListAdapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    public interface ArtistListUiCallback {
        void updateMusicBar(Track track);
    }

    public MusicListAdapter getMusicListAdapter() {
        return musicListAdapter;
    }

}
