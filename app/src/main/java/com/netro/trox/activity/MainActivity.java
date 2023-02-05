package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.netro.trox.R;
import com.netro.trox.adapter.SliderAdapterHome;
import com.netro.trox.model.SliderData;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = findViewById(R.id.imageSlider);

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData("https://firebasestorage.googleapis.com/v0/b/ourwebrtc.appspot.com/o/Group%202608917.png?alt=media&token=d62982d7-4c3f-42ef-ad6b-46d284ef00ec"));
        sliderDataArrayList.add(new SliderData("https://firebasestorage.googleapis.com/v0/b/ourwebrtc.appspot.com/o/Group%202608917.png?alt=media&token=d62982d7-4c3f-42ef-ad6b-46d284ef00ec"));
        sliderDataArrayList.add(new SliderData("https://firebasestorage.googleapis.com/v0/b/ourwebrtc.appspot.com/o/Group%202608917.png?alt=media&token=d62982d7-4c3f-42ef-ad6b-46d284ef00ec"));

        SliderAdapterHome adapter = new SliderAdapterHome(this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }
}