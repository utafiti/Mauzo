package com.example.mauzo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import android.widget.ImageView;

public class CustomAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Item> data;//modify here

    public CustomAdapter(Context mContext, ArrayList<Item> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();// # of items in your arraylist
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);// get the actual item
    }
    @Override
    public long getItemId(int id) {
        return id;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_layout, parent,false);//modify here
            viewHolder = new ViewHolder();
            //modify this block of code
            viewHolder.tvdate = (TextView) convertView.findViewById(R.id.tvDate);
            viewHolder.tvsales = (TextView) convertView.findViewById(R.id.tvSales);
            viewHolder.tvexpenses = (TextView) convertView.findViewById(R.id.tvExpenses);
            viewHolder.tvmargin = (TextView) convertView.findViewById(R.id.tvMargin);

            //Up to here
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //MODIFY THIS BLOCK OF CODE
        Item person = data.get(position);//modify here
        viewHolder.tvdate.setText(person.getDate());//modify here
        viewHolder.tvsales.setText(person.getSales());//modify here
        viewHolder.tvexpenses.setText(person.getExpenses());//modify here
        viewHolder.tvmargin.setText(person.getMargin());//modify here
        return convertView;

    }
    static class ViewHolder {
        TextView tvdate;
        TextView tvsales;
        TextView tvexpenses;
        TextView tvmargin;

    }

}