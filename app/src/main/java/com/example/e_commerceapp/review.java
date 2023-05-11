package com.example.e_commerceapp;

import static com.example.e_commerceapp.cart.arrayList;
import static com.example.e_commerceapp.cart.cartItems_ga3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.analytics.FirebaseAnalytics.Event;

import java.util.Random;

public class review extends AppCompatActivity {
private Button pay;
private TextView review1,review2,review3;
FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ViewGroup rootLayout = findViewById(android.R.id.content);
        View overlayView = getLayoutInflater().inflate(R.layout.popup_overlay, rootLayout, false);
        rootLayout.addView(overlayView);

        pay = findViewById(R.id.ordered);
       review1 = findViewById(R.id.review1);
       review2 =findViewById(R.id.review2);
       review3  = findViewById(R.id.review3);
       Bundle clearall = getIntent().getExtras();
       review1.setText("Products Bought are shipped in 2 Working days and for express delivery within 48HRS Delivery at additional cost your net Payable is:  "+clearall.getString("ITEM_TOTAL_NEW").toUpperCase());
       review2.setText("name-"+clearall.getString("ADD_NAME")+", Address-"+clearall.getString("ADD_ADD")+clearall.getString("ADD_ADD2")+", city-"+clearall.getString("ADD_CITY")+", state-"+clearall.getString("ADD_STATE")+", pincode-"+clearall.getString("ADD_ZIP"));
       review3.setText("You will be redirected to the chosen payment mode: "+clearall.getString("ITEM_PAYMENT_MODE"));

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        pay.setOnClickListener(view ->  {

                View overlay = findViewById(R.id.popup_overlay);
                overlay.setVisibility(view.VISIBLE);

                View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);
                PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        // handle dismiss event
                    }
                });

                // show the popup window
                popupWindow.showAtLocation(pay, Gravity.CENTER, 0, 0);

                // handle button click event
                Button btnOk = popupView.findViewById(R.id.btn_close);
                btnOk.setOnClickListener(view1 ->  {

                        arrayList.clear();
                        popupWindow.dismiss();

                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                });

            Bundle purchaseParams = new Bundle(Address_and_Payment.addPaymentParams);
            purchaseParams.putString(FirebaseAnalytics.Param.TRANSACTION_ID,"TA-APP-002711");
            purchaseParams.putString(FirebaseAnalytics.Param.AFFILIATION, "Tatvic Online Store");
            purchaseParams.putDouble(FirebaseAnalytics.Param.TAX, 2.58);
            purchaseParams.putDouble(FirebaseAnalytics.Param.SHIPPING, 5.34);
            purchaseParams.putString("custom_app_id",MainActivity.appid);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.PURCHASE, purchaseParams);
            
//***************************************** PURCHASE GA3*********************************************

            Bundle ecommerceBundle = new Bundle();
            ecommerceBundle.putParcelableArrayList("items", cartItems_ga3);
            ecommerceBundle.putString( Param.TRANSACTION_ID, "TA-APP-002711" );
            ecommerceBundle.putString( Param.AFFILIATION, "Tatvic Online Store" );
            ecommerceBundle.putDouble( Param.TAX, 2.58);
            ecommerceBundle.putDouble( Param.SHIPPING, 5.34);
            ecommerceBundle.putString("custom_app_id",MainActivity.appid);
            ecommerceBundle.putString("GA3","true");
      //      mFirebaseAnalytics.logEvent( Event.ECOMMERCE_PURCHASE, purchaseParams );
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.screenviews.putString(Param.SCREEN_NAME,"Review Page");
        MainActivity.screenviews.putString(Param.SCREEN_CLASS,"review");
        mFirebaseAnalytics.logEvent(Event.SCREEN_VIEW,MainActivity.screenviews);
    }
}