package com.example.neel.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ewallet extends AppCompatActivity {
    Button bADD;
    EditText etAmount;
    TextView tvbal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewallet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etAmount=(EditText)findViewById(R.id.etAmount);
        bADD=(Button)findViewById(R.id.bAdd);
        tvbal=(TextView)findViewById(R.id.tvbal);



        setBalance(tvbal);
        bADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etAmount.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "please enter the amount", Toast.LENGTH_LONG).show();
                }
                else{
                    updateBalance(etAmount);


                }




            }
        });

    }



    void updateBalance(EditText etAmount)
    {
        String enbal=(etAmount.getText().toString());
        int bal=Integer.parseInt(enbal);
        int prebal=Integer.parseInt(Myapp.userdata.get("balance")+"");

        int newbal=bal+prebal;
        Myapp.myref.child("balance").setValue(newbal+"").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                  setBalance(tvbal);
            }
        });
    }

    void setBalance(TextView tvbal)
    {
        tvbal.setText("\u20B9 "+Myapp.userdata.get("balance"));
    }

}
