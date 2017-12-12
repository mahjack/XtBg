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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdzx.xtbg.R;


public class InputAlertDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout alert_layout;
    private TextView alert_title;
    private EditText alert_edit;
    private Button alert_cancel;
    private Button alert_ensure;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public InputAlertDialog(Context context){
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }
    public InputAlertDialog builder(){
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_alert_input,null);
        // 获取控件
        alert_layout = (LinearLayout) view.findViewById(R.id.alert_layout);
        // 标题
        alert_title = (TextView) view.findViewById(R.id.alert_title);
        alert_title.setVisibility(View.GONE);
        // 输入框
        alert_edit = (EditText) view.findViewById(R.id.alert_edit);
        //alert_edit.setVisibility(View.GONE);
        // 取消
        alert_cancel = (Button) view.findViewById(R.id.alert_cancel);
        alert_cancel.setVisibility(View.GONE);
        // 确定
        alert_ensure = (Button) view.findViewById(R.id.alert_ensure);
        alert_ensure.setVisibility(View.GONE);
        // 定义Dialog 布局 和参数
        dialog = new Dialog(context,R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整Dialog 背景大小
        alert_layout.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }
    public InputAlertDialog setTitle(String title){
        showTitle = true;
        if ("".equals(title)) {
            alert_title.setText("标题");
        } else {
            alert_title.setText(title);
        }
        return this;
    }

    public InputAlertDialog setMsg(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            alert_edit.setHint("内容");
        } else {
            alert_edit.setHint(msg);
        }
        return this;
    }

    public InputAlertDialog setText(String text) {
        showMsg = true;
        if ("".equals(text)) {
            alert_edit.setHint("输入内容");
        } else {
            alert_edit.setText(text);
        }
        return this;
    }

    public InputAlertDialog setCancelable(boolean cancel){
        dialog.setCancelable(cancel);
        return this;
    }
    public InputAlertDialog setPositiveButton(String text, final View.OnClickListener listener){
        showPosBtn = true;
        if("".equals(text)){
            alert_ensure.setText("确定");
        }else {
            alert_ensure.setText(text);
        }
        alert_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }
    public InputAlertDialog setNegativeButton(String text, final View.OnClickListener listener){
        showNegBtn = true;
        if ("".equals(text)) {
            alert_cancel.setText("取消");
        } else {
            alert_cancel.setText(text);
        }
        alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return this;
    }
    private void setLayout() {
        if (!showTitle && !showMsg) {
            alert_title.setText("提示");
            alert_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            alert_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            alert_edit.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            alert_ensure.setText("确定");
            alert_ensure.setVisibility(View.VISIBLE);
            alert_ensure.setBackgroundResource(R.drawable.alertdialog_single_selector);
            alert_ensure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            alert_ensure.setVisibility(View.VISIBLE);
            alert_ensure.setBackgroundResource(R.drawable.alertdialog_right_selector);
            alert_cancel.setVisibility(View.VISIBLE);
            alert_cancel.setBackgroundResource(R.drawable.alertdialog_left_selector);
        }

        if (showPosBtn && !showNegBtn) {
            alert_ensure.setVisibility(View.VISIBLE);
            alert_ensure.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            alert_cancel.setVisibility(View.VISIBLE);
            alert_cancel.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }
    public void dismiss(){
        dialog.dismiss();
    }
}
