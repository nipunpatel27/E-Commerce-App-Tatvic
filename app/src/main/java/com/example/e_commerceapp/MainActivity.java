package com.example.e_commerceapp;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;
import static com.example.e_commerceapp.cart.arrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.analytics.FirebaseAnalytics.Event;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button home_button,refund;
    private ImageView[] imageViews;
    private int currentImageIndex = 0;
    private Handler handler;
    private Runnable runnable;
    private FirebaseAnalytics mFirebaseAnalytics;
    static Bundle item_apple,item_milk,item_watch,item_cloth,item_cloth1,item_mobile,item_shoe,item_food,item_food1,item_food3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home_button = findViewById(R.id.home_button);
        refund = findViewById(R.id.refund);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FirebaseAnalytics.getInstance(this).setSessionTimeoutDuration(100000);
        item_apple = new Bundle();
        item_apple.putString(FirebaseAnalytics.Param.ITEM_NAME,"Fresh Apple's");
        item_apple.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Fruits");
        item_apple.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Shoppers.Tatvic");
        item_apple.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        item_apple.putDouble(FirebaseAnalytics.Param.PRICE, 40.00);

        item_milk = new Bundle();
        item_milk.putString(FirebaseAnalytics.Param.ITEM_NAME,"Amul Milk");
        item_milk.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Dairy");
        item_milk.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Amul India");
        item_milk.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        item_milk.putDouble(FirebaseAnalytics.Param.PRICE, 50.00);

        item_watch = new Bundle();
        item_watch.putString(FirebaseAnalytics.Param.ITEM_NAME,"Rolex Watch");
        item_watch.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Accessories");
        item_watch.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Rolex");
        item_watch.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        item_watch.putDouble(FirebaseAnalytics.Param.PRICE, 3000.00);

        item_cloth = new Bundle();
        item_cloth.putString(FirebaseAnalytics.Param.ITEM_NAME,"Tatvic T-shirt");
        item_cloth.putString("Size","M");
        item_cloth.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Clothing");
        item_cloth.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Shoppers.Tatvic");
        item_cloth.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        item_cloth.putDouble(FirebaseAnalytics.Param.PRICE, 799.00);

        item_cloth1 = new Bundle();
        item_cloth1.putString(FirebaseAnalytics.Param.ITEM_NAME,"Tommy Jeans");
        item_cloth1.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Clothing");
        item_cloth1.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Tommy Hilfiger");
        item_cloth1.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        item_cloth1.putDouble(FirebaseAnalytics.Param.PRICE, 4999.00);

        item_mobile = new Bundle();
        item_mobile.putString(FirebaseAnalytics.Param.ITEM_NAME,"iPhone 14 Pro Max");
        item_mobile.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Mobile");
        item_mobile.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Apple");
        item_mobile.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        item_mobile.putDouble(FirebaseAnalytics.Param.PRICE, 129999.00);

        item_shoe = new Bundle();
        item_shoe.putString(FirebaseAnalytics.Param.ITEM_NAME,"Nike Jordan");
        item_shoe.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Footwear");
        item_shoe.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Nike");
        item_shoe.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        item_shoe.putDouble(FirebaseAnalytics.Param.PRICE, 28999.00);

        item_food = new Bundle();
        item_food.putString(FirebaseAnalytics.Param.ITEM_NAME,"Lunch Box");
        item_food.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Food");
        item_food.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Shoppers.Tatvic");
        item_food.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        item_food.putDouble(FirebaseAnalytics.Param.PRICE, 100.00);

        item_food1 = new Bundle();
        item_food1.putString(FirebaseAnalytics.Param.ITEM_NAME,"White Bread");
        item_food1.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Bakery");
        item_food1.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Shoppers.Tatvic");
        item_food1.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        item_food1.putDouble(FirebaseAnalytics.Param.PRICE, 20.00);

        item_food3 = new Bundle();
        item_food3.putString(FirebaseAnalytics.Param.ITEM_NAME,"Balaji Snack");
        item_food3.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Packaged Food");
        item_food3.putString(FirebaseAnalytics.Param.ITEM_BRAND, "Shoppers.Tatvic");
        item_food3.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        item_food3.putDouble(FirebaseAnalytics.Param.PRICE, 18.00);

        Bundle item_appleWithIndex = new Bundle(item_apple);
        item_appleWithIndex.putLong(FirebaseAnalytics.Param.INDEX, 1);

        Bundle item_milkWithIndex = new Bundle(item_milk);
        item_milkWithIndex.putLong(FirebaseAnalytics.Param.INDEX, 2);

        Bundle item_watchWithIndex = new Bundle(item_watch);
        item_watchWithIndex.putLong(FirebaseAnalytics.Param.INDEX, 3);

        Bundle item_clothWithIndex = new Bundle(item_cloth);
        item_clothWithIndex.putLong(FirebaseAnalytics.Param.INDEX, 4);

        Bundle item_cloth1WithIndex = new Bundle(item_cloth1);
        item_cloth1WithIndex.putLong(FirebaseAnalytics.Param.INDEX, 5);

        Bundle item_mobileWithIndex = new Bundle(item_mobile);
        item_mobileWithIndex.putLong(FirebaseAnalytics.Param.INDEX, 6);

        Bundle item_shoeWithIndex = new Bundle(item_mobile);
        item_shoeWithIndex.putLong(FirebaseAnalytics.Param.INDEX, 7);

        Bundle item_foodWithIndex = new Bundle(item_food);
        item_foodWithIndex.putLong(FirebaseAnalytics.Param.INDEX, 8);

        Bundle item_food1WithIndex = new Bundle(item_food1);
        item_food1WithIndex.putLong(FirebaseAnalytics.Param.INDEX, 9);

        Bundle item_food3WithIndex = new Bundle(item_food3);
        item_food3WithIndex.putLong(FirebaseAnalytics.Param.INDEX, 10);

        home_button.setOnClickListener(view -> {
            Intent intent = new Intent(this,product_page.class);
            startActivity(intent);
            Bundle viewItemListParams = new Bundle();
            viewItemListParams.putString(Param.SCREEN_NAME,"Products Page");
            viewItemListParams.putString(Param.SCREEN_CLASS,"MainActivity");
            viewItemListParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]{item_appleWithIndex, item_milkWithIndex, item_watchWithIndex, item_clothWithIndex, item_cloth1WithIndex, item_mobileWithIndex, item_shoeWithIndex, item_foodWithIndex, item_food1WithIndex, item_food3WithIndex});
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, viewItemListParams);

//***************************************************view items GA3********************************************

            ArrayList <Bundle>items = new ArrayList<Bundle>();
            items.add(item_apple);
            items.add(item_cloth);
            items.add(item_food);
            items.add(item_milk);
            items.add(item_mobile);
            items.add(item_shoe);
            items.add(item_food1);
            items.add(item_food3);
            items.add(item_watch);
            items.add(item_cloth1);

            Bundle viewItemListParams_GA3 = new Bundle();
            viewItemListParams_GA3.putString("GA3","true");
            viewItemListParams_GA3.putParcelableArrayList( "items", items);

            mFirebaseAnalytics.logEvent(Event.VIEW_ITEM_LIST, viewItemListParams_GA3);
        });
        
        refund.setOnClickListener(view -> {
            Toast.makeText(this, "Refund Request accepted", Toast.LENGTH_LONG).show();

            Bundle refundParams = new Bundle();
            refundParams.putString(FirebaseAnalytics.Param.TRANSACTION_ID, "T12345");
            refundParams.putString(FirebaseAnalytics.Param.AFFILIATION, "Google Store");
            refundParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
            refundParams.putDouble(FirebaseAnalytics.Param.VALUE, 10.00);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.REFUND, refundParams);

// **********************************Refund GA3***************************************************************

            Bundle refundedProduct = new Bundle();
            refundedProduct.putString( Param.ITEM_ID, "Default-001" );
            refundedProduct.putLong( Param.QUANTITY, 1 );
            refundedProduct.putString( "GA3", "true");
            ArrayList <Bundle>items = new ArrayList<Bundle>();
            items.add(refundedProduct);
            refundParams.putParcelableArrayList( "items", items);

            mFirebaseAnalytics.logEvent( Event.PURCHASE_REFUND, refundParams);
        });

        imageViews = new ImageView[]{
                findViewById(R.id.imageView1),
                findViewById(R.id.imageView2),
                findViewById(R.id.imageView3)
        };

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                showNextImage();
                handler.postDelayed(this, 3000);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private void showNextImage() {
        imageViews[currentImageIndex].setVisibility(View.GONE);
        currentImageIndex = (currentImageIndex + 1) % imageViews.length;
        imageViews[currentImageIndex].setVisibility(View.VISIBLE);

        imageViews[currentImageIndex].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle promoParams = new Bundle();
                promoParams.putString(FirebaseAnalytics.Param.PROMOTION_ID, "SUMMER_FUN_01");
                promoParams.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Summer Sale");
                promoParams.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "Shoe-Cola-Food");
                promoParams.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "Shoppers_Summer_Batch");
                promoParams.putString(FirebaseAnalytics.Param.LOCATION_ID, "PROMO_HERO_BANNER");

                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, promoParams);

//*******************************Promotion selected GA3***********************************************

                Bundle promotion = new Bundle();
                promotion.putString( Param.ITEM_ID, "SUMMER_FUN_01");
                promotion.putString( Param.ITEM_NAME, "Summer Sale");
                promotion.putString( Param.CREATIVE_NAME, "Shoe-Cola-Food");
                promotion.putString( Param.CREATIVE_SLOT, "Shoppers_Summer_Batch");

                ArrayList<Bundle> promotions = new ArrayList<Bundle>();
                promotions.add(promotion);
                Bundle ecommerceBundle = new Bundle();
                ecommerceBundle.putParcelableArrayList("promotions", promotions);
                mFirebaseAnalytics.logEvent(Event.SELECT_CONTENT, ecommerceBundle);
            }
        });
    }
}