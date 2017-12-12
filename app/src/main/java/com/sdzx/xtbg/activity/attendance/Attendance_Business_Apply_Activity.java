package com.sdzx.xtbg.activity.attendance;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.AttendanceResponse;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.constant.DateUtils;
import com.sdzx.xtbg.dialog.AttendanceApproveDialog;
import com.sdzx.xtbg.tools.StringUtils;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 出差申请
 * Author:Eron
 * Date: 2016/5/25 0025
 * Time: 16:49
 */
public class Attendance_Business_Apply_Activity extends Activity implements View.OnClickListener {


    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    TextView headerRight;
    @Bind(R.id.bottom_btn1)
    Button bottomBtn1;
    @Bind(R.id.bottom_btn2)
    Button bottomBtn2;

    @Bind(R.id.business_person_edt)
    EditText business_person_edt;// 经办人
    @Bind(R.id.business_apply_name)
    EditText businessApplyName; // 出差申请人
    @Bind(R.id.business_apply_reason)
    EditText businessApplyReason;
    @Bind(R.id.business_apply_place)
    EditText businessApplyPlace;
    @Bind(R.id.business_state_n)
    RadioButton businessStateN;// 是否派车（默认否）（is_pai,1是0否）
    @Bind(R.id.business_state_y)
    RadioButton businessStateY;
    @Bind(R.id.business_state_rad)
    RadioGroup businessStateRad;
    @Bind(R.id.ll_attendance_status)
    LinearLayout llAttendanceStatus;
    @Bind(R.id.business_apply_tool)
    EditText businessApplyTool;// 交通工具
    @Bind(R.id.vacation_start_date)
    TextView vacationStartDate;
    @Bind(R.id.vacation_end_date)
    TextView vacationEndDate;
    @Bind(R.id.business_apply_days)
    EditText businessApplyDays; // 总天数
    @Bind(R.id.vacation_choice_approve)
    TextView vacationChoiceApprove; // 审核人
    @Bind(R.id.business_apply_phone)
    EditText businessApplyPhone;
    @Bind(R.id.business_apply_zs)
    EditText businessApplyZs;
    @Bind(R.id.business_apply_bz)
    EditText businessApplyBz;
    @Bind(R.id.business_apply_bzhj)
    EditText businessApplyBzhj;
    @Bind(R.id.business_apply_renshu)
    EditText business_apply_renshu;

    @Bind(R.id.radiobutton1)
    RadioButton radiobutton1;
    @Bind(R.id.radiobutton2)
    RadioButton radiobutton2;
    @Bind(R.id.radiobutton3)
    RadioButton radiobutton3;
    @Bind(R.id.checkdx)
    CheckBox checkdx;
    private int isMessage;
    private String startDate = "";// 选定的开始日期
    private String endDate = "";// 结束日期
    private Context mContext;
    private SharedPreferences preferences;
    private String uid = "";
    private String is_pai = "0";// 默认0不派车
    private String jtbt;
    private String jsfs;

    private RequestCall mCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_attendance_business_apply);
        ButterKnife.bind(this);
        initConstants();
        initViews();
        initEvents();
        businessApplyName.setText(StringUtils.USER_NAME);
    }

    private void initConstants() {
        mContext = Attendance_Business_Apply_Activity.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        uid = preferences.getString(ConstantString.UID, "");
        Log.e("tag", "用户的UID--------------------------->" + uid);
    }

    private void initViews() {
        headerTitle.setText("出差申请");
        headerRight.setText("申请列表");
        bottomBtn1.setText("提交");
        bottomBtn2.setText("重置");
        business_person_edt.setText(StringUtils.USER_NAME);// 经办人
    }

    private void initEvents() {
        headerBack.setOnClickListener(this);
        bottomBtn1.setOnClickListener(this);
        bottomBtn2.setOnClickListener(this);
        headerRight.setOnClickListener(this);
        checkdx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isMessage = 1;
//                    document_process_message.setVisibility(View.GONE);
                } else {
                    isMessage = 0;
//                    document_process_message.setVisibility(View.GONE);
                }
            }
        });

//        businessStateRad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                if (checkedId == R.id.business_state_n) {
//                    is_pai = "0";
//                    Toast.makeText(mContext, "否", Toast.LENGTH_SHORT).show();
//                } else {
//                    is_pai = "1";
//                    Toast.makeText(mContext, "是", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    /**
     * 开始日期
     */
    @OnClick(R.id.vacation_start_date)
    void startDate() {
        DatePickerDialog datePickerDialogStart = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startDate = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) :
                        (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                vacationStartDate.setText(startDate);
            }
        }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
        datePickerDialogStart.show();
    }

    /**
     * 结束日期
     */
    @OnClick(R.id.vacation_end_date)
    void endDate() {
        DatePickerDialog datePickerDialogEnd = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                endDate = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) :
                        (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                vacationEndDate.setText(endDate);
            }
        }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
        datePickerDialogEnd.show();
    }

    /**
     * 选择审批人
     */
    @OnClick(R.id.vacation_choice_approve)
    void choiceApprove() {
//        Toast.makeText(mContext, "选择审批人", Toast.LENGTH_SHORT).show();
        new AttendanceApproveDialog(mContext)
                .readData(ConstantURL.ATTENDANCE_BUSINESS_APPROVE, uid)
                .builder()
                .setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vacationChoiceApprove.setText(StringUtils.approve_name);
                    }
                })
                .show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.header_right:
                startActivity(new Intent(mContext, Attendance_Business_List_Activity.class)
                        .putExtra(StringUtils.MY_APPROVAL_LIST, 2));
                break;
            case R.id.bottom_btn1:// 提交
                if (checkInput()) {
                    sendRequest();
                }
                break;
            case R.id.bottom_btn2:// 重置
                reset();
                break;
        }
    }

    /**
     * 重置数据
     */
    private void reset() {
        businessApplyName.setText("");
        businessApplyReason.setText("");
        businessApplyPlace.setText("");
        vacationStartDate.setText("");
        vacationEndDate.setText("");
        businessApplyTool.setText("");
        businessApplyDays.setText("");
        vacationChoiceApprove.setText("");// 审核人

    }

    private boolean checkInput() {
        if (businessApplyName.getText().toString().equals("")) {
            Toast.makeText(mContext, "请输入出差人员！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (businessApplyReason.getText().toString().equals("")) {
            Toast.makeText(mContext, "请输入出事由！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (businessApplyPlace.getText().toString().equals("")) {
            Toast.makeText(mContext, "请输入出差地点！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (businessApplyTool.getText().toString().equals("")) {
            Toast.makeText(mContext, "请输入交通工具！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (vacationStartDate.getText().toString().equals("")) {
            Toast.makeText(mContext, "请选择开始时间！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (vacationEndDate.getText().toString().equals("")) {
            Toast.makeText(mContext, "请选择结束时间！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (businessApplyDays.getText().toString().equals("")) {
            Toast.makeText(mContext, "请输入出差天数！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (vacationChoiceApprove.getText().toString().equals("")) {
            Toast.makeText(mContext, "请选择审批人！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 发送提交请求
     */
    public void sendRequest() {
        if (isMessage != 0) {
            mCall = OkHttpUtils
                    .post()
                    .url(ConstantURL.ATTENDANCE_BUSINESS_APPLY + uid)
                    .addParams("position", businessApplyPhone.getText().toString())
                    .addParams("title", businessApplyReason.getText().toString())
                    .addParams("addr", businessApplyPlace.getText().toString())
                    .addParams("jtgj", businessApplyTool.getText().toString())
                    .addParams("start", vacationStartDate.getText().toString())
                    .addParams("end", vacationEndDate.getText().toString())
                    .addParams("day", businessApplyDays.getText().toString())
                    .addParams("usernum", business_apply_renshu.getText().toString())
                    .addParams("cuser", businessApplyName.getText().toString())
                    .addParams("duanxin", isMessage+"")
                    .addParams("tag", StringUtils.tag)
                    .addParams("userid", StringUtils.approve_uid)// 审核人的UID
                    .build();
        }else{
            mCall = OkHttpUtils
                    .post()
                    .url(ConstantURL.ATTENDANCE_BUSINESS_APPLY + uid)
                    .addParams("position", businessApplyPhone.getText().toString())
                    .addParams("title", businessApplyReason.getText().toString())
                    .addParams("addr", businessApplyPlace.getText().toString())
                    .addParams("jtgj", businessApplyTool.getText().toString())
                    .addParams("start", vacationStartDate.getText().toString())
                    .addParams("end", vacationEndDate.getText().toString())
                    .addParams("day", businessApplyDays.getText().toString())
                    .addParams("usernum", business_apply_renshu.getText().toString())
                    .addParams("cuser", businessApplyName.getText().toString())
                    .addParams("tag", StringUtils.tag)
                    .addParams("userid", StringUtils.approve_uid)// 审核人的UID
                    .build();
        }
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json(response);
                    Gson gson = new Gson();
                    AttendanceResponse response1 = gson.fromJson(response, AttendanceResponse.class);
                    String state = response1.getState();
                    if (state.equals("ok")) {
                        Toast.makeText(mContext, "提交成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(mContext, "提交失败！请检查网络后重试！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick({R.id.business_state_n, R.id.business_state_y, R.id.radiobutton1, R.id.radiobutton2, R.id.radiobutton3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.business_state_n:
                jtbt=1+"";
                break;
            case R.id.business_state_y:
                jtbt=2+"";
                break;
            case R.id.radiobutton1:
                jsfs=1+"";
                break;
            case R.id.radiobutton2:
                jsfs=2+"";
                break;
            case R.id.radiobutton3:
                jsfs=3+"";
                break;
        }
    }
}
