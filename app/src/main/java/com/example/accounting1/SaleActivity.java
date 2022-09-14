package com.example.accounting1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SaleActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    static String DONNEESPARTAGEES = "DonneesApplication";
    static String NOM = "username";
    ListView listesale = null;
    static ArrayList<Sale> listeSales;
    SaleAdapter adapter = null;

    FloatingActionButton add;

    static String CLE1 = "indice";
    int indice1=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        listeSales = new ArrayList<Sale>();

        listesale = findViewById(R.id.salelv);

        add = findViewById(R.id.addsale);

        LireListe1();
        adapter = new SaleAdapter(this, listeSales);
        listesale.setAdapter(adapter);
        listesale.setOnItemClickListener(this);//update
        listesale.setOnItemLongClickListener(this);//delete
        add.setOnClickListener(this);//insert
    }
    @Override
    public void onClick(View view) {
        indice1=-1;
        LancerDetail1();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        indice1=position;
        LancerDetail1();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String titre = "Confirm delete ?";
        String yes = "Yes";
        String no = "No";
        final int pos = position;
        builder
                .setTitle(titre)
                .setMessage("")
                .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Delete1 ( pos );//delete from Db
                        listeSales.remove(pos);//remove from listView
                        adapter.notifyDataSetChanged();//Adapter
                    }
                })
                .setNegativeButton(no, null)
                .show();
        return true;
    }

    private void Delete1(int ind) {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String st = String.valueOf(SaleActivity.listeSales.get(ind).getS_id());
        db.delete("sales", "id=?", new String[] {st});
        helper.close();
    }

    private void LireListe1() {
        listeSales= new ArrayList<Sale>();
        //1
        DBHelper helper = new DBHelper(this);
        //2
        SQLiteDatabase db = helper.getReadableDatabase();//Select
        Boolean retour = false;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            SharedPreferences pref = getApplicationContext().getSharedPreferences(SaleActivity.DONNEESPARTAGEES, MODE_PRIVATE); // 0 - for private mode
            String s1= pref.getString ( SaleActivity.NOM,"" );
            //3
            String st = "SELECT * FROM sales where user like '"+s1+"'";
            //String st = "SELECT * FROM sales";
            //4
            Cursor cursor = db.rawQuery(st, null);
            if (cursor.getCount() > 0)//si il y a encore de lignes?
                while (cursor.moveToNext()) { //
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String i_name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
                    int quant = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                    float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                    String i_photo = cursor.getString(cursor.getColumnIndexOrThrow("photo"));
                    String dates = cursor.getString(cursor.getColumnIndexOrThrow("dates"));
                    String icolor = cursor.getString(cursor.getColumnIndexOrThrow("color"));
                    String s = cursor.getString(cursor.getColumnIndexOrThrow("client"));
                    int p= cursor.getInt(cursor.getColumnIndexOrThrow("cl_phone"));
                    String n= cursor.getString(cursor.getColumnIndexOrThrow("notes"));
                    String u= s1;
                    String a = cursor.getString(cursor.getColumnIndexOrThrow("adresse"));
                    //;[-Date date= new Date();
                    try {
                        listeSales.add(new Sale(id, i_name, quant, price,simpleDateFormat.parse(dates),icolor,s,p,i_photo,a,u,n));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    helper.close();
                }
        } catch (Error e ) {
            Message("DB EMPTY", "There are no sales");
        }
    }
    private void Message(String titre, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(titre);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void LancerDetail1() {
        Intent ecr = new Intent(this, DetailsSaleActivity.class);
        ecr.putExtra(CLE1,indice1);
        int retour=0;
        startActivityForResult(ecr,retour);
    }

    private void Insert1(int ind) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        SharedPreferences pref = getApplicationContext().getSharedPreferences(SaleActivity.DONNEESPARTAGEES, MODE_PRIVATE); // 0 - for private mode
        String s1= pref.getString ( SaleActivity.NOM,"" );
        //1
        DBHelper helper = new DBHelper(this);
        //2
        SQLiteDatabase db = helper.getWritableDatabase();
        //3
        ContentValues value = new ContentValues();
        //          Db
        value.put("item_name",listeSales.get(ind).getIname());
        value.put("color",listeSales.get(ind).getItem_color());
        value.put("quantity",listeSales.get(ind).getSale_quantity());
        value.put("price",listeSales.get(ind).getSale_price());
        value.put("client",listeSales.get(ind).getClient_name());
        value.put("cl_phone",listeSales.get(ind).getClient_phone());
        try {
            value.put("dates", simpleDateFormat.format(listeSales.get(ind).getDate_sale()));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        value.put("user",s1);
        value.put("photo",listeSales.get(ind).getIphoto());
        value.put("adresse",listeSales.get(ind).getAdresse());
        value.put("notes",listeSales.get(ind).getNotes());
        long nouveau = db.insert("sales", null,value);
        listeSales.get(ind).setS_id((int)nouveau);
        helper.close();
    }
    private void Update1(int ind) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String id = String.valueOf(listeSales.get(ind).getS_id());

        ContentValues value = new ContentValues();

        value.put("item_name",listeSales.get(ind).getIname());
        value.put("color",listeSales.get(ind).getItem_color());
        value.put("quantity",listeSales.get(ind).getSale_quantity());
        value.put("price",listeSales.get(ind).getSale_price());
        value.put("client",listeSales.get(ind).getClient_name());
        value.put("cl_phone",listeSales.get(ind).getClient_phone());
        value.put("dates",simpleDateFormat.format(listeSales.get(ind).getDate_sale()));
        value.put("adresse",listeSales.get(ind).getAdresse());
        value.put("notes",listeSales.get(ind).getNotes());

        db.update("sales", value, "id = ?", new String[] {id});
        helper.close();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==3)
        {
            adapter.notifyDataSetChanged();//refresh listView
            if (indice1==-1) {
                Insert1(listeSales.size ()-1);//indice = dernier indice de listView
            } else {
                Update1 ( indice1 );
            }
        }
    }
}