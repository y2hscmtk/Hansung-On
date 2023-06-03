package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//영화 상세보기 페이지
public class DetailFragment extends Fragment {


    ArrayList<Movie> metaData;

    //크래딧어레이
    Credit2[] creditArray;

    // Movie 객체들을 영화 이름을 키로 하는 맵 생성
    Map<String, Movie> movieMap = new HashMap<>();
    
    //매개변수로 원본 데이터 받아와서 초기화
    public DetailFragment(ArrayList<Movie> metaData,Credit2[] creditArray){
        this.metaData = metaData;
        this.creditArray =creditArray;
        Log.d("디테일", String.valueOf(creditArray.length));
    }

    public void initMap(){
        //Map에 저장 => 유사 영화를 위해
        for (Movie movie : metaData) {
            String title = movie.getTitle();
            movieMap.put(title, movie);
        }
        Log.d("테스트", String.valueOf(metaData.size()));
    }

    //영화 제목
    private TextView titleView;
    //평균 평점
    private TextView voteAverage;
    //플레이타임
    private TextView runTime;
    //시청연령
    private TextView adult;
    //언어
    private TextView language;
    //오버뷰
    private TextView overview;
    //포스터
    private ImageView poster;
    //투표수
    private TextView voteCount;
    //투표버튼
    private ImageView voteBtn;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        //맵 초기화
        initMap();

        //참조 가져오기
        titleView = view.findViewById(R.id.m_title);
        overview = view.findViewById(R.id.m_overiew);
        runTime = view.findViewById(R.id.m_runTime);
        voteAverage = view.findViewById(R.id.m_vote_average);
        adult = view.findViewById(R.id.m_adult);
        language = view.findViewById(R.id.m_language);
        poster = view.findViewById(R.id.big_poster);
        voteCount = view.findViewById(R.id.vote_count);
        voteBtn = view.findViewById(R.id.btn_vote);


        int index = 0;
        Movie movie = metaData.get(index);
        //아무것도 선택되지 않았으면 가장 인기높은 영화를 화면에 뿌린다. => 0번째 인덱스


        // Bundle에서 전달된 데이터 추출
        Bundle bundle = getArguments();

        if (bundle != null) {
            String movieId = bundle.getString("movieId");
            Log.d("bundle get",movieId);

            //맵을 이용하여 영화 참조 가져오기

            String movieTitle = bundle.getString("movieTitle");
            Log.d("movieTitle",movieTitle);
            movie = movieMap.get(movieTitle);
            //index = Integer.parseInt(bundle.getString("moveIndex"));
            //movie = movieMap.get(movieId);

            /*//반복문 돌면서 일치하는 영화 찾기
            for(Movie testMovie : metaData){
                if(testMovie.getId()==Integer.parseInt(movieId)){
                    movie = testMovie;
                    break;
                }
            }*/
        }

        Log.d("영화 체크",movie.getTitle());

        //유사 영화 테스트
        TextView similarMovie = (TextView)view.findViewById(R.id.similar_movies);

        //유사 영화 목록 가져오기
        ArrayList<String> similarMovieList = movie.getSimilarMovieList();



        /*String imageName = "img_" + item.getId();

        // 이미지 리소스 아이디 가져오기
        int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        poster.setImageResource(resourceId);
        * */
        //포스터
        String imageName = "img_"+movie.getId();
        int resourceId = getContext().getResources().getIdentifier(imageName, "drawable", getContext().getPackageName());
        poster.setImageResource(resourceId);


        //투표수
        voteCount.setText(Long.toString(movie.getVote_count()));

        //투표 버튼 눌렸을때 이벤트
        Movie finalMovie = movie;
        final boolean[] notClick = {true};
        voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notClick[0]) {
                    finalMovie.setVote_count(finalMovie.getVote_count() + 1);
                    notClick[0] = false; //한번만 클릭하게
                    voteCount.setText(Long.toString(finalMovie.getVote_count()));
                    voteBtn.setColorFilter(Color.rgb(244,88,89));
                    voteCount.setTextColor(Color.rgb(244,88,89));
                }
            }
        });

        //영화 제목
        titleView.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        //영화 평점 vote_average
        voteAverage.setText(movie.getVote_average().toString());
        //플레이타임
        runTime.setText(Long.toString(movie.getRuntime()));
        //시청연령

        if(movie.getAdult()) {
            adult.setText("Adult");
            Log.d("성인","성인");
        }
        else
            adult.setText("All ages"); //전연령
        //언어
        language.setText(movie.getOriginal_language());
        //오버뷰
        overview.setText(movie.getOverview());

        //감독, 배우
        Long id = movie.getId();

        Credit2 credit = null;
        for(Credit2 c : creditArray){
            Log.d("credit반복문","반복시작"+c.getId());
            if(c.getId().equals(id)){
                Log.d("credit반복문","찾음"+String.valueOf(c.getId()));
                credit = c;
                break;
            }
        }
        Log.d("credit반복문",credit.getDirector());



        TextView director = (TextView)view.findViewById(R.id.m_director);
        director.setText("Not Found");
        String name = credit.getDirector();
        Log.d("체크2",name);
//        Log.d("credit","crewList사이즈"+crewList.size());
        director.setText(name);


        TextView leading_actor = (TextView)view.findViewById(R.id.m_leading_actor);
        leading_actor.setText("Not Found");
        name = credit.getLeading_role();
        Log.d("체크2",name);
//        Log.d("credit","crewList사이즈"+crewList.size());
        leading_actor.setText(name);



        //추천영화 리사이클러뷰
        RecommandMovieRecyclerAdapter recommandAdapter; //리사이클러뷰 어뎁터(추천영화 리스트)
        //리뷰 리사이클러뷰 어뎁터 생성
        recommandAdapter = new RecommandMovieRecyclerAdapter(getContext(),movieMap);
        //추천영화 리사이클러뷰 참조 가져오기
        RecyclerView recommandReCyclerView = (RecyclerView) view.findViewById(R.id.recommend_movie_view);

        //어뎁터에 추천영화목록 붙이기
        recommandAdapter.setReviewList(similarMovieList);

        //리사이클러뷰에 어뎁터 붙이기
        recommandReCyclerView.setAdapter(recommandAdapter);

        //가로로 넘길 수 있도록 HORIZONTAL로 붙이기
        recommandReCyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),RecyclerView.HORIZONTAL,false));




        //검색 결과 리스트 생성
        /*ArrayList<Movie> resultMovieList = getMovieListVoteAverage();*/
        ReviewRecyclerAdapter reviewAdapter; //리사이클러뷰 어뎁터(리뷰 리스트)
        //리뷰 리사이클러뷰 어뎁터 생성
        reviewAdapter = new ReviewRecyclerAdapter();

        //리뷰 리사이클러뷰 참조 가져오기
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.review_view);

        //어뎁터에 리뷰 목록들 삽입
        ArrayList<String> reviewList = movie.getReviewList();


        //사용자 리뷰는 10개만 보이도록 설정
        //10개보다 많은경우 10개 이하로 추려서 재저장
        if(reviewList.size()>10)
            reviewList = new ArrayList<>(reviewList.subList(0, 10));

        reviewAdapter.setReviewList(reviewList);

        //리사이클러뷰에 어뎁터 붙이기
        recyclerView.setAdapter(reviewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),RecyclerView.VERTICAL,false));

        return view;
    }
}