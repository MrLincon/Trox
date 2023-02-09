package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.netro.trox.R;
import com.netro.trox.authentication.LoginActivity;
import com.netro.trox.bottomsheet.BottomSheetLanguage;

public class ProfileActivity extends AppCompatActivity {

    ImageView back;

    CardView language, savedAddresses, permissions, emergencySupport, privacyPolicies, logOut;

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
        logOut = findViewById(R.id.log_out);

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

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(ProfileActivity.this);
                View sheetView = ProfileActivity.this.getLayoutInflater().inflate(R.layout.bottomsheet_logout, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();

                ImageView close = sheetView.findViewById(R.id.close);
                CardView btnLogout = sheetView.findViewById(R.id.btn_logout);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.cancel();
                    }
                });

                btnLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.cancel();
                        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                        finish();
                    }
                });
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
    }
}