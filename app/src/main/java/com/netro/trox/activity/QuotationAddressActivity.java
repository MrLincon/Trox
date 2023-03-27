package com.netro.trox.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.netro.trox.R;
import com.netro.trox.adapter.LocationListAdpater;
import com.netro.trox.model.LocationList;
import com.netro.trox.util.Tools;

import java.util.ArrayList;
import java.util.List;

public class QuotationAddressActivity extends AppCompatActivity {


    ImageView back;
    RecyclerView recyclerView;

    LinearLayout main;

    SearchView search;

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userID;

    List<LocationList> locationData;
    LocationListAdpater adapter;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_address);

        main = findViewById(R.id.main);
        back = findViewById(R.id.back);
        search = findViewById(R.id.search);

        recyclerView = findViewById(R.id.recyclerView);

        tools = new Tools();

        tools.setLightStatusBar(main, QuotationAddressActivity.this);

//Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getUid();

//Data init
        String data = getIntent().getStringExtra("data");
        String country = getIntent().getStringExtra("country");
        String state = getIntent().getStringExtra("state");
        String city = getIntent().getStringExtra("city");

        if (data.equals("country")) {
            search.setQueryHint(getResources().getString(R.string.enter_country));

            db.collection("Countries").orderBy("name", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        locationData = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String data = document.getString("name");
                            locationData.add(new LocationList(data));
                        }
                        // Load the data in your list
                        adapter = new LocationListAdpater(locationData, QuotationAddressActivity.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(QuotationAddressActivity.this));


                        adapter.setOnItemClickListener(new LocationListAdpater.OnItemClickListener() {
                            @Override
                            public void onItemClick(String value) {
                                Intent intent = new Intent();
                                intent.putExtra("key", value);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
                    } else {
                        tools.logMessage("DeliveryAddressActivity", task.getException().toString());
                    }
                }
            });


        } else if (data.equals("state")) {
            search.setQueryHint(getResources().getString(R.string.enter_state));


            db.collection("Countries").document(country).collection("States").orderBy("name", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        locationData = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String data = document.getString("name");
                            locationData.add(new LocationList(data));
                        }
                        // Load the data in your list
                        adapter = new LocationListAdpater(locationData, QuotationAddressActivity.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(QuotationAddressActivity.this));

                        adapter.setOnItemClickListener(new LocationListAdpater.OnItemClickListener() {
                            @Override
                            public void onItemClick(String value) {
                                Intent intent = new Intent();
                                intent.putExtra("key", value);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
                    } else {
                        tools.logMessage("DeliveryAddressActivity", task.getException().toString());
                    }
                }
            });
        } else if (data.equals("city")) {
            search.setQueryHint(getResources().getString(R.string.enter_city));

            db.collection("Countries").document(country).collection("States").document(state).collection("Cities").orderBy("name", Query.Direction.ASCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                locationData = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String data = document.getString("name");
                                    locationData.add(new LocationList(data));
                                }
                                // Load the data in your list
                                adapter = new LocationListAdpater(locationData, QuotationAddressActivity.this);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(QuotationAddressActivity.this));

                                adapter.setOnItemClickListener(new LocationListAdpater.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(String value) {
                                        Intent intent = new Intent();
                                        intent.putExtra("key", value);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                });
                            } else {
                                tools.logMessage("DeliveryAddressActivity", task.getException().toString());
                            }
                        }
                    });
        }


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // handle search query submit
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String capitalizedText = null;
                if (newText.length()!=0){
                    capitalizedText = newText.substring(0, 1).toUpperCase() + newText.substring(1).toLowerCase();
                }
                if (data.equals("country")) {
                    search.setQueryHint(getResources().getString(R.string.enter_country));

                    db.collection("Countries")
                            .orderBy("name", Query.Direction.ASCENDING)
                            .startAt(capitalizedText).endAt(capitalizedText + "\uf8ff")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                locationData = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String data = document.getString("name");
                                    locationData.add(new LocationList(data));
                                }
                                // Load the data in your list
                                adapter = new LocationListAdpater(locationData, QuotationAddressActivity.this);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(QuotationAddressActivity.this));


                                adapter.setOnItemClickListener(new LocationListAdpater.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(String value) {
                                        Intent intent = new Intent();
                                        intent.putExtra("key", value);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                });
                            } else {
                                tools.logMessage("DeliveryAddressActivity", task.getException().toString());
                            }
                        }
                    });


                } else if (data.equals("state")) {
                    search.setQueryHint(getResources().getString(R.string.enter_state));


                    db.collection("Countries").document(country).collection("States")
                            .orderBy("name", Query.Direction.ASCENDING)
                            .startAt(capitalizedText.toUpperCase()).endAt(capitalizedText + "\uf8ff")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                locationData = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String data = document.getString("name");
                                    locationData.add(new LocationList(data));
                                }
                                // Load the data in your list
                                adapter = new LocationListAdpater(locationData, QuotationAddressActivity.this);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(QuotationAddressActivity.this));

                                adapter.setOnItemClickListener(new LocationListAdpater.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(String value) {
                                        Intent intent = new Intent();
                                        intent.putExtra("key", value);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                });
                            } else {
                                tools.logMessage("DeliveryAddressActivity", task.getException().toString());
                            }
                        }
                    });
                } else if (data.equals("city")) {
                    search.setQueryHint(getResources().getString(R.string.enter_city));

                    db.collection("Countries").document(country)
                            .collection("States")
                            .document(state).collection("Cities")
                            .orderBy("name", Query.Direction.ASCENDING)
                            .startAt(capitalizedText.toUpperCase()).endAt(capitalizedText + "\uf8ff")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        locationData = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String data = document.getString("name");
                                            locationData.add(new LocationList(data));
                                        }
                                        // Load the data in your list
                                        adapter = new LocationListAdpater(locationData, QuotationAddressActivity.this);
                                        recyclerView.setAdapter(adapter);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(QuotationAddressActivity.this));

                                        adapter.setOnItemClickListener(new LocationListAdpater.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(String value) {
                                                Intent intent = new Intent();
                                                intent.putExtra("key", value);
                                                setResult(RESULT_OK, intent);
                                                finish();
                                            }
                                        });
                                    } else {
                                        tools.logMessage("DeliveryAddressActivity", task.getException().toString());
                                    }
                                }
                            });
                }
                return true;
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