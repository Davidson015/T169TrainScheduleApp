package com.eproject.t169trainschedule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class SettingsActivity extends AppCompatActivity {

    DrawerLayout drawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    RadioGroup radioGroup;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.orange_1));
        getSupportActionBar().setTitle("Settings");

        // Initializing drawer layout and actionbarToggle
        drawer = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Initializing NavigationView
        navigationView = findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);

        // Initializing Preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Initializing the RadioGroup
        radioGroup = findViewById(R.id.radio_group);
        // Setting the checked radio button to the last selected one
        radioGroup.check(preferences.getInt("last_checked", 0));

        // Checking and toggling the conditions respectively
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int mode) {
                SharedPreferences.Editor editor = preferences.edit();
                switch (mode) {
                    case R.id.light_mode:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        editor.putInt("last_checked", mode);
                        break;

                    case R.id.dark_mode:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor.putInt("last_checked", mode);
                        break;

                    case R.id.sys_default:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        editor.putInt("last_checked", mode);
                        break;
                }

                // Applying Editor changes to the SharedPreferences
                editor.apply();
            }
        });
    }

    // Creating the setUpDrawerContent method
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    // Creating the selectDrawerItem method to handle the navigation of the drawer
    @SuppressLint("NonConstantResourceId")
    public void selectDrawerItem(MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.home:
                intent = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.schedules:
                intent = new Intent(SettingsActivity.this, ScheduleActivity.class);
                intent.putExtra("from", "Shanghai South");
                intent.putExtra("to", "Guangzhou");
                startActivity(intent);
                finish();
                break;
            case R.id.settings:
                drawer.closeDrawers();
                break;
            case R.id.about:
                intent = new Intent(SettingsActivity.this, AboutActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.exit_app:
                // Confirming if the user wants to exit the app
                confirmExit();
                break;
            default:
                break;
        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawer.closeDrawers();
    }

    // Creating the onBackPressed method to handle the back button press
    @SuppressLint("RtlHardcoded")
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    // Creating the confirmExit method to confirm if the user wants to exit the app
    public void confirmExit() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit App");
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(false);
        // Setting the positive button to exit the app
        builder.setPositiveButton("Yes", (dialog, which) -> finishAffinity());
        // Setting the negative button to cancel the exit
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}