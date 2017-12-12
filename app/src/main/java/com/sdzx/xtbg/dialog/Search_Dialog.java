package com.sdzx.xtbg.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sdzx.xtbg.R;


/**
 * Author：Mark
 * Date：2016/1/16 0016
 * Tell：15006330640
 */
public class Search_Dialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private LinearLayout lLayout_bg;
    private ImageView search_voice;
    private EditText search_et;
    private Button btn_neg,btn_pos;


    public Search_Dialog(Context context){
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public Search_Dialog builder(){
        // 获取 Dialog 布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_search,null);
        // 获取控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        search_voice = (ImageView) view.findViewById(R.id.search_voice); //语音
        search_et = (EditText) view.findViewById(R.id.search_et);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth()), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }
    public Search_Dialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public Search_Dialog setPositiveButton(String text,
                                         final View.OnClickListener listener) {
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public Search_Dialog setNegativeButton(String text,
                                         final View.OnClickListener listener) {
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }
    public void show() {
//        setLayout();
        dialog.show();
    }
}
