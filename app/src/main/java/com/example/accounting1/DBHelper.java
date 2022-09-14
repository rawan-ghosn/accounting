package com.example.accounting1;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    double amount;
    //public static final String DBNAME= Environment.getExternalStorageDirectory()+ File.separator +"RG/app.db";
    public static final String DBNAME = "accountingdb";

    public DBHelper(Context context) {

        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(username TEXT primary key, password TEXT)");
        db.execSQL("create table if not exists purchases(itemid integer primary key autoincrement, item_name TEXT not null, color TEXT not null,quantity integer not null, user text not null,supplier text default '',supp_phone text default'', price double not null, dateP text default '', photo text default '')");
        db.execSQL("create table if not exists sales(id integer primary key autoincrement, item_name TEXT not null, color TEXT not null,quantity integer not null, client text default '',cl_phone default '',price double not null, user text, dates text default'', photo text default '',adresse text not null, notes text default '')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists users");
        db.execSQL("drop table if exists purchases");
        db.execSQL("drop table if exists sales");
    }

    public Boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        if (result == -1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;
        } else return false;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else return false;
    }
    public String getprofit(String s1,Date d1, Date d2)
    {   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        SQLiteDatabase database= getReadableDatabase();
        String sAmount;
        Cursor cursor=database.rawQuery("select sum(price) from purchases where user='"+s1+"' and DATE(dateP) >= ? and DATE(dateP)  <= ? ",new String[]{simpleDateFormat.format(d1),simpleDateFormat.format(d2)});

        if(cursor.moveToFirst()){

                sAmount = String.valueOf(cursor.getDouble(0));

        } else{
            sAmount="0";
        }
        cursor.close();
        database.close();
        return sAmount;
    }

    public String countpurchases(String s1)
    {
        SQLiteDatabase database= getReadableDatabase();
        String sAmount;
        Cursor cursor=database.rawQuery("select count(*) from purchases where user='"+s1+"'",null);

        if(cursor.moveToFirst()){

            sAmount = String.valueOf(cursor.getInt(0));

        } else{
            sAmount="0";
        }
        cursor.close();
        database.close();
        return sAmount;
    }
    public String countsales(String s1)
    {
        SQLiteDatabase database= getReadableDatabase();
        String sAmount;
        Cursor cursor=database.rawQuery("select count(*) from sales where user='"+s1+"'",null);

        if(cursor.moveToFirst()){

            sAmount = String.valueOf(cursor.getInt(0));

        } else{
            sAmount="0";
        }
        cursor.close();
        database.close();
        return sAmount;
    }


}