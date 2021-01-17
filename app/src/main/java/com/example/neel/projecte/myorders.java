package com.example.neel.projecte;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.neel.projecte.adapter.itemadapter;
import com.example.neel.projecte.adapter.itemmodel;
import com.example.neel.projecte.adapter.order;
import com.example.neel.projecte.adapter.orderAdapter;
import com.mancj.slideup.SlideUp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class myorders extends AppCompatActivity {
    RecyclerView recy;
    orderAdapter adapter;
    List<order> list;
    RelativeLayout slider;
    static RecyclerView recy1;
    static itemadapter adapter1;
    static List<itemmodel> list1;
    static SlideUp slide;
    static Bitmap bit;
    static Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        con=this;

        ImageView lgout=(ImageView)findViewById(R.id.lgbtn);
        ImageView backbtn=(ImageView)findViewById(R.id.backbtn);
        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myorders.super.onBackPressed();
            }
        });


        recy1=(RecyclerView)findViewById(R.id.recy1);
        list1=new ArrayList<>();
        adapter1=new itemadapter(list1);
        slider=(RelativeLayout)findViewById(R.id.slider);
        setSlide();
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(Myapp.con);
        recy1.setLayoutManager(mLayoutManager1);
        recy1.setItemAnimator(new DefaultItemAnimator());
        recy1.setAdapter(adapter1);


        recy=(RecyclerView)findViewById(R.id.recy);
        list=new ArrayList<>();
        adapter=new orderAdapter(list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Myapp.con);
        recy.setLayoutManager(mLayoutManager);
        recy.setItemAnimator(new DefaultItemAnimator());
        recy.setAdapter(adapter);
try {
    setData();
}
catch(Exception e)
{
    Myapp.showMsg("There is no order");
}
}
    @Override
    public void onBackPressed() {

        if(slide.isVisible())
        {
            slide.hide();
        }
        else
        {
            super.onBackPressed();
        }
    }

    public static void setItemdata(Map<String,Object> data)
    {
        list1.clear();
        List<String> datakey=new ArrayList<>(data.keySet());
        for(int i=0;i<data.size();i++)
        {
            Map<String,String> dd=(Map<String, String>)data.get(datakey.get(i));
            list1.add(new itemmodel(dd.get("name"),dd.get("price"),dd.get("item")));
        }
        adapter1.notifyDataSetChanged();

        if(slide.isVisible())
        {
            slide.hide();
        }
        else
        {
            slide.show();
        }


    }

    void setData()
    {
        Map<String,Object> ords=(Map<String, Object>)Myapp.userdata.get("order");
        List<String> keys=new ArrayList<>(ords.keySet());

        Collections.sort(keys);

        for(int i=keys.size()-1;i>=0;i--)
        {
            Map<String,Object> oo=(Map<String, Object>) ords.get(keys.get(i));
            list.add(new order(oo,keys.get(i)));
        }
        adapter.notifyDataSetChanged();
    }

    void setSlide()
    {
        slide = new SlideUp.Builder(slider)
                .withListeners(new SlideUp.Listener() {
                    @Override
                    public void onSlide(float percent) {

                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {

                    }
                })
                .withStartGravity(Gravity.TOP)
                .withLoggingEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();


    }

   public static void ShowQR(String oid) {


        QRGEncoder qrgEncoder = new QRGEncoder(Myapp.mynumber+":"+oid, null, QRGContents.Type.TEXT, 300);

        final Dialog dialog = new Dialog(con);


        dialog.setContentView(R.layout.dialoug_qr);


        ImageView iv=(ImageView)dialog.findViewById(R.id.qrimg);
        try {
            bit = qrgEncoder.encodeAsBitmap();
            // Setting Bitmap to ImageView
           iv.setImageBitmap(bit);
        }
        catch(Exception e)
        {
            Myapp.showMsg("Please try again later");
        }

        final ImageView btnClose = (ImageView) dialog.findViewById(R.id.canclebtn);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
       dialog.show();

    }



    }
