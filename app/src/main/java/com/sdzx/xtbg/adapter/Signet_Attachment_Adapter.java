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
import com.sdzx.xtbg.bean.SignetAttachment;
import com.sdzx.xtbg.tools.FileUtils;
import com.sdzx.xtbg.tools.ToolUtils;
import com.sdzx.xtbg.tools.Util;
import com.sdzx.xtbg.tools.utils;

import java.util.List;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author：Mark
 * Date：2016/4/27 0027
 * Tell：15006330640
 */
public class Signet_Attachment_Adapter extends BaseAdapter {
    private Context context;
    private List<SignetAttachment> attachments;

    public Signet_Attachment_Adapter(Context context, List<SignetAttachment> attachments) {
        this.context = context;
        this.attachments = attachments;
    }

    @Override
    public int getCount() {
        return attachments == null ? 0 : attachments.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        String file_path1 = "";
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_attachment, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SignetAttachment attachment = attachments.get(position);
        Log.e("地址：", attachment.getPath() + "--" + attachment.getTitle());
        if(attachment.getPath().contains("jpg") || attachment.getPath().contains(".jpeg") || attachment.getPath().contains(".png")){
            viewHolder.mailDetailsFileImg.setImageResource(R.mipmap.file_attach_img);
        }else if(attachment.getPath().contains(".doc") || attachment.getPath().contains(".docx")){
            viewHolder.mailDetailsFileImg.setImageResource(R.mipmap.file_attach_doc);
        }else if(attachment.getPath().contains(".pdf")){
            viewHolder.mailDetailsFileImg.setImageResource(R.mipmap.file_attach_pdf);
        }
        viewHolder.mailDetailsFile.setText(attachment.getTitle());
        viewHolder.mailDetailsFile.setFocusable(true);
        viewHolder.mailDetailsFile.requestFocus();
        if(!attachment.getPath().equals("")){
            String file_name = "";
            String split = ":/";
            StringTokenizer token = new StringTokenizer(attachment.getPath(), split);
            while (token.hasMoreTokens()) {
                file_name = token.nextToken();
            }

            file_path1 = ToolUtils.ATTACHMENT_PATH + "/" + attachment.getTitle();
            if(utils.isNetUseful(context)){
                if(FileUtils.fileIsExists(file_path1)){
                    // 如果存在
                    Log.e("文件","存在");
                }else {
                    // 如果不存在 下载
                    Log.e("文件","不存在");
                    if(!ToolUtils.isDownloading){
                        viewHolder.mailFile.setClickable(false);
//                        FileUtils.checkDownload(context, path, ToolUtils.ATTACHMENT_PATH + file_name);
                        FileUtils.attachmentDownload(attachment.getPath(),file_path1);
                    }else {
                        viewHolder.mailFile.setClickable(true);
                    }
                }
            }
        }
        final String finalFile_path = file_path1;
        viewHolder.mailFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ToolUtils.isDownloading){
                    Toast.makeText(context,"正在下载,请稍等.", Toast.LENGTH_SHORT).show();
                }else {
                        try {
                            Intent intent = Util.getIntent(finalFile_path);
                            context.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(context, "无法打开！", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                    }
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.mail_details_file_img)
        ImageView mailDetailsFileImg;
        @Bind(R.id.mail_details_file)
        TextView mailDetailsFile;
        @Bind(R.id.file_delete)
        ImageView fileDelete;
        @Bind(R.id.mail_file)
        RelativeLayout mailFile;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
