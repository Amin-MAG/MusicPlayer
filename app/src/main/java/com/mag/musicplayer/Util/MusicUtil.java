package com.mag.musicplayer.Util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.Var.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicUtil {

    public static void loadMusics(ContentResolver contentResolver) {

        Cursor cursor = contentResolver.query(Constants.externalMusicUri, null, null, null, null);
        List<Track> tracks = new ArrayList<>();

        while (cursor.moveToNext()) {

            String trackId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String trackTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String trackAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String trackArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String trackLength = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

            tracks.add(new Track(Long.parseLong(trackId), trackTitle, trackAlbum, trackArtist, null, Integer.parseInt(trackLength), null));

        }

        cursor.close();

        MusicRepository.getInstance().setTracks(tracks);

    }

    public static void playMusic(long id, Context context) throws IOException {

        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setDataSource(context.getApplicationContext(), contentUri);
        mediaPlayer.prepare();
        mediaPlayer.start();

    }

}
