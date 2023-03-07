package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netro.trox.R;
import com.netro.trox.authentication.LoginActivity;
import com.netro.trox.authentication.ResetPasswordActivity;
import com.netro.trox.bottomsheet.BottomSheetLanguage;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    ImageView back;

    CircleImageView userImage;
    TextView userName, userEmail;


    LocationManager locationManager;

    CardView language, savedAddresses, permissions, emergencySupport, privacyPolicies, rate, logOut, changePassword, editProfile;
    ImageView selectImage;

    private FirebaseFirestore db;
    private DocumentReference document_ref;
    private String userID;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        back = findViewById(R.id.back);
        language = findViewById(R.id.language);
        savedAddresses = findViewById(R.id.saved_addresses);
        changePassword = findViewById(R.id.change_password);
        editProfile = findViewById(R.id.edit_profile);
        permissions = findViewById(R.id.permissions);
        emergencySupport = findViewById(R.id.emergency_support);
        privacyPolicies = findViewById(R.id.privacy_policy);
        logOut = findViewById(R.id.log_out);
        userImage = findViewById(R.id.user_image);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        selectImage = findViewById(R.id.select_image);
        rate = findViewById(R.id.rate);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //firebase init
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getUid();


        //load user data
        db.collection("userDetails").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String user_image = documentSnapshot.getString("user_image");
                    String user_name = documentSnapshot.getString("user_name");
                    String user_email = documentSnapshot.getString("user_email");
                    String user_type = documentSnapshot.getString("user_type");

                    Glide.with(ProfileActivity.this).load(user_image).into(userImage);
                    userName.setText(user_name);
                    userEmail.setText(user_email);


                }
            }
        });


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ResetPasswordActivity.class));
                finish();
            }
        });


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
                finish();
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("Coming soon..." + getPackageName())));
                }
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
                finish();
            }
        });

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
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(ProfileActivity.this, SavedAddressesActivity.class));
                }
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
                        FirebaseAuth.getInstance().signOut();
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