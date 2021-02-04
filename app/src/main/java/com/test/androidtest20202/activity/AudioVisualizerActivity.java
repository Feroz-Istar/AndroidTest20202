package com.test.androidtest20202.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gauravk.audiovisualizer.visualizer.BlastVisualizer;
import com.test.androidtest20202.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AudioVisualizerActivity extends AppCompatActivity {


    @BindView(R.id.blast)
    BlastVisualizer mVisualizer;
    MediaPlayer audioPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_visualizer);
        ButterKnife.bind(this);
        audioPlayer = new MediaPlayer();
        try {
            Uri myUri = Uri.parse("https://storage.googleapis.com/istar-static/20601432.wav");

            audioPlayer.setDataSource("https://storage.googleapis.com/istar-static/20601432.wav");
            audioPlayer.setLooping(false);
            audioPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    audioPlayer.start();

                    int audioSessionId = audioPlayer.getAudioSessionId();
                    if (audioSessionId != -1)
                        mVisualizer.setAudioSessionId(audioSessionId);
                }
            });
            audioPlayer.prepareAsync();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVisualizer != null)
            mVisualizer.release();
    }
}