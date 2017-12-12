package com.sdzx.xtbg.activity.attendance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.AttendanceAudit;
import com.sdzx.xtbg.bean.HolidayDetail;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.constant.DateUtils;
import com.sdzx.xtbg.dialog.AttendanceAuditDialog;
import com.sdzx.xtbg.tools.StringUtils;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 休假审批详情
 * Author:Eron
 * Date: 2016/5/26 0026
 * Time: 9:01
 */
public class Attendance_Approval_Detail extends Activity {


    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    ImageView headerRight;

    @Bind(R.id.document_btn)
    Button documentBtn;


    @Bind(R.id.attendance_apply_name)
    TextView attendanceApplyName;
    @Bind(R.id.attendance_apply_reason)
    TextView attendanceApplyReason;
    @Bind(R.id.attendance_apply_time)
    TextView attendanceApplyTime;
    @Bind(R.id.attendance_destroy_time)
    TextView attendanceDestroyTime;


    @Bind(R.id.tv_depart_opinion)
    TextView tvDepartOpinion;// 科室负责人意见
    @Bind(R.id.tv_depart_opinion_name)
    TextView tvDepartOpinionName;
    @Bind(R.id.tv_depart_opinion_time)
    TextView tvDepartOpinionTime;
    @Bind(R.id.tv_hr_opinion)
    TextView tvHrOpinion;// 人事科意见
    @Bind(R.id.tv_depart_hr_name)
    TextView tvDepartHrName;
    @Bind(R.id.tv_depart_hr_time)
    TextView tvDepartHrTime;
    @Bind(R.id.tv_minor_opinion)
    TextView tvMinorOpinion;// 分管负责人意见
    @Bind(R.id.tv_depart_minor_name)
    TextView tvDepartMinorName;
    @Bind(R.id.tv_depart_minor_time)
    TextView tvDepartMinorTime;
    @Bind(R.id.tv_main_opinion)
    TextView tvMainOpinion;// 主要负责人意见
    @Bind(R.id.tv_depart_main_name)
    TextView tvDepartMainName;
    @Bind(R.id.tv_depart_main_time)
    TextView tvDepartMainTime;

    @Bind(R.id.ll_need_hide)
    LinearLayout ll_need_hide;


    @Bind(R.id.ll_attendance_receiver)
    LinearLayout llAttendanceReceiver;// 审核人
    @Bind(R.id.ll_attendance_opinion)
    LinearLayout llAttendanceOpinion;// 审核意见
    @Bind(R.id.ll_attendance_status)
    LinearLayout llAttendanceStatus;// 审核状态Button
    @Bind(R.id.attendance_state_rad)
    RadioGroup attendanceStateRad;// RadioGroup
    @Bind(R.id.attendance_state_y)
    RadioButton attendanceStateY;// 状态 同意
    @Bind(R.id.attendance_state_n)
    RadioButton attendanceStateN;
    @Bind(R.id.attendance_receive_people)
    TextView attendanceReceivePeople;// 接收人
    @Bind(R.id.attendance_audit_edt)
    EditText attendanceAuditEdt;// 审核意见

    // 签名版
    @Bind(R.id.signature_pad)
    com.github.gcacace.signaturepad.views.SignaturePad SignaturePad;// 签名版
    @Bind(R.id.clear_button)
    Button clear_button;// 清除图片
    @Bind(R.id.save_button)
    Button save_button;// 保存图片

    @Bind(R.id.ll_sing_board)
    LinearLayout ll_sing_board;// 签名版

    @Bind(R.id.iv_depart_opinion_sign)
    ImageView ivDepartOpinionSign;
    @Bind(R.id.iv_hr_opinion_sign)
    ImageView ivHrOpinionSign;
    @Bind(R.id.iv_minor_opinion_sign)
    ImageView ivMinorOpinionSign;
    @Bind(R.id.iv_main_opinion_sign)
    ImageView ivMainOpinionSign;

    private Context mContext;

    private RequestCall mCall;
    private SharedPreferences preferences;
    private String uid, add_user_id = "";
    private String detailId = "";

    private HolidayDetail detail = new HolidayDetail();
    private AttendanceAudit audit = new AttendanceAudit();// 状态审核实体类
    private String Depart = "";// 审核接收人的部门
    private String Receiver = "";// 审核接收人
    private String tag = "";
    private int totag = 0;
    private String attendance_status = "1";// 状态 （String）（1同意/0 不同意）
    private int is_my_list = 0;

    private static final int UPDATE_UI = 1;
    private String currentTime = "";
    private String returnToApply = "";// 返回给请假人

    private String signet_url = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_UI:
                    if (detail.getValue() != null) {
                        attendanceApplyName.setText(detail.getValue().get(0).getUser());
                        attendanceApplyReason.setText(detail.getValue().get(0).getTitle());
                        attendanceApplyTime.setText(detail.getValue().get(0).getStart() + "起至"
                                + detail.getValue().get(0).getEnd() + "共计"
                                + detail.getValue().get(0).getDays() + "天");
                        if (detail.getValue().get(0).getXiaojia().equals("")) {
                            attendanceDestroyTime.setText("");
                        } else {
                            attendanceDestroyTime.setText(detail.getValue().get(0).getXiaojia());// 销假时间
                        }

                    }
                    if (detail.getValue1().size() != 0) {
                        if (detail.getValue1().get(0).getContent() != null) {
                            tvDepartOpinion.setText(detail.getValue1().get(0).getContent());
                        }
                        tvDepartOpinionName.setText(detail.getValue1().get(0).getAdduser());
                        tvDepartOpinionTime.setText(detail.getValue1().get(0).getAddtime());
                        KLog.e("图片地址", detail.getValue1().get(0).getFujian());
                        Glide.with(mContext)
                                .load(detail.getValue1().get(0).getFujian())
                                .into(ivDepartOpinionSign);
                    }
                    if (detail.getValue2().size() != 0) {
                        if (detail.getValue2().get(0).getContent() != null) {
                            tvHrOpinion.setText(detail.getValue2().get(0).getContent());
                        }
                        tvDepartHrName.setText(detail.getValue2().get(0).getAdduser());
                        tvDepartHrTime.setText(detail.getValue2().get(0).getAddtime());
                        Glide.with(mContext)
                                .load(detail.getValue2().get(0).getFujian())
                                .into(ivHrOpinionSign);
                    }
                    if (detail.getValue3().size() != 0) {
                        if (detail.getValue3().get(0).getContent() != null) {
                            tvMinorOpinion.setText(detail.getValue3().get(0).getContent());
                        }
                        tvDepartMinorName.setText(detail.getValue3().get(0).getAdduser());
                        tvDepartMinorTime.setText(detail.getValue3().get(0).getAddtime());
                        Glide.with(mContext)
                                .load(detail.getValue3().get(0).getFujian())
                                .into(ivMinorOpinionSign);
                    }
                    if (detail.getValue4().size() != 0) {
                        if (detail.getValue4().get(0).getContent() != null) {
                            tvMainOpinion.setText(detail.getValue4().get(0).getContent());
                        }
                        tvDepartMainName.setText(detail.getValue4().get(0).getAdduser());
                        tvDepartMainTime.setText(detail.getValue4().get(0).getAddtime());
                    }

                    // tag=5 时为主要领导签批
                    try {

                        if (tag.equals("4")) {
                            headerTitle.setText("签批");
                            documentBtn.setText("签批");
                            llAttendanceReceiver.setVisibility(View.GONE);
                        }
                        // tag=5 时为销假状态
                        if (tag.equals("5")) {
                            headerTitle.setText("销假");
                            documentBtn.setText("销假");
                            llAttendanceStatus.setVisibility(View.GONE);
                            llAttendanceReceiver.setVisibility(View.GONE);
                            llAttendanceOpinion.setVisibility(View.GONE);
                        } else if (is_my_list == 2) {
                            documentBtn.setVisibility(View.GONE);// 在我的申请列表中，若tag != 5，隐藏操作Button
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_approval_detail);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();

    }

    private void initConstant() {
        mContext = Attendance_Approval_Detail.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        uid = preferences.getString(ConstantString.UID, "");
        Intent intent = getIntent();
        detailId = intent.getStringExtra(ConstantString.ATTENDANCE_DETAIL_ID);
        is_my_list = intent.getIntExtra(ConstantString.ATTENDANCE_MY_LIST, 0);

        currentTime = DateUtils.getTimeYMDHMS();
//        Log.e("tag", "当前时间-------------------------->" + currentTime);
        // 还原全局变量
        StringUtils.approve_uid = "";

    }

    private void initData() {
        requestDetail();// 请求当前审批的详情数据
        requestState();
    }

    private void initView() {
        headerTitle.setText("休假审批单");
        headerRight.setVisibility(View.GONE);
        documentBtn.setText("提交");
        attendanceReceivePeople.setText("选择审批人");
        ll_need_hide.setVisibility(View.GONE);
        ll_sing_board.setVisibility(View.GONE);

        // 若 is_my_list=2,则为我的列表详情，隐藏所有操作布局
        if (is_my_list == 2) {
            llAttendanceStatus.setVisibility(View.GONE);
            llAttendanceReceiver.setVisibility(View.GONE);
            llAttendanceOpinion.setVisibility(View.GONE);
            ll_sing_board.setVisibility(View.GONE);
            headerTitle.setText("休假查询");
        }
        if (totag == 5 || totag == 9) {
            // totag为 5 时，隐藏审核人选项
            llAttendanceReceiver.setVisibility(View.GONE);
        }
    }

    private void initEvent() {
        attendanceStateY.setChecked(true);// 默认同意
        attendanceStateRad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.attendance_state_y) {
                    attendance_status = "1";
                    Toast.makeText(mContext, "同意", Toast.LENGTH_SHORT).show();
                } else {
                    attendance_status = "2";
                    Toast.makeText(mContext, "拒绝", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 签名版
        SignaturePad.setOnSignedListener(new com.github.gcacace.signaturepad.views.SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                save_button.setEnabled(true);
                clear_button.setEnabled(true);
            }

            @Override
            public void onClear() {
                save_button.setEnabled(false);
                clear_button.setEnabled(false);
            }
        });

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignaturePad.clear();
                deleteSignPicture();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap signatureBitmap = SignaturePad.getSignatureBitmap();
                if (addJpgSignatureToGallery(signatureBitmap)) {
                    Toast.makeText(mContext, "已保存", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "不能保存", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 删除签名图片
     */
    private void deleteSignPicture() {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/SignaturePad/Signature_1.jpg");
        if (file.isFile() && file.exists()) {
            file.delete();
            KLog.d("删除成功", "删除");
        } else {
            KLog.d("签名文件已经删除", "删除");
        }
    }

    /**
     * 添加照片到相册
     *
     * @param signature
     * @return
     */
    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", 1));
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
            signet_url = Environment.getExternalStorageDirectory().getPath() + "/Pictures/SignaturePad/Signature_1.jpg";
            KLog.d("附件地址-------------->", signet_url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        mContext.sendBroadcast(mediaScanIntent);
    }

    @OnClick(R.id.header_back)
    void finishThis() {
        finish();
    }

    /**
     * 选择接收人
     * http://gzwxtbg.rizhao.cn/oa/json/leave.php?act=shenpido&uid=16&id=69&do=show
     */
    @OnClick(R.id.attendance_receive_people)
    void selectReceiver() {
        new AttendanceAuditDialog(mContext)
                .readData(ConstantURL.ATTENDANCE_AUDIT_USER + uid + "&id=" + detailId + "&do=show")
                .builder()
                .setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        attendanceReceivePeople.setText(StringUtils.approve_name);
                    }
                })
                .show();
    }

    /**
     * 提交审核
     */
    @OnClick(R.id.document_btn)
    void submitAudit() {
        if (tag.equals("5")) {
            submitFinish();// 提交销假
        } else {
            if (totag == 5) {
                StringUtils.approve_uid = add_user_id;
                submitSign();// 大领导签批，传回请假人ID

            }
            if (attendance_status.equals("2")) {
                submitApproval();// 提交审核数据
            } else if (checkInput()) {
                submitApproval();// 提交审核数据
            }
        }
    }


    /**
     * 请求休假详情
     */
    void requestDetail() {
        try {
            mCall = OkHttpUtils
                    .get()
                    .url(ConstantURL.ATTENDANCE_HOLIDAY_DETAIL + uid + "&id=" + detailId)
                    .build();
            mCall.execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {
//                    Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response) {
                    try {
                        KLog.json("休假详情数据------>", response);
                        Gson gson = new Gson();
                        detail = gson.fromJson(response, HolidayDetail.class);
                        if (detail != null) {
                            Message message = new Message();
                            message.what = UPDATE_UI;
                            handler.sendMessage(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求当前休假的状态
     */
    private void requestState() {
//        Log.e("tag", "审批页面，查询状态URL------------------>" + ConstantURL.ATTENDANCE_REQUEST_STATE + uid + "&id=" + detailId + "&do=show");
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.ATTENDANCE_REQUEST_STATE + uid + "&id=" + detailId + "&do=show")
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(mContext, "网络错误！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json("当前休假的状态---------->", response);
                    Gson gson = new Gson();
                    audit = gson.fromJson(response, AttendanceAudit.class);
                    if (audit.getTag() != null) {
                        tag = audit.getTag();
                        if (tag.equals("3")) {
                            llAttendanceReceiver.setVisibility(View.GONE);
                        }
                    }
                    if (null != (Object) audit.getTotag()) {
                        totag = audit.getTotag();
                    }
                    if (null != audit.getUser()) {
                        add_user_id = audit.getUser().get(0).getId();
                        KLog.d("请假人的ID", add_user_id);
                    }
                    if (tag.equals("5")) {
                    } else {
                        if (null != audit.getUser()) {
                            returnToApply = audit.getUser().get(0).getId();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 提交审批
     */
    private void submitApproval() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("tag", tag);
        params.addBodyParameter("totag", totag + "");
        params.addBodyParameter("userid", StringUtils.approve_uid);
        params.addBodyParameter("content", attendanceAuditEdt.getText().toString());
        params.addBodyParameter("status", attendance_status);
        // 签名板图片
//        File file = new File(signet_url);
//        KLog.d("文件上传路径------>", file);
//        if (file.exists()) {
//            KLog.d("tag", "文件存在------>路径" + signet_url);
//            params.addBodyParameter("fujian", file);
//        } else {
//            KLog.d("tag", "文件不存在----->");
//        }
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.ATTENDANCE_AUDIT_ACTION + uid + "&id=" + detailId + "&do=do",
                params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            Log.e("审批返回", responseInfo.result);
                            Gson gson = new Gson();
                            Status status = gson.fromJson(responseInfo.result, Status.class);
                            if (status != null) {
                                if (status.getState().equals("ok")) {
                                    Toast.makeText(mContext, "处理成功！", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(mContext, "处理失败！", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }

    /**
     * 提交签批
     */
    private void submitSign() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("tag", tag);
        params.addBodyParameter("totag", totag + "");
        params.addBodyParameter("userid", StringUtils.approve_uid);
        params.addBodyParameter("content", attendanceAuditEdt.getText().toString());
        params.addBodyParameter("status", attendance_status);
        // 签名板图片
        File file = new File(signet_url);
        KLog.d("文件上传路径------>", file);
        if (file.exists()) {
            KLog.d("tag", "文件存在------>路径" + signet_url);
            params.addBodyParameter("fujian", file);
        } else {
            KLog.d("tag", "文件不存在----->");
        }
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.ATTENDANCE_AUDIT_ACTION + uid + "&id=" + detailId + "&do=do",
                params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            Log.e("审批返回", responseInfo.result);
                            Gson gson = new Gson();
                            Status status = gson.fromJson(responseInfo.result, Status.class);
                            if (status != null) {
                                if (status.getState().equals("ok")) {
                                    Toast.makeText(mContext, "处理成功！", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(mContext, "处理失败！", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }

    /**
     * 提交销假
     */
    private void submitFinish() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("tag", tag);
        params.addBodyParameter("totag", totag + "");
        params.addBodyParameter("content", attendanceAuditEdt.getText().toString());
        params.addBodyParameter("status", attendance_status);
        params.addBodyParameter("xiaojia", currentTime);
        // 签名板图片
        File file = new File(signet_url);
        KLog.d("文件上传路径------>", file);
        if (file.exists()) {
            KLog.d("tag", "文件存在------>路径" + signet_url);
            params.addBodyParameter("fujian", file);
        } else {
            KLog.d("tag", "文件不存在----->");
        }
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.ATTENDANCE_AUDIT_ACTION + uid + "&id=" + detailId + "&do=do",
                params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            Log.e("审批返回", responseInfo.result);
                            Gson gson = new Gson();
                            Status status = gson.fromJson(responseInfo.result, Status.class);
                            if (status != null) {
                                if (status.getState().equals("ok")) {
                                    Toast.makeText(mContext, "处理成功！", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(mContext, "处理失败！", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }

    /**
     * 检查输入状态
     *
     * @return
     */
    private boolean checkInput() {
        if (attendanceReceivePeople.getText().toString().equals("选择审批人")) {
            if (tag.equals("1") || tag.equals("2")) {
                Toast.makeText(mContext, "请选择审批人！", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mCall.cancel();
    }
}
