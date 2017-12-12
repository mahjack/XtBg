package com.sdzx.xtbg.activity.senddocument;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.fragment.sendDocument.SendDocumentHandling;
import com.sdzx.xtbg.fragment.sendDocument.SendDocumentRefer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SendDocumentManageActivity extends FragmentActivity implements View.OnClickListener {

    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    TextView headerRight;
    @Bind(R.id.fragment_btn1)
    Button fragmentBtn1;
    @Bind(R.id.fragment_btn2)
    Button fragmentBtn2;
    @Bind(R.id.fragment_viewpager)
    ViewPager fragmentViewpager;
    @Bind(R.id.search_btn)
    ImageView searchBtn;

    private Context context;
    // 对象
    private FragmentPagerAdapter pagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private int index = 0;
    private SendDocumentHandling sendDocumentHandling;// 发文代办
    private SendDocumentRefer sendDocumentRefer;// 发文查阅

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_document_manage);
        ButterKnife.bind(this);

        initConstants();
        initViews();
        initEvents();

    }

    private void initConstants() {
        this.context = SendDocumentManageActivity.this;
    }

    private void initViews() {
        headerTitle.setText("发文管理");
        headerRight.setText("发文拟稿");
//        headerRight.setVisibility(View.GONE);
        searchBtn.setVisibility(View.GONE);
        sendDocumentHandling = new SendDocumentHandling();
        sendDocumentRefer = new SendDocumentRefer();
        fragments.add(sendDocumentHandling);
        fragments.add(sendDocumentRefer);

        // 初始化 Adapter
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        fragmentViewpager.setAdapter(pagerAdapter);
    }

    private void initEvents() {
        headerBack.setOnClickListener(this);
        headerRight.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        fragmentBtn1.setOnClickListener(this);
        fragmentBtn2.setOnClickListener(this);


        // ViewPager 页面切换
        fragmentViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 重置颜色
                resetColor();
                index = position;
                switch (position) {
                    case 0:
                        fragmentBtn1.setTextColor(getResources().getColor(R.color.white));
                        fragmentBtn1.setBackground(getResources().getDrawable(R.drawable.shape_left_press));
                        break;
                    case 1:
                        fragmentBtn2.setTextColor(getResources().getColor(R.color.white));
                        fragmentBtn2.setBackgroundColor(getResources().getColor(R.color.main_color));
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragmentViewpager.setCurrentItem(0);
    }

    // 重置导航栏颜色
    private void resetColor() {
        fragmentBtn1.setTextColor(getResources().getColor(R.color.main_color));
        fragmentBtn1.setBackground(getResources().getDrawable(R.drawable.shape_left_normal));
        fragmentBtn2.setTextColor(getResources().getColor(R.color.main_color));
        fragmentBtn2.setBackgroundColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.header_right:
                startActivity(new Intent(context, SendDocumentActivity.class));
                break;
            case R.id.search_btn:
                // 搜索
//                createDialog();
                switch (index) {
                    case 0:
//                        sendDocumentHandling.search();
                        break;
                    case 1:
//                        sendDocumentRefer.search();
                        break;
                }
                break;

            case R.id.fragment_btn1:
                fragmentViewpager.setCurrentItem(0);
                break;
            case R.id.fragment_btn2:
                fragmentViewpager.setCurrentItem(1);
                break;
        }
    }
}
