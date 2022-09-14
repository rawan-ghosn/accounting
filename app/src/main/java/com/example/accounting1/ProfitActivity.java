package com.example.accounting1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfitActivity extends AppCompatActivity {
    TextView output, d1, d2;
    Button bt1, bt2,btnok;
    Double result;
    DBHelper dbHelper;
    private Date date,date1,date2,date3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit);
        output = findViewById(R.id.output);
        d1 = findViewById(R.id.d1);
        d2 = findViewById(R.id.d2);
        bt1 = findViewById(R.id.btnd1);
        bt2 = findViewById(R.id.btnd2);
        btnok=findViewById(R.id.btnok);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonDate_onClick1(view);
                }
        });
         bt2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 buttonDate_onClick2(view); }
         });
         btnok.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(TextUtils.isEmpty(d1.getText().toString()) || TextUtils.isEmpty(d2.getText().toString()))
                     Toast.makeText(ProfitActivity.this, "All fields Required",Toast.LENGTH_SHORT).show();
                 else {
                     SharedPreferences pref = getApplicationContext().getSharedPreferences(SaleActivity.DONNEESPARTAGEES, MODE_PRIVATE); // 0 - for private mode
                     String titreNom= pref.getString ( SaleActivity.NOM,"Nom non initialisé" );
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                     try {
                         date2=sdf.parse(d1.getText().toString());
                         date3=sdf.parse(d2.getText().toString());
                     } catch (ParseException e) {
                         e.printStackTrace();
                     }
                     String output1 = dbHelper.getprofit(titreNom,date,date1);
                     output.setText(output1);
                 }
                 }
             });
    }

    public void buttonDate_onClick1(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.datepicker_layout);
        AlertDialog alertDialog = builder.show();
        CalendarView calendarView = alertDialog.findViewById(R.id.calendarViewDate);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                try {
                    calenderview_onSelectedDayChange1(calendarView, i, i1, i2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        Button buttonSelect = alertDialog.findViewById(R.id.btnchoose);
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    private void calenderview_onSelectedDayChange1(@NonNull CalendarView calendarView, int i, int i1, int i2) throws ParseException {
        try {
            String str_selectedDate= String.format("%04d.%02d.%02d", i, i1 + 1, i2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            date = sdf.parse(str_selectedDate);
            d1.setText(sdf.format(date));
        } catch(Exception e){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String currentDateandTime = sdf.format(new Date());
            d1.setText(currentDateandTime);
            date=sdf.parse(currentDateandTime);
            }
    }
    public void buttonDate_onClick2(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.datepicker_layout);
        AlertDialog alertDialog = builder.show();
        CalendarView calendarView = alertDialog.findViewById(R.id.calendarViewDate);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                try {
                    calenderview_onSelectedDayChange2(calendarView, i, i1, i2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        Button buttonSelect = alertDialog.findViewById(R.id.btnchoose);
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    private void calenderview_onSelectedDayChange2(@NonNull CalendarView calendarView, int i, int i1, int i2) throws ParseException {
        try {
            String str_selectedDate= String.format("%04d.%02d.%02d", i, i1 + 1, i2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            date1 = sdf.parse(str_selectedDate);
            d2.setText(sdf.format(date1));
        } catch(Exception e){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String currentDateandTime = sdf.format(new Date());
            d2.setText(currentDateandTime);
           date1=sdf.parse(currentDateandTime);
        }
    }
}