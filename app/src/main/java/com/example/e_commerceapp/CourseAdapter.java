package com.example.e_commerceapp;

import static com.example.e_commerceapp.cart.arrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private final Context context;
    public final ArrayList<CourseModel> courseModelArrayList;
    private FirebaseAnalytics mFirebaseAnalytics;
    // Constructor
    Bundle global;
    String d;
    public CourseAdapter(Context context, ArrayList<CourseModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        CourseModel model = courseModelArrayList.get(position);
        holder.courseNameTV.setText(model.getCourse_name());
        holder.courseRatingTV.setText("$ "+model.getCourse_rating()+".00");
        holder.courseIV.setImageResource(model.getCourse_image());

        holder.p_butt.setOnClickListener(view ->  {
                Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show();
                cartModel detail=new cartModel(model.getCourse_image(), model.getCourse_rating(), model.getCourse_name(), 1);
                arrayList.add(detail);

            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context.getApplicationContext());
            switch (model.getCourse_name()){
                case "Fresh Apple's":
                    MainActivity.item_apple.putInt(Param.QUANTITY,1);
                    global = MainActivity.item_apple;
                    break;
                case "Amul Milk":
                    MainActivity.item_milk.putInt(Param.QUANTITY,1);
                    global = MainActivity.item_milk;
                    break;
                case "Rolex Watch":
                    MainActivity.item_watch.putInt(Param.QUANTITY,1);
                    global = MainActivity.item_watch;
                    break;
                case "Tatvic T-shirt":
                    MainActivity.item_cloth.putInt(Param.QUANTITY,1);
                    global = MainActivity.item_cloth;
                    break;
                case "Tommy Jeans":
                    MainActivity.item_cloth1.putInt(Param.QUANTITY,1);
                    global = MainActivity.item_cloth1;
                    break;
                case "iPhone 14 Pro Max":
                    MainActivity.item_mobile.putInt(Param.QUANTITY,1);
                    global = MainActivity.item_mobile;
                    break;
                case "Nike Jordan":
                    MainActivity.item_shoe.putInt(Param.QUANTITY,1);
                    global = MainActivity.item_shoe;
                    break;
                case "Lunch Box":
                    MainActivity.item_food.putInt(Param.QUANTITY,1);
                    global = MainActivity.item_food;
                    break;
                case "White Bread":
                    MainActivity.item_food1.putInt(Param.QUANTITY,1);
                    global = MainActivity.item_food1;
                    break;
                case "Balaji Snack":
                    MainActivity.item_food3.putInt(Param.QUANTITY,1);
                    global = MainActivity.item_food3;
                    break;
                default: global = MainActivity.item_apple;
                    break;
            }

            //add to cart
            Bundle itemaddcart = new Bundle(global);

            Bundle addToWishlistParams = new Bundle();
            addToWishlistParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
            addToWishlistParams.putDouble(FirebaseAnalytics.Param.VALUE, 1 * model.getCourse_rating());
            addToWishlistParams.putString("custom_app_id",MainActivity.appid);
            addToWishlistParams.putString("GA3","true");
            addToWishlistParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]{itemaddcart});
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, addToWishlistParams);

//***********************************Add to cart GA3****************************************************

            Bundle ecommerceBundle = new Bundle(addToWishlistParams);
            ecommerceBundle.putString("custom_app_id",MainActivity.appid);
            ecommerceBundle.putBundle("items", global);
            mFirebaseAnalytics.logEvent(Event.ADD_TO_CART, ecommerceBundle);
        });
    }
    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView courseIV;
        private final TextView courseNameTV;
        private final TextView courseRatingTV;
        ImageButton p_butt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseIV = itemView.findViewById(R.id.idIVCourseImage);
            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
            courseRatingTV = itemView.findViewById(R.id.idTVCourseRating);
            p_butt = itemView.findViewById(R.id.p_add);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                CourseModel model = courseModelArrayList.get(position);
                Intent intent = new Intent(view.getContext(), product_detail.class);
                Bundle bundle = new Bundle();
                bundle.putString("ITEM_NAME", model.getCourse_name());
                bundle.putInt("ITEM_RATING", model.getCourse_rating());
                bundle.putInt("ITEM_IMAGE", model.getCourse_image());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
                mFirebaseAnalytics = FirebaseAnalytics.getInstance(context.getApplicationContext());
                switch (model.getCourse_name()){
                    case "Fresh Apple's": global = MainActivity.item_apple;
                        d="The apple is a deciduous tree";
                    break;
                    case "Amul Milk": global = MainActivity.item_milk;
                        d="Amul Milk is the most hygienic liquid milk available in the market.";
                        break;
                    case "Rolex Watch": global = MainActivity.item_watch;
                        d="Rolex is the leading name in luxury wristwatches.";
                        break;
                    case "Tatvic T-shirt": global = MainActivity.item_cloth;
                        d="A T-shirt (also spelled tee-shirt or tee shirt).";
                        break;
                    case "Tommy Jeans": global = MainActivity.item_cloth1;
                        d="Inspired by American denim classics with a modern, casual edge.";
                        break;
                    case "iPhone 14 Pro Max": global = MainActivity.item_mobile;
                        d="The iPhone 14 Pro Max measures 160.70 x 77.60 x 7.85mm";
                        break;
                    case "Nike Jordan": global = MainActivity.item_shoe;
                        d="Air Jordan is a line of basketball shoes";
                        break;
                    case "Lunch Box": global = MainActivity.item_food3;
                        d="A lunch box (alt. spelling lunchbox) refers to a hand-held container";
                        break;
                    case "White Bread": global = MainActivity.item_food1;
                        d="White bread typically refers to breads ";
                        break;
                    case "Balaji Snack": global = MainActivity.item_food3;
                        d="Balaji Wafers is a major snack food manufacturer and distributor in Gujarat.";
                        break;
                    default: global = MainActivity.item_apple;
                        break;
                }

                //select_product
                Bundle selectItemParams = new Bundle();
                selectItemParams.putString("custom_app_id",MainActivity.appid);
                selectItemParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                        new Parcelable[]{global});
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, selectItemParams);

                //details_product
                Bundle viewItemParams = new Bundle();
                viewItemParams.putString("custom_app_id",MainActivity.appid);
                viewItemParams.putString(FirebaseAnalytics.Param.VALUE, d);
                viewItemParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                        new Parcelable[]{global});
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, viewItemParams);

//************************************* Select/detail GA3**************************************************

                Bundle select_pro_bun = new Bundle();
                select_pro_bun.putString(Param.VALUE,d);
                select_pro_bun.putString("custom_app_id",MainActivity.appid);
                select_pro_bun.putString("GA3","true");
                select_pro_bun.putBundle("items",global);

                mFirebaseAnalytics.logEvent(Event.SELECT_CONTENT,select_pro_bun);

                mFirebaseAnalytics.logEvent(Event.VIEW_ITEM,select_pro_bun);
            }
    }
}
}

