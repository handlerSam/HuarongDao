package com.example.huarongdaoapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class DragTextViewUp extends LinearLayout {
    public TextView downView;
    public DragTextViewUp(Context context, TextView downView, String text){
        super(context);
        final LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.drag_text_view_up,DragTextViewUp.this,true);
        final TextView textView = linearLayout.findViewById(R.id.textView);
        textView.setText(text);
        this.downView = downView;
        downView.setText("Sam");
        textView.setId(R.id.TextViewUp);
        textView.setOnTouchListener(new OnTouchListener() {
            private float x;
            private float y;
            private ConstraintLayout.LayoutParams params;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        params = (ConstraintLayout.LayoutParams) linearLayout.getLayoutParams();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        params.topMargin += (event.getY()-y);
                        params.leftMargin += (event.getX()-x);
                        linearLayout.setLayoutParams(params);
                        break;
                }
                return true;
            }
        });
    }

}
