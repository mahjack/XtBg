package com.sdzx.xtbg.activity.register;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.google.gson.Gson;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.RegisterState;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.constant.DateUtils;
import com.sdzx.xtbg.permission.PermissionActivity;
import com.sdzx.xtbg.tools.MapUtils;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 签到
 * Author:Eron
 * Date: 2016/6/16 0016
 * Time: 14:51
 */
public class Register_Activity extends PermissionActivity implements AMapLocationListener, View.OnClickListener {


    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    TextView headerRight;
    @Bind(R.id.iv_register_avatar)
    ImageView ivRegisterAvatar;
    @Bind(R.id.tv_register_name)
    TextView tvRegisterName;
    @Bind(R.id.tv_register_desc)
    TextView tvRegisterDesc;// 签到状态
    @Bind(R.id.tv_register_week)
    TextView tvRegisterWeek;
    @Bind(R.id.tv_register_date)
    TextView tvRegisterDate;
    @Bind(R.id.tv_register_time)
    TextView tvRegisterTime;
    @Bind(R.id.iv_register_map)
    MapView ivRegisterMap;
    @Bind(R.id.tv_register_loc)
    TextView tvRegisterLoc;
    @Bind(R.id.tv_register_loc_det)
    TextView tvRegisterLocDet;
    @Bind(R.id.tv_register_loc_que)
    TextView tvRegisterLocQue;
    @Bind(R.id.button_register_sign)
    Button buttonRegisterSign;
    @Bind(R.id.tv_register_test)
    TextView registerTest;

    private Context context;

    // 定位
    private AMapLocationClient locationClient = null;
    private AMap aMap;
    private AMapLocationClientOption locationOption = null;

    private double distance;// 距离
    private int currentState;// 签到状态

    double now_latitude;// 当前纬度
    double now_longitude;// 当前经度
    String mLocation;// 详细地址

    private SharedPreferences preferences;
    private String userName;
    private String userId;
    private RequestCall mCall;
    private int tag=1;//签到

    private String now_date;
    private long start;
    private long end,end1;
    private long startpm;
    private long startpm1;
    private long endpm,endpm1;
    private long now;
    private MaterialDialog materialDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        ivRegisterMap.onCreate(savedInstanceState);

        initConstant();

        checkPermissions();

        initData();
        initView();
        initEvent();

    }

    /**
     * 6.0 系统检查权限
     */
    private void checkPermissions() {
        checkPermission(new CheckPermListener() {
            @Override
            public void superPermission() {
                KLog.d("定位权限获取成功！");
            }
        }, R.string.permission_location, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    private void initConstant() {
        context = Register_Activity.this;

        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        userName = preferences.getString(ConstantString.NAME, "");
        userId = preferences.getString(ConstantString.UID, "");
        tvRegisterName.setText(userName);

        // 地图相关
        aMap = ivRegisterMap.getMap();
        aMap.getUiSettings().setZoomControlsEnabled(false);

        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setOnceLocation(true);// 设置单次定位
        // 设置定位监听
        locationClient.setLocationListener(this);

        now_date= DateUtils.getDateYear()+"-"+DateUtils.getDateMonth()+"-"+DateUtils.getDateDay();
        now=getDate();
        start=getTime(now_date+" 08:30:00");
        end=getTime(now_date+" 12:00:00");
        end1=getTime(now_date+" 12:30:00");

        startpm=getTime(now_date+" 13:30:00");
        startpm1=getTime(now_date+" 14:00:00");
        endpm=getTime(now_date+" 17:30:00");
        endpm1=getTime(now_date+" 18:00:00");
    }

    //获取当前时间戳
    public  long getDate(){
        long time= Calendar.getInstance().getTimeInMillis();
        String str=(time+"").substring(0,10);
        long time2= Long.parseLong(str);
        KLog.d(time2);
        return time2;
    }

    // 将字符串转为时间戳
    public static long getTime(String user_time) {
        String re_time = null;
        long l2=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
            l2= Long.parseLong(re_time);
        }catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return l2;
    }


    private void initData() {
        tvRegisterTime.setText(DateUtils.getTime());// 刷新时间
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
        mHandler.sendEmptyMessage(MapUtils.MSG_LOCATION_START);

        requestState();

    }

    /**
     * 请求签到状态
     */
    private void requestState() {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.REGISTER_STATE + userId)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "网络错误！", Toast.LENGTH_SHORT).show();
                materialDialog.dismiss();
            }

            @Override
            public void onResponse(String response) {
                try {

                    if (materialDialog != null && materialDialog.isShowing()){
                        materialDialog.dismiss();
                    }
                    Log.e("tag", "签到状态----------------->" + response);
                    Gson gson = new Gson();
                    RegisterState state = gson.fromJson(response, RegisterState.class);
                    currentState = state.getState();

                    if (currentState == 1) {
                        tvRegisterDesc.setText("未签到");
                        buttonRegisterSign.setText("签到");
                        tag=1;
                    } else if (currentState == 2) {
                        tvRegisterDesc.setText("已签到");
                        buttonRegisterSign.setText("签退");
                        tag=2;
                    }else if (currentState == 3){
                        tvRegisterDesc.setText("已签退");
                        buttonRegisterSign.setText("签到");
                        tag=3;
                    }else if (currentState == 4){
                        tvRegisterDesc.setText("已签到");
                        buttonRegisterSign.setText("签退");
                        tag=4;
                    }else{
                        tvRegisterDesc.setText("已签退");
                        buttonRegisterSign.setText("已签退");
                        tag=5;
                    }

//                    if (currentState == 1) {
//                        tvRegisterDesc.setText("未签到");
//                        buttonRegisterSign.setText("签到");
//                        tag=1;
//                    } else if(currentState == 2){
//                        tvRegisterDesc.setText("已签到");
//                        buttonRegisterSign.setText("签退");
//                        tag=2;
//                    }else if (currentState == 0) {
//                        tvRegisterDesc.setText("已签到");
//                        buttonRegisterSign.setText("已签到");
//                        tag=2;
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        headerTitle.setText("签到");
        headerRight.setText("签到列表");
        headerRight.setOnClickListener(this);
    }

    private void initEvent() {

    }

    @OnClick(R.id.header_back)
    void exit() {
        finish();
    }

    @OnClick(R.id.button_register_sign)
    void sendRequest() {


        if (currentState == 1) {
            if (now<=start){
                if(distance>500){
                    final EditText editText = new EditText(Register_Activity.this);
                    AlertDialog.Builder inputDialog =
                            new AlertDialog.Builder(Register_Activity.this);
                    inputDialog.setTitle("超出签到距离请填写原因").setView(editText);
                    inputDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (editText.getText().length()>1){
                                        materialDialog = new MaterialDialog.Builder(context)
                                                .title("签到")
                                                .content("正在签到……")
                                                .progress(true,0)
                                                .progressIndeterminateStyle(true)
                                                .cancelable(false)
                                                .show();
                                        sendRegisterRequest(editText.getText().toString());
                                    }else{
                                        Toast.makeText(Register_Activity.this,"签到不成功,请填写原因！",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).show();
                }else{
                    materialDialog = new MaterialDialog.Builder(context)
                            .title("签到")
                            .content("正在签到……")
                            .progress(true,0)
                            .progressIndeterminateStyle(true)
                            .cancelable(false)
                            .show();
                    sendRegisterRequest("");
                }
            }else {
                final EditText editText = new EditText(Register_Activity.this);
                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(Register_Activity.this);
                inputDialog.setTitle("迟到签到请填写原因").setView(editText);
                inputDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (editText.getText().length()>1){
                                    materialDialog = new MaterialDialog.Builder(context)
                                            .title("签到")
                                            .content("正在签到……")
                                            .progress(true,0)
                                            .progressIndeterminateStyle(true)
                                            .cancelable(false)
                                            .show();
                                    sendRegisterRequest(editText.getText().toString());
                                }else{
                                    Toast.makeText(Register_Activity.this,"签到不成功,请填写原因！",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
            }

        } else if (currentState == 2) {

            if (now>=end&&now<=end1){
                if(distance>500){
                    final EditText editText = new EditText(Register_Activity.this);
                    AlertDialog.Builder inputDialog =
                            new AlertDialog.Builder(Register_Activity.this);
                    inputDialog.setTitle("超出签退距离请填写原因").setView(editText);
                    inputDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (editText.getText().length()>1){
                                        materialDialog = new MaterialDialog.Builder(context)
                                                .title("签退")
                                                .content("正在签退……")
                                                .progress(true,0)
                                                .progressIndeterminateStyle(true)
                                                .cancelable(false)
                                                .show();
                                        sendRegisterRequest(editText.getText().toString());
                                    }else{
                                        Toast.makeText(Register_Activity.this,"签退不成功,请填写原因！",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).show();
                }else{
                    materialDialog = new MaterialDialog.Builder(context)
                            .title("签退")
                            .content("正在签退……")
                            .progress(true,0)
                            .progressIndeterminateStyle(true)
                            .cancelable(false)
                            .show();
                    sendRegisterRequest("");
                }

            }else {
                final EditText editText = new EditText(Register_Activity.this);
                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(Register_Activity.this);
                inputDialog.setTitle("早退签退请填写原因").setView(editText);
                inputDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (editText.getText().length()>1){
                                    materialDialog = new MaterialDialog.Builder(context)
                                            .title("签退")
                                            .content("正在签退……")
                                            .progress(true,0)
                                            .progressIndeterminateStyle(true)
                                            .cancelable(false)
                                            .show();
                                    sendRegisterRequest(editText.getText().toString());
                                }else{
                                    Toast.makeText(Register_Activity.this,"签退不成功,请填写原因！",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
            }
        }else if (currentState == 3){
            if (now<=startpm1&&now>=startpm){
                if(distance>500){
                    final EditText editText = new EditText(Register_Activity.this);
                    AlertDialog.Builder inputDialog =
                            new AlertDialog.Builder(Register_Activity.this);
                    inputDialog.setTitle("超出签到距离请填写原因").setView(editText);
                    inputDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (editText.getText().length()>1){
                                        materialDialog = new MaterialDialog.Builder(context)
                                                .title("签到")
                                                .content("正在签到……")
                                                .progress(true,0)
                                                .progressIndeterminateStyle(true)
                                                .cancelable(false)
                                                .show();
                                        sendRegisterRequest(editText.getText().toString());
                                    }else{
                                        Toast.makeText(Register_Activity.this,"签到不成功,请填写原因！",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).show();
                }else{
                    materialDialog = new MaterialDialog.Builder(context)
                            .title("签到")
                            .content("正在签到……")
                            .progress(true,0)
                            .progressIndeterminateStyle(true)
                            .cancelable(false)
                            .show();
                    sendRegisterRequest("");
                }
            }else {
                final EditText editText = new EditText(Register_Activity.this);
                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(Register_Activity.this);
                inputDialog.setTitle("迟到签到请填写原因").setView(editText);
                inputDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (editText.getText().length()>1){
                                    materialDialog = new MaterialDialog.Builder(context)
                                            .title("签到")
                                            .content("正在签到……")
                                            .progress(true,0)
                                            .progressIndeterminateStyle(true)
                                            .cancelable(false)
                                            .show();
                                    sendRegisterRequest(editText.getText().toString());
                                }else{
                                    Toast.makeText(Register_Activity.this,"签到不成功,请填写原因！",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
            }
//            Toast.makeText(context, "您已签退！", Toast.LENGTH_SHORT).show();
        }else if(currentState == 4){
            if (now>=endpm){
                if(distance>500){
                    final EditText editText = new EditText(Register_Activity.this);
                    AlertDialog.Builder inputDialog =
                            new AlertDialog.Builder(Register_Activity.this);
                    inputDialog.setTitle("超出签退距离请填写原因").setView(editText);
                    inputDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (editText.getText().length()>1){
                                        materialDialog = new MaterialDialog.Builder(context)
                                                .title("签退")
                                                .content("正在签退……")
                                                .progress(true,0)
                                                .progressIndeterminateStyle(true)
                                                .cancelable(false)
                                                .show();
                                        sendRegisterRequest(editText.getText().toString());
                                    }else{
                                        Toast.makeText(Register_Activity.this,"签退不成功,请填写原因！",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).show();
                }else{
                    materialDialog = new MaterialDialog.Builder(context)
                            .title("签退")
                            .content("正在签退……")
                            .progress(true,0)
                            .progressIndeterminateStyle(true)
                            .cancelable(false)
                            .show();
                    sendRegisterRequest("");
                }

            }else {
                final EditText editText = new EditText(Register_Activity.this);
                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(Register_Activity.this);
                inputDialog.setTitle("早退签退请填写原因").setView(editText);
                inputDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (editText.getText().length()>1){
                                    materialDialog = new MaterialDialog.Builder(context)
                                            .title("签退")
                                            .content("正在签退……")
                                            .progress(true,0)
                                            .progressIndeterminateStyle(true)
                                            .cancelable(false)
                                            .show();
                                    sendRegisterRequest(editText.getText().toString());
                                }else{
                                    Toast.makeText(Register_Activity.this,"签退不成功,请填写原因！",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
            }
        }else{
            Toast.makeText(context, "您已签退！", Toast.LENGTH_SHORT).show();
        }

//        requestState();// 请求状态
//        if (currentState == 1) {
//            sendRegisterRequest();
//        } else if (currentState == 2){
//            Toast.makeText(context, "您已签到！", Toast.LENGTH_SHORT).show();
//        }else if (currentState == 0) {
//            Toast.makeText(context, "您已签到！", Toast.LENGTH_SHORT).show();
//            tvRegisterDesc.setText("已签到");
//            buttonRegisterSign.setText("已签到");
//        }
    }

    /**
     * 发送签到请求
     */
    private void sendRegisterRequest(String cont) {
        Log.e("tag", "签到请求地址----------->" + ConstantURL.REGISTER_SEND_REQUEST + userId + "&act=do_sign");
        mCall = OkHttpUtils
                .post()
                .url(ConstantURL.REGISTER_SEND_REQUEST + userId + "&act=do_sign")
                .addParams("coord", now_latitude + "、" + now_longitude)
                .addParams("sign_time", DateUtils.getTime())
                .addParams("sign_addr", mLocation)
                .addParams("tag",tag+"")
                .addParams("cont",cont)
                .build();
        KLog.d("-------",tag);
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "网络错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
//                    Log.e("tag", "签到返回数据------------------------>" + response);
//                    tvRegisterDesc.setText("已签到");
//                    buttonRegisterSign.setText("已签到");

                    KLog.json(response);
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.optString("message").equals("ok")){
                        Toast.makeText(context, "成功!", Toast.LENGTH_SHORT).show();
                    }


                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                sleep(2*1000);
                                requestState();// 请求状态
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    Handler mHandler = new Handler() {
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case MapUtils.MSG_LOCATION_START:
                    registerTest.setText("开始定位");
                    tvRegisterLoc.setText("开始定位");
                    tvRegisterLocDet.setText("");
                    break;
                case MapUtils.MSG_LOCATION_FINISH:// 设置定位结果
                    AMapLocation loc = (AMapLocation) msg.obj;
                    String result = MapUtils.getLocationStr(loc);
                    mLocation = loc.getAddress();//地址
                    String po=loc.getPoiName();
                    String mainLoc = loc.getPoiName();
                    if (mainLoc.contains("千寻音乐")){
                        mainLoc="人防大厦";
                    }
                    mLocation=mLocation.replace("千寻音乐","人防大厦");
                    tvRegisterLocDet.setText(mLocation);
                    tvRegisterLoc.setText(mainLoc);

//                    registerTest.setText(result);
                    // 距离
                    now_latitude = loc.getLatitude();
                    now_longitude = loc.getLongitude();
                    //119.527328,35.432389日照市商务局 35.460573，119.541594至信
                    double default_latitude = 35.432389;
                    double default_longitude = 119.527328;
                    // 计算距离
                    distance = MapUtils.DistanceOfTwoPoints(now_latitude, now_longitude, default_latitude, default_longitude);
                    registerTest.setText("纬度：" + now_latitude + "经度：" + now_longitude + "距离" + String.valueOf(distance) + "米");

                    //绘制marker
                    Marker marker = aMap.addMarker(new MarkerOptions()
                            .position(new LatLng(now_latitude, now_longitude))
                            .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                    .decodeResource(getResources(), R.mipmap.ic_loc_point)))
                            .draggable(true));

                    AMap amap = ivRegisterMap.getMap();
                    CameraPosition p = new CameraPosition.Builder().target(new LatLng(now_latitude, now_longitude)).zoom(15).build();
                    CameraUpdate c = CameraUpdateFactory.newCameraPosition(p);
                    amap.moveCamera(c);

                    break;
                //停止定位
                case MapUtils.MSG_LOCATION_STOP:
                    registerTest.setText("定位停止");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != aMapLocation) {
            Message msg = mHandler.obtainMessage();
            msg.obj = aMapLocation;
            msg.what = MapUtils.MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivRegisterMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ivRegisterMap.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ivRegisterMap.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ivRegisterMap.onDestroy();

        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_right:
                startActivity(new Intent(context, Register_List.class));
        }
    }
    private void showInputDialog() {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(Register_Activity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(Register_Activity.this);
        inputDialog.setTitle("超出签到距离请填写原因").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Register_Activity.this,
                                editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
}
