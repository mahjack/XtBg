package com.sdzx.xtbg.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sdzx.xtbg.MyApp;
import com.sdzx.xtbg.R;
import com.sdzx.xtbg.adapter.Home_Adapter;
import com.sdzx.xtbg.bean.Home_Object;
import com.sdzx.xtbg.bean.Unread;
import com.sdzx.xtbg.constant.ConstantString;
import com.sdzx.xtbg.constant.ConstantURL;
import com.sdzx.xtbg.tools.DataCleanManager;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;


/**
 * 首页
 * Author：Mark
 * Date：2015/11/24 0024
 * Tell：15006330640
 */
public class Fragment_Home extends Fragment implements View.OnClickListener {
    private static final String TAG = "Fragment_Home";

    private static final int HOME_CHECK = 1;

    // 来文管理
    @Bind(R.id.home_document)
    FrameLayout home_document;
    @Bind(R.id.home_unread_document)
    TextView home_unread_document; // 未读
    // 多点分发
    @Bind(R.id.home_multipoint)
    FrameLayout homeMultipoint;
    @Bind(R.id.home_unread_multipoint)
    TextView unreadMultipoint;
    // 督察督办
    @Bind(R.id.home_supervise)
    FrameLayout homeSupervise;
    @Bind(R.id.home_unread_supervise)
    TextView unreadSupervise;
    // 会议申请
    @Bind(R.id.home_meeting)
    FrameLayout homeMeeting;
    @Bind(R.id.home_unread_meeting)
    TextView unreadMeeting;
    // 考勤管理
    @Bind(R.id.home_attendance)
    FrameLayout homeAttendance;
    @Bind(R.id.home_unread_attendance)
    TextView unreadAttendance;
    // 综合并读
    @Bind(R.id.home_read)
    FrameLayout home_read;
    @Bind(R.id.home_unread_read)
    TextView home_unread_read;
    // 邮件
    @Bind(R.id.home_mail)
    FrameLayout home_mail;
    @Bind(R.id.home_unread_mail)
    TextView home_unread_mail;
    // 关于
    @Bind(R.id.home_about)
    LinearLayout home_about;
    // 检测更新
    @Bind(R.id.home_check)
    LinearLayout home_check;
    //    @Bind(R.id.home_pending)
//    LinearLayout home_pending;
    // 名字
    @Bind(R.id.home_name)
    TextView home_name;
    // 广告
    @Bind(R.id.daily_vp)
    ViewPager daily_vp;
    @Bind(R.id.v_dot0)
    View v_dot0;
    @Bind(R.id.v_dot1)
    View v_dot1;
    @Bind(R.id.v_dot2)
    View v_dot2;
    @Bind(R.id.v_dot3)
    View v_dot3;
    @Bind(R.id.v_dot4)
    View v_dot4;
    @Bind(R.id.home_recycle)
    RecyclerView homeRecycle;
    // 对象
    private List<ImageView> imageViews; // 滑动的图片集合
    private int[] imageResId; // 图片ID
    private List<View> dots; // 图片标题正文的那些点
    private int currentItem = 0; // 当前图片的索引号
    private ScheduledExecutorService scheduledExecutorService;
    private static Context context;
    private SharedPreferences preferences;
    private static boolean ised;
    private ArrayList<Home_Object> mArrayList = new ArrayList<Home_Object>();

    private RequestCall mCall;
    // 切换图片
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            daily_vp.setCurrentItem(currentItem);// 切换当前显示的图片
        }
    };
    private int[] resImage = new int[]{
            R.mipmap.home_document,R.mipmap.home_read,  R.mipmap.home_ling,
            R.mipmap.home_mail, R.mipmap.home_meeting, R.mipmap.home_attendance,
            R.mipmap.home_zhou ,R.mipmap.home_bannian ,R.mipmap.home_gwyc,R.mipmap.home_yue,R.mipmap.home_updata, R.mipmap.home_cache};
    private int[] resText = new int[]{
            R.string.home_document, R.string.send_document, R.string.home_signet,
            R.string.home_mail, R.string.home_meeting, R.string.home_attendance,
            R.string.main_book,R.string.main_book,R.string.main_book,R.string.home_kaoqin, R.string.sliding_cache, R.string.home_cache};
    private int[] unReads = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private boolean[] isVisible = new boolean[]{true,  true, true, true, true, true, true, true, true, true, true, true};
    private String[] events = new String[]{"Document","sendDocument",  "signet", "Mail", "Meeting", "Attendance","Gwjd","Gwcc","Gzc","kaoqin","Cache", "About"};


    private Home_Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initConstants();
        initUnReadNum();
        initView();
        try {
            Log.e("缓存大小", DataCleanManager.getFormatSize(DataCleanManager.getFolderSize(new File(MyApp.BASE_PATH))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initConstants() {
        context = getActivity();
        preferences = getActivity().getSharedPreferences("info", Activity.MODE_PRIVATE);
        imageResId = new int[]{R.mipmap.a1, R.mipmap.a2,
                R.mipmap.a3, R.mipmap.a4};
        imageViews = new ArrayList<ImageView>();
        // 初始化 图片资源
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }
        // 图片下标
        dots = new ArrayList<View>();
        dots.add(v_dot0);
        dots.add(v_dot1);
        dots.add(v_dot2);
        dots.add(v_dot3);
        dots.add(v_dot4);
    }

    private void initView() {
        // 设置填充ViewPager页面的适配器
        daily_vp.setAdapter(new MyAdapter());
        // 设置一个监听器，当ViewPager中的页面改变时调用
        daily_vp.setOnPageChangeListener(new MyPageChangeListener());
        // 设置 事件
        home_document.setOnClickListener(this);
        homeMultipoint.setOnClickListener(this);
        homeSupervise.setOnClickListener(this);
        homeMeeting.setOnClickListener(this);
        homeAttendance.setOnClickListener(this);
        home_read.setOnClickListener(this);
        home_mail.setOnClickListener(this);
        home_about.setOnClickListener(this);
        home_check.setOnClickListener(this);
        home_name.setText(getString(R.string.base_welcome) + preferences.getString(ConstantString.NAME, ""));
    }

    @Override
    public void onStart() {
        Log.e("启动", "切换图片");
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
        super.onStart();
    }

    @Override
    public void onPause() {
        Log.e("关闭", "停止切换图片");
        // 当Activity不可见的时候停止切换
        scheduledExecutorService.shutdown();
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("关闭", "停止切换图片");
        // 当Activity不可见的时候停止切换
        scheduledExecutorService.shutdown();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.home_document:
//                // 来文
//                startActivity(new Intent(context, Document_Activity.class));
//                break;
//            case R.id.home_multipoint:
//                // 多点分发
//                startActivity(new Intent(context, Multipoint_Activity.class));
//                break;
//            case R.id.home_supervise:
//                startActivity(new Intent(context, Supervise_Activity.class));
//                // 督察督办
//                break;
//            case R.id.home_meeting:
//                // 会议申请
//                startActivity(new Intent(context, Meeting_Activity.class));
//                break;
//            case R.id.home_attendance:
//                // 考勤管理
//                break;
//            case R.id.home_read:
//                // 综合并读
//                startActivity(new Intent(context, Notices_Activity.class));
//                break;
//            case R.id.home_mail:
//                // 邮件
//                startActivity(new Intent(context, Mail_Activity.class));
//                break;
//            case R.id.home_check:
////                checkUpdate(HOME_CHECK);
//                break;
//        }
    }

    /**
     * 换行切换任务
     */
    private class ScrollTask implements Runnable {
        @Override
        public void run() {
            synchronized (daily_vp) {
                currentItem = (currentItem + 1) % imageViews.size();
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }
    }

    /**
     * 填充ViewPager页面的适配器
     */
    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageResId.length;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(imageViews.get(arg1));
            return imageViews.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentItem = position;
            dots.get(oldPosition).setBackgroundResource(R.drawable.shape_dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.shape_dot_press);
            oldPosition = position;
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("测试", "onResume");
        initUnReadNum();
    }

    /**
     * 初始化未读数量
     */
    private void initUnReadNum() {
        mCall = OkHttpUtils
                .get()
                .url(ConstantURL.UNREAD_URL)
                .addParams(ConstantString.UID, preferences.getString(ConstantString.UID, ""))
                .addParams(ConstantString.ACT, "total")
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "访问服务器失败！", Toast.LENGTH_SHORT).show();
                initRecycle();
            }

            @Override
            public void onResponse(String response) {
                try {
                    KLog.json("主页未读数量------>", response);
                    Gson gson = new Gson();
                    Unread unRead = gson.fromJson(response, Unread.class);
                    if (unRead != null) {
                        // 来文管理
                        if (Integer.valueOf(unRead.getGongwen()) != 0) {
                            unReads[0] = Integer.parseInt(unRead.getGongwen());
                        } else {
                            unReads[0] = 0;
                        }
                        // 来文管理
                        if (Integer.valueOf(unRead.getFawen()) != 0) {
                            unReads[1] = Integer.parseInt(unRead.getFawen());
                        } else {
                            unReads[1] = 0;
                        }
                        // 综合必读
                        if (Integer.valueOf(unRead.getGwjd()) != 0) {
                            unReads[6] = Integer.parseInt(unRead.getGwjd());
                        } else {
                            unReads[6] = 0;
                        }
                        // 内部邮件
                        if (Integer.valueOf(unRead.getEmail()) != 0) {
                            unReads[3] = Integer.parseInt(unRead.getEmail());
                        } else {
                            unReads[3] = 0;
                        }
                        // 发文管理
                        if (Integer.valueOf(unRead.getWpgl()) != 0) {
                            unReads[2] = Integer.parseInt(unRead.getWpgl());
                        } else {
                            unReads[2] = 0;
                        }
                        // 考勤
                        if (Integer.valueOf(unRead.getAtten()) != 0) {
                            unReads[5] = Integer.parseInt(unRead.getAtten());
                        } else {
                            unReads[5] = 0;
                        }
                        // 会议申请
                        if (!unRead.getMeeting().equals("0")) {
                            unReads[4] = Integer.parseInt(unRead.getMeeting());
                        } else {
                            unReads[4] = 0;
                        }
                        // 会议申请
                        if (!unRead.getBusiness().equals("0")) {
                            unReads[7] = Integer.parseInt(unRead.getBusiness());
                        } else {
                            unReads[7] = 0;
                        }
                        // 会议申请
                        if (!unRead.getCar().equals("0")) {
                            unReads[8] = Integer.parseInt(unRead.getCar());
                        } else {
                            unReads[8] = 0;
                        }

                        initRecycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    initRecycle();
                }

            }
        });
    }

    private void initRecycle() {
        // date
        if (!mArrayList.isEmpty()) {
            mArrayList.clear();
        }
        for (int i = 0; i < 12; i++) {
            Home_Object object = new Home_Object();
            object.setName(resText[i]);
            object.setResId(resImage[i]);
            object.setUnRead(unReads[i]);
            object.setIsVisible(isVisible[i]);
            object.setEvent(events[i]);
            if (isVisible[i]) {
                mArrayList.add(object);
            }
        }
        // RecycleView
        mAdapter = new Home_Adapter(getActivity(), mArrayList, R.layout.item_home);
//        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        homeRecycle.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        homeRecycle.setAdapter(mAdapter);
    }
}
