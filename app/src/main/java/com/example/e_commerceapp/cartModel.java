package com.example.e_commerceapp;

public class cartModel {
    private int cart_image;
    private int cart_price;
    private String cart_name;
    private int cart_qty;

    public cartModel(int cart_image, int cart_price, String cart_name, int cart_qty) {
        this.cart_image = cart_image;
        this.cart_price = cart_price;
        this.cart_name = cart_name;
        this.cart_qty = cart_qty;
    }

    public cartModel(String cart_name) {
        this.cart_name = cart_name;
    }

    public int getCart_image() {
        return cart_image;
    }

    public void setCart_image(int cart_image) {
        this.cart_image = cart_image;
    }

    public int getCart_price() {
        return cart_price;
    }

    public void setCart_price(int cart_price) {
        this.cart_price = cart_price;
    }

    public String getCart_name() {
        return cart_name;
    }

    public void setCart_name(String cart_name) {
        this.cart_name = cart_name;
    }

    public int getCart_qty() {
        return cart_qty;
    }

    public void setCart_qty(int cart_qty) {
        this.cart_qty = cart_qty;
    }

}
