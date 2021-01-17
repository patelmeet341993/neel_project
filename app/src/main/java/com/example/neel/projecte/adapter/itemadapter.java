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

public class itemadapter extends RecyclerView.Adapter<itemadapter.MyViewHolder> {

private List<itemmodel> moviesList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name,price,totalamt;


    public MyViewHolder(View view) {
        super(view);

        name=(TextView)view.findViewById(R.id.time);
         totalamt=(TextView) view.findViewById(R.id.booked);
        price=(TextView) view.findViewById(R.id.avail);

    }
}


    public itemadapter(List<itemmodel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ord_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final itemmodel movie = moviesList.get(position);

        holder.price.setText("Price "+movie.price+" \u20B9/-");

        int it= Integer.parseInt(movie.item);
        holder.name.setText(movie.name+" ( "+it+" qty )");
        int pr= Integer.parseInt(movie.price);
        holder.totalamt.setText("Total "+(it*pr)+" \u20B9/-");

}

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
