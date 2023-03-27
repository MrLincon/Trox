package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.netro.trox.R;
import com.netro.trox.adapter.SliderAdapterHome;
import com.netro.trox.model.SliderData;
import com.netro.trox.util.Tools;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    ImageView notification;
    LinearLayout sendParcel, priceCheck, trackOrder, local, domestic, international, homeAddress, workAddress;
    ChipNavigationBar bottomNav;
    CoordinatorLayout main;
    CircleImageView userImage;
    TextView userName;
    TextView homeAddressText, workAddressText;
    EditText search;
    ImageView btnSearch;

    private FirebaseFirestore db;
    private DocumentReference document_ref;
    private String userID;
    private FirebaseAuth mAuth;

    LocationManager locationManager;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userImage = findViewById(R.id.user_image);
        userName = findViewById(R.id.user_name);
        bottomNav = findViewById(R.id.bottomNav);
        sendParcel = findViewById(R.id.send_parcel);
        priceCheck = findViewById(R.id.price_check);
        trackOrder = findViewById(R.id.track_order);
        notification = findViewById(R.id.notification);
        local = findViewById(R.id.local);
        domestic = findViewById(R.id.domestic);
        international = findViewById(R.id.international);
        homeAddress = findViewById(R.id.home);
        workAddress = findViewById(R.id.work);
        homeAddressText = findViewById(R.id.home_address);
        workAddressText = findViewById(R.id.work_address);
        search = findViewById(R.id.search);
        btnSearch = findViewById(R.id.btn_search);

        tools = new Tools();

        //firebase init
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getUid();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        bottomNav.setItemSelected(R.id.nav_home, true);

        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        SliderView sliderView = findViewById(R.id.imageSlider);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

        String url = "https://firebasestorage.googleapis.com/v0/b/ourwebrtc.appspot.com/o/Group%202608917.png?alt=media&token=d62982d7-4c3f-42ef-ad6b-46d284ef00ec";

        sliderDataArrayList.add(new SliderData(url));
        sliderDataArrayList.add(new SliderData(url));
        sliderDataArrayList.add(new SliderData(url));

        SliderAdapterHome adapter = new SliderAdapterHome(this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        //load user data
        db.collection("userDetails").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String user_image = documentSnapshot.getString("user_image");
                    String user_name = documentSnapshot.getString("user_name");
                    String Home = documentSnapshot.getString("home_address");
                    String Work = documentSnapshot.getString("work_address");

                    Glide.with(MainActivity.this).load(user_image).into(userImage);
                    userName.setText(user_name);


                    try {
                        if (!Home.equals("")) {
                            homeAddressText.setText(Home);
                        }

                        if (!Work.equals("")) {
                            workAddressText.setText(Work);
                        }
                    } catch (Exception e) {

                    }

                }
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                finish();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                finish();
            }
        });

        sendParcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SendParcelActivity.class));
            }
        });

        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(MainActivity.this, ParcelOrderDetailsActivity.class);
                    intent.putExtra("type", "Local");
                    intent.putExtra("ID", "fromHome");
                    startActivity(intent);
                }

            }
        });


        domestic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(MainActivity.this, ParcelOrderDetailsActivity.class);
                    intent.putExtra("type", "Domestic");
                    intent.putExtra("ID", "fromHome");
                    startActivity(intent);
                }

            }
        });

        international.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(MainActivity.this, NOCActivity.class);
                    intent.putExtra("type", "International");
                    startActivity(intent);
                }
            }
        });

        homeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);

                } else {
                    startActivity(new Intent(MainActivity.this, SavedAddressesActivity.class));
                }
            }
        });

        workAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);

                } else {
                    startActivity(new Intent(MainActivity.this, SavedAddressesActivity.class));
                }
            }
        });

        priceCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GetQuotationActivity.class));
            }
        });

        trackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActiveOrdersActivity.class));
                finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = search.getText().toString();
                Intent intent = new Intent(MainActivity.this, SearchOrdersActivity.class);
                intent.putExtra("order_id", text);
                startActivity(intent);
            }
        });

        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.nav_home:
                        break;

                    case R.id.nav_order:
                        startActivity(new Intent(MainActivity.this, OrdersActivity.class));
                        break;

                    case R.id.nav_profile:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        tools.makeSnack(main, "Use double back press to exit");
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNav.setItemSelected(R.id.nav_home, true);
    }
}