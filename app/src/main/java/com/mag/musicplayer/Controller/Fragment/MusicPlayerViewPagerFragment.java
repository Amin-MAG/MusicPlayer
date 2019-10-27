package com.mag.musicplayer.Controller.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.mag.musicplayer.Model.Adapter.MusicViewPagerAdapter;
import com.mag.musicplayer.Model.MusicRepository;
import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.R;

import java.util.HashMap;
import java.util.List;

public class MusicPlayerViewPagerFragment extends Fragment {

    public static final String VIEW_PAGER__MUSIC_LIST = "view_pager__music_list";
    public static final String VIEW_PAGER_ALBUM_LIST = "view_pager__album_list";
    public static final String VIEW_PAGER__ARTIST_LIST = "view_pager__artist_list";
    private HashMap<String, Fragment> fragments = new HashMap<>();
    private MusicViewPagerAdapter musicViewPagerAdapter;
    private ViewPager viewPager;

    public static MusicPlayerViewPagerFragment newInstance() {

        Bundle args = new Bundle();

        MusicPlayerViewPagerFragment fragment = new MusicPlayerViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MusicPlayerViewPagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_player_view_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragments.put(VIEW_PAGER__MUSIC_LIST, MusicListFragment.newInstance());
        fragments.put(VIEW_PAGER_ALBUM_LIST , AlbumListFragment.newInstance());
        fragments.put(VIEW_PAGER__ARTIST_LIST, ArtistListFragment.newInstance());

        viewPager = view.findViewById(R.id.musicViewPagerFragment_viewPager);
        musicViewPagerAdapter = new MusicViewPagerAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(musicViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public MusicListFragment getMusicList() {
        return (MusicListFragment) fragments.get(VIEW_PAGER__MUSIC_LIST);
    }

    public AlbumListFragment getAlbumList() {
        return (AlbumListFragment) fragments.get(VIEW_PAGER_ALBUM_LIST);
    }

    public ArtistListFragment getArtistList() {
        return (ArtistListFragment) fragments.get(VIEW_PAGER__ARTIST_LIST);
    }


}
