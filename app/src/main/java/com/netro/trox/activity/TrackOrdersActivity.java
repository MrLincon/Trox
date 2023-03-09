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
    View stepLine1, stepLine2, stepLine3, stepLine4;

    String status, order_id, fragment;
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
        stepLine1 = findViewById(R.id.step_line_1);
        stepLine2 = findViewById(R.id.step_line_2);
        stepLine3 = findViewById(R.id.step_line_3);
        stepLine4 = findViewById(R.id.step_line_4);

        tools = new Tools();

        tools.setLightStatusBar(main, this);

        status = getIntent().getStringExtra("status");
        order_id = getIntent().getStringExtra("order_id");

        orderStatusUpdate();

        value = 4;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        btnOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackOrdersActivity.this, OrderDetailsActivity.class);
                intent.putExtra("value", value);
                intent.putExtra("data", fragment);
                intent.putExtra("order_id", order_id);
                startActivity(intent);
            }
        });

    }

    private void orderStatusUpdate() {
        if (status.equals("Pending")){
            fragment = "FragmentPickUpRequest";
            step1.setImageDrawable(getResources().getDrawable(R.drawable.ic_selected));

        } else if (status.equals("Processing")) {
            fragment = "FragmentPickUpRequest";
            step1.setImageDrawable(getResources().getDrawable(R.drawable.ic_selected));
            step2.setImageDrawable(getResources().getDrawable(R.drawable.ic_selected));
        }
        else if (status.equals("Picked Up")) {
            fragment = "FragmentPickedUp";
            step1.setImageDrawable(getResources().getDrawable(R.drawable.ic_selected));
            step2.setImageDrawable(getResources().getDrawable(R.drawable.ic_selected));
            step3.setImageDrawable(getResources().getDrawable(R.drawable.ic_selected));
            step4.setImageDrawable(getResources().getDrawable(R.drawable.ic_selected));
        }
        else if (status.equals("Delivered")) {
            fragment = "FragmentDelivered";
            step1.setImageDrawable(getResources().getDrawable(R.drawable.ic_selected));
            step2.setImageDrawable(getResources().getDrawable(R.drawable.ic_selected));
            step3.setImageDrawable(getResources().getDrawable(R.drawable.ic_selected));
            step4.setImageDrawable(getResources().getDrawable(R.drawable.ic_selected));
            step5.setImageDrawable(getResources().getDrawable(R.drawable.ic_selected));
        }else if (status.equals("Returned")) {
            fragment = "FragmentReturned";
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}