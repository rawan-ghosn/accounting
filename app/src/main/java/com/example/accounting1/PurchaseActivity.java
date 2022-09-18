package com.example.accounting1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PurchaseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    ListView listepurchase = null;
    static ArrayList<Purchase> listePurchases;
    PurchaseAdapter adapter = null;

    FloatingActionButton add1;

    static String CLE = "indice";
    int indice = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        listePurchases = new ArrayList<Purchase>();

        listepurchase = findViewById(R.id.purchaselv);

        add1 = findViewById(R.id.addpurchase);

        LireListe();
        adapter = new PurchaseAdapter(this, listePurchases);
        listepurchase.setAdapter(adapter);
        listepurchase.setOnItemClickListener(this);//update
        listepurchase.setOnItemLongClickListener(this);//delete
        add1.setOnClickListener(this);//insert
    }

    @Override
    public void onClick(View view) {
        indice=-1;
        LancerDetail();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        indice=position;
        LancerDetail();
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
                        Delete ( pos );//delete from Db
                        listePurchases.remove(pos);//remove from listView
                        adapter.notifyDataSetChanged();//Adapter
                    }
                })
                .setNegativeButton(no, null)
                .show();
        return true;
    }

    private void Delete(int ind) {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String st = String.valueOf(PurchaseActivity.listePurchases.get(ind).getP_id());
        db.delete("purchases", "itemid=?", new String[] {st});
        helper.close();
    }

    private void LireListe() {
        listePurchases= new ArrayList<Purchase>();
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
            String st = "SELECT * FROM purchases where user like '"+s1+"'";
            //String st = "SELECT * FROM sales";
            //4
            Cursor cursor = db.rawQuery(st, null);
            if (cursor.getCount() > 0)//si il y a encore de lignes?
                while (cursor.moveToNext()) { //
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("itemid"));
                    String i_name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"));
                    int quant = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                    float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                    String i_photo = cursor.getString(cursor.getColumnIndexOrThrow("photo"));
                    String dates = cursor.getString(cursor.getColumnIndexOrThrow("dateP"));
                    String icolor = cursor.getString(cursor.getColumnIndexOrThrow("color"));
                    String s = cursor.getString(cursor.getColumnIndexOrThrow("supplier"));
                    int p= cursor.getInt(cursor.getColumnIndexOrThrow("supp_phone"));
                    String u= s1;
                    //Date date= new Date();
                    try {
                        listePurchases.add(new Purchase(id, i_name, quant, price,simpleDateFormat.parse(dates),icolor,s,p,i_photo,u));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    helper.close();
                }
        } catch (Error e ) {
            Message("DB EMPTY", "There are no purchases");
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

    private void LancerDetail() {
        Intent ecr = new Intent(this, DetailsPurchaseActivity.class);
        ecr.putExtra(CLE,indice);
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
        value.put("item_name",listePurchases.get(ind).getItem_name());
        value.put("color",listePurchases.get(ind).getI_color());
        value.put("quantity",listePurchases.get(ind).getP_quantity());
        value.put("price",listePurchases.get(ind).getP_price());
        value.put("supplier",listePurchases.get(ind).getSupp_name());
        value.put("supp_phone",listePurchases.get(ind).getSupp_phone());
        try {
            value.put("dateP", simpleDateFormat.format(listePurchases.get(ind).getDate_purchase()));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        value.put("user",s1);
        value.put("photo",listePurchases.get(ind).getPhoto());
        long nouveau = db.insert("purchases", null,value);
        listePurchases.get(ind).setP_id((int)nouveau);
        helper.close();
    }
    private void Update(int ind) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String id = String.valueOf(listePurchases.get(ind).getP_id());

        ContentValues value = new ContentValues();

        value.put("item_name",listePurchases.get(ind).getItem_name());
        value.put("color",listePurchases.get(ind).getI_color());
        value.put("quantity",listePurchases.get(ind).getP_quantity());
        value.put("price",listePurchases.get(ind).getP_price());
        value.put("supplier",listePurchases.get(ind).getSupp_name());
        value.put("supp_phone",listePurchases.get(ind).getSupp_phone());
        value.put("dateP",simpleDateFormat.format(listePurchases.get(ind).getDate_purchase()));

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
            if (indice==-1) {
                Insert1(listePurchases.size ()-1);//indice = dernier indice de listView
            } else {
                Update ( indice );
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1:
                Intent ecr = new Intent(PurchaseActivity.this, LoginActivity.class);
                startActivity ( ecr );
                return true;
            case R.id.item2:
                return true;
            case R.id.item3:
                return true;
            case R.id.item4:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}