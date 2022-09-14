package com.example.accounting1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PurchaseAdapter extends ArrayAdapter<Purchase> {
    public PurchaseAdapter(Context context, ArrayList<Purchase> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Purchase p = getItem(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_purchase_adapter, parent, false);
        }
        TextView tvnom1 = (TextView) view.findViewById(R.id.txtpurchase);

        tvnom1.setText("Purchase "+ simpleDateFormat.format(p.getDate_purchase()));

        return view;
    }
}