package com.eproject.t169trainschedule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class HomeActivity extends AppCompatActivity {

    Button searchBtn, scheduleBtn;
    TextInputEditText from, to;
    DrawerLayout drawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_home);

        // Getting the search Values from the layout
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);

        // Defining a Button to search through the schedule
        searchBtn = findViewById(R.id.search_btn);
        searchBtn.setOnClickListener( v -> {
            if (from.getText().toString().length() == 0 || to.getText().toString().length() == 0) {
                from.setError("Field cannot be empty!");
                to.setError("Field cannot be empty!");
                Toast toast = Toast.makeText(getApplicationContext(), "Please enter Start and End stations", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Intent intent = new Intent(HomeActivity.this, ScheduleActivity.class);
                intent.putExtra("from", from.getText().toString().trim());
                intent.putExtra("to", to.getText().toString().trim());
                startActivity(intent);
            }
        });

        // Defining a Button to View full Schedule
        scheduleBtn = findViewById(R.id.schedule_btn);
        scheduleBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ScheduleActivity.class);
            intent.putExtra("from", "Shanghai South");
            intent.putExtra("to", "Guangzhou");
            startActivity(intent);
        });

        // Initializing Toolbar and setting it as the actionbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.orange_1));
        getSupportActionBar().setTitle("Search Destinations");

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
                drawer.closeDrawers();
                break;
            case R.id.schedules:
                intent = new Intent(HomeActivity.this, ScheduleActivity.class);
                intent.putExtra("from", "Shanghai South");
                intent.putExtra("to", "Guangzhou");
                startActivity(intent);
                break;
            case R.id.settings:
                intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                intent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.exit_app:
                finishAffinity();
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
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                finishAffinity();
            } else {
                Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            pressedTime = System.currentTimeMillis();
        }
    }

}