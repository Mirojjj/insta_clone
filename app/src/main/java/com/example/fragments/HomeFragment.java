package com.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<UserModel> userModels = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.story_recyclerview);

        setUpUserModels();

        StoryAdapter adapter = new StoryAdapter(requireContext(), userModels);
//        recyclerView.scrollToPosition(adapter.getItemCount()-1);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void setUpUserModels(){
            String[] usersList = getResources().getStringArray(R.array.insta_users);

        for (String u : usersList) {
            userModels.add(new UserModel(u));
        }
    }
}
