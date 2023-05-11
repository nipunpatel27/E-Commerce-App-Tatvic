package com.example.e_commerceapp;

import static com.example.e_commerceapp.cart.arrayList;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Event;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.ViewHolder> {

    private final Context context;
    static ArrayList<cartModel> cartModelArrayList;
    Bundle global1;
    FirebaseAnalytics mFirebaseAnalytics;
    // Constructor
    public cartAdapter(Context context, ArrayList<cartModel> cartModelArrayList) {
        this.context = context;
        this.cartModelArrayList = cartModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_to_cart_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cartModel model = cartModelArrayList.get(position);
        holder.cartNameTV.setText(model.getCart_name());
        holder.cartRatingTV.setText(model.getCart_price()+".00");
        holder.cartIV.setImageResource(model.getCart_image());
        holder.cartQtyTV.setText(""+model.getCart_qty());

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context.getApplicationContext());

        switch (model.getCart_name()){
            case "Fresh Apple's": global1 = MainActivity.item_apple;
                break;
            case "Amul Milk": global1 = MainActivity.item_milk;
                break;
            case "Rolex Watch": global1 = MainActivity.item_watch;
                break;
            case "Tatvic T-shirt": global1 = MainActivity.item_cloth;
                break;
            case "Tommy Jeans": global1 = MainActivity.item_cloth1;
                break;
            case "iPhone 14 Pro Max": global1 = MainActivity.item_mobile;
                break;
            case "Nike Jordan": global1 = MainActivity.item_shoe;
                break;
            case "Lunch Box": global1 = MainActivity.item_food3;
                break;
            case "White Bread": global1 = MainActivity.item_food1;
                break;
            case "Balaji Snack": global1 = MainActivity.item_food3;
                break;
            default: global1 = new Bundle();
                break;
        }
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (model.getCart_name()){
                    case "Fresh Apple's": global1 = MainActivity.item_apple;
                        break;
                    case "Amul Milk": global1 = MainActivity.item_milk;
                        break;
                    case "Rolex Watch": global1 = MainActivity.item_watch;
                        break;
                    case "Tatvic T-shirt": global1 = MainActivity.item_cloth;
                        break;
                    case "Tommy Jeans": global1 = MainActivity.item_cloth1;
                        break;
                    case "iPhone 14 Pro Max": global1 = MainActivity.item_mobile;
                        break;
                    case "Nike Jordan": global1 = MainActivity.item_shoe;
                        break;
                    case "Lunch Box": global1 = MainActivity.item_food3;
                        break;
                    case "White Bread": global1 = MainActivity.item_food1;
                        break;
                    case "Balaji Snack": global1 = MainActivity.item_food3;
                        break;
                    default: global1 = new Bundle();
                        break;
                }
                Bundle removeCartParams = new Bundle();
                removeCartParams.putString("custom_app_id",MainActivity.appid);
                removeCartParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                        new Parcelable[]{global1});
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.REMOVE_FROM_CART, removeCartParams);

//**************************************Remove from cart GA3****************************************************

                Bundle removeCartParams_GA3 = new Bundle();
                removeCartParams_GA3.putString("custom_app_id",MainActivity.appid);
                removeCartParams_GA3.putString("GA3","true");
                removeCartParams_GA3.putBundle("items",global1);
                mFirebaseAnalytics.logEvent(Event.REMOVE_FROM_CART, removeCartParams_GA3);

                arrayList.remove(model);
                notifyDataSetChanged();
                ((cart) context).totals.setText("Grand Total: $ " + getTotalAmount());
            }
        });

        holder.qtyadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = model.getCart_qty();
                model.setCart_qty(qty+1);
                notifyDataSetChanged();
                ((cart) context).totals.setText("Grand Total: $ " + getTotalAmount());
                Bundle itemaddcart = new Bundle(global1);
                itemaddcart.putString("custom_app_id",MainActivity.appid);
                itemaddcart.putLong(FirebaseAnalytics.Param.QUANTITY, 1);
                mFirebaseAnalytics.logEvent("PRODUCT_QTY_PLUS", itemaddcart);
            }
        });

        holder.qtydel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = model.getCart_qty();
                if(qty>1){
                model.setCart_qty(qty-1);
                notifyDataSetChanged();
                ((cart) context).totals.setText("Grand Total: $ " + getTotalAmount());
                Bundle itemdelcart = new Bundle(global1);
                itemdelcart.putString("custom_app_id",MainActivity.appid);
                    itemdelcart.putLong(FirebaseAnalytics.Param.QUANTITY, 1);
                    mFirebaseAnalytics.logEvent("PRODUCT_QTY_MINUS", itemdelcart);
            }
            }
        });
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return cartModelArrayList.size();
    }

    public double getTotalAmount() {
        double total = 0;
        for (cartModel model : cartModelArrayList) {
            total += model.getCart_qty() * model.getCart_price();
        }
        return total;
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView cartIV;
        private final TextView cartNameTV;
        private final TextView cartRatingTV;
        private final TextView cartQtyTV;
        ImageButton btnDelete,qtyadd,qtydel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartIV = itemView.findViewById(R.id.cart_p_image);
            cartNameTV = itemView.findViewById(R.id.cart_p_name);
            cartRatingTV = itemView.findViewById(R.id.cart_p_price);
            cartQtyTV = itemView.findViewById(R.id.cart_p_qty);
            btnDelete = itemView.findViewById(R.id.cart_delete);
            qtyadd = itemView.findViewById(R.id.cart_add_qty);
            qtydel = itemView.findViewById(R.id.cart_minus_qty);
        }
    }
    }

