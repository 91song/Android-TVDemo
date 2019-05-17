package com.androidmov.tvdemo;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.owen.focus.FocusBorder;

public class FocusBorderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_border);
        initFocusBorder();
    }

    private void initFocusBorder() {
        FocusBorder focusBorder = new FocusBorder.Builder()
                .asColor()
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 20f)
                // 阴影颜色
                .shadowColor(Color.parseColor("#3FBB66"))
                // 边框宽度(方法borderWidth(2f)也可以设置边框宽度)
                .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 3.2f)
                // 边框颜色
                .borderColor(Color.parseColor("#00FF00"))
                // padding值
                .padding(2f)
                // 动画时长
                .animDuration(300)
                // 不要闪光动画
                // .noShimmer()
                // 闪光颜色
                .shimmerColor(Color.parseColor("#66FFFFFF"))
                // 闪光动画时长
                .shimmerDuration(1000)
                // 不要呼吸灯效果
                // .noBreathing()
                // 呼吸灯效果时长
                .breathingDuration(3000)
                .build(this);
        focusBorder.boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
            @Nullable
            @Override
            public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                if (null != newFocus) {
                    switch (newFocus.getId()) {
                        case R.id.rfl_pic:
                            return FocusBorder.OptionsFactory.get(1.2f, 1.2f, dp2px());
                        default:
                            break;
                    }
                    return FocusBorder.OptionsFactory.get(1.2f, 1.2f, 10);
                }
                return null;
            }
        });
    }

    private float dp2px() {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, (float) 160, getResources().getDisplayMetrics());
    }
}
