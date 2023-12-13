package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.view.GameView;

public class GameFragment extends Fragment {

    public GameView gameView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 使用游戏布局文件
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化游戏视图
        gameView = view.findViewById(R.id.gameView);
    }
}

