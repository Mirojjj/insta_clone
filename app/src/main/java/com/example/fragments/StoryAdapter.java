package com.example.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_FIRST = 0;
    private static final int VIEW_TYPE_DEFAULT = 1;
    Context context;
    ArrayList<UserModel> userModels;
    public  StoryAdapter(Context context, ArrayList<UserModel> userModels){
            this.context = context;
            this.userModels = userModels;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    //this method inflate the layout (giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType == VIEW_TYPE_FIRST){
            View view = inflater.inflate(R.layout.my_story, parent, false);
            return new MyStoryViewHolder(view);
        }else{
            View view = inflater.inflate(R.layout.other_story, parent, false);
            return  new DefaultStoryViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //assigning values to the views we created in the recycler_view_row layout file
        //based on the position of the recycler view
//        holder.username.setText(userModels.get(position).getUsername());
        UserModel user = userModels.get(position);
        if(holder instanceof MyStoryViewHolder){
            ((MyStoryViewHolder) holder).username.setText(user.getUsername());
        }else if( holder instanceof  DefaultStoryViewHolder){
            ((DefaultStoryViewHolder) holder).username.setText(user.getUsername());
        }
    }

    @Override
    public int getItemCount() {
        //how many numbers of item do we want to display

        return userModels.size();
    }

    public int getItemViewType(int position){
            return  (position == 0) ? VIEW_TYPE_FIRST : VIEW_TYPE_DEFAULT;
    }

    public static class MyStoryViewHolder extends  RecyclerView.ViewHolder{
        TextView username;

        public MyStoryViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.my_story);
        }
    }

    public  static class DefaultStoryViewHolder extends RecyclerView.ViewHolder{
        //grabbing the views from our recycler_view_row layout file

        TextView username;
        public DefaultStoryViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.other_username);
        }
    }
}
