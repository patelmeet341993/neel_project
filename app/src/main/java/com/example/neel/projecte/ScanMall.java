package com.example.neel.projecte;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class ScanMall extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {
    private QRCodeReaderView qrCodeReaderView;
    LottieAnimationView animationView;

    boolean take=true;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_mall);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv=findViewById(R.id.tv);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setAnimation("scan.json");
        animationView.loop(true);
        animationView.playAnimation();
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);

        ImageView backbtn=(ImageView)findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanMall.super.onBackPressed();
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv.getText().toString().equals("Wrong QR.\n Click here to scan again"))
                {
                    tv.setText("Scanning...");
                    take=true;

                }
                else
                {

                }
            }
        });
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if(take) {

            if(text.equals("mall1"))
            {
                Myapp.mallname="mall1";
                Myapp.showMsg("Welcome to Mall-1");
                Intent i=new Intent(getApplicationContext(),cart.class);
                startActivity(i);
                finish();
            }
            else if(text.equals("mall2"))
            {

                Myapp.mallname="mall2";
                Myapp.showMsg("Welcome to Mall-2");
                Intent i=new Intent(getApplicationContext(),cart.class);
                startActivity(i);
                finish();
            }

            else if(text.equals("mall3"))
            {

                Myapp.mallname="mall3";
                Myapp.showMsg("Welcome to Mall-3");
                Intent i=new Intent(getApplicationContext(),cart.class);
                startActivity(i);
                finish();
            }
            else if(text.equals("mall4"))
            {

                Myapp.mallname="mall4";
                Myapp.showMsg("Welcome to Mall-4");
                Intent i=new Intent(getApplicationContext(),cart.class);
                startActivity(i);
                finish();
            }
            else
            {
                tv.setText("Wrong QR.\n Click here to scan again");
            }


            take=false;
        }
    }
}
