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

public class DetailsSaleActivity extends AppCompatActivity  implements View.OnClickListener {
    EditText name_et1,color_et1,quant_et1,price_et1,supp_et1,phone_et1,date_et1,et_adress,notes;
    ImageView img1;
    String image1;
    Button ok1,cancel1,takephoto1,btndate1;
    int indice1;
    Uri imageUri;
    private Date date;
    TextView t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_sale);
        name_et1 = findViewById(R.id.etname1);
        color_et1 = findViewById(R.id.etcolor1);
        phone_et1 = findViewById(R.id.etphone1);
        quant_et1 = findViewById(R.id.etquant1);
        price_et1 = findViewById(R.id.etprice1);
        supp_et1 = findViewById(R.id.etclient);
        et_adress=findViewById(R.id.etadress1);
        notes=findViewById(R.id.etnotes1);
        img1=findViewById(R.id.imgsale1);
        t2=findViewById(R.id.etdate1);
        image1 ="";
        ok1 = findViewById(R.id.btnsave1);
        cancel1 = findViewById(R.id.btncancel1);
        takephoto1 = findViewById(R.id.btnphoto1);
        ok1.setOnClickListener(this);
        cancel1.setOnClickListener(this);
        takephoto1.setOnClickListener(this);
        btndate1=findViewById(R.id.selectdate);
        btndate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonDate_onClick(view);
            }
        });

        Bundle extras = getIntent().getExtras();
        indice1 = extras.getInt(SaleActivity.CLE1);

        if (indice1 >= 0) {//update
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            name_et1.setText(SaleActivity.listeSales.get(indice1).getIname());
            color_et1.setText(SaleActivity.listeSales.get(indice1).getItem_color());
            phone_et1.setText(String.valueOf(SaleActivity.listeSales.get(indice1).getClient_phone()));
            quant_et1.setText(String.valueOf(SaleActivity.listeSales.get(indice1).getSale_quantity()));
            price_et1.setText(String.valueOf(SaleActivity.listeSales.get(indice1).getSale_price()));
            supp_et1.setText(SaleActivity.listeSales.get(indice1).getClient_name());
            et_adress.setText(SaleActivity.listeSales.get(indice1).getAdresse());
            //t2.setText(simpleDateFormat.format(SaleActivity.listeSales.get(indice1).getDate_sale()));
            notes.setText(SaleActivity.listeSales.get(indice1).getNotes());
            if (SaleActivity.listeSales.get(indice1).getIphoto () != "" ) {
                String p=SaleActivity.listeSales.get(indice1).getIphoto();
                //img1.setImageResource(R.drawable.p);
            }
            else img1.setImageResource(R.drawable.img);
        }
    }
    static final int PICK_IMAGE = 100;
    @Override
    public void onClick(View v) {
        if (v.equals(ok1)) {
            if(TextUtils.isEmpty(name_et1.getText().toString()) || TextUtils.isEmpty(et_adress.getText().toString())||TextUtils.isEmpty(price_et1.getText().toString())||TextUtils.isEmpty(supp_et1.getText().toString())||TextUtils.isEmpty(quant_et1.getText().toString())||TextUtils.isEmpty(phone_et1.getText().toString())||TextUtils.isEmpty(color_et1.getText().toString()))
                Toast.makeText(DetailsSaleActivity.this, "All fields Required",Toast.LENGTH_SHORT).show();
            else {
                if (phone_et1.getText().toString().length() != 8) {
                    Toast.makeText(DetailsSaleActivity.this, "Phone should be 8 digits!",Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences pref = getApplicationContext().getSharedPreferences(SaleActivity.DONNEESPARTAGEES, MODE_PRIVATE); // 0 - for private mode
                    String titreNom= pref.getString ( SaleActivity.NOM,"Nom non initialisé" );
                    Sale sale= null;

                        sale = new Sale(name_et1.getText().toString(), Integer.valueOf(quant_et1.getText().toString()), Float.valueOf(price_et1.getText().toString()),date,color_et1.getText().toString(), supp_et1.getText().toString(),Integer.valueOf(phone_et1.getText().toString()),image1,  titreNom,et_adress.getText().toString(),notes.getText().toString());

                    if (indice1 >= 0) {
                        sale.setS_id(SaleActivity.listeSales.get(indice1).getS_id());
                        SaleActivity.listeSales.set(indice1, sale);
                    } else
                        SaleActivity.listeSales.add(sale);
                    Intent intent = new Intent();
                    setResult(3, intent);
                    finish();
                }
            }
        } else if (v.equals ( cancel1 )){
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
            //t2.setText(str_selectedDate);
        } catch(Exception e){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String currentDateandTime = sdf.format(new Date());
            date=sdf.parse(currentDateandTime);
            //t2.setText(currentDateandTime);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            img1.setImageURI(imageUri);
        }
    }
}