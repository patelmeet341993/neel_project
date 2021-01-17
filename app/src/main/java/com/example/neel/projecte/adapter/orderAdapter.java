package com.example.neel.projecte.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.neel.projecte.Myapp;
import com.example.neel.projecte.R;
import com.example.neel.projecte.myorders;

import java.util.List;


/**
 * Created by Meet on 16-10-2017.
 */

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.MyViewHolder> {

private List<order> moviesList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView year,time,date,day,month,amt,items,invoice,offer;
    public ImageView iv,qr;

    public MyViewHolder(View view) {
        super(view);

        time = (TextView) view.findViewById(R.id.time);
        year = (TextView) view.findViewById(R.id.year);
        month = (TextView) view.findViewById(R.id.month);
        day = (TextView) view.findViewById(R.id.day);
        amt = (TextView) view.findViewById(R.id.amt);
        date=(TextView)view.findViewById(R.id.date);
        qr=(ImageView)view.findViewById(R.id.qr);
        items= (TextView) view.findViewById(R.id.items);
        iv=(ImageView)view.findViewById(R.id.carticon);
        offer=(TextView)view.findViewById(R.id.offer);
        invoice=(TextView) view.findViewById(R.id.invoice);
    }
}


    public orderAdapter(List<order> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final order movie = moviesList.get(position);

        holder.items.setText(movie.item+" items");
        holder.year.setText(movie.year);
        holder.date.setText(movie.date);
        holder.day.setText(movie.day);
        holder.month.setText(movie.month);
        holder.amt.setText(movie.price+" \u20B9 /-");
        holder.time.setText(movie.time);
        holder.invoice.setText("#"+movie.invoice);

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myorders.setItemdata(movie.orders);
            }
        });

        holder.qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myorders.ShowQR(movie.key);
            }
        });

        if(movie.offer.equals("no"))
        {
            holder.offer.setText("Not available");
            holder.offer.setTextColor(Myapp.con.getResources().getColor(R.color.md_grey_500));
        }
        else
        {
            holder.offer.setText(movie.offer);
            holder.offer.setTextColor(Myapp.con.getResources().getColor(R.color.md_blue_500));

        }


    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
