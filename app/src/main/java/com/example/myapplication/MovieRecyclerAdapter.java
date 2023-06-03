package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//리사이클러뷰 정의
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Map;

//
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private ArrayList<Movie> movieArrayList;

    private ArrayList<Movie> metaData;

    //크래딧어레이
    Credit2[] creditArray;

    private Context context;

    public MovieRecyclerAdapter(Context context,ArrayList<Movie> metaData){
        this.context = context;
        this.metaData = metaData;
    }

    @NonNull
    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(movieArrayList.get(position));
    }

    //영화 객체 리스트를 받아옴
    public void setMovieArrayList(ArrayList<Movie> list,Credit2[] creditArray){
        this.movieArrayList = list;
        notifyDataSetChanged();
        this.creditArray = creditArray;
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    //값을 할당해주는 클래스(뷰 홀더)
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView title;
        TextView overview;
        TextView genres ;
        ImageView poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.movie_id); //영화 아이디
            title = (TextView) itemView.findViewById(R.id.movie_title); //영화 제목
            overview = (TextView) itemView.findViewById(R.id.movie_overview); //줄 거리
            genres  = (TextView) itemView.findViewById(R.id.movie_generes); //장르
            poster = (ImageView) itemView.findViewById(R.id.movie_poster); //영화 포스터

            //뷰 클릭 이벤트 작성
            //=> 영화 클릭시 해당 정보를 갖고 리뷰 페이지로 이동
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 클릭된 아이템의 위치(position) 정보
                    int position = getAdapterPosition();
                    // 해당 위치의 영화 정보 가져오기
                    Movie clickedMovie = movieArrayList.get(position);
                    //클릭한 영화의 아이디 가져오기
                    Long movieID = clickedMovie.getId();
                    Log.d("bundle id", String.valueOf(movieID));
                    if (v.getContext() instanceof MainActivity) {
                        //메인 엑티비티 참조 가져오기
                        MainActivity mainActivity = (MainActivity) v.getContext();

                        // Fragment 전환을 위한 코드 작성
                        // => DetailFragment로 전환
                        //movieArrayList
                        Log.d("바인딩 이벤트", String.valueOf(metaData.size()));

                        DetailFragment detailFragment = new DetailFragment(metaData,creditArray);
                        // 전환에 필요한 데이터를 Bundle로 전달
                        Bundle bundle = new Bundle();
                        // 클릭한 영화의 이름 번들로 보내주기
                        bundle.putString("movieTitle", clickedMovie.getTitle());
                        // 클릭한 영화의 아이디를 번들에 삽입
                        bundle.putString("movieId", String.valueOf(movieID));
                        Log.d("클릭",String.valueOf(movieID));
                        detailFragment.setArguments(bundle);

                        // Fragment 전환
                        mainActivity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, detailFragment)
                                .addToBackStack(null)
                                .commit();

                        //하단바 색 변경

                        //왼쪽에서부터 tab_home,tab_search,tab_detail
                        BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottom);
                        // 하단바 아이템을 선택 상태로 변경
                        bottomNavigationView.getMenu().findItem(R.id.tab_home).setChecked(false);
                        bottomNavigationView.getMenu().findItem(R.id.tab_detail).setChecked(true);


                    }
                }
            });
        }

        void onBind(Movie item){
            //장르 리스트 가져오기
            ArrayList<String> genreList = item.getGenreList();
            String genresText = "";
            int i =0;
            for(i=0;i<genreList.size()-1;i++){
                genresText += (genreList.get(i) + ", ");
            }
            genresText += genreList.get(i); //마지막 값은 ,없이
            //String genresText = genreList.get(0);
            //String test = item.getTest();

            //영화 포스터 이미지 변경
            //String path = "R.drawable.img_" + item.getId();
            String imageName = "img_" + item.getId();

            // 이미지 리소스 아이디 가져오기
            int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            poster.setImageResource(resourceId);
            genres.setText(genresText);
            //genres.setText(test);
            id.setText("#" + Long.toString(item.getId()));
            title.setText(item.getTitle());
            overview.setText(item.getOverview());


        }
    }
}