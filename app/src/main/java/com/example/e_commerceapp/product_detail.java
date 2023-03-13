package com.example.e_commerceapp;

import static com.example.e_commerceapp.cart.arrayList;
import static com.example.e_commerceapp.cart.cartItems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class product_detail extends AppCompatActivity {
    private TextView name,price,desc;
    private ImageView img;
    String a,d;
    Bundle global;
    Intent my_intent;
    int b,c;
    cartModel detail;
    private FirebaseAnalytics mFirebaseAnalytics;
    ArrayList<Bundle> cartItems_ga3_1 = new ArrayList<Bundle>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        name = findViewById(R.id.p_name);
        img = findViewById(R.id.p_img);
        price = findViewById(R.id.p_price);
        desc = findViewById(R.id.description);
        Button add_cart = findViewById(R.id.add_to_cart_button);
        Button buy_button = findViewById(R.id.buy_now_button);
        Bundle bun = getIntent().getExtras();
        a=bun.getString("ITEM_NAME");
        b=bun.getInt("ITEM_IMAGE");
        c=bun.getInt("ITEM_RATING");
        name.setText(a);
        img.setImageResource(b);
        price.setText("$ "+c+".00");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        switch (a){
            case "Fresh Apple's": d="The apple is a deciduous tree, generally standing 2 to 4.5 m (6 to 15 ft) tall in cultivation and up to 9 m (30 ft) in the wild. When cultivated, the size, shape and branch density are determined by rootstock selection and trimming method. The leaves are alternately arranged dark green-colored simple ovals with serrated margins and slightly downy undersides";
            global = MainActivity.item_apple;
            break;
            case "Amul Milk": d="Amul Milk is the most hygienic liquid milk available in the market. It is pasteurized in state-of-the-art processing plants and pouch-packed to make it conveniently available to consumers.";
            global = MainActivity.item_milk;
            break;
            case "Rolex Watch": d="Rolex is the leading name in luxury wristwatches. It is headquartered in Geneva, Switzerland, but relies on 4,000 watchmakers in more than 100 countries. It created the world's first waterproof watch in 1926. Rolex has a major presence in the sports world with endorsements in golf, motor sports, tennis and yachting.";
            global = MainActivity.item_watch;
            break;
            case "Tatvic T-shirt": d="A T-shirt (also spelled tee-shirt or tee shirt), or tee for short, is a style of fabric shirt named after the T shape of its body and sleeves. Traditionally, it has short sleeves and a round neckline, known as a crew neck, which lacks a collar. T-shirts are generally made of a stretchy, light, and inexpensive fabric and are easy to clean. The T-shirt evolved from undergarments used in the 19th century and, in the mid-20th century, transitioned from undergarment to general-use casual clothing";
            global = MainActivity.item_cloth;
            break;
            case "Tommy Jeans": d="Inspired by American denim classics with a modern, casual edge. Adding a youthful energy and irreverent twist to our heritage, the men's and women's collections focus on premium denim. The line targets the next generation of 18-to-30-year-old denim-oriented consumers.";
            global = MainActivity.item_cloth1;
            break;
            case "iPhone 14 Pro Max": d="The iPhone 14 Pro Max measures 160.70 x 77.60 x 7.85mm (height x width x thickness) and weighs 240.00 grams. It was launched in Space Black, Silver, Gold, and Deep Purple colours. It features an IP68 rating for dust and water protection. Connectivity options on the iPhone 14 Pro Max include Wi-Fi and Lightning.";
            global = MainActivity.item_mobile;
            break;
            case "Nike Jordan": d="Air Jordan is a line of basketball shoes and athletic apparel produced by American corporation Nike, Inc. The first Air Jordan shoe was produced for Hall of Fame former basketball player Michael Jordan during his time with the Chicago Bulls in late 1984 and released to the public on April 1, 1985.";
            global = MainActivity.item_shoe;
            break;
            case "Lunch Box": d="A lunch box (alt. spelling lunchbox) refers to a hand-held container used to transport food, usually to work or to school. It is commonly made of metal or plastic, is reasonably airtight and often has a handle for carrying.";
            global = MainActivity.item_food;
            break;
            case "White Bread": d="White bread typically refers to breads made from wheat flour from which the bran and the germ layers have been removed from the whole wheatberry as part of the flour grinding or milling process, producing a light-colored flour.";
            global = MainActivity.item_food1;
            break;
            case "Balaji Snack": d="Balaji Wafers is a major snack food manufacturer and distributor in Gujarat, India. It produces and distributes a variety of potato chips and other grain-based snack foods.";
            global = MainActivity.item_food3;
            break;
            default: d="NA";
            break;
        }
        desc.setText(d);
        detail = new cartModel(b, c, a, 1);
        add_cart.setOnClickListener(view ->  {
            arrayList.add(detail);
            Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,product_page.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            //add to cart
            Bundle itemaddcart = new Bundle(global);
            itemaddcart.putLong(FirebaseAnalytics.Param.QUANTITY, 1);

            Bundle addToWishlistParams = new Bundle();
            addToWishlistParams.putString(FirebaseAnalytics.Param.CURRENCY, "USD");
            addToWishlistParams.putDouble(FirebaseAnalytics.Param.VALUE, c);
            addToWishlistParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]{itemaddcart});
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, addToWishlistParams);

//*************************************Add to cart GA3*****************************************************

            Bundle ecommerceBundle = new Bundle(addToWishlistParams);
            ecommerceBundle.putString("GA3","true");
            ecommerceBundle.putBundle("items", global);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, ecommerceBundle);
        });

        buy_button.setOnClickListener(view -> {
            Bundle buns = new Bundle();
            buns.putString("ITEM_TOTAL",""+c);
            buns.putBundle("SCREEN_NAME",global);

            my_intent = new Intent(this,Address_and_Payment.class);
            my_intent.putExtras(buns);
            startActivity(my_intent);

            Bundle addToWishlistParams = new Bundle(global);
            addToWishlistParams.putInt(FirebaseAnalytics.Param.QUANTITY,1);
            addToWishlistParams.putDouble(FirebaseAnalytics.Param.VALUE,c);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT, addToWishlistParams);

//**********************************Begin checkout GA 3************************************************

            cartItems_ga3_1.add(addToWishlistParams);
            Bundle ecomme = new Bundle();
            ecomme.putString("GA3","true");
            ecomme.putParcelableArrayList("items",cartItems_ga3_1);
            mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.BEGIN_CHECKOUT, ecomme);


        });
    }
}