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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.AttendanceResponse;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.constant.DateUtils;
import com.sdzx.xtbg.dialog.AttendanceApproveDialog;
import com.sdzx.xtbg.dialog.Time_Picker_Dialog;
import com.sdzx.xtbg.tools.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 请假申请
 * Author:Eron
 * Date: 2016/5/25 0025
 * Time: 16:49
 */
public class Attendance_Leave_Apply_Activity extends Activity implements View.OnClickListener {

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
    @Bind(R.id.vacation_person_edt)
    EditText vacationPersonEdt;// 休假人
    //    @Bind(R.id.attendance_department_edt)
//    EditText attendanceDepartmentEdt;// 部门信息没有返回且不好拿，暂时放弃显示，上传时不需要这个参数
    @Bind(R.id.vacation_reason_edt)
    EditText vacationReasonEdt;
    @Bind(R.id.vacation_start_date)
    TextView vacationStartDate;// 开始日期
    @Bind(R.id.vacation_end_date)
    TextView vacationEndDate;
    @Bind(R.id.vacation_start_time)
    TextView vacationStartTime;// 开始时间
    @Bind(R.id.vacation_end_time)
    TextView vacationEndTime;
    @Bind(R.id.vacation_count_days)
    EditText vacationCountDays;// 休假天数
    @Bind(R.id.vacation_choice_approve)
    TextView vacationChoiceApprove;// 审批人
    @Bind(R.id.radiobutton1)
    RadioButton radiobutton1;
    @Bind(R.id.radiobutton2)
    RadioButton radiobutton2;
    @Bind(R.id.radiobutton3)
    RadioButton radiobutton3;
    @Bind(R.id.radiobutton4)
    RadioButton radiobutton4;
    @Bind(R.id.radiobutton5)
    RadioButton radiobutton5;
    @Bind(R.id.radiobutton6)
    RadioButton radiobutton6;
    @Bind(R.id.radiobutton7)
    RadioButton radiobutton7;
    @Bind(R.id.radiobutton8)
    RadioButton radiobutton8;

    @Bind(R.id.checkdx)
    CheckBox checkdx;
    private int isMessage;


    private String startDate = "";// 选定的开始日期
    private String endDate = "";// 结束日期
    private Context mContext;
    private SharedPreferences preferences;
    private String uid = "";

    private RequestCall mCall;
    private String title = "";
    private String type="1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_attendance_apply);
        ButterKnife.bind(this);
        initConstants();
        initViews();
        initEvents();
    }

    private void initConstants() {
        mContext = Attendance_Leave_Apply_Activity.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        uid = preferences.getString(ConstantString.UID, "");
        Log.e("tag", "用户的UID--------------------------->" + uid);
    }

    private void initViews() {
        headerTitle.setText("请假申请");
        headerRight.setText("申请列表");
        bottomBtn1.setText("提交");
        bottomBtn2.setText("重置");
        vacationPersonEdt.setText(StringUtils.USER_NAME);
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
    }

    private void initEvents() {
        headerBack.setOnClickListener(this);
        bottomBtn1.setOnClickListener(this);
        bottomBtn2.setOnClickListener(this);
        headerRight.setOnClickListener(this);
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
     * 开始时间
     */
    @OnClick(R.id.vacation_start_time)
    void startTime() {
        new Time_Picker_Dialog(mContext)
                .builder()
                .setCancelable(false)
                .setTitle("开始时间")
                .setNegativeButton("取消")
                .setPositiveButton("确定", vacationStartTime)
                .show();
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
     * 结束时间
     */
    @OnClick(R.id.vacation_end_time)
    void endTime() {
        new Time_Picker_Dialog(mContext)
                .builder()
                .setCancelable(false)
                .setTitle("开始时间")
                .setNegativeButton("取消")
                .setPositiveButton("确定", vacationEndTime)
                .show();
    }

    /**
     * 选择审批人
     */
    @OnClick(R.id.vacation_choice_approve)
    void choiceApprove() {
//        Toast.makeText(mContext, "选择审批人", Toast.LENGTH_SHORT).show();
        new AttendanceApproveDialog(mContext)
                .readData(ConstantURL.ATTENDANCE_LEAVE_APPROVE, uid)
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
                startActivity(new Intent(mContext, Attendance_Leave_List_Activity.class)
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
        vacationReasonEdt.setText("");
        vacationStartDate.setText("");
        vacationStartTime.setText("");
        vacationEndDate.setText("");
        vacationEndTime.setText("");
        vacationCountDays.setText("");
        vacationChoiceApprove.setText("");
    }

    private boolean checkInput() {
        if (vacationReasonEdt.getText().toString().equals("")) {
            Toast.makeText(mContext, "请输入请假事由！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (vacationStartDate.getText().toString().equals("")) {
            Toast.makeText(mContext, "请选择开始日期！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (vacationStartTime.getText().toString().equals("")) {
            Toast.makeText(mContext, "请选择开始时间！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (vacationEndDate.getText().toString().equals("")) {
            Toast.makeText(mContext, "请选择结束日期！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (vacationEndTime.getText().toString().equals("")) {
            Toast.makeText(mContext, "请选择结束时间！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (vacationCountDays.getText().toString().equals("")) {
            Toast.makeText(mContext, "请输入请假天数！", Toast.LENGTH_SHORT).show();
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
        if (isMessage!=0){
            mCall = OkHttpUtils
                    .post()
                    .url(ConstantURL.ATTENDANCE_LEAVE_APPLY + uid)
                    .addParams("title", vacationReasonEdt.getText().toString())
                    .addParams("start", vacationStartDate.getText().toString() + " " + vacationStartTime.getText().toString())
                    .addParams("end", vacationEndDate.getText().toString() + " " + vacationEndTime.getText().toString())
                    .addParams("days", vacationCountDays.getText().toString())
                    .addParams("type", type)
                    .addParams("tag", StringUtils.tag)
                    .addParams("duanxin", isMessage+"")
                    .addParams("userid", StringUtils.approve_uid)// 审核人的UID
                    .build();
        }else {
            mCall = OkHttpUtils
                    .post()
                    .url(ConstantURL.ATTENDANCE_LEAVE_APPLY + uid)
                    .addParams("title", vacationReasonEdt.getText().toString())
                    .addParams("start", vacationStartDate.getText().toString() + " " + vacationStartTime.getText().toString())
                    .addParams("end", vacationEndDate.getText().toString() + " " + vacationEndTime.getText().toString())
                    .addParams("days", vacationCountDays.getText().toString())
                    .addParams("type", type)
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
                    Log.e("tag", "提交休假返回值--------------------------->" + response);
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

    @OnClick({R.id.radiobutton1, R.id.radiobutton2, R.id.radiobutton3, R.id.radiobutton4, R.id.radiobutton5, R.id.radiobutton6, R.id.radiobutton7, R.id.radiobutton8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radiobutton1:
                title = "公休假";
                type="1";
                break;
            case R.id.radiobutton2:
                title = "事假";
                type="2";
                break;
            case R.id.radiobutton3:
                title = "病假";
                type="3";
                break;
            case R.id.radiobutton4:
                title = "探亲假";
                type="4";
                break;
            case R.id.radiobutton5:
                title = "婚假";
                type="5";
                break;
            case R.id.radiobutton6:
                title = "产假";
                type="6";
                break;
            case R.id.radiobutton7:
                title = "丧假";
                type="7";
                break;
//            case R.id.radiobutton8:
//                title = "其他";
//                break;
        }
    }
}
