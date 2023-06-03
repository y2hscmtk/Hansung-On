package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AdapterView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

//
public class MainActivity extends AppCompatActivity {

    Fragment mainFragment;
    Fragment searchFragment;
    Fragment detailFragment;

    ArrayList<Movie> metaData = new ArrayList<>();

    ArrayList<Credit2> creditData = new ArrayList<>();


    Credit2[] creditArray;

    //원본 데이터 불러오기
    //gson라이브러리 이용하여 json파일로 변환한 csv파일의 데이터 읽어서 객체 생성
    //생성한 객체들 metaData에 저장
    private void initMetadata() {
        Gson gson = new Gson();
        try {
            //getAssets()은 context의 메소드이기 때문에 getContext()로 연결해야함
            //metadata.json은 vote_count를 기준으로 선별된 top100의 데이터를 json파일로 생성한 것임
            InputStream is = getAssets().open("metadata_add_similar_movies.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            //JSONObject jsonObject = new JSONObject(json);
            //영화 객체 리스트에 불러온 json파일의 영화 객체들 삽입
            Movie[] movie_array = gson.fromJson(json,Movie[].class);
            Log.d("영화 생성","생성됨");

            //유사 영화 파싱
            for(Movie movie:movie_array){
                movie.setSimilarMovieList(); //유사 영화 리스트 초기화
                ArrayList<String> similarMovie = movie.getSimilarMovieList(); //래퍼랜스 가져오기
                String similar_movies = movie.getSimilar_movies();
                //앞 뒤 []제거
                similar_movies = similar_movies.trim().substring(1, similar_movies.length() - 1);
                //작음 따음표 제거
                similar_movies = similar_movies.replace("'", "");

                String[] array = similar_movies.split(", "); //영화별 분리하여 array에 삽입
                /*Log.d("유사영화 파싱",array[0]);*/
                //리뷰 리스트에 삽입
                for(String review:array){
                    similarMovie.add(review); //리뷰들 삽입
                }
            }


            //장르 파싱
            ArrayList<String> genreList;
            for(Movie movie:movie_array) {
                movie.setGenreList(); //장르 리스트 초기화
                //장르 리스트에 대한 래퍼랜스 받아오기
                genreList = movie.getGenreList();
                JsonArray jsonArray = JsonParser.parseString(movie.getGenres()).getAsJsonArray();
                Log.d("확인", Integer.toString(jsonArray.size()));
                for (JsonElement jsonElement : jsonArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    String name = jsonObject.get("name").getAsString();
                    genreList.add(name);
                }
                //Log.d("파싱",genreList.get(0));
            }

            //리뷰 파싱
            for(Movie movie:movie_array){
                movie.setReviewList(); //리뷰 리스트 초기화
                ArrayList<String> reviewList = movie.getReviewList(); //래퍼랜스 가져오기
                String reviews = movie.getReview();
                String[] array = reviews.split(", "); //리뷰별 분리하여 array에 삽입
                Log.d("리뷰 파싱","성공");
                //리뷰 리스트에 삽입
                for(String review:array){
                    reviewList.add(review); //리뷰들 삽입
                }
            }

            //ArrayList에 영화 객체들 저장
            metaData.addAll(Arrays.asList(movie_array));

/*            //영화 20개 추려서 새로운 ArrayList 생성
            movieArrayList = new ArrayList<>(movie_list.subList(0, 20));*/

        } catch (Exception e) {
            Log.d("영화 미스","에러"+e.getLocalizedMessage());

            e.printStackTrace();
        }
    }


    //감독, 출연진에 대한 데이터 불러와서 초기화하는 함수
    private void initCreditList() {
        Gson gson = new Gson();
        try {
            InputStream is = getAssets().open("credits2.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            //JSONObject jsonObject = new JSONObject(json);
            //Credit 객체 리스트에 불러온 json파일의 Credit 객체들 삽입
            creditArray = gson.fromJson(json,Credit2[].class);
            Log.d("크래딧 생성","성공 사이즈 : "+creditArray.length);
            Log.d("크래딧 생성","성공 주연 : "+creditArray[0].getDirector());
            Log.d("크래딧 생성","성공 주연 : "+creditArray[0].getLeading_role());
            Log.d("크래딧 생성","성공 주연 : "+creditArray[0].getId());


            //ArrayList에 crewList저장
            //creditData.addAll(Arrays.asList(creditArray));
            //Log.d("크래딧 생성2","삽입 사이즈 : ");


        } catch (Exception e) {
            if (e != null) {
                String errorMessage = e.getLocalizedMessage();
                if (errorMessage != null) {
                    Log.d("크래딧 미스", "에러" + errorMessage);
                }
            }
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //메타 데이터 초기화
        initMetadata();
        Log.d("메타","사이즈"+metaData.size());
        //크래딧 데이터 초기화
        initCreditList();
        Log.d("어레이 ","사이즈"+creditArray.length);


        //영화 페이지 => 인기 영화 Top20, 사용자의 선택에 따라 장르별 Top20 보여주기
        mainFragment = new MainFragment(metaData,creditArray);
        //검색 페이지
        searchFragment = new SearchFragment(metaData,creditArray);
        //영화 상세보기 페이지 => 평균 평점이 가장 높은 영화를 보여줌
        detailFragment = new DetailFragment(metaData,creditArray);
        
        // 초기화면 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
        // 하단 네비게이션 바 붙이기
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        
        //탭 누르면 해당하는 프레그먼트롷 화면 전환
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_home: //영화 페이지
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
                    return true;

                case R.id.tab_search: //검색 페이지
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
                    return true;

                case R.id.tab_detail: //리뷰 페이지
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, detailFragment).commit();
                    return true;
            }
            return true;
        });
    }
}