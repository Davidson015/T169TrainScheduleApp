package com.eproject.t169trainschedule;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class AboutActivity extends AppCompatActivity {

    DrawerLayout drawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.orange_1));
        getSupportActionBar().setTitle("About");

        // Initializing drawer layout and actionbarToggle
        drawer = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Initializing NavigationView
        navigationView = findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);
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
                intent = new Intent(AboutActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.schedules:
                intent = new Intent(AboutActivity.this, ScheduleActivity.class);
                intent.putExtra("from", "Shanghai South");
                intent.putExtra("to", "Guangzhou");
                startActivity(intent);
                finish();
                break;
            case R.id.settings:
                intent = new Intent(AboutActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.about:
                drawer.closeDrawers();
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