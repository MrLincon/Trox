package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.baoyachi.stepview.VerticalStepView;
import com.netro.trox.R;

import java.util.ArrayList;
import java.util.List;

public class TrackOrdersActivity extends AppCompatActivity {

    LinearLayout btnOrderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_orders);

        btnOrderDetails = findViewById(R.id.btn_order_details);

        btnOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrackOrdersActivity.this, DeliveryDetailsActivity.class));
            }
        });

    }
}