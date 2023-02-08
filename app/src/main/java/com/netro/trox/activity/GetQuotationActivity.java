package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.netro.trox.R;
import com.netro.trox.util.Tools;

public class GetQuotationActivity extends AppCompatActivity {

    ImageView back;
    EditText pickupCity, destinationCity;

    LinearLayout main;

    Tools tools;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_quotation);

        back = findViewById(R.id.back);
        pickupCity = findViewById(R.id.pickup_city);
        destinationCity = findViewById(R.id.destination_city);
        main = findViewById(R.id.main);

        tools = new Tools();

        tools.setLightStatusBar(main,this);

        pickupCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GetQuotationActivity.this,QuotationAddressActivity.class));
            }
        });

        destinationCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GetQuotationActivity.this,QuotationAddressActivity.class));
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