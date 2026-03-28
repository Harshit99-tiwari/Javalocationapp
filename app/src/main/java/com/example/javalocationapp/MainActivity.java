package com.example.javalocationapp;
import android.widget.Button;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.javalocationapp.locationmodel;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

import android.location.Location;
import android.location.LocationManager;
import android.content.Context;

import android.location.Geocoder;
import java.util.List;
import android.location.Address;
import java.util.Locale;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationClient;

    RecyclerView recyclerView;
    LocationAdapter adapter;
    Button btnLocation;

    TextView tvEmpty;


    ArrayList<locationmodel> locationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnLocation = findViewById(R.id.btnLocation);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();
        adapter = new LocationAdapter(locationList);
        recyclerView.setAdapter(adapter);
        checkEmptyState();
    }
    public void getLocation(View view) {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {

                        double lat = location.getLatitude();
                        double lon = location.getLongitude();

                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                        try {
                            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);

                            if (addresses != null && !addresses.isEmpty()) {
                                String address = addresses.get(0).getAddressLine(0);

                                locationmodel loc = new locationmodel(lat, lon, address);
                                locationList.add(loc);
                                adapter.notifyDataSetChanged();

                                saveData();
                                checkEmptyState();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                    }
                });
    }




    public void clearHistory(View view) {
        locationList.clear();
        adapter.notifyDataSetChanged();
        checkEmptyState();
        saveData();
    }
    private void saveData() {

        SharedPreferences prefs = getSharedPreferences("location_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(locationList);

        editor.putString("location_list", json);
        editor.apply();
    }
    private void loadData() {

        SharedPreferences prefs = getSharedPreferences("location_prefs", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString("location_list", null);

        if (json != null) {
            Type type = new com.google.gson.reflect.TypeToken<ArrayList<locationmodel>>() {}.getType();
            locationList = gson.fromJson(json, type);
        }
    }
    private void checkEmptyState() {
        if (locationList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
