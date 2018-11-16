package com.zero.bcloud.module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import com.zero.bcloud.R;

public class MainActivity extends AppCompatActivity {

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = this.findViewById(R.id.text);
        this.findViewById(R.id.btn).setOnClickListener(v -> {
            if(i % 2 == 0){
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            }else{
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20);
            }
            i ++;
        });
    }
}
