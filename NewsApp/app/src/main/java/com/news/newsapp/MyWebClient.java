package com.news.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MyWebClient extends WebViewClient {

    private ProgressBar progressBar;
    private Context context;
    public MyWebClient(Context context, ProgressBar progressBar){
        this.context=context;
        this.progressBar=progressBar;
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String request) {
      //  progressBar.setVisibility(View.VISIBLE);
        view.loadUrl(request);
        return true;

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
      progressBar.setVisibility(View.GONE);
    }
}
