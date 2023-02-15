package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyachi.stepview.VerticalStepView;
import com.netro.trox.R;
import com.netro.trox.util.Tools;

import java.util.ArrayList;
import java.util.List;

public class TrackOrdersActivity extends AppCompatActivity {

    LinearLayout btnOrderDetails;
    CoordinatorLayout main;
    ImageView back;
    ImageView step1, step2, step3, step4, step5;

    TextView status;
    int value;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_orders);

        main = findViewById(R.id.main);
        btnOrderDetails = findViewById(R.id.btn_order_details);
        back = findViewById(R.id.back);
        step1 = findViewById(R.id.step_1);
        step2 = findViewById(R.id.step_2);
        step3 = findViewById(R.id.step_3);
        step4 = findViewById(R.id.step_4);
        step5 = findViewById(R.id.step_5);
        status = findViewById(R.id.status);

        tools = new Tools();

        tools.setLightStatusBar(main, this);

        value = 4;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrackOrdersActivity.this, ActiveOrdersActivity.class));
                finish();
            }
        });

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value==4){
                    step5.setImageDrawable(getResources().getDrawable(R.drawable.ic_selected));
                    value++;
                    status.setText("Delivery Details");
                }
            }
        });

        btnOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackOrdersActivity.this, DeliveryDetailsActivity.class);
                intent.putExtra("value", value);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TrackOrdersActivity.this, ActiveOrdersActivity.class));
        finish();
    }

}