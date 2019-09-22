package com.news.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.news.newsapp.api.ApiClient;
import com.news.newsapp.api.ApiInterface;
import com.news.newsapp.models.Article;
import com.news.newsapp.models.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Top-HeadLines");
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        addNewFragment("Top HeadLines");
        // LoadJson();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.headlines:
                addNewFragment("Top HeadLines");

                return true;
            case R.id.science:
                addNewFragment("science");
                return true;
            case R.id.technology:
               /* TechnologyFragment technologyFragment=new TechnologyFragment();
                fragmentManager.beginTransaction().replace(R.id.framelayout,technologyFragment,"Technology").commit();
                getSupportActionBar().setTitle("Technology");
                drawerLayout.closeDrawers();

                return true;*/
                addNewFragment("technology");
                return true;
            case R.id.business:
                addNewFragment("business");
                return true;
            case R.id.sports:
                addNewFragment("sports");

                return true;

            case R.id.entertainment:
                addNewFragment("entertainment");

                return true;
            case R.id.health:
                addNewFragment("health");
                return true;
            default:
                return false;
        }
    }





    private void addNewFragment(String title) {
        TopHeadlinesFragment topHeadlinesFragment = new TopHeadlinesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putString("newstopics", title);
        topHeadlinesFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.framelayout, topHeadlinesFragment).commit();
        getSupportActionBar().setTitle(title.toUpperCase());
        drawerLayout.closeDrawers();
    }
}
