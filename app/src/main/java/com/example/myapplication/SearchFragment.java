package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//영화 검색 페이지
// - 최대 반환되는 영화의 개수는 5개이다.
// - 검색된 영화를 터치하면 영화 상세보기 페이지로 이동할 수 있어야 한다.
public class SearchFragment extends Fragment {

    //원본 데이터
    ArrayList<Movie> metaData;

    //크래딧어레이
    Credit2[] creditArray;

    //매개변수로 원본 데이터 받아와서 vote_average순으로 재정렬
    public SearchFragment(ArrayList<Movie> metaData,Credit2[] creditArray){
        this.metaData = metaData;

        this.creditArray = creditArray;
        Log.d("생성자 호출(search) ","사이즈"+creditArray.length);
        //vote_average순으로 재정렬
        Collections.sort(metaData,new VoteAverageMovieComparator());

    }

    //화면에 보여줄 결과 데이터
    ArrayList<Movie> resultArrayList = new ArrayList<>();


    //정렬의 기준을 위한 클래스 작성
    class VoteAverageMovieComparator implements Comparator<Movie> {
        @Override
        public int compare(Movie a,Movie b){

            //vote_average값 가져오기
            BigDecimal standardA = a.getVote_average();
            BigDecimal standardB = b.getVote_average();
            //비교값
            int compareResult = standardA.compareTo(standardB);

            //a의 vote_average값이 b의 vote_average값보다 작은 경우
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



    //입력받은 검색어를 이용하여 resultArrayList값 할당
    //최대 5개까지 반환
    public void search(String query){

        Log.d("search","호출됨");
        Movie matchMovie;
        //metaData를 돌면서 해당하는 값이 제목에 있는지 확인
        for(Movie movie : metaData){
            //contains를 이용하여 원하는 값이 포함되어 있는지 판단
            if(movie.getTitle().contains(query)){
                resultArrayList.add(movie); //원하는 값 찾으면 resultArray에 삽입
            }
        }

        //일치하는 감독,배우가 있는지 확인
        int id = -1;
        for (Credit2 credit : creditArray) {
            boolean find = false;
            if (credit == null) {
                continue;
            }
            String director = credit.getDirector();
            String actor = credit.getLeading_role();
            //주연배우 혹은 감독 이름에 query가 들어간다면
            if(director.contains(query)||actor.contains(query))
                id = Math.toIntExact(credit.getId());
                //해당 아아디의 영화 래퍼랜스 가져와서 삽입
            for(Movie movie:metaData){
                if(movie.getId()==id){
                    resultArrayList.add(movie);
                    find = true;
                    break;
                }
            }
            //중복 영화 삽입 방지
            if(find)
                break;
        }

        //검색된 영화가 5개보다 많은 경우에만 5개로 추려서 저장
        if(resultArrayList.size()>5)
            //영화 5개 추려서 새로운 ArrayList 생성
            resultArrayList = new ArrayList<>(resultArrayList.subList(0, 5));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);



        //검색 버튼 
        TextView eraseEditText = (TextView) view.findViewById(R.id.eraseEditText);
        //검색 결과 리스트 생성
        /*ArrayList<Movie> resultMovieList = getMovieListVoteAverage();*/
        MovieRecyclerAdapter movieAdapter; //리사이클러뷰 어뎁터(검색 결과)
        //영화 리사이클러뷰 어뎁터 생성
        movieAdapter = new MovieRecyclerAdapter(getContext(),metaData);

        //검색 결과를 보여주기 위한 리사이클러뷰
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.search_result_view);

        //어뎁터에 생성한 영화 데이터모델 삽입(기본 설정은 vote_count를 기준으로)
        //초기에 resultArray는 아무것도 없는 상태
        movieAdapter.setMovieArrayList(resultArrayList,creditArray);
        //리사이클러뷰에 어뎁터 붙이기
        recyclerView.setAdapter(movieAdapter);

        //취소 버튼 이벤트 작성 => 클릭시 EditText값 지우고, 결과리스트 내용 지움
        eraseEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //기존의 값들은 모두 지우기
                resultArrayList.clear();

                //editText값 지우기
                EditText editText = view.findViewById(R.id.query);
                editText.setText("");

            }
        });

        
        //EditText의 내용이 변경되는 순간 검색리스트로 검색어 전달하여 리스트 생성하는 이벤트 작성
        EditText editText = view.findViewById(R.id.query);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력 전 이벤트 발생 시 처리할 내용
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 텍스트 변경 중 이벤트 발생 시 처리할 내용
                String query = s.toString().trim(); // 입력된 텍스트 가져오기

                // 기존의 값들은 모두 지우기
                resultArrayList.clear();

                if (!query.isEmpty()) {
                    // query 값이 비어있지 않은 경우에만 이벤트 처리
                    // 해당하는 제목이 있는가 탐색하여 리스트 초기화
                    search(query);
                }
                // 어뎁터에 생성한 영화 데이터모델 삽입(기본 설정은 vote_count를 기준으로)
                movieAdapter.setMovieArrayList(resultArrayList,creditArray);
                // 리사이클러뷰에 어뎁터 붙이기
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),RecyclerView.VERTICAL,false));
        return view;
    }
}