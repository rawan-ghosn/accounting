package com.example.accounting1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    CardView btpurchase, btsales,btprofit;
    String count,count1;
    DBHelper dbHelper;
    TextView outputpurch,outputsale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btpurchase=findViewById(R.id.purchase);
        btsales=findViewById(R.id.sales);
        btprofit=findViewById(R.id.money);
        outputpurch=findViewById(R.id.outputpurch);
        outputsale=findViewById(R.id.outputsales);
        dbHelper= new DBHelper(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(SaleActivity.DONNEESPARTAGEES, MODE_PRIVATE); // 0 - for private mode
        String titreNom= pref.getString ( SaleActivity.NOM,"Nom non initialisé" );

        btpurchase.setOnClickListener ( this );
        btsales.setOnClickListener (this);
        btprofit.setOnClickListener (this);
        count=dbHelper.countpurchases(titreNom);
        outputpurch.setText(count);
        count1=dbHelper.countsales(titreNom);
        outputsale.setText(count1);

    }

    @Override
    public void onClick(View v) {
        Intent ecr;
        if (v.equals(btpurchase)){
            ecr = new Intent(v.getContext (), PurchaseActivity.class);
           startActivity(ecr);
        } else if(v.equals(btsales)) {
            ecr = new Intent(v.getContext (), SaleActivity.class);
            startActivity ( ecr );
        }else if(v.equals(btprofit)) {
            ecr = new Intent(v.getContext(), ProfitActivity.class);
            startActivity(ecr);
        }
    }
}