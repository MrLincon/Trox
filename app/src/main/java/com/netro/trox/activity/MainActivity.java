package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.netro.trox.R;
import com.netro.trox.adapter.SliderAdapterHome;
import com.netro.trox.model.SliderData;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    ImageView notification;
    LinearLayout sendParcel, priceCheck;
    ChipNavigationBar bottomNav;
    CircleImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userImage = findViewById(R.id.user_image);
        bottomNav = findViewById(R.id.bottomNav);
        sendParcel = findViewById(R.id.send_parcel);
        priceCheck = findViewById(R.id.price_check);
        notification = findViewById(R.id.notification);

        bottomNav.setItemSelected(R.id.nav_home,true);

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


        priceCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GetQuotationActivity.class));
            }
        });


        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.nav_offer:
                       startActivity(new Intent(MainActivity.this, OffersActivity.class));
                        break;
                    case R.id.nav_home:

                        break;
                    case R.id.nav_order:
                        startActivity(new Intent(MainActivity.this, OrdersActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNav.setItemSelected(R.id.nav_home,true);
    }
}