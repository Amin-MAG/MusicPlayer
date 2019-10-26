package com.mag.musicplayer.Model.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.HashMap;

public class MusicViewPagerAdapter extends FragmentStatePagerAdapter {

    private HashMap<String, Fragment> fragments;
    public MusicViewPagerAdapter(FragmentManager fm, HashMap<String, Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragments.get("view_pager__music_list");
            case 1:
                return fragments.get("view_pager__album_list");
            case 2:
                return fragments.get("view_pager__music_list3");
            case 3:
                return fragments.get("view_pager__music_list4");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
