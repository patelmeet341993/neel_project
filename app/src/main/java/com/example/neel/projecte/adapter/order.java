package com.example.neel.projecte.adapter;

import java.util.Map;

/**
 * Created by Meet on 16-10-2017.
 */

public class order
{
    String item,time,year,month,date,day,price,invoice,offer;
    Map<String,Object> orders;
    Map<String,Object> data;
    String key;

    public order(Map<String,Object> data, String key)
    {
        this.data=data;
        this.key=key;
        orders=(Map<String, Object>)data.get("order");

        item=""+data.get("items");
        time=""+data.get("time");
        year=""+data.get("year");
        price=""+data.get("amount");
        month=""+data.get("month");
        date=""+data.get("date");
        day=""+data.get("day");

        invoice=""+data.get("invoice");

        if(data.containsKey("offer"))
        {
            if(Integer.parseInt(data.get("offer")+"")>0)
            {
                offer=data.get("offer")+" % Discount";

            }
            else
            {
                offer="no";
            }
        }
        else {
            offer="no";
        }

    }

}
