package com.mag.musicplayer.Controller.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mag.musicplayer.R;
import com.mag.musicplayer.data.model.Adapter.MusicViewPagerAdapter;
import com.mag.musicplayer.databinding.FragmentMusicPlayerMainBinding;
import com.mag.musicplayer.view.fragment.AlbumListFragment;
import com.mag.musicplayer.view.fragment.ArtistListFragment;
import com.mag.musicplayer.view.fragment.FileExplorerFragment;
import com.mag.musicplayer.view.fragment.MusicListFragment;
import com.mag.musicplayer.view.fragment.PlayListFragment;

public class MusicPlayerMainFragment extends Fragment {


    private FragmentMusicPlayerMainBinding binding;

    private MusicViewPagerAdapter musicViewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public static MusicPlayerMainFragment newInstance() {

        Bundle args = new Bundle();

        MusicPlayerMainFragment fragment = new MusicPlayerMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MusicPlayerMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_music_player_main, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findComponents();

        setViewPagerAdapter();

        setViewpager();

        setEvents();


    }

    private void setEvents() {


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void findComponents() {
        viewPager = binding.musicViewPagerFragmentViewPager;
        tabLayout = binding.musicPlayerActivityTabLayout;
    }

    private void setViewPagerAdapter() {
        musicViewPagerAdapter = new MusicViewPagerAdapter(getFragmentManager());
        musicViewPagerAdapter.addFrag(MusicListFragment.newInstance(), "All Musics");
        musicViewPagerAdapter.addFrag(AlbumListFragment.newInstance(), "Albums");
        musicViewPagerAdapter.addFrag(ArtistListFragment.newInstance(), "Artists");
        musicViewPagerAdapter.addFrag(PlayListFragment.newInstance(), "Playlists");
        musicViewPagerAdapter.addFrag(FileExplorerFragment.newInstance(), "File Explorer");
    }

    private void setViewpager() {

        viewPager.setAdapter(musicViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // Add Tabs
        for (int k = tabLayout.getTabCount(); k < musicViewPagerAdapter.getCount(); k++)
            tabLayout.addTab(tabLayout.newTab().setText(musicViewPagerAdapter.getPageTitle(k)));

        //  Go To First Page
        tabLayout.getTabAt(0).select();

    }

}
