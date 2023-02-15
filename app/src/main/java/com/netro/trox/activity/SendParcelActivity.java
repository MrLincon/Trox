package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.netro.trox.R;

public class SendParcelActivity extends AppCompatActivity {

    ImageView back;

    RelativeLayout domestic, international;

    CardView btnContinue;

    String selected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_parcel);

        back = findViewById(R.id.back);
        btnContinue = findViewById(R.id.btn_continue);
        domestic = findViewById(R.id.domestic);
        international = findViewById(R.id.international);

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
                if (selected.equals("Domestic")) {
                    Intent intent = new Intent(getBaseContext(), DomesticOrderDetailsActivity.class);
                    intent.putExtra("type", "Domestic");
                    startActivity(intent);
                } else if (selected.equals("International")) {
                     Intent intent = new Intent(getBaseContext(), InternationalOrderDetailsActivity.class);
                     intent.putExtra("type", "International");
                     startActivity(intent);
                }
            }
        });


    }
}