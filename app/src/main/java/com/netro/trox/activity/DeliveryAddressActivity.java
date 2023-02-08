package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.netro.trox.R;

public class DeliveryAddressActivity extends AppCompatActivity {

    ImageView back;

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);

        back = findViewById(R.id.back);
        search = findViewById(R.id.search);

        String data = getIntent().getStringExtra("data");

        if (data.equals("country")){
            search.setHint(getResources().getString(R.string.enter_country));
        }else if (data.equals("city")){
            search.setHint(getResources().getString(R.string.enter_city));
        }else if (data.equals("state")){
            search.setHint(getResources().getString(R.string.enter_state));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}