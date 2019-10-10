package com.news.newsapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.news.newsapp.api.ApiClient;
import com.news.newsapp.api.ApiInterface;
import com.news.newsapp.models.Article;
import com.news.newsapp.models.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopHeadlinesFragment extends Fragment {
    public static final String API_KEY="<<use your API KEY here >>";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articleList=new ArrayList<>();
    private CustomAdapter adapter=null;
    private String TAG=MainActivity.class.getSimpleName();
    private ProgressBar progressBar;

    private LinearLayout error_linearLayout;
    private ImageView error_imageView;
    private TextView errorTitle,errorMessage;
    private Button retry;
    public TopHeadlinesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_top_headlines, container, false);
        progressBar=view.findViewById(R.id.progressbar1);
        recyclerView=view.findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        error_linearLayout=view.findViewById(R.id.error_linearlayout);
        error_imageView=view.findViewById(R.id.errorimage);
        errorTitle=view.findViewById(R.id.errortitle);
        errorMessage=view.findViewById(R.id.errormessage);
        retry=view.findViewById(R.id.retry);

        LoadJson();



        return view;

    }

    public void LoadJson(){
        String newstopics=getArguments().getString("newstopics");
        progressBar.setVisibility(View.VISIBLE);
        error_linearLayout.setVisibility(View.GONE);
        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        String country=Utils.getCountry();
        Call<News> call=null;

        if(newstopics.equals("Top HeadLines")) {
            call = apiInterface.getNews(country, API_KEY);
        }
        else{
            call=apiInterface.getCategoryNews(country,newstopics,API_KEY);

        }
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful() && response.body().getArticleList()!=null){
                    if(!articleList.isEmpty()){
                        articleList.clear();
                    }
                    articleList=response.body().getArticleList();
                    adapter=new CustomAdapter(articleList,getContext());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    initListener();

                }
                else{
                    progressBar.setVisibility(View.GONE);
                    String errorCode="";
                    switch (response.code()){
                        case 404:
                            errorCode="404 Not Found ";
                            break;
                        case 500:
                            errorCode="500 Server Error";
                            break;
                            default:
                                errorCode="Unknown Error";
                    }
                    showerror(R.drawable.connectionerror,"No Result ","Please Try Again \n "+ errorCode);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showerror(R.drawable.connectionerror,"No Result ","Please Try Again \n"+t.toString());
            }
        });
    }

    private  void showerror(int imageview,String title, String message){
        if(error_linearLayout.getVisibility()==View.GONE){
            error_linearLayout.setVisibility(View.VISIBLE);
        }
        error_imageView.setImageResource(imageview);
        errorTitle.setText(title);
        errorMessage.setText(message);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadJson();
            }
        });

    }

    private void initListener(){
        adapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getContext(),DetailNewsActivity.class);
                intent.putExtra("title",articleList.get(position).getTitle());
                intent.putExtra("source",articleList.get(position).getSource().getName());
                intent.putExtra("url",articleList.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

}
