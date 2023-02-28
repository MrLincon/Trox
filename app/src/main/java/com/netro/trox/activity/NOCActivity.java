package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.netro.trox.R;
import com.netro.trox.util.Tools;

public class NOCActivity extends AppCompatActivity {

    ImageView back;
    CardView btnContinue;

    LinearLayout upload, file;
    CoordinatorLayout main;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noc);

        main = findViewById(R.id.main);
        back = findViewById(R.id.back);
        btnContinue = findViewById(R.id.btn_continue);
        upload = findViewById(R.id.upload);
        file = findViewById(R.id.file);

        tools = new Tools();

        tools.setLightStatusBar(main, this);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file.setVisibility(View.VISIBLE);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ParcelOrderDetailsActivity.class);
                intent.putExtra("type", "International");
                startActivity(intent);
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