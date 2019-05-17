package com.androidmov.tvdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.owen.adapter.CommonRecyclerViewAdapter;
import com.owen.adapter.CommonRecyclerViewHolder;
import com.owen.focus.FocusBorder;
import com.owen.tvrecyclerview.widget.StaggeredGridLayoutManager;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvRecyclerViewActivity extends AppCompatActivity {
    @BindView(R.id.tv_recycler_view)
    TvRecyclerView mTvRecyclerView;
    @BindView(R.id.tv_top)
    TextView tvTop;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;

    private FocusBorder mFocusBorder;
    private CommonRecyclerViewAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_recycler_view);
        ButterKnife.bind(this);
        initFocusBorder();
        initTvRecyclerView();
        loadData();
    }

    private void loadData() {
        mAdapter = new CommonRecyclerViewAdapter<String>(
                this, getData(20)) {

            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item;
            }

            @Override
            public void onBindItemHolder(CommonRecyclerViewHolder helper, String item, int position) {
                Glide.with(mContext)
                        .load(item)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .centerCrop()
                        .thumbnail(0.5f)
                        .into((ImageView) helper.getHolder().getView(R.id.iv_pic));
                View itemView = helper.itemView;
                int size;
                if (position == 0 || position == 1) {
                    size = 328;
                } else {
                    size = 158;
                }
                int span;
                if (position == 0) {
                    span = 2;
                } else {
                    span = 1;
                }
                StaggeredGridLayoutManager.LayoutParams lp =
                        (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
                lp.span = span;
                lp.height = size;
                itemView.setLayoutParams(lp);
            }
        };
        mTvRecyclerView.setAdapter(mAdapter);
    }

    private List<String> getData(int count) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add("http://www.pptbz.com/pptpic/UploadFiles_6909/201306/2013062320262198.jpg");
        }
        return list;
    }

    private void initFocusBorder() {
        mFocusBorder = new FocusBorder.Builder()
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
    }

    private void initTvRecyclerView() {
        mTvRecyclerView.setSpacingWithMargins(12, 12);
        mTvRecyclerView.setOnItemListener(new TvRecyclerView.OnItemListener() {

            @Override
            public void onItemPreSelected(TvRecyclerView parent, View itemView, int position) {

            }

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                mFocusBorder.onFocus(itemView, FocusBorder.OptionsFactory.get(1.1f,
                        1.1f, 10));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                System.out.println(position);
            }
        });
        mTvRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Glide.with(TvRecyclerViewActivity.this).pauseRequests();
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Glide.with(TvRecyclerViewActivity.this).resumeRequests();
                        break;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(0)) {
                    tvBottom.setVisibility(View.VISIBLE);
                } else {
                    tvBottom.setVisibility(View.INVISIBLE);
                }
                if (recyclerView.canScrollVertically(-1)) {
                    tvTop.setVisibility(View.VISIBLE);
                } else {
                    tvTop.setVisibility(View.INVISIBLE);
                }
            }
        });
        mTvRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mFocusBorder.setVisible(hasFocus);
            }
        });
        mTvRecyclerView.setOnInBorderKeyEventListener(new TvRecyclerView.OnInBorderKeyEventListener() {

            @Override
            public boolean onInBorderKeyEvent(int direction, View focused) {
                switch (direction) {
                    case View.FOCUS_DOWN:
                        System.out.println("down");
                        break;
                    case View.FOCUS_UP:
                        System.out.println("up");
                        break;
                    case View.FOCUS_LEFT:
                        System.out.println("left");
                        break;
                    case View.FOCUS_RIGHT:
                        System.out.println("right");
                        break;
                }
                return false;
            }
        });
        mTvRecyclerView.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {

            @Override
            public boolean onLoadMore() {
                mTvRecyclerView.setLoadingMore(true);
                mAdapter.appendDatas(getData(10));
                mTvRecyclerView.setLoadingMore(false);
                return mAdapter.getItemCount() < 100;
            }
        });
    }
}
