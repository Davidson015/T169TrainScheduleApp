package com.eproject.t169trainschedule;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.eproject.t169trainschedule.model.Schedule;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ScheduleActivity extends AppCompatActivity {

    DrawerLayout drawer;
    String from, to;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView from_view, to_view;

    RecyclerView recyclerView;
    Context context;
    private List<Schedule> scheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Getting the search Values from the layout and setting them to Views in the Schedule Activity
        from_view = findViewById(R.id.from_view);
        to_view = findViewById(R.id.to_view);
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");

        // Checking if from and to are in lowercase for better display
        if (from.toLowerCase().equals(from) && to.toLowerCase().equals(to)) {
            from_view.setText(from.toUpperCase().charAt(0) + from.substring(1).toLowerCase());
            to_view.setText(to.toUpperCase().charAt(0) + to.substring(1).toLowerCase());
        } else {
            from_view.setText(from);
            to_view.setText(to);
        }

        // Getting the recyclerView from the layout
        recyclerView = findViewById(R.id.recyclerView);

        // Setting the layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        // Initializing scheduleList
        scheduleList = new ArrayList<>();

        // Creating a new instance of the AsyncTask - GetSchedules
        new GetSchedules().execute();


        // Navigation Drawer
        // Initializing Toolbar and setting it as the actionbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.orange_1));
        getSupportActionBar().setTitle("Schedule");

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

    // Creating the selectDrawerItem method to handle navigation item clicks
    @SuppressLint("NonConstantResourceId")
    public void selectDrawerItem(MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.home:
                intent = new Intent(ScheduleActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.schedules:
                drawer.closeDrawers();
                break;
            case R.id.settings:
                intent = new Intent(ScheduleActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.about:
                intent = new Intent(ScheduleActivity.this, AboutActivity.class);
                startActivity(intent);
                finish();
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

    // Overriding the onBackPressed method
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

    // Creating the initializeAdapter method
    private void initializeAdapter(ItemScheduleAdapter adapter) {
        // Setting the adapter to the recyclerView
        recyclerView.setAdapter(adapter);
    }

    // Getting Schedules from API
    private class GetSchedules extends AsyncTask<Void, Void, List<Schedule>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(ScheduleActivity.this, "Getting Schedules...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected List<Schedule> doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();

            // Making a request to url and getting response
            String url = "https://apisbydivad.netlify.app/t169schedule.json";
            String jsonStr = httpHandler.makeServiceCall(url);

            Log.e(MainActivity.class.getSimpleName(), "Response from url: " + jsonStr);
            if(jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    // Getting Json Array node
                    JSONArray schedules = jsonObject.getJSONArray("schedules");

                    // Looping through all the elements in the json array
                    for (int i = 0; i < schedules.length(); i++) {
                        JSONObject sch = schedules.getJSONObject(i);
                        int stationId = sch.getInt("stationId");
                        String stationName = sch.getString("stationName");
                        String stationImage = sch.getString("stationImage");

                        // Getting Time node (JSON Object)
                        JSONObject time = sch.getJSONObject("time");
                        String arrTime = time.getString("arrTime");
                        String depTime = time.getString("depTime");
                        String runningTime = time.getString("runningTime");
                        String timeFromLast = time.getString("timeFromLast");

                        // Getting Distance node (JSON Object)
                        JSONObject distance = sch.getJSONObject("distance");
                        String totalDistance = distance.getString("totalDistance");
                        String distanceFromLast = distance.getString("distanceFromLast");

                        // Getting Price node (JSON Object)
                        JSONObject price = sch.getJSONObject("pricing");
                        String hardSeatPrice = price.getString("hardSeatPrice");
                        String hardSleeper = price.getString("hardSleeper");
                        String softSleeper = price.getString("softSleeper");

                        // Instantiating a new Schedule Object with data gotten from API
                        Schedule schedule = new Schedule(stationId, stationName, stationImage, depTime, arrTime, runningTime, timeFromLast, totalDistance, distanceFromLast, hardSeatPrice, hardSleeper, softSleeper);

                        // Adding Schedule to the Schedule list
                        scheduleList.add(schedule);
                    }
                } catch (final JSONException e) {
                    Log.e(MainActivity.class.getSimpleName(), "Json parsing error: " + e.getMessage());
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            } else {
                Log.e(MainActivity.class.getSimpleName(), "Couldn't get json from server.");
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Couldn't get Schedules from server.\nEnsure you have access to the internet.", Toast.LENGTH_LONG).show());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Schedule> schedules) {
            super.onPostExecute(schedules);

            ItemScheduleAdapter adapter;

            // filtering through ScheduleList
            if (from.equalsIgnoreCase("Shanghai South") && to.equalsIgnoreCase("Guangzhou")) {
                // Setting the adapter to the recyclerView
                adapter = new ItemScheduleAdapter(scheduleList);

                // Calling the initializeAdapter method
                initializeAdapter(adapter);
            } else {
                try {
                    List<Schedule> originalSchedules = scheduleList;
                    List<Schedule> filteredSchedules = filterSchedule(originalSchedules);

                    // Checking if filteredSchedules is empty
                    if (filteredSchedules.isEmpty()) {
                        Toast.makeText(ScheduleActivity.this, "Destination not found.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Setting the adapter to the recyclerView
                        adapter = new ItemScheduleAdapter(filteredSchedules);

                        // Calling the initializeAdapter method
                        initializeAdapter(adapter);
                    }
                } catch (Exception e) {
                    Toast.makeText(ScheduleActivity.this, "Make sure Destinations are along train route!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        }

        public List<Schedule> filterSchedule(List<Schedule> schedules) {
            // Filtering through ScheduleList
            ListIterator<Schedule> iter = schedules.listIterator();
            Schedule start = null;
            Schedule end = null;
            while (iter.hasNext()) {
                Schedule nextSchedule = iter.next();

                if (nextSchedule.getStationName().toLowerCase().contains(to.toLowerCase())) {
                    end = nextSchedule;
                } else if (nextSchedule.getStationName().toLowerCase().contains(from.toLowerCase())) {
                    start = nextSchedule;
                }
            }

            return schedules.subList(schedules.indexOf(start), schedules.indexOf(end) + 1);
        }
    }
}