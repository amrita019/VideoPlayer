package com.amrita.videoplayer.mainscreen.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.amrita.videoplayer.R;
import com.amrita.videoplayer.mainscreen.adapter.VideoListRecyclerAdapter;
import com.amrita.videoplayer.mainscreen.model.VideoObject;
import com.amrita.videoplayer.mainscreen.utils.VerticalSpacingItemDecorator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class MainView extends AppCompatActivity {

    private VideoPlayerRecyclerView mRecyclerView;
    private ArrayList<VideoObject> videoObjectArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);

        openGallery();

    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setMediaObjects(videoObjectArrayList);
        VideoListRecyclerAdapter adapter = new VideoListRecyclerAdapter(videoObjectArrayList, initGlide());
        mRecyclerView.setAdapter(adapter);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
    }

    private RequestManager initGlide(){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }


    @Override
    protected void onDestroy() {
        if(mRecyclerView!=null)
            mRecyclerView.releasePlayer();
        super.onDestroy();
    }

    private void openGallery() {
        if (checkPermissionForGallery()) {
            getAllVideos();
            initRecyclerView();
        } else {
            requestGalleryPermission();
        }
    }

    private boolean checkPermissionForGallery() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestGalleryPermission() {
        Dexter.withActivity(MainView.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        getAllVideos();
                        initRecyclerView();
                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        showAlert(MainView.this, "Needs Permission", "App needs to access Gallery", "Okay", (dialog, which) -> { openGallery(); }, false);
                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public static void showAlert(Activity activity, String title, String message, String positiveMessage,
                                 DialogInterface.OnClickListener listener, boolean isCancellable) {
        if (activity != null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            if (!title.equals("")) {
                alert.setTitle(title);
            }
            alert.setMessage(message);
            alert.setPositiveButton(positiveMessage, listener);
            alert.setCancelable(isCancellable);
            alert.show();
        }
    }

    public ArrayList<VideoObject> getAllVideos() {
        ArrayList<VideoObject> videoList = new ArrayList<>();
        String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = this.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do{
                VideoObject videoObject = new VideoObject(
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)),
                        ""
                );
                videoList.add(videoObject);
            }while(cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        videoObjectArrayList = videoList;
        return videoList;
    }
}