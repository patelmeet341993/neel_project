package com.example.neel.projecte.adapter;

/**
 * Created by Meet on 16-10-2017.
 */

public class plan_model
{
    String name,dec,fees,capacity,eprice;

    public plan_model(String name, String fees, String dec, String capacity, String eprice)
    {
        this.name=name;
        this.dec=dec;
        this.eprice=eprice;
        this.capacity=capacity;
        this.fees=fees;
    }

}
