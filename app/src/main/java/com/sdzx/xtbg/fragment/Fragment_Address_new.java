package com.sdzx.xtbg.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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
import com.sdzx.xtbg.adapter.Address_Depart_Adapter;
import com.sdzx.xtbg.bean.Address;
import com.sdzx.xtbg.bean.Depart;
import com.sdzx.xtbg.bean.User;
import com.sdzx.xtbg.constant.ConstantURL;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Mark
 * Date：2016/1/3 0003
 * Tell：15006330640
 */
public class Fragment_Address_new extends Fragment {
    @ViewInject(R.id.address_list)
    ListView address_list;
    //\
    private List<Depart> departs = new ArrayList<Depart>();
    private List<User> contactList = new ArrayList<User>();
    private Address_Depart_Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initConstant();
        initEvents();
    }

    private void initView() {

    }

    private void initEvents() {

    }

    private void initConstant() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        httpUtils.send(HttpRequest.HttpMethod.POST, ConstantURL.ADDRESS_URL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    Gson gson = new Gson();
//                    KLog.json("通讯录------>", responseInfo.result);
                    Address address = gson.fromJson(responseInfo.result, Address.class);
                    if (address != null) {
                        contactList = address.getUser();
                        departs = address.getDepart();
                        initList();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.base_server), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getActivity(), getString(R.string.base_server), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initList() {
        // 设置adapter
        adapter = new Address_Depart_Adapter(getActivity(), departs, contactList);
        address_list.setAdapter(adapter);
    }
}
