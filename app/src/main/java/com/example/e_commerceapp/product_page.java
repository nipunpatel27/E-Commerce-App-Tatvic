package com.example.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics.Event;

import android.os.Bundle;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
//public class product_page extends AppCompatActivity {
//
//        private ListView listView;
//    private String[] productNames = {"Product 1", "Product 2", "Product 3", "Product 4", "Product 5", "Product 6", "Product 7", "Product 8", "Product 9", "Product 10"};
//    private String[] productDescriptions = {"Product 1 Description", "Product 2 Description", "Product 3 Description", "Product 4 Description", "Product 5 Description", "Product 6 Description", "Product 7 Description", "Product 8 Description", "Product 9 Description", "Product 10 Description"};
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_product_page);
//        listView = findViewById(R.id.listView);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productNames);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Bundle bundle = new Bundle();
//                Intent intent = new Intent(product_page.this, product_detail.class);
//                bundle.putString("name", productNames[position]);
//                bundle.putString("description", productDescriptions[position]);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//
//    }
//}


import android.os.Bundle;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class product_page extends AppCompatActivity {
private ImageView icon;
FirebaseAnalytics mFirebaseAnalytics;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_product_page);
        icon = findViewById(R.id.toolbar_icon);
        RecyclerView courseRV = findViewById(R.id.idRVCourse);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // Here, we have created new array list and added data to it
        ArrayList<CourseModel> courseModelArrayList = new ArrayList<CourseModel>();
        courseModelArrayList.add(new CourseModel("Fresh Apple's", 40, R.drawable.apple));
        courseModelArrayList.add(new CourseModel("Amul Milk", 50, R.drawable.milk));
        courseModelArrayList.add(new CourseModel("Rolex Watch", 3000, R.drawable.watch));
        courseModelArrayList.add(new CourseModel("Tatvic T-shirt", 799, R.drawable.shirt));
        courseModelArrayList.add(new CourseModel("Tommy Jeans", 4999, R.drawable.jeans));
        courseModelArrayList.add(new CourseModel("iPhone 14 Pro Max", 129999, R.drawable.iphone));
        courseModelArrayList.add(new CourseModel("Nike Jordan", 28999, R.drawable.sneakers));
        courseModelArrayList.add(new CourseModel("Lunch Box", 100, R.drawable.food));
        courseModelArrayList.add(new CourseModel("White Bread", 20, R.drawable.bread));
        courseModelArrayList.add(new CourseModel("Balaji Snack", 18, R.drawable.snack));
        // we are initializing our adapter class and passing our arraylist to it.
        CourseAdapter courseAdapter = new CourseAdapter(this, courseModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(courseAdapter);
        icon.setOnClickListener(view -> {
        Intent intent = new Intent(this,cart.class);
        startActivity(intent);
    });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.screenviews.putString(FirebaseAnalytics.Param.SCREEN_NAME,"Products Page");
        MainActivity.screenviews.putString(FirebaseAnalytics.Param.SCREEN_CLASS,"product_page");
        mFirebaseAnalytics.logEvent(Event.SCREEN_VIEW,MainActivity.screenviews);
    }
}


