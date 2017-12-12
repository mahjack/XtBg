package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.activity.document.Document_Activity;
import com.sdzx.xtbg.activity.mail.Mail_Activity;
import com.sdzx.xtbg.activity.register.Register_Activity;
import com.sdzx.xtbg.activity.senddocument.SendDocumentManageActivity;
import com.sdzx.xtbg.bean.Home_Object;
import com.sdzx.xtbg.dialog.AlertDialog;
import com.sdzx.xtbg.tools.DataCleanManager;
import com.sdzx.xtbg.tools.ToolUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Author：Mark
 * Date：2016/3/17 0017
 * Tell：15006330640
 */
public class Home_Adapter extends BaseRecycleAdapter<Home_Object> {

    private static final int ADAPTER_CHECK = 2;

    private Context mContext;

    public Home_Adapter(Context context, ArrayList<Home_Object> list, int layoutResId) {
        super(context, list, layoutResId);
        mContext = context;
    }

    @Override
    protected void bindViews(BaseViewHolder holder, final Home_Object item) {
        holder.setText(R.id.home_text, item.getName())
                .setImageResource(R.id.home_image, item.getResId())
                .setVisible(R.id.home_frame, item.isVisible())
                .setUnread(R.id.home_unread, item.getUnRead()) // 未读数量
                .setOnClickListener(R.id.home_frame, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String event = item.getEvent();

                        // 来文
                        if (event.equals("Document")) {
                            mContext.startActivity(new Intent(mContext, Document_Activity.class));
                        }
                        // 发文管理
                        if (event.equals("sendDocument")) {
                            mContext.startActivity(new Intent(mContext, SendDocumentManageActivity.class));
                        }
                        // 印鉴
//                        if (event.equals("signet")) {
//                            mContext.startActivity(new Intent(mContext, BgypManagerActivity.class));
//                        }
////                      else if(event.equals("Multipoint")){
////                      // 多点分发
////                      mContext.startActivity(new Intent(mContext, Multipoint_Activity.class));
////                      }else if(event.equals("Supervise")){
////                    // 督察督办
////                    mContext.startActivity(new Intent(mContext, Supervise_Activity.class));
////                      }
//
                        else if (event.equals("Mail")) {
                            // 内部邮件
                            mContext.startActivity(new Intent(mContext, Mail_Activity.class));
//                        } else if (event.equals("Meeting")) {
//                            // 会议申请
//                            mContext.startActivity(new Intent(mContext, Meeting_Activity.class));
//                        } else if (event.equals("Attendance")) {
//                            // 外出报备
//                            mContext.startActivity(new Intent(mContext, Attendance_Activity.class));
//                        } else if (event.equals("Gwcc")) {
//                            //公务出差
//                            mContext.startActivity(new Intent(mContext, GwccActivity.class));
//                        }else if (event.equals("Gwjd")) {
//                            //公务接待
////                            Toast.makeText(mContext, "开发中", Toast.LENGTH_SHORT).show();
//                            mContext.startActivity(new Intent(mContext, ReciptionMangerActivity.class));
//                        }else if (event.equals("Gzc")) {
//                            //车辆管理
////                            Toast.makeText(mContext, "开发中", Toast.LENGTH_SHORT).show();
//                            mContext.startActivity(new Intent(mContext, GZCActivity.class));
                        }else if (event.equals("kaoqin")) {
                            //考勤管理
//                            Toast.makeText(mContext, "开发中", Toast.LENGTH_SHORT).show();
                            mContext.startActivity(new Intent(mContext, Register_Activity.class));
//                        }else if (event.equals("Notices")) {
//                            // 综合
//                            mContext.startActivity(new Intent(mContext, Notices_Activity.class));
//                        } else if (event.equals("Update")) {
//                            // 更新
//                            if (utils.isWifiConnected(mContext)) {
//                                Fragment_Home.checkUpdate(ADAPTER_CHECK);
//                            }
//                        } else if (event.equals("Office")) {
//                            mContext.startActivity(new Intent(mContext, Office_Activity.class));
                        } else if (event.equals("About")) {
                            // 关于 -- 清除缓存
//                    mContext.startActivity(new Intent(mContext, ZfManager.class));
                            try {
                                String size = "缓存大小（" +
                                        DataCleanManager.getFormatSize(DataCleanManager.getFolderSize(new File(ToolUtils.BASE_PATH))) + ")";
                                new AlertDialog(mContext).builder()
                                        .setMsg(size)
                                        .setTitle("您确定要清除缓存吗？")
                                        .setPositiveButton("确定", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                DataCleanManager.deleteFolderFile(ToolUtils.ATTACHMENT_PATH, true);
                                            }
                                        })
                                        .setNegativeButton("取消", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        })
                                        .show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
//                        // 公务接待
//                        else if (event.equals("Service")) {
////                            mContext.startActivity(new Intent(mContext, Public_Affair_Activity.class));
//                            Toast.makeText(mContext, "正在开发中", Toast.LENGTH_SHORT).show();
//                        }
                    }
                });
    }
}
