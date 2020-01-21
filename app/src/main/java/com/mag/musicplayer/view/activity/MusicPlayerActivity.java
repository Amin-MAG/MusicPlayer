package com.mag.musicplayer.Controller.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.mag.musicplayer.R;
import com.mag.musicplayer.data.repository.MusicPlayer;
import com.mag.musicplayer.util.UiUtil;
import com.mag.musicplayer.view.fragment.MusicBarFragment;
import com.mag.musicplayer.view.fragment.MusicPlayerMainFragment;
import com.mag.musicplayer.viewmodel.MusicPlayerViewModel;

import java.util.HashMap;

public class MusicPlayerActivity extends AppCompatActivity {

    public static final String TAG_FRAGMENT_MUSIC_BAR = "tag_fragment_music_bar";
    public static final String TAG_MUSIC_PLAYER_VIEW_PAGER = "tag_music_player_view_pager";
    public static final String STORAGE_PERMISSION = "android.permission.READ_EXTERNAL_STORAGE";
    private static final int REQUEST_READ_STORAGE = 10002;

    private MusicPlayerViewModel viewModel;

    private MusicBarFragment musicBarFragment = MusicBarFragment.newInstance();
    private MusicPlayerMainFragment musicPlayerMainFragment = MusicPlayerMainFragment.newInstance();

    HashMap<String, Boolean> permissionsStatus = new HashMap<>();

    public static Intent newIntent(Context context) {
        return new Intent(context, MusicPlayerActivity.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        fillPermissionStatus(permissions, grantResults);

        switch (requestCode) {
            case REQUEST_READ_STORAGE:
                if (permissionsStatus.get(STORAGE_PERMISSION)) {
                    viewModel.loadMusics();
                    startActivity(newIntent(this));
                    finish();
                }
                break;
            default:
                break;
        }


    }

    private void fillPermissionStatus(@NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.length; i++)
            permissionsStatus.put(permissions[i], grantResults[i] != -1);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        viewModel = ViewModelProviders.of(this).get(MusicPlayerViewModel.class);

        checkStoragePermission();

        UiUtil.changeFragment(getSupportFragmentManager(), musicPlayerMainFragment, R.id.musicPlayerActivity_mainFrame, true, TAG_MUSIC_PLAYER_VIEW_PAGER);
        UiUtil.changeFragment(getSupportFragmentManager(), musicBarFragment, R.id.musicPlayerActivity_trackFrame, true, TAG_FRAGMENT_MUSIC_BAR);

    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
        else
            MusicPlayer.getInstance().loadMusics(getContentResolver());
    }


}
