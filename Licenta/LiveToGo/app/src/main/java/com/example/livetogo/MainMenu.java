package com.example.livetogo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainMenu extends AppCompatActivity {

    @BindView(R.id.menu) FloatingActionButton menuButton;
    @BindView(R.id.settings)FloatingActionButton settingsButton;
    @BindView(R.id.logout)FloatingActionButton logoutButton;
    @BindView(R.id.create_event)FloatingActionButton createEventButton;
    @BindView(R.id.list_events)FloatingActionButton listEventsButton;

    private boolean displayMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);

        displayMenu = true;

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(displayMenu) {
                    settingsButton.setVisibility(View.INVISIBLE);
                    logoutButton.setVisibility(View.INVISIBLE);
                    listEventsButton.setVisibility(View.INVISIBLE);
                    createEventButton.setVisibility(View.INVISIBLE);

                    displayMenu = false;
                } else {
                    settingsButton.setVisibility(View.VISIBLE);
                    logoutButton.setVisibility(View.VISIBLE);
                    listEventsButton.setVisibility(View.VISIBLE);
                    createEventButton.setVisibility(View.VISIBLE);

                    displayMenu = true;
                }
            }
        });

        if(displayMenu) {
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            });

            createEventButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), TestStream.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            });

            settingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Settings.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            });

            listEventsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PlayVideo.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            });
        }
    }
}
