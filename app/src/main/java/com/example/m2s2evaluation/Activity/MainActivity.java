package com.example.m2s2evaluation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m2s2evaluation.Connection.ApiClient;
import com.example.m2s2evaluation.Connection.Network;
import com.example.m2s2evaluation.Model.ResponseData;
import com.example.m2s2evaluation.Model.WeatherItem;
import com.example.m2s2evaluation.R;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText searchEdit;
    private Button searchBtn;
    private LinearLayout resultLayout;
    private TextView humidityTv;
    private TextView descriptionTv;
    private TextView tempTv;
    private TextView speedTv;
    private TextView nameTv;
    private static String apiId = "6699c70179c894baaa21d440091b9a55";
    private static String unit = "imperial";
    private Button showIconBtn;
    ResponseData responseData = new ResponseData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        searchBtn = findViewById(R.id.searchBtn);
        searchEdit = findViewById(R.id.cityInputET);
        resultLayout = findViewById(R.id.resultLayout);
        humidityTv = findViewById(R.id.humidityTV);
        descriptionTv = findViewById(R.id.descriptionTV);
        tempTv = findViewById(R.id.tempTv);
        speedTv = findViewById(R.id.speedTv);
        nameTv = findViewById(R.id.nameTv);
        showIconBtn = findViewById(R.id.showIconBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchEdit.getText().toString()!=null){
                    callApi(searchEdit.getText().toString());
                }
            }
        });
    }

    private void callApi(String search) {
        ApiClient apiClient = Network.getRetrofitInstance(this).create(ApiClient.class);
        Call<ResponseData> responseDataCall = apiClient.getData(search,apiId,unit);
        responseDataCall.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body()!=null && response.code()== HttpURLConnection.HTTP_OK){
                    resultLayout.setVisibility(View.VISIBLE);
                   responseData = response.body();
                   humidityTv.setText(responseData.getMain().getHumidity()+" ");
                    WeatherItem weatherItem = new WeatherItem();
                    weatherItem = responseData.getWeather().get(0);
                    descriptionTv.setText(weatherItem.getDescription());
                    tempTv.setText(responseData.getMain().getTemp()+" ");
                    speedTv.setText( responseData.getWind().getSpeed()+" ");
                    nameTv.setText(responseData.getName());
                    showIconBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this,ShowIconActivty.class);
                            intent.putExtra("description",responseData.getWeather().get(0).getDescription());
                            intent.putExtra("icon",responseData.getWeather().get(0).getIcon());
                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failure - "+t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}