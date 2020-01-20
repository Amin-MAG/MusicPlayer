package com.mag.musicplayer.data.model.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.musicplayer.R;
import com.mag.musicplayer.data.model.Album;
import com.mag.musicplayer.databinding.LayoutAlbumBoxBinding;
import com.mag.musicplayer.viewmodel.AlbumViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumListViewHolder> {

    private List<Album> albums;

    private Activity activity;

    public AlbumListAdapter(List<Album> albums) {
        this.albums = albums;
    }

    @NonNull
    @Override
    public AlbumListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        LayoutAlbumBoxBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.layout_album_box, parent, false);
        return new AlbumListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumListViewHolder holder, int position) {
        holder.bind(albums.get(position));
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class AlbumListViewHolder extends RecyclerView.ViewHolder {

        private LayoutAlbumBoxBinding binding;
        private AlbumViewModel viewModel;

        public AlbumListViewHolder(@NonNull LayoutAlbumBoxBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
            this.viewModel = ViewModelProviders.of((FragmentActivity) activity).get(AlbumViewModel.class);

        }

        public void bind(final Album album) {

            viewModel.setAlbum(album);
            binding.setAlbumViewModel(viewModel);
            binding.executePendingBindings();

            Picasso.get().load(viewModel.getCoverSrc()).placeholder(activity.getResources().getDrawable(R.drawable.music_icon)).into(binding.boxLayoutImage);

            binding.getRoot().setOnClickListener(view -> {
                viewModel.setAlbum(album);
                viewModel.showTrackView();
            });

        }

    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }


}
