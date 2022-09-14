package com.example.accounting1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SaleAdapter extends ArrayAdapter<Sale> {
public SaleAdapter(Context context, ArrayList<Sale> items) {
        super(context, 0, items);
        }

@Override
public View getView(int position, View view, ViewGroup parent) {
        Sale p = getItem(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        if (view == null) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.activity_sale_adapter, parent, false);
        }
        TextView tvnom = (TextView) view.findViewById(R.id.txtsale);

        tvnom.setText("Sale "+ simpleDateFormat.format(p.getDate_sale()));
        //tvnom.setText("Sale ");

        return view;
        }
}