package com.example.myapplication;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder> {


    private ArrayList<String> reviewList;
    private int id = 1;
    private int idOp1 = R.drawable.user_ic_gray;
    private int idOp2 = R.drawable.user_ic_white;


    @NonNull
    @Override
    public ReviewRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ReviewRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(reviewList.get(position));
    }

    public void setReviewList(ArrayList<String> reviewList){
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userIcon;
        TextView userId;
        TextView review;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userIcon = (ImageView) itemView.findViewById(R.id.user_icon);
            userId = (TextView) itemView.findViewById(R.id.user_id);
            review = (TextView) itemView.findViewById(R.id.user_review);
        }

        void onBind(String item){

            //아이콘 붙이기
            if(id%2==0)
                userIcon.setImageResource(idOp1);
            else
                userIcon.setImageResource(idOp2);


            //리뷰들 붙이기
            userId.setText("user "+id);
            id+=1;
            review.setText(item);
        }
    }
}
