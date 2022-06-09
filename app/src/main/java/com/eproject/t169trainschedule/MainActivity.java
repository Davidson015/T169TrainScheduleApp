package com.eproject.t169trainschedule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Animation fadeIn;
    private TextView splashText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_main);

        // Initialize view
        splashText = findViewById(R.id.splash_text);

        // Initialize animation
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        splashText.setVisibility(TextView.INVISIBLE);
        splashText.startAnimation(fadeIn);

        // Delay for 2 seconds
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}