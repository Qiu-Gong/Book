package com.qiugong.skin_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qiugong.skin_app.R;
import com.qiugong.skin_app.adapter.GirlAdapter;

public class MusicFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        RecyclerView recycler = view.findViewById(R.id.rel_view);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(new GirlAdapter());
        return view;
    }
}


