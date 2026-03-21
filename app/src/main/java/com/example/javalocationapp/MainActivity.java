package com.example.javalocationapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {

    TextView locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationText = findViewById(R.id.locationText);
    }

    public void getLocation(View view) {

        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {
                List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);

                if (addresses != null && !addresses.isEmpty()) {
                    String address = addresses.get(0).getAddressLine(0);

                    locationText.setText(
                            "📍 Latitude: " + lat +
                                    "\n🌍 Longitude: " + lon +
                                    "\n🏠 Address: " + address
                    );
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            locationText.setText("Turn ON location and try again");
        }


    }
}
