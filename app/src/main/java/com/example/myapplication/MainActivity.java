package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View;

public class MainActivity extends Activity {

    Button buttonChangeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text_view_hello_world);
        String helloText = getString(R.string.hello_android);
        textView.setText(helloText);

        // 初始化视图对象
        TextView textViewHello = findViewById(R.id.textViewHello);
        TextView textViewJNU = findViewById(R.id.textViewJNU);
        Button buttonChangeText = findViewById(R.id.buttonChangeText);
        buttonChangeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapText();
            }
        });
    }
    // 处理按钮点击事件的方法
    public void swapText() {
        // 交换两个TextView的文本
        TextView textViewHello = findViewById(R.id.textViewHello);
        TextView textViewJNU = findViewById(R.id.textViewJNU);
        String tempText = textViewHello.getText().toString();
        textViewHello.setText(textViewJNU.getText());
        textViewJNU.setText(tempText);

        // 显示Toast
        Toast.makeText(this, "交换成功", Toast.LENGTH_SHORT).show();

        // 显示AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("交换成功")
                .setMessage("文本已交换")
                .setPositiveButton("确定", null)
                .show();
    }

        //
//        // 设置按钮点击事件监听器
//        buttonChangeText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 交换两个TextView的文本
//                String tempText = textViewHello.getText().toString();
//                textViewHello.setText(textViewJNU.getText());
//                textViewJNU.setText(tempText);
//
//                // 显示Toast
//                Toast.makeText(MainActivity.this, "交换成功", Toast.LENGTH_SHORT).show();
//
//                // 显示AlertDialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("交换成功")
//                        .setMessage("文本已交换")
//                        .setPositiveButton("确定", null)
//                        .show();
//            }
//        });
//        public void swapText(View view){
//            // 交换两个TextView的文本
//            String tempText = textViewHello.getText().toString();
//            textViewHello.setText(textViewJNU.getText());
//            textViewJNU.setText(tempText);
//
//            // 显示Toast
//            Toast.makeText(this, "交换成功", Toast.LENGTH_SHORT).show();
//
//            // 显示AlertDialog
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("交换成功")
//                    .setMessage("文本已交换")
//                    .setPositiveButton("确定", null)
//                    .show();
//        }
//        RelativeLayout relativeLayout = new RelativeLayout(this);
//        RelativeLayout.LayoutParams params =  new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);  //设置布局中的控件居中显示
//        TextView textView = new TextView(this);                       //创建TextView控件
//        textView.setText("Java 代码实现界面布局");                     //设置TextView的文字内容
//        textView.setTextColor(Color.RED);                                  //设置TextView的文字颜色
//        textView.setTextSize(18);                                                //设置TextView的文字大小
//        relativeLayout.addView(textView, params);                  //添加TextView对象和TextView的布局属性
//        setContentView(relativeLayout);                                  //设置在Activity中显示RelativeLayout

    }