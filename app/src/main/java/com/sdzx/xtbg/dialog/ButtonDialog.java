package com.sdzx.xtbg.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.sdzx.xtbg.R;


/**
 * Created by Administrator on 2015/5/31.
 */
public class ButtonDialog extends Dialog {
    private Button dialog_button1,dialog_button2;
    private Display display;
    private Dialog dialog;
    private Context context;
    private String button1_text,button2_text;
    private ClickListenerInterface clickListenerInterface;

    public ButtonDialog(Context context, String button1, String button2) {
        super(context, R.style.AlertsheetStyle);
        this.context = context;
        this.button1_text = button1;
        this.button2_text = button2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_button,null);

        dialog_button1 = (Button) view.findViewById(R.id.dialog_button1);
        dialog_button2 = (Button) view.findViewById(R.id.dialog_button2);

        dialog_button1.setText(button1_text);
        dialog_button2.setText(button2_text);
        dialog_button1.setOnClickListener(new clickListener());
        dialog_button2.setOnClickListener(new clickListener());

        // 设置 布局样式
//        dialog = new Dialog(context,R.style.AlertsheetStyle);
        setContentView(view);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

    }
    public interface ClickListenerInterface{
        public void doButton1();
        public void daButton2();
    }

    public void setClickListener(ClickListenerInterface clickListenerInterface){
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dialog_button1:
                    clickListenerInterface.doButton1();
                    break;
                case R.id.dialog_button2:
                    clickListenerInterface.daButton2();
                    break;
            }
        }
    }
}
