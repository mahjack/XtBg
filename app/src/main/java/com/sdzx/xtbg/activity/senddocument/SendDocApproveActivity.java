package com.sdzx.xtbg.activity.senddocument;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.bean.Status;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.dialog.SendDocDetailApproveDialog;
import com.sdzx.xtbg.tools.StringUtils;
import com.socks.library.KLog;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发文审批
 * Author:Eron
 * Date: 2016/6/25 0025
 * Time: 21:57
 */
public class SendDocApproveActivity extends Activity {

    @Bind(R.id.header_back)
    TextView headerBack;
    @Bind(R.id.header_title)
    TextView headerTitle;
    @Bind(R.id.header_right)
    TextView headerRight;
    @Bind(R.id.send_doc_detail_approver)
    TextView sendDocDetailApprover;

    @Bind(R.id.send_doc_receiver_ll)
    LinearLayout sendDocReceiverLL;
    @Bind(R.id.send_doc_detail_approve_rb_y)
    RadioButton sendDocDetailApproveRbY;
    @Bind(R.id.send_doc_detail_approve_rb_n)
    RadioButton sendDocDetailApproveRbN;
    @Bind(R.id.send_doc_detail_approve_rg)
    RadioGroup sendDocDetailApproveRg;

    @Bind(R.id.send_doc_detail_submit)
    Button sendDocDetailSubmit;

    // 签名版
    @Bind(R.id.signature_pad)
    com.github.gcacace.signaturepad.views.SignaturePad SignaturePad;// 签名版
    @Bind(R.id.clear_button)
    Button clear_button;// 清除图片
    @Bind(R.id.save_button)
    Button save_button;// 保存图片

    @Bind(R.id.checkdx)
    CheckBox checkdx;
    private int isMessage;
    @Bind(R.id.document_process_idea)
    EditText document_process_idea; // 意见


    private Context context;

    private SharedPreferences preferences;
    private RequestCall mCall;
    private String detailId, userId, totag, tagName, user_id = "";
    private int status = 1;// 是否同意,1 默认同意
    private List<String> receiverId = new ArrayList<>();

    private String signet_url = "";
    String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_doc_approve);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();

    }

    private void initConstant() {
        context = SendDocApproveActivity.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        userId = preferences.getString(ConstantString.UID, "");
        Intent intent = getIntent();
        detailId = intent.getStringExtra("send_doc_detailId");
        totag = intent.getStringExtra("send_doc_totag");
        tagName = intent.getStringExtra("send_doc_tag_name");
        tag = intent.getStringExtra("tag");
        if ("5".equals(totag)|| "9".equals(totag)) {
            // totag为 5 时，隐藏审核人选项
            sendDocReceiverLL.setVisibility(View.GONE);
        }

        Log.e("tag", "发文审核---detailId------------>" + detailId);
        Log.e("tag", "发文审核---totag------------>" + totag);
        Log.e("tag", "发文审核---to_name------------>" + tagName);
    }

    private void initData() {
        // 判断审核状态
        sendDocDetailApproveRbY.setChecked(true);
        sendDocDetailApproveRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.send_doc_detail_approve_rb_y) {
                    status = 1;
                    Toast.makeText(context, "同意", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.send_doc_detail_approve_rb_n) {
                    status = 2;
                    Toast.makeText(context, "不同意", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        headerTitle.setText(tagName);
        headerRight.setVisibility(View.GONE);
        if (totag != null) {
            if (totag.equals("9")) {
                sendDocReceiverLL.setVisibility(View.GONE);
            }
        }
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

    private void initEvent() {

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
                    Toast.makeText(context, "内容已保存", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "不能保存，请检查读写内存卡权限", Toast.LENGTH_SHORT).show();
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
        context.sendBroadcast(mediaScanIntent);
    }

    @OnClick(R.id.header_back)
    void close() {
        finish();
    }


    /**
     * 选择核稿/审签等步骤操作人
     */
    @OnClick(R.id.send_doc_detail_approver)
    void choiceApprove() {
        new SendDocDetailApproveDialog(context)
                .readData(ConstantURL.SEND_DOCUMENT_ADD_APPROVE, detailId, "&uid=", userId)
                .builder()
                .setNegativeButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            Log.e("显示：", StringUtils.name + "----" + StringUtils.user_id);
                            user_id = StringUtils.user_id.substring(0, StringUtils.user_id.length() - 1);
                            sendDocDetailApprover.setText(StringUtils.name.substring(0, StringUtils.name.length() - 1));
                        }catch (Exception e){

                        }
//                        sendDocDetailApprover.setText(StringUtils.approve_name);
//                        receiverId.add(StringUtils.approve_name);
                    }
                })
                .show();
    }

    /**
     * 提交审批数据
     * fawen.php?act=shenpido&do=do&id=2&uid=3
     */
    @OnClick(R.id.send_doc_detail_submit)
    void submitApprove() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("tag", tag);
        params.addBodyParameter("totag", totag);
        params.addBodyParameter("status", "" + status);
        params.addBodyParameter(ConstantString.CONTENT, document_process_idea.getText().toString());
        if (isMessage!=0){
            params.addBodyParameter("duanxin", isMessage+"");
        }
        if ("9".equals(totag)) {
            // 签发，不需要传userid
        } else {
            params.addBodyParameter("userid", user_id);
        }
        // 签名板图片
        File file = new File(signet_url);
        KLog.d("文件上传路径------>", file);
        if (file.exists()) {
            KLog.d("tag", "文件存在------>路径" + signet_url);
            params.addBodyParameter("fujian", file);
        } else {
            KLog.d("tag", "文件不存在----->");
        }
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.SEND_DOC_DETAIL_APPROVE + detailId + "&uid=" + userId,
                params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            Log.e("审批返回", responseInfo.result);
                            Gson gson = new Gson();
                            Status status = gson.fromJson(responseInfo.result, Status.class);
                            if (status != null) {
                                if (status.getState().equals("ok")) {
                                    Toast.makeText(context, "处理成功！", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(context, "处理失败！", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
