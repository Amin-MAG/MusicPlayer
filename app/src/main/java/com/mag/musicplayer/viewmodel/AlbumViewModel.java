package com.mag.musicplayer.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mag.musicplayer.data.model.Album;
import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.data.repository.AlbumRepository;
import com.mag.musicplayer.data.repository.TrackRepository;

import java.util.List;

public class AlbumViewModel extends AndroidViewModel {

    private MutableLiveData<Album> album = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAlbumSelected = new MutableLiveData<>();

    private AlbumRepository albumRepository = AlbumRepository.getInstance();
    private TrackRepository trackRepository = TrackRepository.getInstance();

    public AlbumViewModel(@NonNull Application application) {
        super(application);

        isAlbumSelected.setValue(false);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Album> search(String searchingString) {
        return albumRepository.getAlbums(searchingString);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Track> getTracksByAlbum() {
        return trackRepository.getTrackByAlbum(album.getValue());
    }

    public String getTitle() {
        return album.getValue().getAlbumTitle().length() > 24 ? album.getValue().getAlbumTitle().substring(0, 24) + "..." : album.getValue().getAlbumTitle();
    }

    public String getArtist() {
        return album.getValue().getArtistName();
    }

    public Uri getCoverSrc() {
        return album.getValue().getImagePath();
    }

    public void setAlbum(Album album) {
        this.album.setValue(album);
    }

    public MutableLiveData<Boolean> isAlbumSelected() {
        return isAlbumSelected;
    }

    public void showAlbumView() {
        isAlbumSelected.setValue(false);
    }

    public void showTrackView() {
        isAlbumSelected.setValue(true);
    }

    public List<Album> getAlbumList() {
        return albumRepository.getAllAlbums();
    }

}
