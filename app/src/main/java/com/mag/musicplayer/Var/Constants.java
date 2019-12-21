package com.mag.musicplayer.Var;

import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

public class Constants {

    public static final Uri externalMusicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    public static final Uri internalMusicUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
    public static final Handler HANDLER = new Handler();

}
