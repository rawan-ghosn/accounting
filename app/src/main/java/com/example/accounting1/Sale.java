package com.example.accounting1;

import android.graphics.Bitmap;

import java.util.Date;

public class Sale {
    private int s_id;
    private String iname;
    private String item_color;
    private int sale_quantity;
    private String client_name;
    private String iphoto;
    private int client_phone;
    private float sale_price;
    private String user_name;
    private Date date_sale;
    private String adresse;
    private String notes;

    public int getS_id() {
        return s_id;
    }
    public String getIname() {
        return iname;
    }
    public String getItem_color() {return item_color;}
    public int getSale_quantity() {return sale_quantity;}
    public int getClient_phone() {return client_phone;}
    public String getIphoto() { return iphoto;}
    public String getClient_name() {return client_name;}
    public float getSale_price() {return sale_price;}
    public String getUser_name() {return user_name;}
    public Date getDate_sale() {return date_sale;}
    public String getAdresse() {return adresse;}
    public String getNotes() {return notes;}


    public void setS_id(int id) {
        this.s_id = id;
    }
    public void setIname(String v) {
        this.iname = v;
    }
    public void setItem_color(String v) {
        this.item_color= v;
    }
    public void setClient_name(String v) {
        this.client_name =v;
    }
    public void setIphoto(String v) {
        this.iphoto =v;
    }
    public void setClient_phone(int v) {
        this.client_phone =v;
    }
    public void setSale_quantity(int v) {
        this.sale_quantity =v;
    }
    public void setSale_price(Float v) {
        this.sale_price =v;
    }
    public void setUser_name(String v) {
        this.user_name =v;
    }
    public void setDate_sale(Date v) {
        this.date_sale =v;
    }
    public void setAdresse(String v) {
        this.adresse =v;
    }
    public void setNotes(String v) {
        this.notes =v;
    }

    public Sale(int id, String i_name, int quant, float price, Date dates,String color,String Cl, int cl_phone,String iphoto,String adresse,String u, String n) {
        this.s_id = id;
        this.iname = i_name;
        this.sale_quantity = quant;
        this.sale_price=price;
        this.date_sale=dates;
        this.item_color=color;
        this.client_name=Cl;
        this.client_phone=cl_phone;
        this.iphoto=iphoto;
        this.adresse=adresse;
        this.user_name=u;
        this.notes=n;
    }

    public Sale(String name, int quant, float prix,Date dates, String color, String Cl, int phone, String image,String u,String a,String n) {
        this.iname = name;
        this.sale_quantity = quant;
        this.sale_price=prix;
        this.date_sale=dates;
        this.item_color=color;
        this.client_name=Cl;
        this.client_phone=phone;
        this.iphoto=image;
        this.adresse=a;
        this.notes=n;

    }
}
