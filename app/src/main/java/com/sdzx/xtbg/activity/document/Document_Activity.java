package com.sdzx.xtbg.activity.document;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.fragment.document.Document_Backlog_Fragment;
import com.sdzx.xtbg.fragment.document.Document_Consulted_Fragment;
import com.sdzx.xtbg.fragment.document.Document_Deadline_Fragment;
import com.sdzx.xtbg.fragment.document.Document_Handing_Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Mark
 * Date：2015/12/7 0007
 * Tell：15006330640
 */
public class Document_Activity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "Document_Activity";
    // 顶栏
    @ViewInject(R.id.header_back)
    TextView header_back;
    @ViewInject(R.id.header_title)
    TextView header_title;
    @ViewInject(R.id.header_right)
    TextView header_right;
    // 导航栏
    @ViewInject(R.id.fragment_btn1)
    Button fragment_btn1;
    @ViewInject(R.id.fragment_btn2)
    Button fragment_btn2;
    @ViewInject(R.id.fragment_btn3)
    Button fragment_btn3;
    @ViewInject(R.id.fragment_btn4)
    Button fragment_btn4;
    // 滑动页
    @ViewInject(R.id.fragment_viewpager)
    ViewPager fragment_viewpager;
    // 搜索
    @ViewInject(R.id.search_btn)
    ImageView search_btn;
    // 对象
    private FragmentPagerAdapter pagerAdapter;
    private Context context;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private int index = 0;
    private Document_Backlog_Fragment backlog_fragment;
    private Document_Consulted_Fragment consulted_fragment;
    private Document_Deadline_Fragment deadline_fragment;
    private Document_Handing_Fragment handing_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.home_document);
        ViewUtils.inject(this);
        initConstants();
        initViews();
        initEvents();
    }

    private void initConstants() {
        this.context = Document_Activity.this;
    }

    private void initViews() {
        header_right.setText("收文登记");
//        header_right.setVisibility(View.GONE);
        search_btn.setVisibility(View.GONE);
        backlog_fragment = new Document_Backlog_Fragment();
        consulted_fragment = new Document_Consulted_Fragment();
        deadline_fragment = new Document_Deadline_Fragment();
        handing_fragment = new Document_Handing_Fragment();
        fragments.add(backlog_fragment);
        fragments.add(consulted_fragment);
        fragments.add(deadline_fragment);
        fragments.add(handing_fragment);
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
        fragment_viewpager.setAdapter(pagerAdapter);
    }

    private void initEvents() {
        header_title.setText(getString(R.string.document_title));
        header_back.setOnClickListener(this);
        header_right.setOnClickListener(this);
        fragment_btn1.setOnClickListener(this);
        fragment_btn2.setOnClickListener(this);
        fragment_btn3.setOnClickListener(this);
        fragment_btn4.setOnClickListener(this);
        search_btn.setOnClickListener(this);
        // ViewPager 页面切换
        fragment_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
                        fragment_btn1.setTextColor(getResources().getColor(R.color.white));
                        fragment_btn1.setBackground(getResources().getDrawable(R.drawable.shape_left_press));
                        break;
                    case 1:
                        fragment_btn2.setTextColor(getResources().getColor(R.color.white));
                        fragment_btn2.setBackgroundColor(getResources().getColor(R.color.main_color));
                        break;
                    case 2:
                        fragment_btn3.setTextColor(getResources().getColor(R.color.white));
                        fragment_btn3.setBackgroundColor(getResources().getColor(R.color.main_color));
                        break;
                    case 3:
                        fragment_btn4.setTextColor(getResources().getColor(R.color.white));
                        fragment_btn4.setBackground(getResources().getDrawable(R.drawable.shape_right_press));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fragment_viewpager.setCurrentItem(0);
    }

    // 重置导航栏颜色
    private void resetColor() {
        fragment_btn1.setTextColor(getResources().getColor(R.color.main_color));
        fragment_btn1.setBackground(getResources().getDrawable(R.drawable.shape_left_normal));
        fragment_btn2.setTextColor(getResources().getColor(R.color.main_color));
        fragment_btn2.setBackgroundColor(getResources().getColor(R.color.white));
        fragment_btn3.setTextColor(getResources().getColor(R.color.main_color));
        fragment_btn3.setBackgroundColor(getResources().getColor(R.color.white));
        fragment_btn4.setTextColor(getResources().getColor(R.color.main_color));
        fragment_btn4.setBackground(getResources().getDrawable(R.drawable.shape_right_normal));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.header_right:
                // 收文登记
                startActivity(new Intent(context, Document_Add.class)
                        .putExtra("type", 2));
                break;
            case R.id.search_btn:
                // 搜索
//                createDialog();
                switch (index) {
                    case 0:
                        backlog_fragment.search();
                        break;
                    case 1:
                        consulted_fragment.search();
                        break;
                    case 2:
                        deadline_fragment.search();
                        break;
                    case 3:
                        handing_fragment.search();
                        break;
                }
                break;
            case R.id.fragment_btn1:
                fragment_viewpager.setCurrentItem(0);
                break;
            case R.id.fragment_btn2:
                fragment_viewpager.setCurrentItem(1);
                break;
            case R.id.fragment_btn3:
                fragment_viewpager.setCurrentItem(2);
                break;
            case R.id.fragment_btn4:
                fragment_viewpager.setCurrentItem(3);
                break;
        }
    }
}
