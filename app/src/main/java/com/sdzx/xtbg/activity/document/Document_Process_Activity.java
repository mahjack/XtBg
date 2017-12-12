package com.sdzx.xtbg.activity.document;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.dialog.PersonDialog;
import com.sdzx.xtbg.tools.StringUtils;
import com.socks.library.KLog;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static com.sdzx.xtbg.tools.StringUtils.user_id;


/**
 * Author：Mark
 * Date：2015/12/11 0011
 * Tell：15006330640
 * <p/>
 * 办理页面
 */
public class Document_Process_Activity extends Activity implements View.OnClickListener {
    private static final String TAG = "Document_Process_Activity";
    // 顶栏
    @ViewInject(R.id.header_back)
    TextView header_back;
    @ViewInject(R.id.header_title)
    TextView header_title;
    @ViewInject(R.id.header_right)
    ImageView header_right;

    @ViewInject(R.id.signature_pad)
    SignaturePad SignaturePad;// 签名版
    @ViewInject(R.id.clear_button)
    Button clear_button;// 清除图片
    @ViewInject(R.id.save_button)
    Button save_button;// 保存图片

    // 同意
    @ViewInject(R.id.process_agree_ll)
    LinearLayout process_agree_ll;
    @ViewInject(R.id.process_agree)
    RadioButton process_agree; // 同意
    @ViewInject(R.id.process_read)
    RadioButton process_read; // 已阅
    @ViewInject(R.id.process_idea_text)
    TextView process_idea_text;
    @ViewInject(R.id.process_agree_idea)
    RadioButton process_agree_idea; // 同意拟办意见
    @ViewInject(R.id.document_process_idea)
    EditText document_process_idea; // 意见
    // 领导
    @ViewInject(R.id.process_leader_ll)
    LinearLayout process_leader_ll;
    @ViewInject(R.id.process_text1)
    TextView process_text1;
    @ViewInject(R.id.process_leader_text)
    TextView process_leader_text;
    // 办公室
    @ViewInject(R.id.process_office_ll)
    LinearLayout process_office_ll;
    @ViewInject(R.id.process_text2)
    TextView process_text2;
    @ViewInject(R.id.process_office_text)
    TextView process_office_text;

    // 文化科与新闻出版行政审批
    @ViewInject(R.id.process_department_ll)
    LinearLayout process_department_ll;
    @ViewInject(R.id.process_text3)
    TextView process_text3;
    @ViewInject(R.id.process_department_text)
    TextView process_department_text;

    // 所有部门人员
    @ViewInject(R.id.process_all_ll)
    LinearLayout process_all_ll;
    @ViewInject(R.id.process_text4)
    TextView process_text4;// 操作步骤名
    @ViewInject(R.id.process_all_text)
    TextView process_all_text;

    // 短信通知checkdx
    @ViewInject(R.id.process_message_ll)
    LinearLayout process_message_ll;
    @ViewInject(R.id.process_message)
    CheckBox process_message;
    @ViewInject(R.id.checkdx)
    CheckBox checkdx;
    // 短信内容
    @ViewInject(R.id.document_process_message)
    EditText document_process_message;
    // 按钮
    @ViewInject(R.id.document_btn)
    Button document_btn;
    // 对象
    private Context context;
    private int tag = 0, isMessage = 0;
    private String totag, id, title, hand_id, user_idd = "", user_idk = "", user_idu = "", act, tagName, userId;
    private SharedPreferences preferences;

    private RequestCall mCall;

    private String niban_suggestion = "";// 拟办意见

    private String signet_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.document_process);
        ViewUtils.inject(this);
        initConstants();
        readData();
        initViews();
        initEvents();
    }

    private void initConstants() {
        preferences = getSharedPreferences("info", Activity.MODE_PRIVATE);
        userId = preferences.getString(ConstantString.UID, "");

        context = Document_Process_Activity.this;
        Intent intent = super.getIntent();
        tag = intent.getIntExtra(ConstantString.STATUS, 0);
        totag = intent.getStringExtra(ConstantString.TOTAG);
        id = intent.getStringExtra(ConstantString.ID);
        title = intent.getStringExtra(ConstantString.TITLE);
        hand_id = intent.getStringExtra(ConstantString.USER_ID);
        act = intent.getStringExtra("act");
        userId = preferences.getString(ConstantString.UID, "");

        niban_suggestion = intent.getStringExtra(ConstantString.NIBAN_SUGGESTION);

        KLog.e("拟办意见------>", niban_suggestion);

        user_id = "";
        tagName = intent.getStringExtra("SOP");


    }

    private void initViews() {
        header_right.setVisibility(View.GONE);
        document_process_message.setText("您有一件【" + title + "】公文等待办理！");
        switch (tag) {
            case 0:
                // 拟办
                header_title.setText(getString(R.string.document_btn0));
                document_btn.setText(getString(R.string.document_btn0));
                // 同意
                process_agree_ll.setVisibility(View.GONE);
                process_idea_text.setText(getString(R.string.document_opinion));
                document_process_idea.setText(niban_suggestion);// tag 为 2时，自动填充拟办意见
                // 领导
                process_leader_ll.setVisibility(View.VISIBLE);
                process_text1.setText("领导");
                // 办公室
                process_office_ll.setVisibility(View.GONE);
                // 文化科
                process_department_ll.setVisibility(View.GONE);
                // 全部
                process_all_ll.setVisibility(View.GONE);

                break;
            case 1:
                // 分发
                header_title.setText(getString(R.string.document_btn1));
                document_btn.setText(getString(R.string.document_btn1));
                // 同意
                process_agree_ll.setVisibility(View.GONE);
                process_idea_text.setText(getString(R.string.document_opinion4));
                // 领导
                process_leader_ll.setVisibility(View.VISIBLE);
                process_text1.setText(getString(R.string.document_leader));
                // 办公室
                process_office_ll.setVisibility(View.VISIBLE);
                process_text2.setText(getString(R.string.document_office_text));
                // 科室
                process_department_ll.setVisibility(View.VISIBLE);
                process_text3.setText(getString(R.string.document_department));
                break;
            case 2:
                // 批示
                header_title.setText(getString(R.string.document_btn2));
                document_btn.setText(getString(R.string.document_btn2));
                // 同意
                process_agree_ll.setVisibility(View.VISIBLE);
                process_idea_text.setText(getString(R.string.document_opinion3));
                // 领导
                process_text1.setText("领导");
                process_leader_ll.setVisibility(View.GONE);
                // 办公室
                process_office_ll.setVisibility(View.GONE);
                // 科室
                process_department_ll.setVisibility(View.GONE);
                // 所有科室
                process_text4.setText("所有科室");
                process_all_ll.setVisibility(View.GONE);
                break;
            case 3:
                // 阅示
                process_text4.setText("选择办理人");// 显示所有人
                process_all_ll.setVisibility(View.GONE);
                header_title.setText(getString(R.string.document_btn3));
                document_btn.setText(getString(R.string.document_btn3));
                // 同意
                process_agree_ll.setVisibility(View.GONE);
                process_idea_text.setText(getString(R.string.document_opinion5));
                // 领导
                process_leader_ll.setVisibility(View.GONE);
                process_text1.setText(getString(R.string.document_recipient));
                // 办公室
                process_office_ll.setVisibility(View.GONE);
                // 科室
                process_department_ll.setVisibility(View.GONE);
                // 短信
                process_message_ll.setVisibility(View.GONE);
                break;
            case 4:
                // 办理，tag=4,不选择办理人，科室隐藏
                // 718重改，领导分发给科室负责人，tag=4，科室负责人需要下发给部门所有人，取消隐藏，科员需要办理
                process_all_ll.setVisibility(View.GONE);
                header_title.setText(getString(R.string.document_btn4));
                document_btn.setText(getString(R.string.document_btn4));
                // 同意
                process_agree_ll.setVisibility(View.GONE);
                process_idea_text.setText(getString(R.string.document_opinion7));
                // 领导
                process_leader_ll.setVisibility(View.GONE);
                process_text1.setText(getString(R.string.document_recipient));
                // 办公室
                process_office_ll.setVisibility(View.GONE);
                // 科室
                process_department_ll.setVisibility(View.GONE);
                process_text3.setText(getString(R.string.document_man));
                // 全部
                process_all_ll.setVisibility(View.VISIBLE);
                process_text4.setText("选择分办人");
                break;
            case 5:
                // 分办
                process_all_ll.setVisibility(View.GONE);
                header_title.setText(getString(R.string.document_btn5));
                document_btn.setText(getString(R.string.document_btn5));
                // 同意
                process_agree_ll.setVisibility(View.GONE);
                process_idea_text.setText(getString(R.string.document_opinion6));
                // 领导
                process_leader_ll.setVisibility(View.GONE);
                process_text1.setText(getString(R.string.document_recipient));
                // 办公室
                process_office_ll.setVisibility(View.GONE);
                // 科室
                process_department_ll.setVisibility(View.GONE);
                process_text3.setText(getString(R.string.document_man));
                // 短信
                process_message_ll.setVisibility(View.GONE);
                break;
            case 8:
                // 办结
                process_all_ll.setVisibility(View.GONE);
                process_message_ll.setVisibility(View.GONE);
                header_title.setText(getString(R.string.document_btn8));
                document_btn.setText(getString(R.string.document_btn8));
                // 同意
                process_agree_ll.setVisibility(View.GONE);
                process_idea_text.setText(getString(R.string.document_opinion8));
                // 领导
                process_leader_ll.setVisibility(View.GONE);
                process_text1.setText(getString(R.string.document_recipient));
                // 办公室
                process_office_ll.setVisibility(View.GONE);
                // 科室
                process_department_ll.setVisibility(View.GONE);
                process_text3.setText(getString(R.string.document_man));
                break;
            default:
                break;
        }
    }

    private void initEvents() {

        header_back.setOnClickListener(this);
        document_btn.setOnClickListener(this);
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
        process_leader_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == 0) {
//                    Log.e("获取数据",id);
                    // 拟办
                    new PersonDialog(context)
                            .readData("groupld", preferences.getString(ConstantString.UID, ""), id)
                            .builder()
                            .setTitle("领导")
                            .setSingle(true)
                            .setCancelable(false)
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.e("选中", StringUtils.name);
                                    process_leader_text.setText(StringUtils.name);
                                }
                            })
                            .show();
                } else {
                    new PersonDialog(context)
                            .readData("groupys", preferences.getString(ConstantString.UID, ""), id)
                            .builder()
                            .setTitle("领导")
                            .setCancelable(false)
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.e("选中", StringUtils.name);
                                    if (!StringUtils.name.equals("")) {
                                        user_idd = user_id;
                                        process_leader_text.setText(StringUtils.name.substring(0, StringUtils.name.length() - 1));
                                    }
                                }
                            })
                            .show();
                }
            }
        });
        process_office_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PersonDialog(context)
                        .readData("groupbl", preferences.getString(ConstantString.UID, ""), id)
                        .builder()
                        .setTitle("科室负责人")
                        .setCancelable(false)
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("选中", StringUtils.name + "--" + user_id);
                                if (!StringUtils.name.equals("")) {
                                    user_idk = user_id;
                                    process_office_text.setText(StringUtils.name.substring(0, StringUtils.name.length() - 1));
                                }
                            }
                        })
                        .show();
            }
        });
        process_department_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == 4) {
                    new PersonDialog(context)
                            .readData("fenban", preferences.getString(ConstantString.UID, ""), id)
                            .builder()
                            .setTitle("分办人")
                            .setCancelable(false)
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.e("显示：", StringUtils.name + "----" + user_id);
                                    if (!StringUtils.name.equals("")) {
                                        process_department_text.setText(StringUtils.name.substring(0, StringUtils.name.length() - 1));
                                    }
                                }
                            })
                            .show();
                } else {
                    new PersonDialog(context)
                            .readData("groupfb", preferences.getString(ConstantString.UID, ""), id)
                            .builder()
                            .setTitle("分办人")
                            .setCancelable(false)
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.e("显示：", StringUtils.name + "----" + user_id);
                                    user_idu = user_id;
                                    process_department_text.setText(StringUtils.name.substring(0, StringUtils.name.length() - 1));
                                }
                            })
                            .show();
                }
            }
        });

        // 所有部门人员
        process_all_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == 4) {
                    new PersonDialog(context)
                            .readData("fenban", preferences.getString(ConstantString.UID, ""), id)
                            .builder()
                            .setTitle("分办人")
                            .setCancelable(false)
                            .setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    KLog.d("分办人------>", StringUtils.name + "----" + user_id);
                                    // 接收人ID
                                    user_idk = user_id;
                                    // 显示接收人
                                    process_all_text.setText(StringUtils.name.substring(0, StringUtils.name.length() - 1));
                                }
                            })
                            .show();
                } else {
                    if (tag == 2 || tag == 3) {
                        new PersonDialog(context)
                                .readData("groupfb", preferences.getString(ConstantString.UID, ""), id)
                                .builder()
                                .setTitle("分办人")
                                .setCancelable(false)
                                .setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            KLog.d("公文接收人------>", StringUtils.name + "----" + user_id);
                                            // 接收人ID
                                            user_idk = user_id;
                                            // 显示接收人
                                            process_all_text.setText(StringUtils.name.substring(0, StringUtils.name.length() - 1));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                                .show();
                    }
                }

            }
        });

        // 签名版
        SignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
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
                    Toast.makeText(context, "不能保存内容，请检查读写内存卡权限", Toast.LENGTH_SHORT).show();
                }
//                if (addSvgSignatureToGallery(SignaturePad.getSignatureSvg())) {
//                    Toast.makeText(context, "矢量图签名已保存", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, "不能保存矢量图签名", Toast.LENGTH_SHORT).show();
//                }
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


    /**
     * 保存矢量图签名
     *
     * @param signatureSvg
     * @return
     */
    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {
            File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
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


//    /**
//     * 获取分管领导数据
//     */
//    private void getLeaderData() {
//        mCall = OkHttpUtils
//                .get()
//                .url(ConstantURL.DOCUMENT_GET_LEADER + id + "&uid=" + userId)
//                .build();
//        mCall.execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e) {
//                Toast.makeText(context, "网络错误！", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(String response) {
//                try {
//                    Log.e("tag", "分管领导数据--------------------->" + response);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    private void readData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.document_btn:
                if (checkConduct()) {
                    conduct();
                }
                break;
        }
    }

    /**
     * @return 检测办理
     */
    private boolean checkConduct() {
        if (tag == 2 || tag == 3 || tag == 5 || tag == 8 || tag == 4) {
            return true;
        } else if (tag == 1) {
            if (user_idd.equals("") && user_idk.equals("") && user_idu.equals("")) {
                Toast.makeText(context, "选择办理人！", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } else {
            if (user_id.equals("") && !StringUtils.isNo) {
                Toast.makeText(context, "选择办理人！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    /**
     * 提交数据
     */
    private void conduct() {

        HttpUtils httpUtils = new HttpUtils();

        RequestParams params = new RequestParams();
        params.addBodyParameter(ConstantString.TAG, String.valueOf(tag));
        params.addBodyParameter(ConstantString.TOTAG, totag);
//        params.addBodyParameter("userid", totag);
        params.addBodyParameter(ConstantString.CONTENT, document_process_idea.getText().toString());
        Log.e("状态Tag", "" + tag);

        if (tag == 0) {
            Log.e("选中人：", user_id);
            if (!user_id.equals("")) {
                params.addBodyParameter(ConstantString.USER_ID, user_id);
            }
        } else if (tag == 1) {
            // 分发
            if (!user_idd.equals("")) {
                String user_id = user_idd.substring(0, user_idd.length() - 1);
                params.addBodyParameter(ConstantString.USER_ID, user_id);
            }
            if (!user_idk.equals("")) {
                String user_idkk = user_idk.substring(0, user_idk.length() - 1);
                params.addBodyParameter(ConstantString.USER_IDK, user_idkk);
            }
            if (!user_idu.equals("")) {
                String user_iduu = user_idu.substring(0, user_idu.length() - 1);
                params.addBodyParameter(ConstantString.USER_IDU, user_iduu);
            }
        } else if (tag == 2) {
            // 批示
            params.addBodyParameter(ConstantString.USER_ID, hand_id);
            String status = "";
            // 领导ID
//            if (!user_idd.equals("")) {
//                String user_id = user_idd.substring(0, user_idd.length() - 1);
//                KLog.d("上传领导id--------------->", user_id);
//                params.addBodyParameter(ConstantString.USER_ID, user_id);
//            }
            // 其他所有人员ID
//            if (!user_idk.equals("")) {
//                String user_idkk = user_idk.substring(0, user_idk.length() - 1);
//                Log.e("tag", "其他部门人员---------->" + user_idkk);
//                params.addBodyParameter(ConstantString.USER_IDK, user_idkk);
//            }
            if (process_agree.isChecked()) {
                status = "同意";
            } else if (process_read.isChecked()) {
                status = "已阅";
            } else {
                status = "同意拟办意见";
            }
            Log.e("状态", status);
            params.addBodyParameter(ConstantString.STATE, status);
        } else if (tag == 3) {
            // 阅示
                params.addBodyParameter(ConstantString.USER_ID, hand_id);
            // 其他所有人员ID
//            if (!user_idk.equals("")) {
//                String user_idkk = user_idk.substring(0, user_idk.length() - 1);
//                Log.e("tag", "其他部门人员---------->" + user_idkk);
//                params.addBodyParameter(ConstantString.USER_ID, user_idkk);
//            }
        } else if(tag==4){
            if (!user_idk.equals("")) {
                String user_iduu = user_idk.substring(0, user_idk.length() - 1);
                params.addBodyParameter(ConstantString.USER_ID, user_iduu);
            }
        }
        else {
            params.addBodyParameter(ConstantString.USER_ID, hand_id);
//            if (!StringUtils.user_id.equals("")) {
//                String user_id = StringUtils.user_id.substring(0, StringUtils.user_id.length() - 1);
//                params.addBodyParameter(ConstantString.USER_ID, user_id);
//            }
        }
        if (isMessage!=0){
            params.addBodyParameter("duanxin", String.valueOf(isMessage));
        }
//        Log.e("短信：", process_message.isChecked() + "");

        // 签名板图片
//        File file = new File(signet_url);
//        KLog.d("文件上传路径------>", file);
//        if (file.exists()) {
//            KLog.d("tag", "文件存在------>路径" + signet_url);
//            params.addBodyParameter("fujian", file);
//        } else {
//            KLog.d("tag", "文件不存在----->");
//        }
//        KLog.d("审批URL", ConstantURL.getDocURL(act, userId, id));
//        httpUtils.send(HttpMethod.POST, ConstantURL.getDocURL(act, userId, id), params, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                try {
//                    Log.e("审批返回", responseInfo.result);
//                    Gson gson = new Gson();
//                    Status status = gson.fromJson(responseInfo.result, Status.class);
//                    if (status != null) {
//                        if (status.getState().equals("ok")) {
//                            Toast.makeText(context, "处理成功！", Toast.LENGTH_SHORT).show();
//                            finish();
//                        } else {
//                            Toast.makeText(context, "处理失败！", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(HttpException error, String msg) {
//
//            }
//        });
    }
}
