package com.amrita.videoplayer.mainscreen.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amrita.videoplayer.R;
import com.amrita.videoplayer.mainscreen.model.VideoObject;
import com.bumptech.glide.RequestManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnail, volumeControl;
        public ProgressBar progressBar;
        public View parent;
        public RequestManager requestManager;

        @BindView(R.id.media_container)
        FrameLayout media_container;

        public VideoPlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            parent = itemView;
            thumbnail = itemView.findViewById(R.id.thumbnail);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        public void onBind(VideoObject videoObject, RequestManager requestManager) {
            this.requestManager = requestManager;
            parent.setTag(this);
            this.requestManager
                    .asBitmap()
                    .load(videoObject.getFilePath())
                    .into(thumbnail);
        }

}
