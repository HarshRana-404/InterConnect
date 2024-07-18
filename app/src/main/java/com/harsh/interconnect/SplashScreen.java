package com.harsh.interconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    ImageView ivIcon;
    TextView tvTitle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ivIcon = findViewById(R.id.iv_splash_icon);
        tvTitle = findViewById(R.id.tv_splash_title);
        Animation animTranslate = new TranslateAnimation(-400, 0, 0, 0);
        animTranslate.setDuration(400);
        ivIcon.setAnimation(animTranslate);
        Animation animTrans = new TranslateAnimation(400, 0, 0, 0);
        animTrans.setDuration(400);
        tvTitle.setAnimation(animTrans);
        try{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(isConnectedToInternet()){
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                        finish();
                    }else{
                        generateDialog();
                    }
                }
            }, 1000);
        } catch (Exception e) {}

    }
    public Boolean isConnectedToInternet(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        if(network==null){
            return false;
        }
        return true;
    }
    public void generateDialog(){
        AlertDialog.Builder adb = new AlertDialog.Builder(SplashScreen.this);
        adb.setCancelable(false);
        AlertDialog ad = adb.create();
        adb.setTitle("No Internet!");
        adb.setMessage("Network connection not found!");
        adb.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isConnectedToInternet()){
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();
                }else{
                    generateDialog();
                }
            }
        });
        adb.show();
    }

}