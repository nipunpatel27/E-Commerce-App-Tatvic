package com.example.e_commerceapp;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.remoteconfig.ConfigUpdate;
import com.google.firebase.remoteconfig.ConfigUpdateListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button home_button,refund;
    private ImageView[] imageViews;
    private int currentImageIndex = 0;
    private Handler handler;
    private Runnable runnable;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AppsFlyerLib mAppsFlyerLib;
    static Bundle item_apple,item_milk,item_watch,item_cloth,item_cloth1,item_mobile,item_shoe,item_food,item_food1,item_food3,screenviews;
    static String appid;
    private TextView webviewlink, crashlytics;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home_button = findViewById(R.id.home_button);
        refund = findViewById(R.id.refund);
        webviewlink = findViewById(R.id.webviewredirect);
        crashlytics = findViewById(R.id.crashlytics);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FirebaseAnalytics.getInstance(this).setSessionTimeoutDuration(60000);
        getfirebasedynamiclink();
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        mFirebaseAnalytics.getAppInstanceId().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String appInstanceId) {
                appid = appInstanceId;
            }
        });

        FirebaseAnalytics.getInstance(this).getSessionId().addOnCompleteListener(new OnCompleteListener<Long>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Long> task) {
                if (task.isSuccessful()){
                    String sess = String.valueOf(task.getResult());
                    Log.d("session", ""+sess);
                }
            }
        });
//******************************************* AppsFlyer Analytics *******************************************

        mAppsFlyerLib = AppsFlyerLib.getInstance().init("iYnSbmYviwvEE5FBcni56X", null, this);
        mAppsFlyerLib.start(this);
        mAppsFlyerLib.setDebugLog(true);

//******************************************* Ad Mobs *******************************************************

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

//************************************** Firebase Crashlytics ****************************************************
crashlytics.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        throw new RuntimeException("Test Crash");
    }
});

//************************************************ Web View ************************************************************
        webviewlink.setOnClickListener(view -> {
            Intent intent = new Intent(this,webviewfile.class);
            startActivity(intent);
        });

//************************************ Remote Config ***********************************************************

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(10)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.rc_defaults);

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.d(TAG, "Config params updated: " + updated);
                            Toast.makeText(MainActivity.this, "Fetch and activate succeeded",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Fetch failed",
                                    Toast.LENGTH_SHORT).show();
                        }
//                        displayWelcomeMessage();
                    }
                });
        mFirebaseRemoteConfig.addOnConfigUpdateListener(new ConfigUpdateListener() {
            @Override
            public void onUpdate(ConfigUpdate configUpdate) {
                Log.d(TAG, "Updated keys: " + configUpdate.getUpdatedKeys());

                mFirebaseRemoteConfig.activate().addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                       // displayWelcomeMessage();
                    }
                });
            }

            @Override
            public void onError(FirebaseRemoteConfigException error) {
                Log.w(TAG, "Config update error with code: " + error.getCode(), error);
            }
        });

//**************************************************************************************************************

        item_apple = new Bundle();
        item_apple.putString(Param.ITEM_NAME,"Fresh Apple's");
        item_apple.putString(Param.ITEM_CATEGORY, "Fruits");
        item_apple.putString(Param.ITEM_BRAND, "Shoppers.Tatvic");
        item_apple.putString(Param.CURRENCY,"INR");
        item_apple.putDouble(Param.PRICE, 40.00);

        item_milk = new Bundle();
        item_milk.putString(Param.ITEM_NAME,"Amul Milk");
        item_milk.putString(Param.ITEM_CATEGORY, "Dairy");
        item_milk.putString(Param.ITEM_BRAND, "Amul India");
        item_milk.putString(Param.CURRENCY,"INR");
        item_milk.putDouble(Param.PRICE, 50.00);

        item_watch = new Bundle();
        item_watch.putString(Param.ITEM_NAME,"Rolex Watch");
        item_watch.putString(Param.ITEM_CATEGORY, "Accessories");
        item_watch.putString(Param.ITEM_BRAND, "Rolex");
        item_watch.putString(Param.CURRENCY,"INR");
        item_watch.putDouble(Param.PRICE, 3000.00);

        item_cloth = new Bundle();
        item_cloth.putString(Param.ITEM_NAME,"Tatvic T-shirt");
        item_cloth.putString(Param.ITEM_CATEGORY, "Clothing");
        item_cloth.putString(Param.ITEM_BRAND, "Shoppers.Tatvic");
        item_cloth.putString(Param.CURRENCY,"INR");
        item_cloth.putDouble(Param.PRICE, 799.00);

        item_cloth1 = new Bundle();
        item_cloth1.putString(Param.ITEM_NAME,"Tommy Jeans");
        item_cloth1.putString(Param.ITEM_CATEGORY, "Clothing");
        item_cloth1.putString(Param.ITEM_BRAND, "Tommy Hilfiger");
        item_cloth1.putString(Param.CURRENCY,"INR");
        item_cloth1.putDouble(Param.PRICE, 4999.00);

        item_mobile = new Bundle();
        item_mobile.putString(Param.ITEM_NAME,"iPhone 14 Pro Max");
        item_mobile.putString(Param.ITEM_CATEGORY, "Mobile");
        item_mobile.putString(Param.ITEM_BRAND, "Apple");
        item_mobile.putString(Param.CURRENCY,"INR");
        item_mobile.putDouble(Param.PRICE, 129999.00);

        item_shoe = new Bundle();
        item_shoe.putString(Param.ITEM_NAME,"Nike Jordan");
        item_shoe.putString(Param.ITEM_CATEGORY, "Footwear");
        item_shoe.putString(Param.ITEM_BRAND, "Nike");
        item_shoe.putString(Param.CURRENCY,"INR");
        item_shoe.putDouble(Param.PRICE, 28999.00);

        item_food = new Bundle();
        item_food.putString(Param.ITEM_NAME,"Lunch Box");
        item_food.putString(Param.ITEM_CATEGORY, "Food");
        item_food.putString(Param.ITEM_BRAND, "Shoppers.Tatvic");
        item_food.putString(Param.CURRENCY,"INR");
        item_food.putDouble(Param.PRICE, 100.00);

        item_food1 = new Bundle();
        item_food1.putString(Param.ITEM_NAME,"White Bread");
        item_food1.putString(Param.ITEM_CATEGORY, "Bakery");
        item_food1.putString(Param.ITEM_BRAND, "Shoppers.Tatvic");
        item_food1.putString(Param.CURRENCY,"INR");
        item_food1.putDouble(Param.PRICE, 20.00);

        item_food3 = new Bundle();
        item_food3.putString(Param.ITEM_NAME,"Balaji Snack");
        item_food3.putString(Param.ITEM_CATEGORY, "Packaged Food");
        item_food3.putString(Param.ITEM_BRAND, "Shoppers.Tatvic");
        item_food3.putString(Param.CURRENCY,"INR");
        item_food3.putDouble(Param.PRICE, 18.00);

        Bundle item_appleWithIndex = new Bundle(item_apple);
        item_appleWithIndex.putLong(Param.INDEX, 1);

        Bundle item_milkWithIndex = new Bundle(item_milk);
        item_milkWithIndex.putLong(Param.INDEX, 2);

        Bundle item_watchWithIndex = new Bundle(item_watch);
        item_watchWithIndex.putLong(Param.INDEX, 3);

        Bundle item_clothWithIndex = new Bundle(item_cloth);
        item_clothWithIndex.putLong(Param.INDEX, 4);

        Bundle item_cloth1WithIndex = new Bundle(item_cloth1);
        item_cloth1WithIndex.putLong(Param.INDEX, 5);

        Bundle item_mobileWithIndex = new Bundle(item_mobile);
        item_mobileWithIndex.putLong(Param.INDEX, 6);

        Bundle item_shoeWithIndex = new Bundle(item_mobile);
        item_shoeWithIndex.putLong(Param.INDEX, 7);

        Bundle item_foodWithIndex = new Bundle(item_food);
        item_foodWithIndex.putLong(Param.INDEX, 8);

        Bundle item_food1WithIndex = new Bundle(item_food1);
        item_food1WithIndex.putLong(Param.INDEX, 9);

        Bundle item_food3WithIndex = new Bundle(item_food3);
        item_food3WithIndex.putLong(Param.INDEX, 10);

//************************************* AppsFlyer Data Map ****************************************************

        Map<String, Object> eventValues = new HashMap<String, Object>();
        eventValues.put(AFInAppEventParameterName.CONTENT,"Amul Milk");
        eventValues.put(AFInAppEventParameterName.CONTENT_TYPE,"Dairy");
        eventValues.put(AFInAppEventParameterName.CONTENT_LIST,"Amul");
        eventValues.put(AFInAppEventParameterName.CURRENCY,"INR");
        eventValues.put(AFInAppEventParameterName.PRICE,50.00);

        home_button.setOnClickListener(view -> {
            Intent intent = new Intent(this,product_page.class);
            startActivity(intent);
            Bundle viewItemListParams = new Bundle();
            viewItemListParams.putString("custom_app_id",appid);
            viewItemListParams.putParcelableArray(Param.ITEMS,
                    new Parcelable[]{item_appleWithIndex, item_milkWithIndex, item_watchWithIndex, item_clothWithIndex, item_cloth1WithIndex, item_mobileWithIndex, item_shoeWithIndex, item_foodWithIndex, item_food1WithIndex, item_food3WithIndex});
            mFirebaseAnalytics.logEvent(Event.VIEW_ITEM_LIST, viewItemListParams);

//            ******************** AppsFlyer log event ********************
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(),
                    AFInAppEventType.CONTENT_VIEW, eventValues);

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
            viewItemListParams_GA3.putString("custom_app_id",appid);
            viewItemListParams_GA3.putParcelableArrayList( "items", items);
            mFirebaseAnalytics.logEvent(Event.VIEW_ITEM_LIST, viewItemListParams_GA3);
        });
        
        refund.setOnClickListener(view -> {
            Toast.makeText(this, "Refund Request accepted", Toast.LENGTH_LONG).show();

            Bundle refundParams = new Bundle();
            refundParams.putString(Param.TRANSACTION_ID, "T12345");
            refundParams.putString(Param.AFFILIATION, "Google Store");
            refundParams.putString(Param.CURRENCY, "INR");
            refundParams.putDouble(Param.VALUE, 10.00);
            refundParams.putString("custom_app_id",appid);
            mFirebaseAnalytics.logEvent(Event.REFUND, refundParams);

// **********************************Refund GA3***************************************************************

            Bundle refundedProduct = new Bundle();
            refundedProduct.putString( Param.ITEM_ID, "Default-001" );
            refundedProduct.putLong( Param.QUANTITY, 1 );
            refundedProduct.putString("custom_app_id",appid);
            refundedProduct.putString( "GA3", "true");
            ArrayList <Bundle>items = new ArrayList<Bundle>();
            items.add(refundedProduct);
            refundParams.putParcelableArrayList( "items", items);

            //mFirebaseAnalytics.logEvent( Event.PURCHASE_REFUND, refundParams);
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

    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        String packageName = "com.android.chrome";
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName);

            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabsIntent.launchUrl(activity, uri);
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    private void getfirebasedynamiclink() {
        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent()).addOnSuccessListener(
                new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Log.d(TAG, ""+"We have dynamic link");
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1500);
        screenviews = new Bundle();
        screenviews.putString(FirebaseAnalytics.Param.SCREEN_NAME,"Homepage");
        screenviews.putString(FirebaseAnalytics.Param.SCREEN_CLASS,"Main Activity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW,screenviews);
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
                promoParams.putString("custom_app_id",appid);
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, promoParams);

//*******************************Promotion selected GA3***********************************************

                Bundle promotion = new Bundle();
                promotion.putString( Param.ITEM_ID, "SUMMER_FUN_01");
                promotion.putString( Param.ITEM_NAME, "Summer Sale");
                promotion.putString( Param.CREATIVE_NAME, "Shoe-Cola-Food");
                promotion.putString( Param.CREATIVE_SLOT, "Shoppers_Summer_Batch");
                promotion.putString("custom_app_id",appid);
                promotion.putString("check","yes");
                ArrayList<Bundle> promotions = new ArrayList<Bundle>();
                promotions.add(promotion);
                Bundle ecommerceBundle = new Bundle();
                ecommerceBundle.putParcelableArrayList("promotions", promotions);
                mFirebaseAnalytics.logEvent(Event.SELECT_CONTENT, ecommerceBundle);
            }
        });
    }
}

