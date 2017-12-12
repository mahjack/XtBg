package com.sdzx.xtbg.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.Attendance_Approver_Adapter;
import com.sdzx.xtbg.adapter.Attendance_Approver_Adapter3;
import com.sdzx.xtbg.adapter.Attendance_Approver_Adapter4;
import com.sdzx.xtbg.bean.AttendanceApprover;
import com.sdzx.xtbg.bean.AttendanceApprover3;
import com.sdzx.xtbg.bean.Attendance_Examine;
import com.sdzx.xtbg.bean.Lynn_Tag;
import com.sdzx.xtbg.tools.StringUtils;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 考勤审批人Dialog
 * Author:Eron
 * Date: 2016/5/25 0025
 * Time: 10:25
 */
public class AttendanceApproveDialog {

    @Bind(R.id.tv_dialog_title)
    TextView tvDialogTitle;// 标题
    @Bind(R.id.tv_dialog_select_all)
    TextView tvDialogSelectAll;// 全选
    @Bind(R.id.attendance_approval_list)
    ListView attendanceApprovalList;
    @Bind(R.id.dialog_progress_bar)
    ProgressBar dialogProgressBar;
    @Bind(R.id.btn_dialog_negative)
    Button btnDialogNegative;
    @Bind(R.id.btn_dialog_positive)
    Button btnDialogPositive;
    @Bind(R.id.attendanceApproveDialog)
    RelativeLayout attendanceApproveDialog;

    private Attendance_Approver_Adapter adapter;
    private AttendanceApprover approver = new AttendanceApprover();
    private AttendanceApprover3 approver3= new AttendanceApprover3();
    private List<AttendanceApprover.User> userList = new ArrayList<AttendanceApprover.User>();
    private List<AttendanceApprover3.DataEntity.UseridEntity> userid;
    private Attendance_Approver_Adapter3 adapter3;
    private RequestCall mCall;//OkHttpCall

    private Attendance_Examine examine = new Attendance_Examine();

    private Context mContext;
    private Display display;
    private Dialog dialog;

    public AttendanceApproveDialog(Context context) {
        this.mContext = context;
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        this.display = manager.getDefaultDisplay();
    }

    public AttendanceApproveDialog builder() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_attendance_approve, null);
        // 自定义Dialog布局参数
        dialog = new Dialog(mContext, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        ButterKnife.bind(this, view);

        // 调整Dialog大小
        attendanceApproveDialog.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.86), (int) (display.getHeight() * 0.86)));


        return this;
    }

    /**
     * 读取审批人
     *
     * @param url
     * @param uid
     * @return
     */
    public AttendanceApproveDialog readData(String url, String uid) {
        mCall = OkHttpUtils
                .get()
                .url(url + uid)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
                dialogProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response) {
                dialogProgressBar.setVisibility(View.GONE);
                try {
                    KLog.json("审批人数据------------->", response);
                    if (response != null) {
                        Gson gson = new Gson();
                        approver = gson.fromJson(response, AttendanceApprover.class);
                        tvDialogTitle.setText(approver.getDepart().get(0).getName());
                        tvDialogSelectAll.setVisibility(View.GONE);
                        userList = approver.getUser();
                        StringUtils.tag=approver.getTag()+"";
                        adapter = new Attendance_Approver_Adapter(mContext, userList);
                        attendanceApprovalList.setAdapter(adapter);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        return this;
    }

    public AttendanceApproveDialog readData(String url, String uid, int a, int b) {
        mCall = OkHttpUtils
                .get()
                .url(url + uid)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
                dialogProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response) {
                dialogProgressBar.setVisibility(View.GONE);
                try {
                    KLog.json("审批人数据------------->", response);
                    if (response != null) {
                        Gson gson = new Gson();
                        approver3 = gson.fromJson(response, AttendanceApprover3.class);
//                        tvDialogTitle.setText(approver.getDepart().get(0).getName());
                        tvDialogSelectAll.setVisibility(View.GONE);
                        userid = approver3.getData().getUserid();
                        adapter3 = new Attendance_Approver_Adapter3(mContext, userid);
                        attendanceApprovalList.setAdapter(adapter3);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        return this;
    }

    public AttendanceApproveDialog readData(String url) {
        mCall = OkHttpUtils
                .get()
                .url(url)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
                dialogProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response) {
                dialogProgressBar.setVisibility(View.GONE);
                try {
                    KLog.json("审批人数据------------->", response);
                    if (response != null) {
                        Gson gson = new Gson();
                        Lynn_Tag lynn_tag = gson.fromJson(response, Lynn_Tag.class);
                        if (lynn_tag.getData().getDepart() != null)
                            tvDialogTitle.setText(lynn_tag.getData().getDepart().get(0).getName());
                        else
                            tvDialogTitle.setText("审批人");
                        tvDialogSelectAll.setVisibility(View.GONE);
                        if (examine.getTag() + "" != null)
                            StringUtils.approve_totag = lynn_tag.getData().getTotag() + "";
                        Attendance_Approver_Adapter4 adapter4 = new Attendance_Approver_Adapter4(mContext, lynn_tag);
                        attendanceApprovalList.setAdapter(adapter4);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        return this;
    }

    /**
     * 设置Dialog不可取消
     *
     * @param cancel
     * @return
     */
    public AttendanceApproveDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 确定Button
     *
     * @param listener
     * @return
     */
    public AttendanceApproveDialog setPositiveButton(final View.OnClickListener listener) {

        btnDialogPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                listener.onClick(v);
                dialog.dismiss();
            }
        });

        return this;
    }

    /**
     * 取消Button
     *
     * @param listener
     * @return
     */
    public AttendanceApproveDialog setNegativeButton(final View.OnClickListener listener) {
        btnDialogNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
