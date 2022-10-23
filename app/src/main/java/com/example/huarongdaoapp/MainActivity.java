package com.example.huarongdaoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {
    private RelativeLayout father;
    private TextView cao;
    private ConstraintLayout.LayoutParams caoParams;
    private TextView backGround;
    private ConstraintLayout.LayoutParams backGroundParams;
    private TextView zhang;
    private TextView ma;
    private TextView huang;
    private TextView zhao;
    private TextView guan;
    private TextView egg;
    private List<TextView> bing = new ArrayList<>();
    private int screenWidth;
    private int screenHeight;
    private int unitWidth;
    private TextView textViewPoint;
    private Button button;
    private int debugTime = 0;
    private Long moveTime;

    private ConstraintLayout.LayoutParams aniParams;
    private Animation ani;
    private int aniX;
    private int aniY;
    private TextView aniTextView;
    private int xPoint;
    private int yPoint;
    private int xWidth;
    private int yWidth;

    private float xDown;
    private float yDown;
    private int onMove = 0;

    private boolean[][] map = new boolean[4][5];

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(onMove==0) {
                    xDown = event.getX();
                    yDown = event.getY();
                    onMove = 1;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(onMove == 1) {
                    if (Math.abs(xDown - event.getX()) > Math.abs(yDown - event.getY())) {
                        move((TextView) v, (int) (event.getX() - xDown), 0);
                    } else {
                        move((TextView) v, 0, (int) (event.getY() - yDown));
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("确定？");//标题
        dialog.setMessage("确定要重置？");//正文
        dialog.setCancelable(true);//是否能点击屏幕取消该弹窗
        dialog.setNegativeButton("还是算了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //错误逻辑
            }});
        dialog.setPositiveButton("是的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                init();
                textViewPoint.setText("已用步数:0");
            }});
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        backGround = findViewById(R.id.textViewBackGround);
        cao = findViewById(R.id.textViewCao);
        zhang = findViewById(R.id.textViewZhang);
        ma = findViewById(R.id.textViewMa);
        huang = findViewById(R.id.textViewHuang);
        zhao = findViewById(R.id.textViewZhao);
        guan = findViewById(R.id.textViewGuan);
        textViewPoint = findViewById(R.id.textViewPoint);
        button = findViewById(R.id.button);
        egg = findViewById(R.id.textViewEgg);

        bing.add((TextView) findViewById(R.id.textViewBing1));
        bing.add((TextView) findViewById(R.id.textViewBing2));
        bing.add((TextView) findViewById(R.id.textViewBing3));
        bing.add((TextView) findViewById(R.id.textViewBing4));

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;  // 屏幕宽度（px）
        screenHeight= metric.heightPixels;  // 屏幕高度（px）
        int densityDpi = metric.densityDpi;//dp = px*160/densityDpi  px = dp*densityDpi/160
        init();

        button.setOnClickListener(this);

    }
    private void init(){
        for(int i = 0; i <= 3; i++){
            for(int j = 0; j<= 4; j++){
                map[i][j] = false;
            }
        }
        map[1][2] = true;
        map[2][2] = true;
        moveTime = 0l;

        backGroundParams = (ConstraintLayout.LayoutParams) backGround.getLayoutParams();
        backGroundParams.leftMargin = 0;
        backGroundParams.topMargin = 0;
        backGroundParams.width = screenWidth;
        backGroundParams.height = screenWidth*5/4;
        backGround.setLayoutParams(backGroundParams);
        unitWidth = backGroundParams.width/4;

        caoParams = (ConstraintLayout.LayoutParams) cao.getLayoutParams();
        caoParams.leftMargin = backGroundParams.leftMargin + backGroundParams.width/4;
        caoParams.topMargin = backGroundParams.topMargin;
        caoParams.height = unitWidth *2;
        caoParams.width = unitWidth * 2;
        cao.setLayoutParams(caoParams);
        cao.setOnTouchListener(this);


        ConstraintLayout.LayoutParams pointParams = (ConstraintLayout.LayoutParams) textViewPoint.getLayoutParams();
        pointParams.leftMargin = backGroundParams.leftMargin + unitWidth - pointParams.width/2;
        pointParams.topMargin = (screenHeight + backGroundParams.topMargin + backGroundParams.height)/2 - pointParams.height/2;

        ConstraintLayout.LayoutParams buttonParams = (ConstraintLayout.LayoutParams) button.getLayoutParams();
        buttonParams.leftMargin = backGroundParams.leftMargin + unitWidth * 3 - buttonParams.width/2;
        buttonParams.topMargin = (screenHeight + backGroundParams.topMargin + backGroundParams.height)/2 - buttonParams.height/2;


        ConstraintLayout.LayoutParams zhangParams = (ConstraintLayout.LayoutParams) zhang.getLayoutParams();
        zhangParams.leftMargin = backGroundParams.leftMargin;
        zhangParams.topMargin = backGroundParams.topMargin;
        zhangParams.height = unitWidth*2;
        zhangParams.width = unitWidth;
        zhang.setLayoutParams(zhangParams);
        zhang.setOnTouchListener(this);

        ConstraintLayout.LayoutParams maParams = (ConstraintLayout.LayoutParams) ma.getLayoutParams();
        maParams.leftMargin = backGroundParams.leftMargin + unitWidth*3;
        maParams.topMargin = backGroundParams.topMargin;
        maParams.height = unitWidth*2;
        maParams.width = unitWidth;
        ma.setLayoutParams(maParams);
        ma.setOnTouchListener(this);

        ConstraintLayout.LayoutParams huangParams = (ConstraintLayout.LayoutParams) huang.getLayoutParams();
        huangParams.leftMargin = backGroundParams.leftMargin;
        huangParams.topMargin = zhangParams.topMargin + zhangParams.height;
        huangParams.height = unitWidth*2;
        huangParams.width = unitWidth;
        huang.setLayoutParams(huangParams);
        huang.setOnTouchListener(this);

        ConstraintLayout.LayoutParams zhaoParams = (ConstraintLayout.LayoutParams) zhao.getLayoutParams();
        zhaoParams.leftMargin = backGroundParams.leftMargin + unitWidth * 3;
        zhaoParams.topMargin = maParams.topMargin + unitWidth * 2;
        zhaoParams.height = unitWidth*2;
        zhaoParams.width = unitWidth;
        zhao.setLayoutParams(zhaoParams);
        zhao.setOnTouchListener(this);

        ConstraintLayout.LayoutParams guanParams = (ConstraintLayout.LayoutParams) guan.getLayoutParams();
        guanParams.leftMargin = huangParams.leftMargin + unitWidth;
        guanParams.topMargin = caoParams.topMargin + unitWidth * 3;
        guanParams.height = unitWidth;
        guanParams.width = unitWidth*2;
        guan.setLayoutParams(guanParams);
        guan.setOnTouchListener(this);

        ConstraintLayout.LayoutParams bingParams;
        for(TextView textView : bing){
            bingParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
            bingParams.topMargin = guanParams.topMargin + unitWidth;
            bingParams.leftMargin = huangParams.leftMargin + unitWidth * bing.indexOf(textView);
            bingParams.width = unitWidth;
            bingParams.height = unitWidth;
            textView.setOnTouchListener(this);
            textView.setLayoutParams(bingParams);
        }

        ConstraintLayout.LayoutParams eggParams = (ConstraintLayout.LayoutParams)egg.getLayoutParams();
        eggParams.width = screenWidth;
        eggParams.height = pointParams.topMargin - backGroundParams.topMargin - backGroundParams.height;
        eggParams.leftMargin = 0;
        eggParams.topMargin = backGroundParams.topMargin + backGroundParams.height;
        egg.setLayoutParams(eggParams);
        egg.setText("");
        egg.setVisibility(View.INVISIBLE);
    }

    private void move(TextView textView, int x, int y){
        if(x>0){
            aniX = 1;
        }else if( x == 0){
            aniX = 0;
        }else{
            aniX = -1;
        }
        if(y>0){
            aniY = 1;
        }else if( y == 0){
            aniY = 0;
        }else{
            aniY = -1;
        }
        aniTextView = textView;
        aniParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
        xPoint = (int)(aniParams.leftMargin - backGroundParams.leftMargin + 0.5*unitWidth)/unitWidth;
        yPoint = (int)(aniParams.topMargin - backGroundParams.topMargin + 0.5*unitWidth)/unitWidth;
        xWidth = (int)(aniParams.width + 0.5*unitWidth)/unitWidth;
        yWidth = (int)(aniParams.height + 0.5*unitWidth)/unitWidth;
        if(aniJudge()){
            aniFill();
            ani = new TranslateAnimation(0, aniX*unitWidth, 0, aniY*unitWidth);
            ani.setDuration(200);
            ani.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

            }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Animation animation1 = new TranslateAnimation(0,0,0,0);
                    animation1.setDuration(1);
                    aniTextView.startAnimation(animation1);
                    aniParams.leftMargin += aniX*unitWidth;
                    aniParams.topMargin += aniY*unitWidth;
                    aniTextView.setLayoutParams(aniParams);
                    victory();
                    onMove = 0;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

            }
            });
            textView.startAnimation(ani);
            moveTime++;
            textViewPoint.setText("已用步数:"+moveTime);
            egg();
        }
    }
    private boolean aniJudge(){
        if(aniX < 0){
            if(xPoint+aniX>=0){
                return map[xPoint+aniX][yPoint] && map[xPoint+aniX][yPoint+yWidth-1];
            }else{return false;}
        }else if(aniX > 0){
            if(xPoint+xWidth-1+aniX <= 3){
                return map[xPoint+xWidth-1+aniX][yPoint] && map[xPoint+xWidth-1+aniX][yPoint+yWidth-1];
            }else{return false;}
        }else if(aniY < 0){
            if(yPoint+aniY>=0){
                return map[xPoint][yPoint+aniY] && map[xPoint+xWidth-1][yPoint+aniY];
            }else{return false;}
        }else{
            if(yPoint+yWidth-1+aniY <= 4){
                return map[xPoint][yPoint+yWidth-1+aniY] && map[xPoint+xWidth-1][yPoint+yWidth-1+aniY];
            }else{return false;}
        }
    }
    private void aniFill(){
        if(aniX < 0){
            map[xPoint+aniX][yPoint] = false;
            map[xPoint+aniX][yPoint+yWidth-1] = false;
            map[xPoint+xWidth-1][yPoint] = true;
            map[xPoint+xWidth-1][yPoint+yWidth-1] = true;
        }else if(aniX > 0){
            map[xPoint+xWidth-1+aniX][yPoint] = false;
            map[xPoint+xWidth-1+aniX][yPoint+yWidth-1] = false;
            map[xPoint][yPoint] = true;
            map[xPoint][yPoint + yWidth-1] = true;
        }else if(aniY < 0){
            map[xPoint][yPoint+aniY] = false;
            map[xPoint+xWidth-1][yPoint+aniY] = false;
            map[xPoint][yPoint + yWidth-1] = true;
            map[xPoint + xWidth-1][yPoint + yWidth-1] = true;
        }else{
            map[xPoint][yPoint+yWidth-1+aniY] = false;
            map[xPoint+xWidth-1][yPoint+yWidth-1+aniY] = false;
            map[xPoint][yPoint] = true;
            map[xPoint+xWidth-1][yPoint] = true;
        }
    }
    private void debug(){
        debugTime++;
        for(int k = 0; k <= 4; k++){
            String print = "";
            for(int o = 0; o <= 3; o++){
                print += String.valueOf(map[o][k]).substring(0,1)+" ";
            }
            Log.v("Sam.time: ",String.valueOf(k));
            Log.v("Sam: " + debugTime,print);
        }
    }
    private void victory(){
        if((int)(caoParams.leftMargin - backGroundParams.leftMargin + 0.5*unitWidth)/unitWidth == 1 && (int)(caoParams.topMargin - backGroundParams.topMargin + 0.5*unitWidth)/unitWidth == 3){
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("你赢了！曹操跑掉了！");//标题
            dialog.setMessage("你一共用了"+moveTime+"步"+ ((moveTime > 81)? "据说，曹操逃走最少的步数是81步。":"太强了！你走的是最短的步数！"));//正文
            dialog.setCancelable(true);//是否能点击屏幕取消该弹窗
            dialog.show();
        }
    }
    private void egg(){
        if(moveTime == 30){
            egg.setText("(曹操：我是不是太胖了点...)");
            Animation animation = new AlphaAnimation(0,1);
            animation.setDuration(1000);
            egg.startAnimation(animation);
            egg.setVisibility(View.VISIBLE);
        }
        if(moveTime == 35){
            Animation animation = new AlphaAnimation(1,0);
            animation.setDuration(1000);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    egg.setText("");
                    egg.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            egg.startAnimation(animation);
        }
        if(moveTime == 65){
            egg.setText("(曹操：你的移动方式是现代的，构思却相当古老，你究竟是什么人?)");
            Animation animation = new AlphaAnimation(0,1);
            animation.setDuration(1000);
            egg.startAnimation(animation);
            egg.setVisibility(View.VISIBLE);
        }
        if(moveTime == 70){
            Animation animation = new AlphaAnimation(1,0);
            animation.setDuration(1000);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    egg.setText("");
                    egg.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            egg.startAnimation(animation);
        }
        if(moveTime == 100){
            egg.setText("(曹操：态度不端正，方向出问题，观点不正确，分析难到位。)");
            Animation animation = new AlphaAnimation(0,1);
            animation.setDuration(1000);
            egg.startAnimation(animation);
            egg.setVisibility(View.VISIBLE);
        }
        if(moveTime == 105){
            Animation animation = new AlphaAnimation(1,0);
            animation.setDuration(1000);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    egg.setText("");
                    egg.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            egg.startAnimation(animation);
        }
        if(moveTime == 125){
            egg.setText("(曹操：没什么变化啊，我的盟友。)");
            Animation animation = new AlphaAnimation(0,1);
            animation.setDuration(1000);
            egg.startAnimation(animation);
            egg.setVisibility(View.VISIBLE);
        }
        if(moveTime == 130){
            Animation animation = new AlphaAnimation(1,0);
            animation.setDuration(1000);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    egg.setText("");
                    egg.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            egg.startAnimation(animation);
        }
        if(moveTime == 160){
            egg.setText("(关羽：正面上我啊！)");
            Animation animation = new AlphaAnimation(0,1);
            animation.setDuration(1000);
            egg.startAnimation(animation);
            egg.setVisibility(View.VISIBLE);
        }
        if(moveTime == 165){
            Animation animation = new AlphaAnimation(1,0);
            animation.setDuration(1000);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    egg.setText("");
                    egg.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            egg.startAnimation(animation);
        }
        if(moveTime == 190){
            egg.setText("(画外音：那一天，所有曹操都想起了当初被关羽支配的恐惧)");
            Animation animation = new AlphaAnimation(0,1);
            animation.setDuration(1000);
            egg.startAnimation(animation);
            egg.setVisibility(View.VISIBLE);
        }
        if(moveTime == 195){
            Animation animation = new AlphaAnimation(1,0);
            animation.setDuration(1000);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    egg.setText("");
                    egg.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            egg.startAnimation(animation);
        }
        if(moveTime == 250){
            egg.setText("(曹操：丢人。)");
            Animation animation = new AlphaAnimation(0,1);
            animation.setDuration(1000);
            egg.startAnimation(animation);
            egg.setVisibility(View.VISIBLE);
        }
        if(moveTime == 255){
            Animation animation = new AlphaAnimation(1,0);
            animation.setDuration(1000);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    egg.setText("");
                    egg.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            egg.startAnimation(animation);
        }
    }
}