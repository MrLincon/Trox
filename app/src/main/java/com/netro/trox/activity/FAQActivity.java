package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.netro.trox.R;
import com.netro.trox.util.Tools;

public class FAQActivity extends AppCompatActivity {

  LinearLayout layout1, layout2, layout3, layout4;

  ImageView image1, image2, image3, image4;
  CardView card1, card2, card3, card4;
  TextView details1, details2, details3, details4;

  LinearLayout main;

  Tools tools;

    Boolean open1 = false, open2 = false, open3 = false, open4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        main = findViewById(R.id.main);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);
        details1 = findViewById(R.id.details1);
        details2 = findViewById(R.id.details2);
        details3 = findViewById(R.id.details3);
        details4 = findViewById(R.id.details4);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);

        tools = new Tools();

        tools.setLightStatusBar(main,this);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(details1,layout1, image1);
                if (!open1){
                    image1.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_up));
                    open1 = true;

                }else if(open1){
                    image1.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down));
                    open1 = false;
                }
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(details2,layout2, image2);
                if (!open2){
                    image2.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_up));
                    open2 = true;

                }else if(open2){
                    image2.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down));
                    open2 = false;
                }
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(details3,layout3, image3);
                if (!open3){
                    image3.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_up));
                    open3 = true;

                }else if(open3){
                    image3.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down));
                    open3 = false;
                }
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(details4,layout4, image4);
                if (!open4){
                    image4.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_up));
                    open4 = true;

                }else if(open4){
                    image4.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down));
                    open4 = false;
                }
            }
        });

    }

    public void expand(TextView textView, LinearLayout layout, ImageView imageView){
        int v = (textView.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        textView.setVisibility(v);

        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

    }

}