package com.sdzx.xtbg.activity.sliding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.sdzx.xtbg.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司主页
 * <p>
 * Author: Eron
 * Date: 2016/10/10
 * Time: 21:11
 */

public class CompanyHomePageActivity extends Activity {

    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    TextView headerRight;
    @Bind(R.id.company_homepage_webView)
    WebView companyHomepageWebView;

    private Context context;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_homepage);
        ButterKnife.bind(this);

        initConstant();
        initView();
        initEvent();
    }

    @OnClick(R.id.header_back)
    void close() {
        finish();
    }

    private void initConstant() {
        context = CompanyHomePageActivity.this;
    }

    private void initView() {
        headerTitle.setText("山东至信信息科技有限公司");
        headerRight.setVisibility(View.GONE);
    }

    private void initEvent() {
        url = "http://www.sdzxkj.cn/";
        companyHomepageWebView.getSettings().setJavaScriptEnabled(true);
        companyHomepageWebView.getSettings().setLoadWithOverviewMode(true);
        companyHomepageWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        companyHomepageWebView.loadUrl(url);
    }
}
