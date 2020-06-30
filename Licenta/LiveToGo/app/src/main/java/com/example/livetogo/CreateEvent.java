package com.example.livetogo;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;

public class CreateEvent extends AppCompatActivity {
    public final static String TAG = "CreateEvent";
    SimpleExoPlayer player;
    TrackSelector trackSelector;

    private Button sursa1Button;
    private Button sursa2Button;

    Socket s;
    PrintWriter out;
    BufferedReader in;
    String uri = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        PlayerView playerView = findViewById(R.id.simple_player);
        playerView.setPlayer(player);

        buildMediaSource("rtmp://192.168.1.4:1935/live/myStream");

        sursa1Button = (Button) findViewById(R.id.sursa1);
        sursa2Button = (Button) findViewById(R.id.sursa2);

        sursa1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time = player.getCurrentPosition();
                buildMediaSource("rtmp://192.168.1.4:1935/live/myStream");//method already defines above
                player.seekTo(0, time);
            }
        });

        sursa2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time = player.getCurrentPosition();
                buildMediaSource("rtmp://192.168.1.4:1935/live/test");//method already defines above
                player.seekTo(0, time);
            }
        });
    }

    private void buildMediaSource(String uri) {
        RtmpDataSourceFactory rtmpDataSourceFactory = new RtmpDataSourceFactory();
        MediaSource videoSource = new ExtractorMediaSource
                .Factory(rtmpDataSourceFactory)
                .createMediaSource(Uri.parse(uri));

        player.prepare(videoSource);
        player.setPlayWhenReady(true);
    }
}
