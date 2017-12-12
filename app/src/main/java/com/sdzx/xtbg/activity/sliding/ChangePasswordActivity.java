package com.sdzx.xtbg.activity.sliding;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.ChangePwdState;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;



public class ChangePasswordActivity extends Activity {

    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    TextView headerRight;
    @Bind(R.id.change_password_old)
    EditText changePasswordOld;
    @Bind(R.id.change_password_new_1)
    EditText changePasswordNew1;
    @Bind(R.id.change_password_new_2)
    EditText changePasswordNew2;

    private Context context;
    private RequestCall mCall;
    private String uid = "";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
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
        context = ChangePasswordActivity.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        uid = preferences.getString(ConstantString.UID, "");
    }

    private void initView() {
        headerTitle.setText("修改密码");
        headerRight.setText("提交");
    }

    private void initEvent() {

    }

    private boolean checkNewPwd() {
        if (changePasswordOld.getText().toString().equals("")) {
            Toast.makeText(context, "请填写原密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (changePasswordNew1.getText().toString().equals("")) {
            Toast.makeText(context, "请填写新密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (changePasswordNew2.getText().toString().equals("")) {
            Toast.makeText(context, "请重新原密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!changePasswordNew1.getText().toString().equals(changePasswordNew2.getText().toString())) {
            Toast.makeText(context, "两次输入的新密码不一致，请重新输入！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 提交密码修改
     */
    @OnClick(R.id.header_right)
    void submit() {
        if (checkNewPwd()) {
            postData();
        }
    }


    private void postData() {
        mCall = OkHttpUtils
                .post()
                .url(ConstantURL.CHANGE_PASSWORD + uid)
                .addParams("oldpwd", changePasswordOld.getText().toString())
                .addParams("newpwd", changePasswordNew1.getText().toString())
//                .addParams("password2", changePasswordNew2.getText().toString())
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, R.string.base_server, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                KLog.d("修改密码返回", response);
                try {
                    Gson gson = new Gson();
                    ChangePwdState changePwdState = gson.fromJson(response, ChangePwdState.class);
                    int state = changePwdState.getState();
                    if (state == 0) {
                        Toast.makeText(context, "旧密码输入错误!", Toast.LENGTH_SHORT).show();
                    } else if (state == 1) {
                        Toast.makeText(context, "密码修改成功!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (state == 2) {
                        Toast.makeText(context, "密码修改失败!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
