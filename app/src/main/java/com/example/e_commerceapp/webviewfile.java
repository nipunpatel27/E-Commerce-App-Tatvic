package com.example.e_commerceapp;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.analytics.Tracker;

public class webviewfile extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mWebView.addJavascriptInterface(
                    new AnalyticsWebInterface(this), AnalyticsWebInterface.TAG);
        } else {
            Log.w(TAG, "Not adding JavaScriptInterface, API Version: " + Build.VERSION.SDK_INT);
        }
        mWebView.loadUrl("https://e-commerce-700a0.web.app/");
    }
}
