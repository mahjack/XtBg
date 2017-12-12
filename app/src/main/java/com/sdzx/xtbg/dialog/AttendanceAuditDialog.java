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
import com.sdzx.xtbg.adapter.Attendance_Audit_Adapter;
import com.sdzx.xtbg.bean.AttendanceAudit;
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
 * 审核接收人Dialog和ApproveDialog的数据类型不同
 * Author:Eron
 * Date: 2016/5/25 0025
 * Time: 10:25
 */
public class AttendanceAuditDialog {

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

    private Attendance_Audit_Adapter adapter;
    private AttendanceAudit audit = new AttendanceAudit();
    private List<AttendanceAudit.User> userList = new ArrayList<AttendanceAudit.User>();

    private RequestCall mCall;//OkHttpCall

    private Context mContext;
    private Display display;
    private Dialog dialog;

    public AttendanceAuditDialog(Context context) {
        this.mContext = context;
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        this.display = manager.getDefaultDisplay();
    }

    public AttendanceAuditDialog builder() {
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
     * @return
     */
    public AttendanceAuditDialog readData(String url) {
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
                    KLog.json("获取的审批人数据------>", response);
                    if (response != null) {
                        Gson gson = new Gson();
                        audit = gson.fromJson(response, AttendanceAudit.class);
                        try{

                        }catch (Exception e){

                            tvDialogTitle.setText(audit.getDepart().get(0).getName());
                            tvDialogSelectAll.setVisibility(View.GONE);
                        }
                        userList = audit.getUser();
                        adapter = new Attendance_Audit_Adapter(mContext, userList);
                        attendanceApprovalList.setAdapter(adapter);
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
    public AttendanceAuditDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 确定Button
     *
     * @param listener
     * @return
     */
    public AttendanceAuditDialog setPositiveButton(final View.OnClickListener listener) {

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
    public AttendanceAuditDialog setNegativeButton(final View.OnClickListener listener) {
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
