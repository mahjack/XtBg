package com.sdzx.xtbg.activity.sliding;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.sdzx.xtbg.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author：Mark
 * Date：2015/12/14 0014
 * Tell：15006330640
 */
public class About_Activity extends Activity {
    private static final String TAG = "About_Activity";
    // 顶栏
    @Bind(R.id.header_back)
    TextView header_back;
    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.header_right)
    ImageView header_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        header_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        header_right.setVisibility(View.GONE);
        header_title.setText(getString(R.string.sliding_about));
    }
}
