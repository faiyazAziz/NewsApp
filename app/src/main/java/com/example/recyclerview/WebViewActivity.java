package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {
    Intent fromAct;
    WebView webView;
    ProgressBar pgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        fromAct = getIntent();
        webView = (WebView) findViewById(R.id.webView);
        pgBar = (ProgressBar) findViewById(R.id.pgBar);

        if (fromAct!=null) {

            String newsUrl = fromAct.getStringExtra("url");
            webView.loadUrl(newsUrl);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    pgBar.setVisibility(View.GONE);
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    pgBar.setVisibility(View.GONE);
                    super.onPageFinished(view, url);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}