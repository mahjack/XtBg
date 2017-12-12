package com.sdzx.xtbg.activity.document;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.PersonAdapter;
import com.sdzx.xtbg.adapter.SelectPersonAdapter;
import com.sdzx.xtbg.bean.Address_Select;
import com.sdzx.xtbg.bean.Depart;
import com.sdzx.xtbg.bean.User;
import com.sdzx.xtbg.constant.ConstantURL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邮件联系人选择
 * Author：Mark
 * Date：2015/12/21 0021
 * Tell：15006330640
 */
public class Document_Select_Activity extends Activity implements ExpandableListView.OnChildClickListener {
    private static final String TAG = "Mail_Select_Activity";
    @ViewInject(R.id.header_back)
    TextView header_back;
    @ViewInject(R.id.header_title)
    TextView header_title;
    @ViewInject(R.id.header_right)
    ImageView header_right;
    @ViewInject(R.id.alert_cancel)
    Button alert_cancel;
    @ViewInject(R.id.alert_ensure)
    Button alert_ensure;
    @ViewInject(R.id.dialog_text)
    TextView dialog_text;
    @ViewInject(R.id.dialog_progress)
    ProgressBar dialog_progress;
    @ViewInject(R.id.elv_selectPerson)
    ExpandableListView elv_selectPerson;
    //
    private List<User> users = new ArrayList<User>();
    private List<Depart> departs = new ArrayList<Depart>();
    private ArrayList<ArrayList<User>> staffGroup;
    private ArrayList<Map<String, Boolean>> groupCheckBox;
    private ArrayList<ArrayList<Map<String, Boolean>>> childCheckBox;
    private static final String G_CB = "G_CB";
    private static final String C_CB = "C_CB";
    private PersonAdapter adapter;
    private SelectPersonAdapter selectPersonAdapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_select_person);
        ViewUtils.inject(this);
        try{
            initConstants();
            readData();
            initViews();
            initEvents();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initConstants() {
        context = Document_Select_Activity.this;
    }

    private void initViews() {
        header_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        header_title.setText(getString(R.string.mail_chose_receiver));
        header_right.setVisibility(View.GONE);
//        lvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (view.getTag() instanceof PersonAdapter.ViewHolder) {
//
//                    PersonAdapter.ViewHolder holder = (PersonAdapter.ViewHolder) view.getTag();
//
//                    // 会自动出发CheckBox的checked事件
//                    holder.cbCheck.toggle();
//
//                }
//            }
//        });
        // 全选
//        btnAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dialog_text.getText().toString().trim().equals("全选")) {
//
//                    // 所有项目全部选中
//                    adapter.configCheckMap(true);
//
//                    adapter.notifyDataSetChanged();
//
//                    dialog_text.setText("全不选");
//                } else {
//
//                    // 所有项目全部不选中
//                    adapter.configCheckMap(false);
//
//                    adapter.notifyDataSetChanged();
//
//                    dialog_text.setText("全选");
//                }
//            }
//        });
        elv_selectPerson.setOnChildClickListener(this);
    }

    private void initEvents() {
        // 确定
        alert_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                StringUtils.name = "";
//            /*
//			 * 删除算法最复杂,拿到checkBox选择寄存map
//			 */
//                Map<Integer, Boolean> map = adapter.getCheckMap();
//
//                // 获取当前的数据数量
//                int count = adapter.getCount();
//                // 进行遍历
//                for (int i = 0; i < count; i++) {
//                    // 因为List的特性,删除了2个item,则3变成2,所以这里要进行这样的换算,才能拿到删除后真正的position
//                    int position = i - (count - adapter.getCount());
//                    if (map.get(i) != null && map.get(i)) {
//                        Person_User bean = (Person_User) adapter.getItem(position);
//                        StringUtils.name += bean.getRealname()+"、";
//                        StringUtils.user_id += bean.getId()+ "|";
//                    }
//                }
                Log.e("选中A", getReceivePerson() + "----" + getReceivePersonName());
                Intent intent = new Intent();
                intent.putExtra("user_id", getReceivePerson().substring(0, getReceivePerson().length()-1));
                intent.putExtra("user_name",getReceivePersonName().substring(0, getReceivePersonName().length()-1));
                Document_Select_Activity.this.setResult(RESULT_OK, intent);
                Document_Select_Activity.this.finish();
            }
        });
        // 取消
        alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
    }

    private void readData() {
        HttpUtils httpUtils = new HttpUtils();
        final RequestParams params = new RequestParams();
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.ADDRESS_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("tag", "邮件联系人-------------->" + responseInfo.result);
                dialog_progress.setVisibility(View.GONE);
                groupCheckBox = new ArrayList<>();
                childCheckBox = new ArrayList<>();
                Gson gson = new Gson();
                Address_Select address = gson.fromJson(responseInfo.result, Address_Select.class);
                if(address != null){
                    users = address.getUser();
                    departs = address.getDepart();
                    staffGroup = new ArrayList<>();
                    ArrayList<User> mStaff = null;
                    for (int i = 0; i < departs.size(); i++) {

                        mStaff = new ArrayList<User>();

                        for (int j = 0; j < users.size(); j++) {

                            if (users.get(j).getDepart()
                                    .equals(departs.get(i).getId())) {
                                mStaff.add(users.get(j));
                            }
                        }
                        staffGroup.add(mStaff);
                    }

                    for (int i = 0; i < departs.size(); i++) {
                        Map<String, Boolean> curGB = new HashMap<String, Boolean>();
                        curGB.put(G_CB, false);
                        groupCheckBox.add(curGB);
                        ArrayList<Map<String, Boolean>> childCB = new ArrayList<Map<String, Boolean>>();
                        for (int j = 0; j < staffGroup.get(i).size(); j++) {
                            Map<String, Boolean> curCB = new HashMap<String, Boolean>();
                            curCB.put(C_CB, false);
                            childCB.add(curCB);
                        }
                        childCheckBox.add(childCB);
                    }
                    selectPersonAdapter = new SelectPersonAdapter(context,
                            departs, staffGroup, groupCheckBox,
                            childCheckBox, true);

                    elv_selectPerson.setAdapter(selectPersonAdapter);
                }else {

                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dialog_progress.setVisibility(View.GONE);
            }
        });
    }
    public String getReceivePerson() {

        return selectPersonAdapter.getReceivePerson();

    }

    public String getReceivePersonName() {

        return selectPersonAdapter.getReceivePersonName();

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.childCheckBox);

        checkBox.toggle();

        if (childCheckBox.get(groupPosition).get(childPosition)

                .get(C_CB)) {

            childCheckBox.get(groupPosition).get(childPosition)
                    .put(C_CB, false);

            if (groupCheckBox.get(groupPosition).get(G_CB)) {

                groupCheckBox.get(groupPosition).put(G_CB, false);

                for (int i = 0; i < childCheckBox.get(groupPosition).size(); i++) {

                    if (childPosition != i) {

                        childCheckBox.get(groupPosition).get(i).put(C_CB, true);

                    }
                }
            }

        } else {

            int count = 0;

            childCheckBox.get(groupPosition).get(childPosition).put(C_CB, true);

            for (int i = 0; i < childCheckBox.get(groupPosition).size(); i++) {

                if (childCheckBox.get(groupPosition).get(i).get(C_CB)) {

                    count += 1;

                }

            }

            if (count == childCheckBox.get(groupPosition).size()) {

                groupCheckBox.get(groupPosition).put(G_CB, true);

                for (int j = 0; j < childCheckBox.get(groupPosition).size(); j++) {

                }
            }
        }

        selectPersonAdapter.notifyDataSetChanged();

        return false;
    }
}
