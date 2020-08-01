package com.amrita.videoplayer.mainscreen.adapter;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amrita.videoplayer.R;
import com.amrita.videoplayer.mainscreen.model.VideoObject;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class VideoListRecyclerAdapter extends RecyclerView.Adapter<VideoPlayerViewHolder> {

    private ArrayList<VideoObject> videoObjects;
    private RequestManager requestManager;


    public VideoListRecyclerAdapter(ArrayList<VideoObject> videoObjects, RequestManager requestManager) {
        this.videoObjects = videoObjects;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public VideoPlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoPlayerViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoPlayerViewHolder holder, int position) {
        holder.onBind(videoObjects.get(position), requestManager);

    }


    @Override
    public int getItemCount() {
        return videoObjects.size();
    }


}
