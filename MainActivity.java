package com.example.counter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView ctr;
    Button start, stop;
    int counter = 0;
    boolean running = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking UI elements with their IDs
        start = findViewById(R.id.btn_start);
        stop = findViewById(R.id.btn_stop);
        ctr = findViewById(R.id.counter);

        // Start button click listener
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = 0; // Reset counter
                running = true;
                new MyCounter().start();
            }
        });

        // Stop button click listener
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;
            }
        });
    }

    // Handler to update the UI thread with the counter value
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message m) {
            ctr.setText(String.valueOf(m.what));
        }
    };

    // Thread to increment the counter every second
    class MyCounter extends Thread {
        @Override
        public void run() {
            while (running) {
                counter++;
                handler.sendEmptyMessage(counter);
                try {
                    Thread.sleep(1000); // Wait for 1 second
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
