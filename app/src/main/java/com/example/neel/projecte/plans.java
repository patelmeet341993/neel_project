package com.example.neel.projecte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neel.projecte.adapter.PlanAdapter;
import com.example.neel.projecte.adapter.plan_model;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class plans extends AppCompatActivity {

    RecyclerView recy;
    PlanAdapter adapter;
    List<plan_model> list;
    static TextView balance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
               plans.super.onBackPressed();
            }
        });


        recy=(RecyclerView)findViewById(R.id.recy);
        list=new ArrayList<>();
        adapter=new PlanAdapter(list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Myapp.con);
        recy.setLayoutManager(mLayoutManager);
        recy.setItemAnimator(new DefaultItemAnimator());
        recy.setAdapter(adapter);

        balance=(TextView)findViewById(R.id.balance);

        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(plans.this,ewallet.class);
                startActivity(i);
            }
        });

        updateData();
        getData();

    }

    public static void updateData()
    {
        balance.setText(Myapp.userdata.get("balance")+"");
    }

    void getData()
    {
        Myapp.ref.child("system").child("plans").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Map<String,String> data=(Map<String, String>) dataSnapshot.getValue();
                plan_model m=new plan_model(data.get("name"),data.get("price"),data.get("dec"),data.get("capacity"),data.get("eprice"));
                list.add(m);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
