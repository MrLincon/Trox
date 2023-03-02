package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netro.trox.R;
import com.netro.trox.util.Tools;

import java.util.ArrayList;

public class NOCActivity extends AppCompatActivity {

    ImageView back;
    CardView btnContinue;

    LinearLayout upload, file;
    CoordinatorLayout main;


//    RecyclerView recyclerView;

    private CollectionReference item;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userID;

    Dialog popup;

//    OngoingAdapter adapter;

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