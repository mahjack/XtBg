package com.sdzx.xtbg.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdzx.xtbg.R;
import com.sdzx.xtbg.constant.ConstantURL;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 分组员工 DialogFragment
 * Author:Eron
 * Date: 2016/8/12 0012
 * Time: 14:31
 */
public class DepartStaffDialogFragment extends DialogFragment {


    @Bind(R.id.depart_staff_select_all)
    TextView departStaffSelectAll;
    @Bind(R.id.depart_staff_header)
    RelativeLayout departStaffHeader;
    @Bind(R.id.depart_staff_expand_list)
    ExpandableListView departStaffExpandList;

    private RequestCall mCall;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogfragment_depart_staff, null);
        ButterKnife.bind(this, view);

        initData();

        builder.setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "确定", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", null);
        return builder.create();
    }

    private void initData() {
        getContact();
    }

    /**
     * 获取联系人数据
     */
    private void getContact() {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.ADDRESS_URL)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(getActivity(), "请求服务器失败！请稍后重试...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json("联系人数据", response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
