package com.netro.trox.activity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.netro.trox.R;
import com.netro.trox.adapter.OrderDataAdapter;
import com.netro.trox.model.OrderData;
import com.netro.trox.util.LinearRecyclerDecoration;
import com.netro.trox.util.Tools;

public class ActiveOrdersActivity extends AppCompatActivity {

    LinearLayout main;
    ImageView back;

    Dialog popup;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public String item_id;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference orderData;

    private OrderDataAdapter adapter;


    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_orders);

        main = findViewById(R.id.main);
        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        int sidePadding = getResources().getDimensionPixelSize(R.dimen.sidePadding);
        recyclerView.addItemDecoration(new LinearRecyclerDecoration(topPadding, bottomPadding, sidePadding));

        tools = new Tools();

        tools.setLightStatusBar(main, this);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        orderData = db.collection("orders");


        Query query = orderData.orderBy("timestamp", Query.Direction.ASCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(15)
                .build();

        FirestorePagingOptions<OrderData> options = new FirestorePagingOptions.Builder<OrderData>()
                .setQuery(query, config, OrderData.class)
                .build();

        adapter = new OrderDataAdapter(options, swipeRefreshLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh();
            }
        });

        adapter.setOnItemClickListener(new OrderDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot) {
                item_id = documentSnapshot.getId();
                OrderData orderData = documentSnapshot.toObject(OrderData.class);
                assert orderData != null;
                String status = orderData.getOrder_status();

                Intent intent = new Intent(ActiveOrdersActivity.this, TrackOrdersActivity.class);
                intent.putExtra("status", status);
                intent.putExtra("order_id", item_id);
                startActivity(intent);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActiveOrdersActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ActiveOrdersActivity.this, MainActivity.class));
        finish();
    }
}