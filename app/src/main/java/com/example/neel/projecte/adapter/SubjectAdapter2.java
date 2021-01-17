package com.example.neel.projecte.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.neel.projecte.Myapp;
import com.example.neel.projecte.R;
import com.example.neel.projecte.cart;

import java.util.List;
import java.util.Map;


/**
 * Created by Meet on 16-10-2017.
 */

public class SubjectAdapter2 extends RecyclerView.Adapter<SubjectAdapter2.MyViewHolder> {

private List<Subject2> moviesList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name,item,price,totalamt;
    public ImageView min,plus,book;

    public MyViewHolder(View view) {
        super(view);

        item=(TextView)view.findViewById(R.id.cars);
        name=(TextView)view.findViewById(R.id.time);
        min=(ImageView)view.findViewById(R.id.min);
        plus=(ImageView)view.findViewById(R.id.plus);
        book=(ImageView)view.findViewById(R.id.book);
        totalamt=(TextView) view.findViewById(R.id.booked);
        price=(TextView) view.findViewById(R.id.avail);

    }
}


    public SubjectAdapter2(List<Subject2> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usersubject2, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Subject2 movie = moviesList.get(position);
        holder.name.setText(movie.name);
        holder.item.setText(movie.item);
        holder.price.setText("Price "+movie.price+" \u20B9/-");

        int it= Integer.parseInt(movie.item);
        int pr= Integer.parseInt(movie.price);
        holder.totalamt.setText("Total "+(it*pr)+" \u20B9/-");

            holder.plus.setEnabled(true);
            holder.min.setEnabled(true);
            holder.min.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int it= Integer.parseInt(holder.item.getText().toString());
                    int pr= Integer.parseInt(movie.price);

                    if(it>1)
                    {
                        it--;
                        holder.totalamt.setText("Total "+(it*pr)+" \u20B9/-");
                        holder.item.setText(""+it);

                        Map<String,String> data=(Map<String, String>) cart.items.get(movie.key);
                        int i= Integer.parseInt(data.get("item"));
                        data.remove("item");
                        data.put("item",""+it);
                        cart.calculateAmount();



                    }
                    else
                    {
                        Myapp.showMsg("Minimum 1 item require");
                    }
                }
            });
            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int it= Integer.parseInt(holder.item.getText().toString());
                    int pr= Integer.parseInt(movie.price);

                    it++;
                    holder.totalamt.setText("Total "+(it*pr)+" \u20B9/-");
                    holder.item.setText(""+it);


                    Map<String,String> data=(Map<String, String>) cart.items.get(movie.key);
                    int i= Integer.parseInt(data.get("item"));
                    data.remove("item");
                    data.put("item",""+it);
                    cart.calculateAmount();

                }
            });

            holder.book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    cart.items.remove(movie.key);
                    cart.calculateAmount();
                    cart.setRecy();

                }
            });



}

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
