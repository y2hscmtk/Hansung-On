package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

//메인 페이지 => 상위 영화 20개 보여주기
//사용자의 선택에 따라 장르별 영화20개 보여주는 기능 추가
//초기엔 인기순(vote_count) Top 20
public class MainFragment extends Fragment {

    //원본 데이터
    ArrayList<Movie> metaData;

    Credit2[] creditArray;

    //vote_count기준 영화 100개 데이터 생성자로 받아옴
    public MainFragment(ArrayList<Movie> metaData, Credit2[] creditArray){
        this.metaData =metaData;
        this.creditArray = creditArray;
    }

    //영화20개 추려서 리턴(기본값) => 평점 평균순으로
    private ArrayList<Movie> getMovieListVoteAverage() {
        //리턴용 객체
        ArrayList<Movie> movieArrayList;

        //영화 20개 추려서 새로운 ArrayList 생성
        movieArrayList = new ArrayList<>(metaData.subList(0, 20));
            
        return movieArrayList; //생성한 영화 객체 리스트 리턴
    }

    //vote_count순으로 재정렬
    private ArrayList<Movie> getVoteCountList() {
        ArrayList<Movie> voteList = new ArrayList<>();

        //값 전부 복사하여 새로운 배열 생성 => 원본을 수정하지 않기 위해서
        voteList.addAll(metaData);

        //투표수 기준으로 재 정렬
        Collections.sort(voteList,new VoteCountComparator());

        //20개 추려서 새로운 리스트 생성
        voteList = new ArrayList<>(voteList.subList(0, 20));

        return voteList;
    }


    //인기순으로 ArrayList에 담아서 리턴
    //Popularity를 기준으로 정렬
    private ArrayList<Movie> getMovieListPop() {
        ArrayList<Movie> popMovieList = new ArrayList<>();

        //값 전부 복사하여 새로운 배열 생성 => 원본을 수정하지 않기 위해서
        popMovieList.addAll(metaData);

        //인기도를 기준으로 재 정렬
        Collections.sort(popMovieList,new PopMovieComparator());

        //20개 추려서 새로운 리스트 생성
        popMovieList = new ArrayList<>(popMovieList.subList(0, 20));

        return popMovieList;
    }

    //매개변수로 입력받은 장르에 맞추 리스트 반환
    private ArrayList<Movie> getGenreList(String genre){
        ArrayList<Movie> genreArrayList = new ArrayList<>();

        //장르 탐색
        for(int i=0;i<metaData.size();i++){
            Movie movie = metaData.get(i);
            //해당 영화 객체의 genreList를 돌면서 탐색
            ArrayList<String> movieGenreList = movie.getGenreList();
            for(int j=0;j<movieGenreList.size();j++){
                //매개변수로 입력받은 장르와 일치하는 장르를 보유중인 영화 발견시
                if(movieGenreList.get(j).equals(genre)){
                    genreArrayList.add(movie);
                    break;
                }
            }

        }

        return genreArrayList;
    }


    //정렬의 기준을 위한 클래스 작성
    class PopMovieComparator implements Comparator<Movie> {
        @Override
        public int compare(Movie a,Movie b){
            BigDecimal popA = a.getPopularity();
            BigDecimal popB = b.getPopularity();
            //비교값
            int compareResult = popA.compareTo(popB);

            //a의 pop값이 b의 pop보다 작은 경우
            if(compareResult<0){
                return 1;
            }
            else if(compareResult>0){ //a의 값이 b보다 큰 경우
                return -1;
            }
            else{ //같은 경우
                return 0;
            }
        }
    }

    //vote_count순으로 비교하기위해
    class VoteCountComparator implements Comparator<Movie> {
        @Override
        public int compare(Movie a,Movie b){
            Long vote_A = a.getVote_count();
            Long vote_B = b.getVote_count();

            if(vote_A<vote_B){
                return 1;
            }
            else if(vote_A>vote_B){ //a의 값이 b보다 큰 경우
                return -1;
            }
            else{ //같은 경우
                return 0;
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        // Inflate the layout for this fragment
        MovieRecyclerAdapter movieAdapter; //리사이클러뷰 어뎁터
        RecyclerView recyclerView; //리사이클러뷰
        
        //vote_count순 영화 리스트 생성
        ArrayList<Movie> movieListVoteCount = getVoteCountList();
        
        //평점순 영화 리스트 생성
        ArrayList<Movie> movieListVoteAverage = getMovieListVoteAverage();
        //Popularity순 영화 리스트 생성(인기순)
        ArrayList<Movie> movieListPopularity = getMovieListPop();
        //판타지 영화 리스트 생성
        ArrayList<Movie> fantasyMovieList = getGenreList("Fantasy");
        //액션 영화 리스트 생성
        ArrayList<Movie> actionMovieList = getGenreList("Action");
        //스릴러 영화 리스트 생성
        ArrayList<Movie> thrillerMovieList = getGenreList("Thriller");


        //화면에 띄울 리사이클러뷰
        recyclerView = view.findViewById(R.id.recyclerview);
        
        //영화 리사이클러뷰 어뎁터 생성
        movieAdapter = new MovieRecyclerAdapter(getContext(),metaData);
        //어뎁터에 생성한 영화 데이터모델 삽입(기본 설정은 vote_count를 기준으로)
        movieAdapter.setMovieArrayList(movieListVoteCount,creditArray);
        //리사이클러뷰에 어뎁터 붙이기
        recyclerView.setAdapter(movieAdapter);


        //각 버튼에 대한 참조
        TextView goPopButton = (TextView) view.findViewById(R.id.btn_pop);
        TextView goVoteCountButton = (TextView) view.findViewById(R.id.btn_voteCount);
        TextView goActionButton = (TextView) view.findViewById(R.id.btn_action);
        TextView goFantasyButton = (TextView) view.findViewById(R.id.btn_fantasy);
        TextView goVoteAverageButton = (TextView) view.findViewById(R.id.btn_vc);
        TextView goThrillerButton = (TextView) view.findViewById(R.id.btn_Thriller);


        //인기순 버튼을 눌렀을 경우 => 새로운 arrayList를 어뎁터에 연결
        goPopButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                //어뎁터에 생성한 데이터모델 삽입
                movieAdapter.setMovieArrayList(movieListPopularity,creditArray);
                /*recyclerView.setAdapter(movieAdapter);*/
                
                //선택된 버튼을 제외하고 나머지는 색상 회색으로
                goVoteAverageButton.setTextColor(Color.GRAY);
                goVoteCountButton.setTextColor(Color.GRAY);
                goPopButton.setTextColor(Color.WHITE);
                goFantasyButton.setTextColor(Color.GRAY);
                goActionButton.setTextColor(Color.GRAY);
                goThrillerButton.setTextColor(Color.GRAY);

            }
        });

        //투표개수 버튼을 눌렀을 경우 => 새로운 arrayList를 어뎁터에 연결
        goVoteCountButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                //어뎁터에 생성한 데이터모델 삽입
                movieAdapter.setMovieArrayList(movieListVoteCount,creditArray);
                /*recyclerView.setAdapter(movieAdapter);*/

                //선택된 버튼을 제외하고 나머지는 색상 회색으로
                goVoteAverageButton.setTextColor(Color.GRAY);
                goVoteCountButton.setTextColor(Color.WHITE);
                goPopButton.setTextColor(Color.GRAY);
                goFantasyButton.setTextColor(Color.GRAY);
                goActionButton.setTextColor(Color.GRAY);
                goThrillerButton.setTextColor(Color.GRAY);

            }
        });


        //추천 순 버튼을 눌렀을때
        goVoteAverageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //어뎁터에 생성한 데이터모델 삽입
                movieAdapter.setMovieArrayList(movieListVoteAverage,creditArray);
                /*recyclerView.setAdapter(movieAdapter);*/

                //선택된 버튼을 제외하고 나머지는 색상 회색으로
                goVoteCountButton.setTextColor(Color.GRAY);
                goVoteAverageButton.setTextColor(Color.WHITE);
                goPopButton.setTextColor(Color.GRAY);
                goFantasyButton.setTextColor(Color.GRAY);
                goActionButton.setTextColor(Color.GRAY);
                goThrillerButton.setTextColor(Color.GRAY);
            }
        });

        //판타지 버튼을 눌렀을때
        goFantasyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //어뎁터에 생성한 데이터모델 삽입
                movieAdapter.setMovieArrayList(fantasyMovieList,creditArray);
                /*recyclerView.setAdapter(movieAdapter);*/

                //선택된 버튼을 제외하고 나머지는 색상 회색으로
                goVoteCountButton.setTextColor(Color.GRAY);
                goVoteAverageButton.setTextColor(Color.GRAY);
                goPopButton.setTextColor(Color.GRAY);
                goFantasyButton.setTextColor(Color.WHITE);
                goActionButton.setTextColor(Color.GRAY);
                goThrillerButton.setTextColor(Color.GRAY);

            }
        });


        //액션 버튼을 눌렀을때
        goActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //어뎁터에 생성한 데이터모델 삽입
                movieAdapter.setMovieArrayList(actionMovieList,creditArray);
                /*recyclerView.setAdapter(movieAdapter);*/

                //선택된 버튼을 제외하고 나머지는 색상 회색으로
                goVoteCountButton.setTextColor(Color.GRAY);
                goVoteAverageButton.setTextColor(Color.GRAY);
                goPopButton.setTextColor(Color.GRAY);
                goFantasyButton.setTextColor(Color.GRAY);
                goActionButton.setTextColor(Color.WHITE);
                goThrillerButton.setTextColor(Color.GRAY);

            }
        });

        //스릴러 버튼을 눌렀을때

        goThrillerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //어뎁터에 생성한 데이터모델 삽입
                movieAdapter.setMovieArrayList(thrillerMovieList,creditArray);
                /*recyclerView.setAdapter(movieAdapter);*/

                //선택된 버튼을 제외하고 나머지는 색상 회색으로
                goVoteCountButton.setTextColor(Color.GRAY);
                goVoteAverageButton.setTextColor(Color.GRAY);
                goPopButton.setTextColor(Color.GRAY);
                goFantasyButton.setTextColor(Color.GRAY);
                goActionButton.setTextColor(Color.GRAY);
                goThrillerButton.setTextColor(Color.WHITE);

            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),RecyclerView.VERTICAL,false));
        return view;
    }


}