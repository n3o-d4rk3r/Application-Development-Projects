package com.ipixel.speedmeterpro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.ipixel.speedmeterpro.R;

public class SplashActivity extends AppCompatActivity {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_splash);
		init_variables ();
	}

	private void init_variables () {
		SharedPreferences sharedPref = getSharedPreferences (
				"setting", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit ();
		editor.putString ("UNIT", "Mbps");
		editor.apply ();
		Handler handler = new Handler ();
		handler.postDelayed (() -> {
			SplashActivity.this.startActivity (new Intent (SplashActivity.this, MainActivity.class));
			finish ();
		}, 2000);
	}
}
