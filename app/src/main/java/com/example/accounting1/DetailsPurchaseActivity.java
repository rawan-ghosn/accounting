package com.example.accounting1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class DetailsPurchaseActivity extends AppCompatActivity  implements View.OnClickListener {
    EditText name_et,color_et,quant_et,price_et,supp_et,phone_et,date_et;
    ImageView img;
    String image;
    Button ok,cancel,takephoto,btndate;
    int indice;
    Uri imageUri;
    private Date date;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_purchase);
        name_et = findViewById(R.id.etname);
        color_et = findViewById(R.id.etcolor);
        phone_et = findViewById(R.id.etphone);
        quant_et = findViewById(R.id.etquant);
        price_et = findViewById(R.id.etprice);
        supp_et = findViewById(R.id.etsupp);
        img = findViewById(R.id.imgpurchase1);
        t1=findViewById(R.id.etdate);
        image = "";
        ok = findViewById(R.id.btnsave);
        cancel = findViewById(R.id.btncancel);
        takephoto = findViewById(R.id.btnphoto);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        takephoto.setOnClickListener(this);
        btndate = findViewById(R.id.selectdate1);
        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonDate_onClick(view);
            }
        });
        Bundle extras = getIntent().getExtras();
        indice = extras.getInt(PurchaseActivity.CLE);

        if (indice >= 0) {//update
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            name_et.setText(PurchaseActivity.listePurchases.get(indice).getItem_name());
            color_et.setText(PurchaseActivity.listePurchases.get(indice).getI_color());
            phone_et.setText(String.valueOf(PurchaseActivity.listePurchases.get(indice).getSupp_phone()));
            quant_et.setText(String.valueOf(PurchaseActivity.listePurchases.get(indice).getP_quantity()));
            price_et.setText(String.valueOf(PurchaseActivity.listePurchases.get(indice).getP_price()));
            supp_et.setText(PurchaseActivity.listePurchases.get(indice).getSupp_name());
            //t1.setText(simpleDateFormat.format(PurchaseActivity.listePurchases.get(indice).getDate_purchase()));
            if (PurchaseActivity.listePurchases.get(indice).getPhoto() != "" ) {
                String p=PurchaseActivity.listePurchases.get(indice).getPhoto();
                //img1.setImageResource(R.drawable.p);
            }
            else img.setImageResource(R.drawable.img);
        }
    }
    static final int PICK_IMAGE = 100;
    @Override
    public void onClick(View v) {
        if (v.equals(ok)) {
            if(TextUtils.isEmpty(name_et.getText().toString()) || TextUtils.isEmpty(price_et.getText().toString())||TextUtils.isEmpty(supp_et.getText().toString())||TextUtils.isEmpty(quant_et.getText().toString())||TextUtils.isEmpty(phone_et.getText().toString())||TextUtils.isEmpty(color_et.getText().toString()))
                Toast.makeText(DetailsPurchaseActivity.this, "All fields Required",Toast.LENGTH_SHORT).show();
            else {
                if (phone_et.getText().toString().length() != 8) {
                    Toast.makeText(DetailsPurchaseActivity.this, "Phone should be 8 digits!",Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences pref = getApplicationContext().getSharedPreferences(SaleActivity.DONNEESPARTAGEES, MODE_PRIVATE); // 0 - for private mode
                    String titreNom= pref.getString ( SaleActivity.NOM,"Nom non initialisé" );
                    Purchase purchase= null;

                    purchase = new Purchase(name_et.getText().toString(), Integer.valueOf(quant_et.getText().toString()), Float.valueOf(price_et.getText().toString()),date,color_et.getText().toString(), supp_et.getText().toString(),Integer.valueOf(phone_et.getText().toString()),image,  titreNom);

                    if (indice >= 0) {
                        purchase.setP_id(PurchaseActivity.listePurchases.get(indice).getP_id());
                        PurchaseActivity.listePurchases.set(indice, purchase);
                    } else
                        PurchaseActivity.listePurchases.add(purchase);
                    Intent intent = new Intent();
                    setResult(3, intent);
                    finish();
                }
            }
        } else if (v.equals ( cancel )){
            finish();
        } else {
            openGallery();
        }
    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    public void buttonDate_onClick(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(R.layout.datepicker_layout);
        AlertDialog alertDialog= builder.show();
        CalendarView calendarView=alertDialog.findViewById(R.id.calendarViewDate);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                try {
                    calenderview_onSelectedDayChange(calendarView,i,i1,i2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        Button buttonSelect=alertDialog.findViewById(R.id.btnchoose);
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    private void calenderview_onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) throws ParseException {
        try {
            String str_selectedDate= String.format("%04d.%02d.%02d", i, i1 + 1, i2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            date = sdf.parse(str_selectedDate);
            //t1.setText(str_selectedDate);
        } catch(Exception e){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String currentDateandTime = sdf.format(new Date());
            //t1.setText(currentDateandTime);
            date=sdf.parse(currentDateandTime);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            img.setImageURI(imageUri);
        }
    }
}