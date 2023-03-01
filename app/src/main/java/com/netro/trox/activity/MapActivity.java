package com.netro.trox.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.netro.trox.R;
import com.netro.trox.util.Tools;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity  implements OnMapReadyCallback {

    private GoogleMap mMap;
    Marker marker;
    private Location lastKnownLocation;
    List<Address> addresses = null;
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private FusedLocationProviderClient fusedLocationProviderClient;

    double latitude;
    double longitude;

    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    private static final String TAG = "MapActivity";

    String data, city;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        tools = new Tools();


        city = getIntent().getStringExtra("city");
        data = getIntent().getStringExtra("data");

        MapActivity.this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());


        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    private void openBottomSheet(String address){
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(MapActivity.this);
        View sheetView = getLayoutInflater().inflate(R.layout.bottomsheet_pickup_location, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

        TextView locationAddress = mBottomSheetDialog.findViewById(R.id.location_address);
        ImageView close = mBottomSheetDialog.findViewById(R.id.close);
        CardView btnConfirm = mBottomSheetDialog.findViewById(R.id.btn_confirm);

        locationAddress.setText(address.trim());

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (data.equals("pickup")){
                    intent.putExtra("pickupAddress", address.trim());
                    intent.putExtra("pickupLat", latitude);
                    intent.putExtra("pickupLong", longitude);
                } else if (data.equals("delivery")) {
                    intent.putExtra("deliveryAddress", address.trim());
                    intent.putExtra("deliveryLat", latitude);
                    intent.putExtra("deliveryLong", longitude);
                }
                marker.remove();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        getLocationPermission();

        updateLocationUI();

        getDeviceLocation();


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {

                latitude = latLng.latitude;
                longitude = latLng.longitude;

                if (marker!=null){
                    marker.remove();
                }

                LatLng newLocation = new LatLng(latitude, longitude);
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(newLocation);
                marker = mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));

                // Retrieve the address for the selected location
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    StringBuilder addressBuilder = new StringBuilder();
                    for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                        addressBuilder.append(address.getAddressLine(i)).append("\n");
                    }
                    String fullAddress = addressBuilder.toString();
                    Log.d("MapsActivity", "Address: " + fullAddress);
                    openBottomSheet(fullAddress);
                }


            }

        });

    }


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getParent(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
                updateLocationUI();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
//                mMap.setMyLocationEnabled(true);
//                mMap.getUiSettings().setMyLocationButtonEnabled(false);

                openCityMap();

            } else {

                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void openCityMap(){
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses2 = null;
        try {
            addresses2 = geocoder.getFromLocationName(city, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses2 != null && !addresses2.isEmpty()) {
            Address address = addresses2.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        } else {
            // Handle the case where the city name is invalid or not found
        }
    }


    private void getDeviceLocation() {



        openCityMap();

//        try {
//            if (locationPermissionGranted) {
//                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
//                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if (task.isSuccessful()) {
//                            // Set the map's camera position to the current location of the device.
//                            lastKnownLocation = task.getResult();
//                            if (lastKnownLocation != null) {
//
//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                        new LatLng(lastKnownLocation.getLatitude(),
//                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//
////                                LatLng newLocation = new LatLng(lastKnownLocation.getLatitude(),
////                                        lastKnownLocation.getLongitude());
////                                mMap.addMarker(new MarkerOptions().position(newLocation).title("Select position"));
////                                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
//                            }
//                        } else {
//                            Log.d(TAG, "Current location is null. Using defaults.");
//                            Log.e(TAG, "Exception: %s", task.getException());
//                            mMap.moveCamera(CameraUpdateFactory
//                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
//                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e)  {
//            Log.e("Exception: %s", e.getMessage(), e);
//        }


    }

}