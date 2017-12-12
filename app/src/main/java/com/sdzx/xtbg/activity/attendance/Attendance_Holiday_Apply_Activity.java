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
import android.widget.DatePicker;
import android.widget.EditText;
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
 * Author：Mark
 * Date：2016/5/17 0017
 * Tell：15006330640
 * <p/>
 * 休假申请
 */
public class Attendance_Holiday_Apply_Activity extends Activity implements View.OnClickListener {

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
    @Bind(R.id.attendance_department_edt)
    EditText attendanceDepartmentEdt;// 部门信息没有返回且不好拿，暂时放弃显示，上传时不需要这个参数
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
    @Bind(R.id.vacation_reason_nianxian)
    EditText vacationReasonNianxian;
    @Bind(R.id.vacation_xjday)
    EditText vacationXjday;
    @Bind(R.id.vacation_txday)
    EditText vacationTxday;
    @Bind(R.id.vacation_yxjday)
    EditText vacationYxjday;
    @Bind(R.id.vacation_bcxjday)
    EditText vacationBcxjday;


    private String startDate = "";// 选定的开始日期
    private String endDate = "";// 结束日期
    private Context mContext;
    private SharedPreferences preferences;
    private String uid = "";

    private RequestCall mCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_attendance_holiday_apply);
        ButterKnife.bind(this);
        initConstants();
        initViews();
        initEvents();
    }

    private void initConstants() {
        mContext = Attendance_Holiday_Apply_Activity.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        uid = preferences.getString(ConstantString.UID, "");
        Log.e("tag", "用户的UID--------------------------->" + uid);
    }

    private void initViews() {
        headerTitle.setText("休假申请");
        headerRight.setText("申请列表");
        headerRight.setVisibility(View.GONE);
        bottomBtn1.setText("提交");
        bottomBtn2.setText("重置");
        vacationPersonEdt.setText(StringUtils.USER_NAME);
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
                .readData(ConstantURL.ATTENDANCE_APPROVER, uid)
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
                startActivity(new Intent(mContext, Attendance_Approval_List_Activity.class)
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
//        if (vacationReasonEdt.getText().toString().equals("")) {
//            Toast.makeText(mContext, "请输入休假事由！", Toast.LENGTH_SHORT).show();
//            return false;
//        }
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
//        if (vacationCountDays.getText().toString().equals("")) {
//            Toast.makeText(mContext, "请输入休假天数！", Toast.LENGTH_SHORT).show();
//            return false;
//        }
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
        mCall = OkHttpUtils
                .post()
                .url(ConstantURL.ATTENDANCE_HOLIDAY_APPLY + uid)
                .addParams("start", vacationStartDate.getText().toString() + " " + vacationStartTime.getText().toString())
                .addParams("end", vacationEndDate.getText().toString() + " " + vacationEndTime.getText().toString())
                .addParams("zw", attendanceDepartmentEdt.getText().toString())
                .addParams("cjgzsj", vacationReasonEdt.getText().toString())
                .addParams("gznx", vacationReasonNianxian.getText().toString())
                .addParams("qdays",vacationXjday.getText().toString())
                .addParams("yidays",vacationTxday.getText().toString())
                .addParams("ydays",vacationYxjday.getText().toString())
                .addParams("days",vacationBcxjday.getText().toString())
                .addParams("userid", StringUtils.approve_uid)// 审核人的UID
                .build();
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

}
