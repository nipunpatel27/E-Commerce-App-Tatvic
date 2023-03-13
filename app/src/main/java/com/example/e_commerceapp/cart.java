package com.example.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import java.util.ArrayList;
import java.util.List;

public class cart extends AppCompatActivity {
    private Button Checkout;

    private String itemName,truncated;
    int itemRating;
    int itemImg;
    protected TextView totals;
    cartModel cm;
    private cartAdapter ca;

    static ArrayList<cartModel> arrayList= new ArrayList<>();
private Bundle glo;
static Bundle checkoutCartParams;
    static List<Bundle> cartItems;
    static ArrayList <Bundle>cartItems_ga3 = new ArrayList<Bundle>();
    RecyclerView cart_recycler;
FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cart_recycler = findViewById(R.id.cart_recycler);
        Checkout = findViewById(R.id.checkout);
        totals = findViewById(R.id.totalamount);
        Intent i=getIntent();
        itemName = i.getStringExtra("ITEM_NAME");
        itemRating = i.getIntExtra("ITEM_RATING",0);
        itemImg = i.getIntExtra("ITEM_IMAGE",0);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        cartItems = new ArrayList<>();
        
        for (cartModel item : arrayList){
            switch (item.getCart_name()){
                case "Fresh Apple's":
                    glo = MainActivity.item_apple;
                    break;
                case "Amul Milk": glo = MainActivity.item_milk;
                    break;
                case "Rolex Watch": glo = MainActivity.item_watch;
                    break;
                case "Tatvic T-shirt": glo = MainActivity.item_cloth;
                    break;
                case "Tommy Jeans": glo = MainActivity.item_cloth1;
                    break;
                case "iPhone 14 Pro Max": glo = MainActivity.item_mobile;
                    break;
                case "Nike Jordan": glo = MainActivity.item_shoe;
                    break;
                case "Lunch Box": glo = MainActivity.item_food3;
                    break;
                case "White Bread": glo = MainActivity.item_food1;
                    break;
                case "Balaji Snack": glo = MainActivity.item_food3;
                    break;
                default: glo = new Bundle();
                    break;
            }
            Bundle view_cart = new Bundle(glo);
            view_cart.putInt(FirebaseAnalytics.Param.QUANTITY, item.getCart_qty());
            cartItems.add(view_cart);
        }
        Bundle viewCartParams = new Bundle();
        viewCartParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                cartItems.toArray(new Parcelable[cartItems.size()]));
        mFirebaseAnalytics.logEvent(Event.VIEW_CART,viewCartParams);


        if(itemRating==0){
            ca = new cartAdapter(this,arrayList);
            cart_recycler.setAdapter(ca);
            cart_recycler.setLayoutManager(new LinearLayoutManager(this));
            totals.setText("Grand Total: $ "+ca.getTotalAmount());
        }
        else {
            cm = new cartModel(itemImg, itemRating, itemName, 1);
            cart.arrayList.add(cm);
            ca = new cartAdapter(this, arrayList);
            cart_recycler.setAdapter(ca);
            cart_recycler.setLayoutManager(new LinearLayoutManager(this));
            totals.setText("Grand Total: $ "+ca.getTotalAmount());
        }

        Checkout.setOnClickListener(view ->  {
            if(!totals.getText().toString().contains("Grand Total: $ 0.0")){
            Bundle bun = new Bundle();
            Intent check = new Intent(this,Address_and_Payment.class);
            truncated =totals.getText().toString().substring(15);
            bun.putString("ITEM_TOTAL",truncated);
            check.putExtras(bun);
            startActivity(check);

                for (cartModel item : arrayList){
                    switch (item.getCart_name()){
                        case "Fresh Apple's": glo = MainActivity.item_apple;
                            break;
                        case "Amul Milk": glo = MainActivity.item_milk;
                            break;
                        case "Rolex Watch": glo = MainActivity.item_watch;
                            break;
                        case "Tatvic T-shirt": glo = MainActivity.item_cloth;
                            break;
                        case "Tommy Jeans": glo = MainActivity.item_cloth1;
                            break;
                        case "iPhone 14 Pro Max": glo = MainActivity.item_mobile;
                            break;
                        case "Nike Jordan": glo = MainActivity.item_shoe;
                            break;
                        case "Lunch Box": glo = MainActivity.item_food3;
                            break;
                        case "White Bread": glo = MainActivity.item_food1;
                            break;
                        case "Balaji Snack": glo = MainActivity.item_food3;
                            break;
                        default: glo = new Bundle();
                            break;
                    }
                    Bundle checkout = new Bundle(glo);
//                    GA3
                    checkout.putInt(Param.QUANTITY, item.getCart_qty());
                    cartItems_ga3.add(checkout);
                    cartItems.add(checkout);
                }

                checkoutCartParams = new Bundle();
                checkoutCartParams.putString(FirebaseAnalytics.Param.CURRENCY, "USD");
                checkoutCartParams.putDouble(FirebaseAnalytics.Param.VALUE, Double.parseDouble(truncated));
                checkoutCartParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                        cartItems.toArray(new Parcelable[cartItems.size()]));
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT,checkoutCartParams);

                Bundle ecomme = new Bundle();
                ecomme.putString("GA3","true");
                ecomme.putParcelableArrayList("items",cartItems_ga3);
                mFirebaseAnalytics.logEvent( Event.BEGIN_CHECKOUT, ecomme );
            }else{
                Toast.makeText(this, "Add products in cart to checkout", Toast.LENGTH_LONG).show();
            }
        });
    }

}