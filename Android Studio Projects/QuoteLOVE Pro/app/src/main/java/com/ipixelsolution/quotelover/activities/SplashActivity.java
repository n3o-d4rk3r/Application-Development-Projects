package com.ipixelsolution.quotelover.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ipixelsolution.quotelover.R;
import com.ipixelsolution.quotelover.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("ALL")
public class SplashActivity extends AppCompatActivity {

    TextView developer_credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        developer_credit = findViewById(R.id.developer_credit);

        developer_credit.setText(getResources().getString(R.string.developer_credit));
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // If you want to modify a view in your Activity
                SplashActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();

                    }
                });
            }
        }, 3000);
    }
}