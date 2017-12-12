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
public class Attachment_Adapter extends BaseAdapter {
    private Context context;
    private List<String> paths;

    public Attachment_Adapter(Context context, List<String> paths) {
        this.context = context;
        this.paths = paths;
    }

    @Override
    public int getCount() {
        return paths == null ? 0 : paths.size();
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
        String path = paths.get(position);
        if(path.contains("jpg") || path.contains(".jpeg") || path.contains(".png")){
            viewHolder.mailDetailsFileImg.setImageResource(R.mipmap.file_attach_img);
        }else if(path.contains(".doc") || path.contains(".docx")){
            viewHolder.mailDetailsFileImg.setImageResource(R.mipmap.file_attach_doc);
        }else if(path.contains(".pdf")){
            viewHolder.mailDetailsFileImg.setImageResource(R.mipmap.file_attach_pdf);
        }
        viewHolder.mailDetailsFile.setText("附件" + (position+1));
        viewHolder.mailDetailsFile.setFocusable(true);
        viewHolder.mailDetailsFile.requestFocus();
        if(!path.equals("")){
            String file_name = "";
            String split = ":/";
            StringTokenizer token = new StringTokenizer(path, split);
            while (token.hasMoreTokens()) {
                file_name = token.nextToken();
            }
            Log.e("地址：", path + "--" + file_name);
            file_path1 = ToolUtils.ATTACHMENT_PATH + "/" + file_name;
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
                        FileUtils.attachmentDownload(path,file_path1);
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
