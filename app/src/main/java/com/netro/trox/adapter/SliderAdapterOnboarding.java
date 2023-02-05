package com.netro.trox.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.netro.trox.R;
import com.netro.trox.authentication.LoginActivity;
import com.netro.trox.util.App;

public class SliderAdapterOnboarding extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapterOnboarding(Context context) {
        this.context = context;
    }

    public int[] slider_images = {
            R.drawable.onboarding_1,
            R.drawable.onboarding_2,
            R.drawable.onboarding_3
    };

    public String[] slider_headings = {
            App.getAppResources().getString(R.string.onboarding_title1),
            App.getAppResources().getString(R.string.onboarding_title2),
            App.getAppResources().getString(R.string.onboarding_title3)
    };

    public String[] slider_desc = {
            App.getAppResources().getString(R.string.onboarding_desc1),
            App.getAppResources().getString(R.string.onboarding_desc2),
            App.getAppResources().getString(R.string.onboarding_desc3)
    };

    @Override
    public int getCount() {
        return slider_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboarding_slider_layout, container, false);

        ImageView imageView = view.findViewById(R.id.imageViewOnboarding);
        TextView header = view.findViewById(R.id.onboarding_title);
        TextView desc = view.findViewById(R.id.onboarding_description);
        CardView finish = view.findViewById(R.id.finish);

        imageView.setImageResource(slider_images[position]);
        header.setText(slider_headings[position]);
        desc.setText(slider_desc[position]);


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = context.getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("firstStart", false);
                editor.apply();

                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
