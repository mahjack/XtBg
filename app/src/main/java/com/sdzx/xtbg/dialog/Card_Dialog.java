package com.sdzx.xtbg.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.Tel_Adapter;
import com.sdzx.xtbg.bean.Tel_Object;

import java.util.ArrayList;


/**
 * Author：Mark
 * Date：2016/1/18 0018
 * Tell：15006330640
 */
public class Card_Dialog {
    private Context context;
    private Display display;
    private Dialog dialog;

    private LinearLayout dialog_ll;
    private TextView dialog_name;
    private ListView dialog_list;
    private ImageView dialog_icon;
    private TextView dialog_Im;

    private String username;

    private String chatName = "";

    public Card_Dialog(Context context) {
        this.context = context;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = manager.getDefaultDisplay();
    }

    public Card_Dialog builder() {
        // 获取控件
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_card, null);
        dialog_ll = (LinearLayout) view.findViewById(R.id.dialog_ll);
        dialog_name = (TextView) view.findViewById(R.id.dialog_name);
        dialog_list = (ListView) view.findViewById(R.id.dialog_list);
        dialog_icon = (ImageView) view.findViewById(R.id.dialog_icon);
        // 定义Dialog
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        // 调整dialog背景大小
        dialog_ll.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public Card_Dialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public Card_Dialog setName(String name, String username) {
        if (null != name && !name.equals("")) {
            dialog_name.setText(name);
            chatName = name;
        } else {
            dialog_name.setText("名片");
            chatName = "名片";
        }
        if (null != username && !username.equals("")) {
            this.username = username;
        } else {

        }
        return this;
    }

    public Card_Dialog setList(final ArrayList<Tel_Object> tels) {
        View footerView = LayoutInflater.from(context).inflate(R.layout.item_im, null);
        dialog_list.addFooterView(footerView);
        Tel_Adapter adapter = new Tel_Adapter(context, tels);
        dialog_list.setAdapter(adapter);
        dialog_Im = (TextView) footerView.findViewById(R.id.item_im);
        dialog_Im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("------------",tels.get(0).getTel()+dialog_name.getText().toString()+username);
                dialog.dismiss();
                // demo中直接进入聊天页面，实际一般是进入用户详情页
//                context.startActivity(new Intent(context, ChatActivity.class)
//                        .putExtra("userId", username)
//                )
            }
        });
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
