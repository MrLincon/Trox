package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.netro.trox.R;

public class SendParcelActivity extends AppCompatActivity {

    ImageView back;

    RelativeLayout domestic, international;

    CardView btnContinue;

    String selected = "";


    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_parcel);

        back = findViewById(R.id.back);
        btnContinue = findViewById(R.id.btn_continue);
        domestic = findViewById(R.id.domestic);
        international = findViewById(R.id.international);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        domestic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected = "Domestic";
                domestic.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_outline_bg));
                international.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.outline_bg));

            }
        });

        international.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected = "International";
                domestic.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.outline_bg));
                international.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_outline_bg));

            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);

                } else {
                    if (selected.equals("Domestic")) {
                        Intent intent = new Intent(getBaseContext(), ParcelOrderDetailsActivity.class);
                        intent.putExtra("type", "Domestic");
                        intent.putExtra("ID", "fromDomestic");
                        startActivity(intent);
                    } else if (selected.equals("International")) {
                        Intent intent = new Intent(getBaseContext(), NOCActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });


    }
}