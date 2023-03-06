package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.netro.trox.R;
import com.netro.trox.util.Tools;

public class PermissionsActivity extends AppCompatActivity {

    LinearLayout main;
    ImageView back;

    Switch locationSwitch, gallerySwitch, devideStateSwitch;

    LocationManager locationManager;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        main = findViewById(R.id.main);
        back = findViewById(R.id.back);
        locationSwitch = findViewById(R.id.location_switch);
        gallerySwitch = findViewById(R.id.gallery_switch);
        devideStateSwitch = findViewById(R.id.device_state_switch);

        tools = new Tools();

        tools.setLightStatusBar(main,this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationSwitch.setChecked(true);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}