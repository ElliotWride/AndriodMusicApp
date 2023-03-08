package com.example.recview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {
    public static AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        audioManager = new AudioManager();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChooseSongActivity.class);
                intent.putExtra("audioManager",audioManager);
                startActivity(intent);
            }
        });
        final Button button1 = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChooseSongActivity.class);
                startActivity(intent);
            }
        });

    }
}