package com.mag.musicplayer.data.model.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.musicplayer.data.model.Artist;
import com.mag.musicplayer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ArtistListViewHolder> {

    private Artist selectedArtist;
    private List<Artist> artists;

    private ArtistListAdapterCallback callback;

    private Activity activity;

    public ArtistListAdapter(List<Artist> artists, ArtistListAdapterCallback callback) {
        this.callback = callback;
        this.artists = artists;
    }

    @NonNull
    @Override
    public ArtistListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_artist_box, parent, false);
        return new ArtistListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistListViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.bind(artist);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public class ArtistListViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView artistCover;
        private TextView artistName, albumCount;

        public ArtistListViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.boxLayout_cardView);
            artistCover = itemView.findViewById(R.id.boxLayout_image);
            artistName = itemView.findViewById(R.id.boxLayout_titile);
            albumCount = itemView.findViewById(R.id.boxLayout_descritption);

        }

        public void bind(final Artist artist) {

            Picasso.get().load(artist.getImagePath()).placeholder(activity.getResources().getDrawable(R.drawable.music_icon)).into(artistCover);
            artistName.setText(artist.getArtistName().length() > 24 ? artist.getArtistName().substring(0, 24) + "..." : artist.getArtistName());
            albumCount.setText( (artist.getAlbums().size()) + " Albums");

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedArtist = artist;
                    callback.updateUi(artist);

                }
            });

        }

    }

    public Artist getSelectedArtist() {
        return selectedArtist;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public interface ArtistListAdapterCallback {
        void updateUi(Artist artist);
    }

}
