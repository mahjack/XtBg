package com.sdzx.xtbg.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import com.sdzx.xtbg.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 申请办公用品 Dialog
 * Author:Eron
 * Date: 2016/8/8 0008
 * Time: 17:51
 */
public class AddOfficeApplianceDialog extends Dialog {

    @Bind(R.id.add_office_name)
    EditText addOfficeName;
    @Bind(R.id.add_office_standard)
    EditText addOfficeStandard;
    @Bind(R.id.add_office_num)
    EditText addOfficeNum;

    private static int mTheme = R.style.AddOfficeApplianceDialog;
    private Context context;

    public AddOfficeApplianceDialog(Context context) {
        super(context, mTheme);
        this.context = context;
    }

    public AddOfficeApplianceDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_office_appliance);
        ButterKnife.bind(this);

        initEvent();
    }

    private void initEvent() {

    }



}
