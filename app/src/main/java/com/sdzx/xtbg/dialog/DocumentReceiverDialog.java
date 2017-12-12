package com.sdzx.xtbg.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.sdzx.xtbg.R;


/**
 * Created by 林炜智 on 2016/5/5.
 * 公文其他科室人员 Dialog
 */
public class DocumentReceiverDialog extends Dialog {
    public final static String Tag = "AddresseeDialog";
    public DocumentReceiverDialog(Context context) {
        super(context);
    }

    public DocumentReceiverDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private String title, buttonTextOK, buttonTextNO;
        private DialogInterface.OnClickListener listenerOK,listenerNO;
        private BaseExpandableListAdapter adapter;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setAdapter(BaseExpandableListAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public Builder setListenerOK(String buttonTextOK, OnClickListener listenerOK) {
            this.buttonTextOK = buttonTextOK;
            this.listenerOK = listenerOK;
            return this;
        }

        public Builder setListenerNO(String buttonTextNO, OnClickListener listenerNO) {
            this.buttonTextNO = buttonTextNO;
            this.listenerNO = listenerNO;
            return this;
        }

        public DocumentReceiverDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final DocumentReceiverDialog dialog = new DocumentReceiverDialog(context, R.style.AddresseeDialog);
            View view = inflater.inflate(R.layout.dialog_addresseedialog, null);
            dialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            ((TextView) view.findViewById(R.id.dialog_addresseedialog_Title)).setText(title);
            if (buttonTextOK != null)
                ((Button) view.findViewById(R.id.dialog_addresseedialog_OK)).setText(buttonTextOK);
            if (listenerOK != null)
                ((Button) view.findViewById(R.id.dialog_addresseedialog_OK)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listenerOK.onClick(dialog, BUTTON_POSITIVE);
                    }
                });
            if (buttonTextNO != null)
                ((Button) view.findViewById(R.id.dialog_addresseedialog_NO)).setText(buttonTextNO);
            if (listenerNO != null)
                ((Button) view.findViewById(R.id.dialog_addresseedialog_NO)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listenerNO.onClick(dialog, BUTTON_POSITIVE);
                    }
                });

            if (adapter != null)
                ((ExpandableListView) view.findViewById(R.id.dialog_addresseedialog_ExpandableListView)).setAdapter(adapter);

            dialog.setContentView(view);
            return dialog;
        }
    }
}
