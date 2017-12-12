package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.activity.mail.Mail_Forward_Activity;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.tools.BaseViewHolder;
import com.sdzx.xtbg.tools.FileUtils;
import com.sdzx.xtbg.tools.ToolUtils;
import com.sdzx.xtbg.tools.Util;
import com.sdzx.xtbg.tools.utils;
import com.socks.library.KLog;

import java.util.List;

/**
 * Author：Mark
 * Date：2016/1/5 0005
 * Tell：15006330640
 */
public class File_Adapter extends BaseAdapter {
    private Context context;
    private List<String> file_paths;
    private List<String> files;
    private List<String> ids;
    private int status;
    private String file_path1;
    private boolean isChoose;

    public File_Adapter(Context context, List<String> file_paths, List<String> files, List<String> ids, boolean isChoose, int status) {
        this.context = context;
        this.file_paths = file_paths;
        this.files = files;
        this.ids = ids;
        this.isChoose = isChoose;
        this.status = status;
    }

    @Override
    public int getCount() {
        return null == files ? 0 : files.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_file, null);
            viewHolder = new ViewHolder();
            viewHolder.mail_file = BaseViewHolder.getViewHolder(convertView, R.id.mail_file);
            viewHolder.mail_details_file_img = BaseViewHolder.getViewHolder(convertView, R.id.mail_details_file_img);
            viewHolder.mail_details_file = BaseViewHolder.getViewHolder(convertView, R.id.mail_details_file);
            viewHolder.file_delete = BaseViewHolder.getViewHolder(convertView, R.id.file_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            final String file_path = file_paths.get(position);
            final String file_name = files.get(position);
//        final String file_id = ids.get(position);
            if (file_name.contains(".doc")) {
                viewHolder.mail_details_file_img.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_file_doc));
            } else if (file_name.contains(".zip")) {
                viewHolder.mail_details_file_img.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_file_zip));
            } else if (file_name.contains(".rar")) {
                viewHolder.mail_details_file_img.setImageDrawable(context.getResources().getDrawable(R.mipmap.file_icon_rar));
            } else if (file_name.contains(".jpg")) {
                viewHolder.mail_details_file_img.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_file_img));
            } else if (file_name.contains(".jpeg")) {
                viewHolder.mail_details_file_img.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_file_img));
            } else if (file_name.contains(".txt")) {
                viewHolder.mail_details_file_img.setImageDrawable(convertView.getResources().getDrawable(R.mipmap.ic_file_txt));
            } else if (file_name.contains(".pdf")) {
                viewHolder.mail_details_file_img.setImageDrawable(convertView.getResources().getDrawable(R.mipmap.ic_file_pdf));
            } else {
                viewHolder.mail_details_file_img.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_file_other));
            }
            viewHolder.mail_details_file.setText(file_name);
            if (!isChoose) {
                if (!file_path.equals("")) {
                    KLog.d("附件地址-------->", file_path);
                    String str = file_path;
                    try {
//                    String file_name1 = (str.substring(str.indexOf("/UpLoadFile/upfile/"), str.length()-1).replace("/UpLoadFile/upfile/", ""));
//                    String appendix_path = ConstantURL.BASE_PATH + file_path;
                        Log.e("地址：", file_path + "--" + file_name);
                        file_path1 = ToolUtils.ATTACHMENT_PATH + "/" + file_name;
                        ToolUtils.filePath.add(file_path1);
                        if (utils.isNetUseful(context)) {
                            if (FileUtils.fileIsExists(file_path1)) {
                                // 如果存在
                                Log.e("文件", "存在");
                            } else {
                                // 如果不存在 下载
                                if (!ToolUtils.isDownloading) {
                                    viewHolder.mail_file.setClickable(false);
                                    FileUtils.attachmentDownload(ConstantURL.BASE_PATH+file_path, file_path1);
                                } else {
                                    viewHolder.mail_file.setClickable(true);
                                }
                            }
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                }
            } else {
                viewHolder.file_delete.setVisibility(View.VISIBLE);
            }
            viewHolder.mail_details_file.setFocusable(true);
            viewHolder.mail_details_file.requestFocus();
            viewHolder.file_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    file_paths.remove(file_path);
                    files.remove(file_name);
                    Mail_Forward_Activity.name_list.remove(file_name);
                    Mail_Forward_Activity.path_list.remove(file_path);
                    if (status != 3) {
                        Mail_Forward_Activity.file_id.remove(position);
                    }
                    File_Adapter.this.notifyDataSetChanged();
                }
            });
            viewHolder.mail_file.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ToolUtils.isDownloading) {
                        Toast.makeText(context, "正在下载,请稍等.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (isChoose) {
                            try {
                                Intent intent = Util.getIntent(file_paths.get(position));
                                context.startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(context, "无法打开！", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                String filePath = ToolUtils.ATTACHMENT_PATH + "/" + file_name;
                                Intent intent = Util.getIntent(filePath);
                                context.startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(context, "无法打开！", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    class ViewHolder {
        RelativeLayout mail_file;
        ImageView mail_details_file_img;
        TextView mail_details_file;
        ImageView file_delete;
    }
}
