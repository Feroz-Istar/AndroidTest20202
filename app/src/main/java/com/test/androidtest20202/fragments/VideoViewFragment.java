package com.test.androidtest20202.fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.test.androidtest20202.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoViewFragment extends Fragment {
    private ViewGroup container;
    private LayoutInflater inflater;
    public static final String URL ="viewURL";
    private static final String TAG = "VideoViewFragment";

    @BindView(R.id.videoView)
    VideoView videoView;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.container = container;
        this.inflater = inflater;
        return initializeView();
    }


    private View initializeView() {

        final View view;
        view = inflater.inflate(
                R.layout.video_item, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        String videoUrl = bundle.getString(URL);
        Log.d(TAG,videoUrl);

        final MediaController mediacontroller = new MediaController(getContext());
        mediacontroller.setAnchorView(videoView);

        videoView.setMediaController(mediacontroller);
        videoView.setVideoURI(Uri.parse(videoUrl));
        videoView.requestFocus();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d(TAG,"What " + what + " extra " + extra);
                return false;
            }
        });
        // error_subtitle.setText("No Completed Task Found.");
        return view;

    }
}
