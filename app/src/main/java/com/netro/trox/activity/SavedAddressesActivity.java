package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netro.trox.R;
import com.netro.trox.util.Tools;

public class SavedAddressesActivity extends AppCompatActivity {

    CoordinatorLayout main;
    ImageView back;
    RelativeLayout home, work;
    TextView homeAddress, workAddress;

    Tools tools;

    String addressType;

    private static final int REQUEST_CODE = 1011;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_addresses);

        main = findViewById(R.id.main);
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        work = findViewById(R.id.work);
        homeAddress = findViewById(R.id.home_address);
        workAddress = findViewById(R.id.work_address);

        tools = new Tools();

        tools.setLightStatusBar(main, this);


        FirebaseFirestore.getInstance().collection("userDetails").document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String Home = documentSnapshot.getString("home_address");
                            String Work = documentSnapshot.getString("work_address");

                            if (!Home.equals("")) {
                                homeAddress.setText(Home);
                            }
                            if (!Work.equals("")) {
                                workAddress.setText(Work);
                            }
                        }
                    }
                });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressType = "home";
                Intent intent = new Intent(SavedAddressesActivity.this, AddressMapActivity.class);
                intent.putExtra("data", "Home");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressType = "work";
                Intent intent = new Intent(SavedAddressesActivity.this, AddressMapActivity.class);
                intent.putExtra("data", "Work");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String resultHome = data.getStringExtra("home");
            String resultWork = data.getStringExtra("work");

            if (addressType.equals("home")) {
                homeAddress.setText(resultHome);
            } else if (addressType.equals("work")) {
                workAddress.setText(resultWork);
            }
        }
    }


}