package com.example.neel.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class Splashscreen extends AppCompatActivity {
    TextView textView1;
    ImageView logo;
    Animation frombottom;
    Animation fromtop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        textView1=(TextView)findViewById(R.id.text1);
        logo=(ImageView)findViewById(R.id.log);
        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        textView1.setAnimation(frombottom);
        logo.setAnimation(fromtop);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(Splashscreen.this,MainActivity.class);
                startActivity(i);
                finish();



            }},2000);
    }


}
