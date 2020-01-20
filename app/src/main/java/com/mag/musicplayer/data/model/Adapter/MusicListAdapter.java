package com.mag.musicplayer.data.model.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.musicplayer.R;
import com.mag.musicplayer.data.model.Track;
import com.mag.musicplayer.databinding.LayoutTrackBinding;
import com.mag.musicplayer.viewmodel.TrackViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder> {

    private List<Track> tracks;
    private Track selectedTrack;


    private Activity activity;

    public MusicListAdapter(List<Track> tracks) {
        this.tracks = tracks;
    }

    public MusicListAdapter() {
        this.tracks = new ArrayList<>();
    }

    @NonNull
    @Override
    public MusicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        LayoutTrackBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.layout_track, parent, false);
        return new MusicListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListViewHolder holder, int position) {
        holder.bind(tracks.get(position));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class MusicListViewHolder extends RecyclerView.ViewHolder {

        private LayoutTrackBinding binding;

        private TrackViewModel viewModel;


        public MusicListViewHolder(@NonNull LayoutTrackBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
            this.viewModel = ViewModelProviders.of((FragmentActivity) activity).get(TrackViewModel.class);

        }

        @SuppressLint("ResourceType")
        public void bind(final Track track) {

            viewModel.setTrack(track);
            binding.setTrackViewModel(viewModel);
            binding.executePendingBindings();


            // Track Cover
            Picasso.get().load(viewModel.getCoverSrc()).placeholder(activity.getResources().getDrawable(R.drawable.music_icon)).into(binding.trackLayoutTrackImage);

            binding.trackLayoutCardView.setBackgroundColor(Color.parseColor(activity.getString((viewModel.isPlayingTrack()) ? R.color.colorAccent : R.color.colorPrimaryLight)));

            binding.trackLayoutCardView.setOnClickListener(view -> {
                viewModel.setTrack(track);
                viewModel.onTrackClicked();
                notifyDataSetChanged();
            });

        }

    }


    public void updateUi() {

        notifyDataSetChanged();

    }

    public int findTrackIndex(Track track) {
        for (int i = 0; i < tracks.size(); i++) {
            if (tracks.get(i).getTrackId() == track.getTrackId())
                return i;
        }
        return -1;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
        notifyDataSetChanged();
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setSelectedTrack(Track selectedTrack) {
        this.selectedTrack = selectedTrack;
    }


}
