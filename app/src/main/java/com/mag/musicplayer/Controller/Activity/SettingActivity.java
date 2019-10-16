package com.mag.musicplayer.Controller.Activity;

import androidx.fragment.app.Fragment;

import com.mag.musicplayer.Controller.Fragment.SettingFragment;

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
