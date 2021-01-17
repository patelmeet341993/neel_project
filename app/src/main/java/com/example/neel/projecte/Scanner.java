package com.example.neel.projecte;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class Scanner extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener{
    QRCodeReaderView qrCodeReaderView;
    TextView tv;
    TextView tv1;
    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setAnimation("scan.json");
        animationView.loop(true);
        animationView.playAnimation();

        qrCodeReaderView = findViewById(R.id.qr);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        tv=findViewById(R.id.txt);
        tv1=findViewById(R.id.label);

    }




    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        tv.setText(text);
    }
}
