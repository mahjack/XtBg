package com.sdzx.xtbg.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
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
import com.sdzx.xtbg.adapter.SendDocDetailReceiverAdapter;
import com.sdzx.xtbg.bean.SendDocApprove;
import com.sdzx.xtbg.tools.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 发文详情审核人Dialog
 * Author:Eron
 * Date: 2016/5/25 0025
 * Time: 10:25
 */
public class SendDocDetailApproveDialog {

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

    private SendDocDetailReceiverAdapter sendDocReceiverAdapter;
//    private SendDocReceiver receiver = new SendDocReceiver();// 发文接收人
//    private List<SendDocReceiver.ReceiverUser> receiverList = new ArrayList<>();

    private SendDocApprove sendDocApprove = new SendDocApprove();
    private List<SendDocApprove.UserID> userIDList = new ArrayList<>();

    private RequestCall mCall;//OkHttpCall
    private Context mContext;
    private Display display;
    private Dialog dialog;

    public SendDocDetailApproveDialog(Context context) {
        this.mContext = context;
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        this.display = manager.getDefaultDisplay();
    }

    public SendDocDetailApproveDialog builder() {
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
    public SendDocDetailApproveDialog readData(String url, String detailId, String getPath, String uid) {
        mCall = OkHttpUtils
                .get()
                .url(url + detailId + getPath + uid)
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
                    Log.e("tag", "获取发文详情---审批人数据------------------------>" + response);
                    if (response != null) {
//                        JSONObject object=new JSONObject(response);
//                        object.getString("userid");
                        Gson gson=new Gson();
//                        String json="{'tag': '0','tagname': '核稿','status': '1', 'userid': [{'id': '5','realname': '"+object.getString("userid")+"'}],'totag': '1'}";
                        sendDocApprove=gson.fromJson(response, SendDocApprove.class);
//                        tvDialogSelectAll.setText(sendDocApprove.getTagname());
                        tvDialogSelectAll.setVisibility(View.GONE);
                        userIDList = sendDocApprove.getUser();
                        sendDocReceiverAdapter = new SendDocDetailReceiverAdapter(mContext, userIDList);
                        attendanceApprovalList.setAdapter(sendDocReceiverAdapter);
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
    public SendDocDetailApproveDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 确定Button
     *
     * @param listener
     * @return
     */
    public SendDocDetailApproveDialog setPositiveButton(final View.OnClickListener listener) {

//        btnDialogPositive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                listener.onClick(v);
//                dialog.dismiss();
//            }
//        });
        btnDialogPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(StringUtils.isNo){

                    }else {
                        Log.e("有数据","");
                        StringUtils.name = "";
                        StringUtils.user_id = "";
            /*
			 * 删除算法最复杂,拿到checkBox选择寄存map
			 */
                        Map<Integer, Boolean> map = sendDocReceiverAdapter.getCheckMap();

                        // 获取当前的数据数量
                        int count = sendDocReceiverAdapter.getCount();
                        // 进行遍历
                        for (int i = 0; i < count; i++) {
                            // 因为List的特性,删除了2个item,则3变成2,所以这里要进行这样的换算,才能拿到删除后真正的position
                            int position = i - (count - sendDocReceiverAdapter.getCount());
                            if (map.get(i) != null && map.get(i)) {
                                SendDocApprove.UserID bean = (SendDocApprove.UserID) sendDocReceiverAdapter.getItem(position);
                                StringUtils.name += bean.getRealname()+"、";
                                StringUtils.user_id += bean.getId()+ "|";
//                        if (bean.isCanRemove()) {
//                            adapter.getCheckMap().remove(i);
//                            adapter.remove(position);
//                        } else {
//                            map.put(position, false);
//                        }
                            }
                        }
                    }
                Log.e("选中A", StringUtils.name+"----"+StringUtils.user_id);
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
    public SendDocDetailApproveDialog setNegativeButton(final View.OnClickListener listener) {
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
