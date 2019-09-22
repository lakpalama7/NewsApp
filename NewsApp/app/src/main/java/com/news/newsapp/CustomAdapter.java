package com.news.newsapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.news.newsapp.models.Article;

import org.w3c.dom.Text;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<Article> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public CustomAdapter(){}
    public CustomAdapter(List<Article> articleList, Context context) {
        this.articles = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_item_layout,parent,false);
        return new MyViewHolder(view,onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        Article model = articles.get(position);
/*
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(context)
                .load(model.getUrlToImage())
              .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.image); */

        Glide.with(context)  //2
                .load(model.getUrlToImage()) //3
                .into(holder.image); //8

        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        holder.source.setText(model.getSource().getName());
        holder.time.setText(" \u2022 " + Utils.DateToTimeFormat(model.getPublishedAt()));
        holder.publisheddate.setText(Utils.DateFormat(model.getPublishedAt()));
        holder.author.setText(model.getAuthor());
        holder.progressBar.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView author,publisheddate,title,desc,source,time;
        ImageView image;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;
        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            author=itemView.findViewById(R.id.author);
            publisheddate=itemView.findViewById(R.id.publishedate);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.desc);
            source=itemView.findViewById(R.id.source);
            time=itemView.findViewById(R.id.time);

            image=itemView.findViewById(R.id.image);
            progressBar=itemView.findViewById(R.id.progress_load_photo);
            this.onItemClickListener=onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v,getAdapterPosition());
        }
    }
}
