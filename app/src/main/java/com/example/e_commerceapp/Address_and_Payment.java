package com.example.e_commerceapp;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import static com.example.e_commerceapp.cart.cartItems_ga3;
import static com.example.e_commerceapp.cart.checkoutCartParams;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

public class Address_and_Payment extends AppCompatActivity {
    static Bundle ecomme_p;
    private EditText etName, etAddress, etAddress2, etCity, etState, etZip;
    private Spinner spPaymentMode;
    String selectedPaymentMode,pass,name,address,address2,city,state,zip;
    FirebaseAnalytics mFirebaseAnalytics;
    static Bundle addShippingParams,addPaymentParams;
    Bundle screenName,buns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_and_payment);
        buns = getIntent().getExtras();
        screenName = buns.getBundle("SCREEN_NAME");
        pass = buns.getString("ITEM_TOTAL");
        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);
        etAddress2 = findViewById(R.id.et_address2);
        etCity = findViewById(R.id.et_city);
        etState = findViewById(R.id.et_state);
        etZip = findViewById(R.id.et_zip);
        spPaymentMode = findViewById(R.id.payment_mode_spinner);
        Button btnSave = findViewById(R.id.checkout);
        spPaymentMode.setSelection(0);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bun = new Bundle();

        spPaymentMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPaymentMode = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        
        etZip.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                name = etName.getText().toString();
                address = etAddress.getText().toString();
                address2 = etAddress2.getText().toString();
                city = etCity.getText().toString();
                state = etState.getText().toString();
                zip = etZip.getText().toString();

                if (actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    performDoneBtn();
                    return true;
                }
                return false;
            }
        });
        
        btnSave.setOnClickListener(view ->  {
            if (name.isEmpty() || address.isEmpty() || city.isEmpty() || state.isEmpty() || zip.isEmpty() || address2.isEmpty() || selectedPaymentMode.contains("Select Payment Mode")) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
            bun.putString("ADD_NAME",name);
            bun.putString("ADD_ADD",address);
            bun.putString("ADD_ADD2",address2);
            bun.putString("ADD_CITY",city);
            bun.putString("ADD_STATE",state);
            bun.putString("ADD_ZIP",zip);
            bun.putString("ITEM_TOTAL_NEW",pass);
            bun.putString("ITEM_PAYMENT_MODE",selectedPaymentMode);
                Intent intent = new Intent(this,review.class);
                intent.putExtras(bun);
                startActivity(intent);
                addPaymentParams = new Bundle(addShippingParams);
                addPaymentParams.putString(FirebaseAnalytics.Param.PAYMENT_TYPE, selectedPaymentMode);
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_PAYMENT_INFO, addPaymentParams);

//*****************************************Add Payment GA3********************************************************

                ecomme_p = new Bundle();
                ecomme_p.putParcelableArrayList("items",cartItems_ga3);
                ecomme_p.putString("GA3","true");
                ecomme_p.putString( Param.CHECKOUT_OPTION, selectedPaymentMode );
                mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.SET_CHECKOUT_OPTION, ecomme_p);
            }
        });

    }
    private void performDoneBtn(){
        if (name.isEmpty() || address.isEmpty() || city.isEmpty() || state.isEmpty() || zip.isEmpty() || address2.isEmpty()) {
            Toast.makeText(Address_and_Payment.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            addShippingParams = new Bundle();
            addShippingParams.putString(FirebaseAnalytics.Param.SHIPPING," to "+name+", "+address+", "+address2+", "+city+", "+state+", "+zip);
            addShippingParams.putString(FirebaseAnalytics.Param.SHIPPING_TIER,"AIR");
            if(buns.containsKey("SCREEN_NAME")){
                addShippingParams = new Bundle(screenName);
                addShippingParams.putInt(FirebaseAnalytics.Param.QUANTITY,1);
                addShippingParams.putDouble(FirebaseAnalytics.Param.VALUE,Double.parseDouble(buns.getString("ITEM_TOTAL")));
                addShippingParams.putString(FirebaseAnalytics.Param.SHIPPING," to "+name+", "+address+", "+address2+", "+city+", "+state+", "+zip);
                addShippingParams.putString(FirebaseAnalytics.Param.SHIPPING_TIER,"AIR");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_SHIPPING_INFO, addShippingParams);
            }else {
                addShippingParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                        cart.cartItems.toArray(new Parcelable[cart.cartItems.size()]));
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_SHIPPING_INFO, addShippingParams);
            }
        }
    }


}

