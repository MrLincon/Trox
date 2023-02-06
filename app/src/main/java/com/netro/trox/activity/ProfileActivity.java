package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.netro.trox.R;

public class ProfileActivity extends AppCompatActivity {

    ImageView back;

    CardView savedAddresses, permissions, emergencySupport, privacyPolicies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        back = findViewById(R.id.back);
        savedAddresses = findViewById(R.id.saved_addresses);
        permissions = findViewById(R.id.permissions);
        emergencySupport = findViewById(R.id.emergency_support);
        privacyPolicies = findViewById(R.id.privacy_policy);

        savedAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SavedAddressesActivity.class));
            }
        });

        permissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, PermissionsActivity.class));
            }
        });

        emergencySupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EmergencySupportActivity.class));
            }
        });

        privacyPolicies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, PrivacyPolicyActivity.class));
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}