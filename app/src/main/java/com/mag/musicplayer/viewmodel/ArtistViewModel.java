package com.mag.musicplayer.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mag.musicplayer.data.model.Artist;
import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.data.repository.ArtistRepository;
import com.mag.musicplayer.data.repository.TrackRepository;

import java.util.List;

public class ArtistViewModel extends AndroidViewModel {

    private MutableLiveData<Artist> artist = new MutableLiveData<>();
    private MutableLiveData<Boolean> isArtistSelected = new MutableLiveData<>();

    private ArtistRepository artistRepository = ArtistRepository.getInstance();
    private TrackRepository trackRepository = TrackRepository.getInstance();

    public ArtistViewModel(@NonNull Application application) {
        super(application);

        isArtistSelected.setValue(false);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Artist> search(String searchingString) {
        return artistRepository.getArtists(searchingString);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Track> getTracksByArtist() {
        return trackRepository.getTrackByArtist(artist.getValue());
    }


    public int getNumberOfAlbums() {
        return artist.getValue().getAlbums().size();
    }


    public void setArtist(Artist artist) {
        this.artist.setValue(artist);
    }

    public List<Artist> getArtists() {
        return artistRepository.getAllArtists();
    }

    public String getArtistName() {
        return artist.getValue().getArtistName().length() > 24 ? artist.getValue().getArtistName().substring(0, 24) + "..." : artist.getValue().getArtistName();
    }


    public void showArtistView() {
        isArtistSelected.setValue(false);
    }

    public void showTrackView() {
        isArtistSelected.setValue(true);
    }

    public MutableLiveData<Boolean> isArtistSelected() {
        return isArtistSelected;
    }
}
