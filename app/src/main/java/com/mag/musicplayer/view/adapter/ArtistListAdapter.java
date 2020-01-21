package com.mag.musicplayer.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.musicplayer.R;
import com.mag.musicplayer.data.model.Artist;
import com.mag.musicplayer.databinding.LayoutArtistBoxBinding;
import com.mag.musicplayer.viewmodel.ArtistViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ArtistListViewHolder> {

    private List<Artist> artists;

    private Activity activity;

    public ArtistListAdapter(List<Artist> artists) {
        this.artists = artists;
    }

    @NonNull
    @Override
    public ArtistListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        LayoutArtistBoxBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.layout_artist_box, parent, false);
        return new ArtistListViewHolder(binding);
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

        private LayoutArtistBoxBinding binding;

        private ArtistViewModel viewModel;

        public ArtistListViewHolder(@NonNull LayoutArtistBoxBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
            this.viewModel = ViewModelProviders.of((FragmentActivity) activity).get(ArtistViewModel.class);

        }

        public void bind(final Artist artist) {

            viewModel.setArtist(artist);
            binding.setArtistViewModel(viewModel);
            binding.executePendingBindings();

            Picasso.get().load(artist.getImagePath()).placeholder(activity.getResources().getDrawable(R.drawable.music_icon)).into(binding.boxLayoutImage);

            binding.getRoot().setOnClickListener(view -> {
                viewModel.setArtist(artist);
                viewModel.showTrackView();
            });

        }

    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

}
