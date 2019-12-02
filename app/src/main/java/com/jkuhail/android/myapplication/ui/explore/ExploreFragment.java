package com.jkuhail.android.myapplication.ui.explore;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.jkuhail.android.myapplication.R;
import com.jkuhail.android.myapplication.SearchActivity;


public class ExploreFragment extends Fragment {


    private EditText search_explore;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_explore, container, false);
        search_explore = root.findViewById(R.id.search_explore);

        search_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    }
