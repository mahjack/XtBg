package com.sdzx.xtbg.activity.attendance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.activity.register.Register_Activity;
import com.sdzx.xtbg.bean.Unread;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.tools.StringUtils;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Author：Mark
 * Date：2016/5/11 0011
 * Tell：15006330640
 * <p/>
 * 考勤管理
 */
public class Attendance_Activity extends Activity implements View.OnClickListener {

    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    TextView headerRight;
    @Bind(R.id.vacation_apply_img)
    ImageView vacationApplyImg;
    @Bind(R.id.vacation_apply_txt)
    TextView vacationApplyTxt;
    @Bind(R.id.vacation_apple_rel)
    RelativeLayout vacationAppleRel;
    @Bind(R.id.vacation_approval_img)
    ImageView vacationApprovalImg;
    @Bind(R.id.vacation_approval_txt)
    TextView vacationApprovalTxt;// 休假审批
    @Bind(R.id.vacation_approval_rel)
    RelativeLayout vacationApprovalRel;
    @Bind(R.id.vacation_consult_img)
    ImageView vacationConsultImg;
    @Bind(R.id.vacation_consult_txt)
    TextView vacationConsultTxt;
    @Bind(R.id.vacation_consult_rel)
    RelativeLayout vacationConsultRel;
    //    @Bind(R.id.vacation_edit_img)
//    ImageView vacationEditImg;
//    @Bind(R.id.vacation_edit_txt)
//    TextView vacationEditTxt;
    //    @Bind(R.id.vacation_edit_rel)
//    RelativeLayout vacationEditRel;
    @Bind(R.id.leave_apply_img)
    ImageView leaveApplyImg;
    @Bind(R.id.leave_apply_txt)
    TextView leaveApplyTxt;
    @Bind(R.id.leave_apply_rel)
    RelativeLayout leaveApplyRel;
    @Bind(R.id.leave_approval_img)
    ImageView leaveApprovalImg;
    @Bind(R.id.leave_approval_txt)
    TextView leaveApprovalTxt;
    @Bind(R.id.leave_approval_rel)
    RelativeLayout leaveApprovalRel;
    @Bind(R.id.leave_consult_img)
    ImageView leaveConsultImg;
    @Bind(R.id.leave_consult_txt)
    TextView leaveConsultTxt;
    @Bind(R.id.leave_consult_rel)
    RelativeLayout leaveConsultRel;
    //    @Bind(R.id.leave_edit_img)
//    ImageView leaveEditImg;
//    @Bind(R.id.leave_edit_txt)
//    TextView leaveEditTxt;
    //    @Bind(R.id.leave_edit_rel)
//    RelativeLayout leaveEditRel;
    @Bind(R.id.business_apply_img)
    ImageView businessApplyImg;
    @Bind(R.id.business_apply_txt)
    TextView businessApplyTxt;
    @Bind(R.id.business_apply_rel)
    RelativeLayout businessApplyRel;
    @Bind(R.id.business_approval_img)
    ImageView businessApprovalImg;
    @Bind(R.id.tv_inquiry)
    TextView tvInquiry;
    @Bind(R.id.business_approval_rel)
    RelativeLayout businessApprovalRel;
    @Bind(R.id.business_consult_img)
    ImageView businessConsultImg;
    @Bind(R.id.tv_attendance)
    TextView tvAttendance;
    @Bind(R.id.business_consult_rel)
    RelativeLayout businessConsultRel;
    // 未读数量
    @Bind(R.id.unread_leave_num)
    TextView unreadLeaveNum;// 休假
    @Bind(R.id.unread_atten_num)
    TextView unreadAttenNum;// 请假
    @Bind(R.id.unread_business_num)
    TextView unreadBusinessNum;// 出差
    @Bind(R.id.cc)
    LinearLayout cc;
    private Context context;
    private static final int MEETING_APPROVAL = 1; // 会议审批
    private static final int MEETING_EDIT = 2; // 会议编辑
    private SharedPreferences preferences;
    private String userId = "";

    private RequestCall mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_attendance);
        ButterKnife.bind(this);
        initConstants();
        initViews();
        initEvents();
    }

    private void initConstants() {
        context = Attendance_Activity.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        userId = preferences.getString(ConstantString.UID, "");
        initUnreadNum();
    }

    /**
     * 获取未读数量
     */
    private void initUnreadNum() {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.UNREAD_URL)
                .addParams(ConstantString.UID, userId)
                .addParams(ConstantString.ACT, "total")
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "访问服务器失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json("请假未读数量------>", response);
                    Gson gson = new Gson();
                    Unread unRead = gson.fromJson(response, Unread.class);
                    if (unRead != null) {
//                        // 休假
//                        if (Integer.valueOf(unRead.getLeave()) != 0) {
//                            unreadLeaveNum.setVisibility(View.VISIBLE);
//                            unreadLeaveNum.setText(unRead.getLeave());
//                        } else {
//                            unreadLeaveNum.setVisibility(View.GONE);
//                        }
                        // 请假
                        if (!unRead.getAtten().equals("0")) {
                            unreadAttenNum.setVisibility(View.VISIBLE);
                            unreadAttenNum.setText(unRead.getAtten());
                        } else {
                            unreadAttenNum.setVisibility(View.GONE);
                        }
                        // 出差
                        if (Integer.valueOf(unRead.getBusiness()) != 0) {
                            unreadBusinessNum.setVisibility(View.VISIBLE);
                            unreadBusinessNum.setText(unRead.getBusiness());
                        } else {
                            unreadBusinessNum.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initViews() {
        headerTitle.setText("请休假管理");
        headerRight.setText("签到");
        cc.setVisibility(View.GONE);
        headerRight.setVisibility(View.GONE);
    }

    private void initEvents() {
        headerBack.setOnClickListener(this);
        vacationAppleRel.setOnClickListener(this);
        vacationApprovalRel.setOnClickListener(this);
        vacationConsultRel.setOnClickListener(this);
//        vacationEditRel.setOnClickListener(this);
        leaveApplyRel.setOnClickListener(this);
        leaveApprovalRel.setOnClickListener(this);
        leaveConsultRel.setOnClickListener(this);
//        leaveEditRel.setOnClickListener(this);
        businessApplyRel.setOnClickListener(this);
        businessApprovalRel.setOnClickListener(this);
        businessConsultRel.setOnClickListener(this);
//        businessEditRel.setOnClickListener(this);
        headerRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.header_right:// 签到模块
                startActivity(new Intent(context, Register_Activity.class));
                break;
            case R.id.vacation_apple_rel:
                // 休假申请
                startActivity(new Intent(context, Attendance_Holiday_Apply_Activity.class));
                break;
            case R.id.vacation_approval_rel:
                // 休假审批
                startActivity(new Intent(context, Attendance_Approval_List_Activity.class)
                        .putExtra("type", MEETING_APPROVAL));
                break;
            case R.id.vacation_consult_rel:
                // 休假查阅
                startActivity(new Intent(context, Attendance_Approval_List_Activity.class)
                        .putExtra(StringUtils.MY_APPROVAL_LIST, 3));
                break;
//            case R.id.vacation_edit_rel:
//                // 休假编辑
//                startActivity(new Intent(context,Meeting_Edit_Activity.class)
//                        .putExtra("type",MEETING_EDIT));
//                break;
            case R.id.leave_apply_rel:
                // 请假申请
                startActivity(new Intent(context, Attendance_Leave_Apply_Activity.class));
                break;
            case R.id.leave_approval_rel:
                // 请假审批
                startActivity(new Intent(context, Attendance_Leave_List_Activity.class)
                        .putExtra("type", MEETING_APPROVAL));
                break;
            case R.id.leave_consult_rel:
                // 请假查阅
                startActivity(new Intent(context, Attendance_Leave_List_Activity.class)
                        .putExtra(StringUtils.MY_APPROVAL_LIST, 3));
                break;
//            case R.id.leave_edit_rel:
//                // 请假编辑
//                startActivity(new Intent(context,Meeting_Edit_Activity.class)
//                        .putExtra("type",MEETING_EDIT));
//                break;
            case R.id.business_apply_rel:
                // 出差申请
                startActivity(new Intent(context, Attendance_Business_Apply_Activity.class));
                break;
            case R.id.business_approval_rel:
                // 出差审批
                startActivity(new Intent(context, Attendance_Business_List_Activity.class)
                        .putExtra("type", MEETING_APPROVAL));
                break;
            case R.id.business_consult_rel:
                // 出差查阅
                startActivity(new Intent(context, Attendance_Business_List_Activity.class)
                        .putExtra(StringUtils.MY_APPROVAL_LIST, 3));
                break;
//            case R.id.business_edit_rel:
//                // 出差编辑
//                startActivity(new Intent(context,Meeting_Edit_Activity.class)
//                        .putExtra("type",MEETING_EDIT));
//                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUnreadNum();
    }
}
