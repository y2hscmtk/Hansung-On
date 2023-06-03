package com.example.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class RecommandMovieRecyclerAdapter extends RecyclerView.Adapter<RecommandMovieRecyclerAdapter.ViewHolder> {


    private ArrayList<String> recommedMovieList;
    private Context context;
    private Map<String, Movie> movieMap;

    //이미지 아이디값에 직접 접근하기 위해 context를 받고
    //영화 이름으로 영화에 직접 접근하기 위해 Map을 받아준다.
    public RecommandMovieRecyclerAdapter(Context context, Map<String, Movie> movieMap){
        this.context = context;
        this.movieMap = movieMap;
    }


    @NonNull
    @Override
    public RecommandMovieRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommed_movie_view, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecommandMovieRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(recommedMovieList.get(position));
    }

    public void setReviewList(ArrayList<String> reviewList){
        this.recommedMovieList = reviewList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return recommedMovieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster_icon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster_icon = (ImageView) itemView.findViewById(R.id.recommend_movie_poster);
        }

        void onBind(String item){

            //영화 포스터 붙이기
            //영화 이름을 가져온 상태 => 영화에 대한 참조로 변경
            Movie movie = movieMap.get(item);


            //포스터 붙이기
            //영화 포스터 이미지 변경
            //String path = "R.drawable.img_" + item.getId();
            String imageName = "img_" + movie.getId();

            // 이미지 리소스 아이디 가져오기
            int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            poster_icon.setImageResource(resourceId);
        }
    }
}
