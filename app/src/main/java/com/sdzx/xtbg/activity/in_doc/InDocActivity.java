package com.sdzx.xtbg.activity.in_doc;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.fragment.in_doc_fragment.InDoc163MailboxFragment;
import com.sdzx.xtbg.fragment.in_doc_fragment.InDocEmailFragment;
import com.sdzx.xtbg.fragment.in_doc_fragment.InDocFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 内网文件
 * Author:Eron
 * Date: 2016/6/27 0027
 * Time: 11:38
 */
public class InDocActivity extends FragmentActivity implements View.OnClickListener {
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
    @Bind(R.id.fragment_btn3)
    Button fragmentBtn3;

    private Context context;

    private FragmentPagerAdapter pagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private int index = 0;
    private InDocFragment inDocFragment; // 内网文件
    private InDocEmailFragment inDocEmailFragment; // 内网邮件
    private InDoc163MailboxFragment inDoc163MailboxFragment;// 邮箱文件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_doc);
        ButterKnife.bind(this);

        initConstant();
        initView();
        initEvent();

    }

    private void initConstant() {
        context = InDocActivity.this;
    }

    private void initView() {
        headerTitle.setText("内部文件");
        headerRight.setVisibility(View.GONE);

        inDocFragment = new InDocFragment();
        inDocEmailFragment = new InDocEmailFragment();
        inDoc163MailboxFragment = new InDoc163MailboxFragment();
        fragments.add(inDocFragment);
        fragments.add(inDocEmailFragment);
        fragments.add(inDoc163MailboxFragment);
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

    @OnClick(R.id.header_back)
    void back() {
        finish();
    }

    private void initEvent() {

        searchBtn.setOnClickListener(this);
        fragmentBtn1.setOnClickListener(this);
        fragmentBtn2.setOnClickListener(this);
        fragmentBtn3.setOnClickListener(this);

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
                    case 2:
                        fragmentBtn3.setTextColor(getResources().getColor(R.color.white));
                        fragmentBtn3.setBackgroundColor(getResources().getColor(R.color.main_color));
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
        fragmentBtn3.setTextColor(getResources().getColor(R.color.main_color));
        fragmentBtn3.setBackgroundColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.fragment_btn3:
                fragmentViewpager.setCurrentItem(2);
                break;
        }
    }
}
