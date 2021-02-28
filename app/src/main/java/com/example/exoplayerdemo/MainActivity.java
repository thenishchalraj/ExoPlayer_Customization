package com.example.exoplayerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class MainActivity extends AppCompatActivity {

    SimpleExoPlayer exoPlayer;
    PlayerView pvMain;

    String videoURL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpPlayer();
    }

    private void setUpPlayer() {
        pvMain = findViewById(R.id.pv_main); // creating player view

        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter
                    .Builder(this)
                    .build();

            TrackSelector trackSelector = new DefaultTrackSelector(this, new AdaptiveTrackSelection.Factory());

            Uri videoUri = Uri.parse(videoURL);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");

            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaItem mediaItem = new MediaItem.Builder()
                    .setUri(videoUri)
                    .build();
//                    .Factory(dataSourceFactory)
//                    .createMediaSource(videouri);
//                    .setExtractorsFactory(extractorsFactory);

            exoPlayer = new SimpleExoPlayer
                    .Builder(this)
                    .setTrackSelector(trackSelector)
                    .build();

            pvMain.setPlayer(exoPlayer);

            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.setPlayWhenReady(true);
            exoPlayer.prepare();

        }
        catch (Exception e) {
            Log.e("TAG", "Error : " + e.toString());
        }

    }

    @Override
    protected void onPause() {
        pausePlayer(exoPlayer);
        super.onPause();
    }

    private void pausePlayer(SimpleExoPlayer player) {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
    }

    private void playPlayer(SimpleExoPlayer player) {
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onStop() {
        stopPlayer();
        super.onStop();
    }

    private void stopPlayer(){
        pvMain.setPlayer(null);
        exoPlayer.release();
        exoPlayer = null;
    }

    private void seekTo(SimpleExoPlayer player, long positionInMS) {
        if (player != null) {
            player.seekTo(positionInMS);
        }
    }
}