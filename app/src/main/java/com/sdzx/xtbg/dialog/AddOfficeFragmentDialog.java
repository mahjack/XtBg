package com.sdzx.xtbg.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.sdzx.xtbg.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 办公用品申请添加
 * Author:Eron
 * Date: 2016/8/9 0009
 * Time: 14:11
 */
public class AddOfficeFragmentDialog extends DialogFragment {
    @Bind(R.id.add_office_name)
    EditText addOfficeName;
    @Bind(R.id.add_office_standard)
    EditText addOfficeStandard;
    @Bind(R.id.add_office_num)
    EditText addOfficeNum;


    public interface AddOfficeListener {
        void onInputComplete(String name, String standard, String num);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_office_appliance, null);
        ButterKnife.bind(this, view);

        builder.setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddOfficeListener listener = (AddOfficeListener) getActivity();
                        listener.onInputComplete(addOfficeName.getText().toString(),
                                addOfficeStandard.getText().toString(),
                                addOfficeNum.getText().toString());
                    }
                })
                .setNegativeButton("取消", null);
        return builder.create();
    }
}
