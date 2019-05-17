package com.androidmov.tvdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_focus_border:
                startActivity(new Intent(this, FocusBorderActivity.class));
                break;
            case R.id.btn_tv_recycler:
                startActivity(new Intent(this, TvRecyclerViewActivity.class));
                break;
            case R.id.btn_tv_tab_layout:
                break;
        }
    }
}
