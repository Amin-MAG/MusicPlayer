package com.mag.musicplayer.Util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import com.mag.musicplayer.Model.Album;
import com.mag.musicplayer.Model.Artist;
import com.mag.musicplayer.Model.MusicRepository;
import com.mag.musicplayer.Model.Track;
import com.mag.musicplayer.Var.Constants;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MusicPlayer {

    public static final String CONTENT_MEDIA_EXTERNAL_AUDIO_ALBUMART = "content://media/external/audio/albumart";
    private static MusicPlayer instance;
    private MusicPlayerCallback callback;

    private MusicPlayer() {
        this.mediaPlayer = new MediaPlayer();
    }

    public static MusicPlayer getInstance() {
        if (instance == null)
            instance = new MusicPlayer();
        return instance;
    }

    private MediaPlayer mediaPlayer;
    private Track currentTrack;

    public void loadMusics(ContentResolver contentResolver) {

        Cursor cursor = contentResolver.query(Constants.externalMusicUri, null, null, null, null);
        List<Track> tracks = new ArrayList<>();
        List<Album> albums = new ArrayList<>();
        List<Artist> artists = new ArrayList<>();

        while (cursor.moveToNext()) {

            // Music Detail
            String trackId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String trackTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String trackAlbum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String trackArtist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String trackLength = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

            // Music Cover Uri
            Uri uri = ContentUris.withAppendedId(Uri.parse(CONTENT_MEDIA_EXTERNAL_AUDIO_ALBUMART), Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))));

            // Track
            Track thisLoopTrack = new Track(Long.parseLong(trackId), trackTitle, trackAlbum, trackArtist, uri, Integer.parseInt(trackLength), null);

            // Albums
            Album thisLoopAlbum = null;

            boolean albumExists = false;
            for (Album album : albums) {
                if (album.getAlbumTitle().equals(trackAlbum)) {
                    albumExists = true;
                    thisLoopAlbum = album;
                    album.getTracks().add(Long.parseLong(trackId));
                }
            }
            if (!albumExists) {
                thisLoopAlbum = new Album(trackAlbum, trackArtist, uri);
                albums.add(thisLoopAlbum);
            }

            // Artist
            boolean artistExists = false;
            for (Artist artist : artists) {
                if (artist.getArtistName().equals(trackArtist)) {

                    artistExists = true;
                    artist.getTracks().add(thisLoopTrack.getTrackId());


                    // Album For Each Artist
                    boolean artistAlbumExists = false;
                    for (UUID artistAlbums : artist.getAlbums()) {
                        if (thisLoopAlbum.getAlbumId().equals(artistAlbums)) {
                            artistAlbumExists = true;
                            break;
                        }
                    }
                    if (!artistAlbumExists) {
                        artist.getAlbums().add(thisLoopAlbum.getAlbumId());
                    }

                }
            }
            if (!artistExists) {
                artists.add(new Artist(trackArtist, uri));
            }


            tracks.add(thisLoopTrack);

        }

        cursor.close();

        MusicRepository.getInstance().setTracks(tracks);
        MusicRepository.getInstance().setAlbums(albums);
        MusicRepository.getInstance().setArtists(artists);

    }

    public static InputStream getInputStreamOfImage(ContentResolver contentResolver, Uri uri) {
        ContentResolver res = contentResolver;
        InputStream in = null;
        try {
            in = res.openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return in;
    }

    public void playMusic(Track track, final Context context) throws IOException {

        mediaPlayer.stop();

        currentTrack = track;

        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currentTrack.getTrackId());

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(context.getApplicationContext(), contentUri);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Track nextTrack = callback.getNextTrack();
                try {
                    playMusic(nextTrack, context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callback.updateUiAutoSkip();
            }
        });
        mediaPlayer.prepare();
        mediaPlayer.start();

    }

    public void setCallback(MusicPlayerCallback callback) {
        this.callback = callback;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public static String getLengthText(int seconds) {
        int minutes = seconds / 60;
        int secondReminder = seconds % 60;
        return (minutes < 10 ? "0" + minutes : minutes) + ":" + (secondReminder < 10 ? "0" + secondReminder : secondReminder);
    }

    public interface MusicPlayerCallback {
        Track getNextTrack();

        void updateUiAutoSkip();
    }

}
