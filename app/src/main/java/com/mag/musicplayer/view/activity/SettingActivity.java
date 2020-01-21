package com.mag.musicplayer.view.activity;

import androidx.fragment.app.Fragment;

import com.mag.musicplayer.view.fragment.SettingFragment;

public class SettingActivity extends SingleFragmentActivity {

    public static final String TAG_SETTING_FRAGMENT = "tag_setting_fragment";

    @Override
    public Fragment getFragment() {
        return SettingFragment.newInstance();
    }

    @Override
    public String getTagName() {
        return TAG_SETTING_FRAGMENT;
    }

}
