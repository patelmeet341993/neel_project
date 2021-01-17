package com.example.neel.projecte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.andremion.counterfab.CounterFab;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.example.neel.projecte.adapter.Subject2;
import com.example.neel.projecte.adapter.SubjectAdapter2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mancj.slideup.SlideUp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class cart extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener  {
    private QRCodeReaderView qrCodeReaderView;

    RelativeLayout slider;
    ImageView redo;
   static CounterFab fab;
    boolean take=true;
   static TextView tvamt,tvitems,ewallet;
   ProgressDialog pd;

   Button pay;
    public static Map<String,Object> items;
    SlideUp slide;
    RecyclerView recy;
   public static SubjectAdapter2 adapter;
   public static List<Subject2> list;

   static int balance=0,totalord=0,totalorditem=0;
    Map<String,Object> plans=null;

    LottieAnimationView animationView;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        animationView.setAnimation("scan.json");
        animationView.loop(true);
        animationView.playAnimation();

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
                cart.super.onBackPressed();
            }
        });


        items=new HashMap<>();
        pay=(Button)findViewById(R.id.btnpay);
        ewallet=(TextView)findViewById(R.id.ewallet);
        tvitems=(TextView)findViewById(R.id.totitems);
        tvamt=(TextView)findViewById(R.id.totrs);
        fab=(CounterFab)findViewById(R.id.counter_fab);
        slider=(RelativeLayout)findViewById(R.id.slider);
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        redo=(ImageView)findViewById(R.id.redo);
        pd=new ProgressDialog(cart.this);
        pd.setMessage("adding item");
        pd.setCancelable(false);
        recy=(RecyclerView)findViewById(R.id.recy);
        list=new ArrayList<>();
        adapter=new SubjectAdapter2(list);
        getPlans();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Myapp.con);
        recy.setLayoutManager(mLayoutManager);
        recy.setItemAnimator(new DefaultItemAnimator());
        recy.setAdapter(adapter);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
        qrCodeReaderView.setFrontCamera();
        qrCodeReaderView.setBackCamera();

        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             redo.setVisibility(View.GONE);
             take=true;

            }
        });
        setSlide();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(items.size()>0)
                {
                    if(slide.isVisible())
                    {
                        slide.hide();
                    }
                    else {
                        slide.show();
                    }
                }
                else
                {
                    if(slide.isVisible())
                    {
                        slide.hide();
                    }
                    Myapp.showMsg("There are no items in cart");
                }

            }
        });
        balance= Integer.parseInt(Myapp.userdata.get("balance")+"");
        ewallet.setText("Balance "+Myapp.userdata.get("balance")+"\u20B9 /-");

        ewallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(balance<totalord)
                {
                    Intent i=new Intent(Myapp.con,ewallet.class);
                    startActivity(i);
                }
                else
                {
                    Myapp.showMsg("You have enough balance to Buy these items");
                }

            }
        });


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(balance<totalord)
                {
                    Intent i=new Intent(Myapp.con,ewallet.class);
                    startActivity(i);
                }
                else
                {
                    pd.show();
                    pd.setMessage("OTP send to your register mobile number "+Myapp.mynumber);
                    startPhoneNumberVerification(Myapp.mynumber);

                }
            }
        });
        setCallBacks();

    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    void setCallBacks()
    {
        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                pd.dismiss();
                placeOrder();

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                pd.setTitle("please wait");
                pd.setMessage("OTP send to your registered mobile number and auto verify by our application.");
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }
        };
    }

    void placeOrder()
    {
        pd.setMessage("Please wait");
        pd.show();
        if(plans!=null)
        {
            List<String> pl=new ArrayList<>(plans.keySet());
            int flag=0;
            for(int i=0;i<pl.size();i++)
            {
                Map<String,String> datas=(Map<String, String>)plans.get(pl.get(i));
                int st= Integer.parseInt(datas.get("price"));
                int et= Integer.parseInt(datas.get("eprice"));

                if(totalord>=st && totalord<et)
                {
                    int dis= Integer.parseInt(datas.get("capacity"));
                    int dicount=totalord*dis/100;
                    totalord=totalord-dicount;
                    placedData(true,dis);
                    flag=1;
                    break;
                }

            }
            if(flag==0)
            {
                placedData(false,0);
            }

        }
        else {


        }
    }

    void placedData(final boolean offer,final int o)
    {
        String date, month, year, time, fulldate, day;
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        date = df.format(c);

        df = new SimpleDateFormat("MMM");
        month = df.format(c);

        df = new SimpleDateFormat("yyyy");
        year = df.format(c);


        df = new SimpleDateFormat("hh:mm aaa");
        time = df.format(c);

        df = new SimpleDateFormat("dd-MMM,yyyy");
        fulldate = df.format(c);

        df = new SimpleDateFormat("EEEE");
        day = df.format(c);

        Map<String, Object> data = new HashMap<>();
        data.put("day", day);
        data.put("date", date);
        data.put("time", time);
        if(offer)
        {
            data.put("offer",""+o);
        }
        data.put("fulldate", fulldate);
        data.put("month", month.toUpperCase());
        data.put("year", year);
        data.put("amount", "" + totalord);
        data.put("items", "" + totalorditem);
        data.put("order", items);
        data.put("uid", Myapp.mynumber);
        pd.setMessage("Please wait");
        pd.show();
        String key = Myapp.ref.child("users").child(Myapp.mynumber).child("order").push().getKey();
        data.put("invoice", key.substring(0, 6));
        Myapp.ref.child("users").child(Myapp.mynumber).child("order").child(key).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Myapp.ref.child("users").child(Myapp.mynumber).child("balance").setValue("" + (balance - totalord)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        if(offer) {
                            Myapp.showMsg("successfully placed\nyou get "+o+"% discount");
                        }
                        else {     Myapp.showMsg("successfully placed");

                        }
                        Intent i = new Intent(Myapp.con, home.class);
                        startActivity(i);
                        finish();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Myapp.showMsg("try again later");
                pd.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        balance= Integer.parseInt(Myapp.userdata.get("balance")+"");
        ewallet.setText("Balance "+Myapp.userdata.get("balance")+"\u20B9 /-");
        calculateAmount();
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

    @Override
    public void onQRCodeRead(final String text, PointF[] points) {
        if(take) {
          //  Myapp.showMsg("Find Item : " + text);
            pd.show();
            take=false;

            DatabaseReference ref=Myapp.ref.child("menu").child(text);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                pd.dismiss();
                    if(dataSnapshot.getValue()==null)
                    {
                        Myapp.showMsg("Item not found");
                        take=true;

                    }
                    else
                    {
                        Map<String,String> dds=(Map<String, String>)dataSnapshot.getValue();



                        if(dds.get("mall").equals(Myapp.mallname)) {
                            if (items.containsKey(text)) {
                                Map<String,String> data=(Map<String, String>) items.get(text);
                                int i = Integer.parseInt(data.get("item"));
                                data.remove("item");
                                data.put("item", "" + (i + 1));
                                items.remove(text);
                                items.put(text, data);
                            } else {
                                Map<String,String> data=dds;
                                data.put("item", "1");
                                items.put(text, data);
                                Myapp.showMsg("new item added");
                            }
                            calculateAmount();
                            setRecy();
                            redo.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            Myapp.showMsg("Sorry!! This item is not belongs to this mall");
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }


   public void getPlans()
   {
       DatabaseReference reeff=Myapp.ref.child("system").child("plans");
     reeff.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             plans=(Map<String, Object>)dataSnapshot.getValue();
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });


   }

   public static void calculateAmount()
    {
        List<String> keys=new ArrayList<>(items.keySet());

        fab.setCount(keys.size());
        if(keys.size()==0)
        {
            tvitems.setText("Total Items : 0 ");
            tvamt.setText("0 \u20B9 /-");

        }
        else
        {
            int amt=0;
            int totitems=0;
            for(int i=0;i<keys.size();i++)
            {
                Map<String,String> mp=(Map<String, String>)items.get(keys.get(i));
                int itm= Integer.parseInt(mp.get("item"));
                int price= Integer.parseInt(mp.get("price"));
                amt=amt+(itm*price);
                totitems=totitems+itm;

            }
            totalord=amt;
            totalorditem=totitems;
            if(balance<totalord)
            {   ewallet.setTextColor(Color.parseColor("#aa0000"));

                Myapp.showMsg("You have to add "+(totalord-balance)+" \u20B9 /- money to purchase these items");
            }
            else
            {
                ewallet.setTextColor(Color.parseColor("#ffffff"));
            }
                       ewallet.setText("Balance left "+(balance-totalord)+"\u20B9 /-");

            tvitems.setText("Total Items : "+totitems);
            tvamt.setText(amt+" \u20B9 /-");



        }

    }

    public static void setRecy()
    {
        list.clear();
        adapter.notifyDataSetChanged();
        List<String> keys=new ArrayList<>(items.keySet());

            for(int i=0;i<keys.size();i++)
            {
                Map<String,String> mp=(Map<String, String>)items.get(keys.get(i));

                list.add(new Subject2(keys.get(i),mp.get("name"),mp.get("price"),mp.get("item")));

            }
            adapter.notifyDataSetChanged();





    }
}
