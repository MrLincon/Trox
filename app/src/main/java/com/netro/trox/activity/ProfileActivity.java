package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.netro.trox.R;
import com.netro.trox.bottomsheet.BottomSheetLanguage;

public class ProfileActivity extends AppCompatActivity {

    ImageView back;

    CardView language, savedAddresses, permissions, emergencySupport, privacyPolicies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        back = findViewById(R.id.back);
        language = findViewById(R.id.language);
        savedAddresses = findViewById(R.id.saved_addresses);
        permissions = findViewById(R.id.permissions);
        emergencySupport = findViewById(R.id.emergency_support);
        privacyPolicies = findViewById(R.id.privacy_policy);

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetLanguage bottomSheetLanguage = new BottomSheetLanguage();
                bottomSheetLanguage.show(getSupportFragmentManager(), "Language");
            }
        });

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