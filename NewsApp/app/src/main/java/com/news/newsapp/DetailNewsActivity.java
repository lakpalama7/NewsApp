package com.news.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailNewsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private WebView webView;
    private Intent intent;
    private String title,url,source;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        //getSupportActionBar().setTitle("Details News");
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar=findViewById(R.id.progressbar1);
        webView=findViewById(R.id.webView);

        intent=getIntent();
        if(intent!=null){
            title=intent.getStringExtra("title");
            url=intent.getStringExtra("url");
            source=intent.getStringExtra("source");
            getSupportActionBar().setTitle(source);

            loadNewsInfo();

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.share){
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_SUBJECT,source);
            String message=title +"\n" +url +"\n"+"Share from the News App "+"\n";
            intent.putExtra(Intent.EXTRA_TEXT,message);
            startActivity(Intent.createChooser(intent,"Share with: "));

        }

        return super.onOptionsItemSelected(item);
    }

    private void loadNewsInfo() {
        /*  Glide.with(this)  //2
                .load(intent.getStringExtra("image")) //3
                .into(image); //8*/
      //  webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new MyWebClient(this,progressBar));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
      //  progressBar.setVisibility(View.GONE);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
