package com.example.neel.projecte.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.neel.projecte.R;

import java.util.List;


/**
 * Created by Meet on 16-10-2017.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyViewHolder> {

private List<plan_model> moviesList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title, fees,capacity,dec,eprice;


    public MyViewHolder(View view) {
        super(view);

        title = (TextView) view.findViewById(R.id.name);
        fees = (TextView) view.findViewById(R.id.price);
        capacity = (TextView) view.findViewById(R.id.number);
        dec = (TextView) view.findViewById(R.id.address);
        eprice = (TextView) view.findViewById(R.id.eprice);

    }
}


    public PlanAdapter(List<plan_model> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plan_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final plan_model movie = moviesList.get(position);
    holder.capacity.setText("Discount "+movie.capacity+" %");
    holder.fees.setText(movie.fees+" \u20B9 /-");
    holder.dec.setText(movie.dec);
    holder.title.setText(movie.name);
    holder.eprice.setText(movie.eprice+" \u20B9 /-");

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
