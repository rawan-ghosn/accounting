package com.example.accounting1;

import android.graphics.Bitmap;

import java.util.Date;

public class Purchase{
        private int p_id;
        private String item_name;
        private String i_color;
        private int p_quantity;
        private String supp_name;
        private  String photo;
        private int supp_phone;
        private double p_price;
        private String u_name;
        private Date date_purchase;

    public Purchase(int id, String i_name, int quant, double price, Date dates, String icolor, String s, int p, String i_photo, String u) {
        this.p_id=id;
        this.item_name=i_name;
        this.p_quantity=quant;
        this.p_price=price;
        this.date_purchase=dates;
        this.i_color=icolor;
        this.supp_name=s;
        this.supp_phone=p;
        this.photo=i_photo;
        this.u_name=u;
    }

    public Purchase(String i_name, int quant, double price,  Date dates, String icolor, String s, int p, String i_photo, String u) {
        this.item_name=i_name;
        this.p_quantity=quant;
        this.p_price=price;
        this.date_purchase=dates;
        this.i_color=icolor;
        this.supp_name=s;
        this.supp_phone=p;
        this.photo=i_photo;
        this.u_name=u;
    }

    public int getP_id() {
            return p_id;
        }
        public String getItem_name() {
            return item_name;
        }
        public String getI_color() {return i_color;}
        public String getSupp_name() {return supp_name;}
        public int getSupp_phone() {return supp_phone;}
        public int getP_quantity() { return p_quantity;}
        public String getPhoto() {return photo;}
        public double getP_price() {return p_price;}
        public String getU_name() {return u_name;}
        public Date getDate_purchase() {return date_purchase;}


        public void setP_id(int id) {
            this.p_id = id;
        }
        public void setItem_name(String v) {
            this.item_name = v;
        }
        public void setI_color(String v) {
            this.i_color= v;
        }
        public void setSupp_name(String v) {
            this.supp_name =v;
        }
        public void setPhoto(String v) {
            this.photo =v;
        }
        public void setSupp_phone(int v) {
            this.supp_phone =v;
        }
        public void setP_quantity(int v) {
            this.p_quantity =v;
        }
        public void setP_price(Double v) {
            this.p_price =v;
        }
        public void setU_name(String v) {
            this.u_name =v;
        }
        public void setDate_purchase(Date v) {
            this.date_purchase =v;
        }
}
