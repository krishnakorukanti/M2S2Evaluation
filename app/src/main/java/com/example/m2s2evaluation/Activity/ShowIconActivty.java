package com.example.m2s2evaluation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.m2s2evaluation.R;

public class ShowIconActivty extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    private String icon;
    private String des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_icon_activty);
        initViews();
         icon = getIntent().getStringExtra("icon");
         des = getIntent().getStringExtra("description");
         setData();
    }

    private void setData() {
        if (icon!=null&&des!=null){
            textView.setText(des);
            String  url = "https://openweathermap.org/img/wn/"+icon+"@2x.png";
            Glide.with(imageView).load(url).into(imageView);
        }
    }

    private void initViews() {
        imageView = findViewById(R.id.iconImage);
        textView = findViewById(R.id.desTextDis);
    }
}