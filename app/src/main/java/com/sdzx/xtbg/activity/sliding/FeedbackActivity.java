package com.sdzx.xtbg.activity.sliding;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdzx.xtbg.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author：Mark
 * Date：2015/11/30 0030
 * Tell：15006330640
 */
public class FeedbackActivity extends Activity {
    private static final String TAG = "FeedbackActivity";
    // 顶栏
    @Bind(R.id.header_back)
    TextView header_back;
    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.header_right)
    ImageView header_right;
    @Bind(R.id.sliding_submit)
    Button sliding_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.sliding_feedback);
        ButterKnife.bind(this);
        header_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        header_right.setVisibility(View.GONE);
        header_title.setText(getString(R.string.sliding_feedback));
        sliding_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
