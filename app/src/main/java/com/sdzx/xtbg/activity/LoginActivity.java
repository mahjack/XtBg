package com.sdzx.xtbg.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.MyApp;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Login;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.dialog.ECProgressDialog;
import com.sdzx.xtbg.permission.PermissionsActivity;
import com.sdzx.xtbg.permission.PermissionsChecker;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    // 登录名、密码
    @Bind(R.id.login_username)
    EditText et_login_username;
    @Bind(R.id.login_password)
    EditText et_login_password;
    @Bind(R.id.login_check)
    CheckBox login_check;//记住密码
    // 登陆
    @Bind(R.id.login_btn)
    Button loginBtn;
    private Login login;
    private ECProgressDialog mPostingdialog;
    private SharedPreferences mSharedPreferences;
    private static final int REQUEST_CODE = 0; // 请求码
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS
    };
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        // 隐藏键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ButterKnife.bind(this);
        initData();

    }
    private void initData() {
        context=LoginActivity.this;
        loginBtn.setOnClickListener(this);
        mPermissionsChecker = new PermissionsChecker(this);
        mSharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        // CheckBox
//        if (mSharedPreferences.getBoolean("ISCHECKED", false)) {
            // 默认记住密码
            login_check.setChecked(true);
            et_login_username.setText(mSharedPreferences.getString(ConstantString.USERNAME, ""));
            et_login_password.setText(mSharedPreferences.getString(ConstantString.PASSWORD, ""));
            et_login_password.setSelection((mSharedPreferences.getString(ConstantString.PASSWORD, "")).length());// 设置光标位置在文字最后
            et_login_username.setSelection((mSharedPreferences.getString(ConstantString.USERNAME, "")).length());
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (checkInput()) {
                    mPostingdialog = new ECProgressDialog(this, R.string.login_posting);
                    mPostingdialog.show();
                    login();
                }
                break;

        }
    }

    /**
     * 检查帐号密码是否为空
     * @return
     */
    private boolean checkInput() {
        if (et_login_username.getText().toString() == null ||et_login_username.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_login_password.getText().toString() == null || et_login_password.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void login() {
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//        finish();
        OkHttpUtils
                .post()
                .url(ConstantURL.LOGIN_URL)
                .addParams("username", et_login_username.getText().toString())
                .addParams("password", et_login_password.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(getApplicationContext(), "未知错误！请检查您的网络！", Toast.LENGTH_SHORT).show();
                        mPostingdialog.dismiss();
                    }
                    @Override
                    public void onResponse(String response) {
                        try {
                            KLog.json("登录返回------>", response);
                            Gson gson = new Gson();
                            login = gson.fromJson(response, Login.class);
                            if (login != null) {
                                if (login.getState().equals("1")) {
                                    Toast.makeText(context, getString(R.string.login_error1), Toast.LENGTH_SHORT).show();
                                } else if (login.getState().equals("2")) {
                                    Toast.makeText(context, getString(R.string.login_error2), Toast.LENGTH_SHORT).show();
                                } else if (login.getState().equals("3")) {
                                    Toast.makeText(context, getString(R.string.login_error3), Toast.LENGTH_SHORT).show();
                                } else if (login.getState().equals("4")) {
                                    Toast.makeText(context, getString(R.string.login_error4), Toast.LENGTH_SHORT).show();
                                } else if (login.getState().equals("5")) {
                                    MyApp.USERNAME=login.getName();
                                    MyApp.USERID=login.getUid();
                                    if (login_check.isChecked()) {
                                        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                                        mEditor.putString(ConstantString.USERNAME, et_login_username.getText().toString());
                                        mEditor.putString(ConstantString.PASSWORD, et_login_password.getText().toString());
                                        mEditor.putString(ConstantString.UID, login.getUid());
                                        mEditor.putString(ConstantString.NAME, login.getName());
                                        mEditor.commit();
                                    }
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                            } else {
                                Toast.makeText(context, getString(R.string.base_server), Toast.LENGTH_SHORT).show();
                            }
                            mPostingdialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            mPostingdialog.dismiss();
                            Toast.makeText(context, getString(R.string.base_server), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }





    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }


}
