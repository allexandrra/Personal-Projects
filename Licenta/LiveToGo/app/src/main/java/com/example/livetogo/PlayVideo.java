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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import androidx.appcompat.app.AppCompatActivity;

public class PlayVideo extends AppCompatActivity {
    public final static String TAG = "PlayVideo";
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
        setContentView(R.layout.activity_play_video);

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        PlayerView playerView = findViewById(R.id.simple_player);
        playerView.setPlayer(player);

        sursa1Button = (Button) findViewById(R.id.sursa1);
        sursa2Button = (Button) findViewById(R.id.sursa2);

        try {
            s = new Socket("192.168.1.4", 8000);
            out = new PrintWriter(s.getOutputStream());
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Client()).start();

        sursa1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                out.write("1");
                out.flush();
            }
        });

        sursa2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                out.write("2");
                out.flush();
            }
        });
    }

    private void buildMediaSource(String uri) {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        RtmpDataSourceFactory rtmpDataSourceFactory = new RtmpDataSourceFactory();
        MediaSource videoSource = new ExtractorMediaSource
                .Factory(rtmpDataSourceFactory)
                .createMediaSource(Uri.parse(uri));

        player.prepare(videoSource);
        player.setPlayWhenReady(true);
    }

    class Client extends Thread {
        public void run() {
            try {
                boolean receive = true;

                out.write("destination");
                out.flush();
                out.write("live");
                out.flush();

                while (receive == true) {
                    if ((uri = in.readLine()) != null) {

                        Toast.makeText(PlayVideo.this, uri, Toast.LENGTH_SHORT).show();
                        if (uri.equals("quit")) {
                            receive = false;
                            out.close();
                            in.close();
                            s.close();
                        }
                        else {
                            long time = player.getCurrentPosition();
                            RtmpDataSourceFactory rtmpDataSourceFactory = new RtmpDataSourceFactory();
                            MediaSource videoSource = new ExtractorMediaSource
                                    .Factory(rtmpDataSourceFactory)
                                    .createMediaSource(Uri.parse(uri));

                            player.prepare(videoSource);
                            player.setPlayWhenReady(true);
                            player.seekTo(0, time);
                        }
                    }
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
