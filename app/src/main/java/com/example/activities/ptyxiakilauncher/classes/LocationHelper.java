package com.example.activities.ptyxiakilauncher.classes;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationHelper {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private static FusedLocationProviderClient fusedLocationClient;
    private static Location lastKnownLocation; // Variable to store the last known location

    public static void requestLocationPermission(Activity activity) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        // Check for location permission
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, so request location
            requestLocation(activity);
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        }
    }

    private static void requestLocation(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Handle the case where the user hasn't granted the permission
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location
                        if (location != null) {
                            // Store the last known location
                            lastKnownLocation = location;
                        }
                    }
                });
    }

    // Method to get the last known location from another activity
    public static Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    public static void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults,
            Activity activity
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted
                requestLocation(activity);
            } else {
                // Location permission denied
                // Handle the case where the user denied the permission
                // Optionally, ask for permission again
                // Note: Do not ask for permission here to avoid an infinite loop
            }
        }
    }
}
