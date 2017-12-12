package com.sdzx.xtbg.constant;

public class ConstantURL {
    /**安监局*/
    public static final String BASE_PATH = "http://rzajxtbg.sdzxkj.cn/";
    /**访问地址*/
    public static final String BASE_URL = BASE_PATH+"json/";
    /**版本更新*/
    public static final String VERSION = BASE_URL+"version.php?";
    /**登录*/
    public static final String LOGIN_URL = BASE_URL + "login.php";
    /**密码修改*/
    public static final String CHANGE_PASSWORD = BASE_URL + "pwdreset.php?act=do&uid=";
    /**读取未读信息*/
    public static final String UNREAD_URL = BASE_URL +"total.php";

    // 通讯录
    private static final String ADDRESS = "user.php";
    public static final String ADDRESS_URL = BASE_URL + ADDRESS;




    // 收文录入
    public static final String DOCUMENT_ADD_URL = BASE_URL + "add.php";
    // 来文机关
    public static final String DOCUMENT_COME_DEPART = BASE_URL + "add.php?act=get_type&type=";
    // 收文提交
    public static final String DOCUMENT_SUBMIT = BASE_URL + "add.php?act=add&uid=";
    // 发文待办
    public static final String SEND_DOCUMENT_HANDLING = BASE_URL + "fawen.php?act=dolist&uid=";
    // 发文查阅
    public static final String SEND_DOCUMENT_REFER = BASE_URL + "fawen.php?act=list&uid=";
    // 公文是否可撤回
    public static final String DOCUMENT_IS_REBUT = BASE_URL + "gongwen.php?act=che&uid=";
    // 公文撤回接口
    public static final String DOCUMENT_REBUT = BASE_URL + "gongwen.php?act=che&uid=";
    //公文流程，获取分管领导
    public static final String DOCUMENT_GET_LEADER = BASE_URL + "gongwen.php?act=groupys&id=";
    // 获取科室人员
    public static final String DOCUMENT_GET_DEPARTMENT = BASE_URL + "gongwen.php?act=groupfb&id=";
    // 科室负责人向下分
    public static final String DOCUMENT_GET_DEPARTMENT_LEADER_SEND = BASE_URL + "gongwen.php?act=fenban&id=";
    // 发文详情
    public static final String SEND_DOCUMENT_DETAILS = BASE_URL + "fawen.php?act=show&id=";
    // 发文拟稿
    public static final String SEND_DOCUMENT_ADD = BASE_URL + "fawen.php?act=add&uid=";
    // 发文拟稿 接收人
    public static final String SEND_DOCUMENT_ADD_RECEIVER = BASE_URL + "fawen.php?uid=";
    // 发文审批
    public static final String SEND_DOCUMENT_ADD_APPROVE = BASE_URL + "fawen.php?act=shenpido&do=show&id=";
    // 发文提价审批
    public static final String SEND_DOC_DETAIL_APPROVE = BASE_URL + "fawen.php?act=shenpido&do=do&id=";

    // 内网文件
    public static final String IN_DOC_LIST = BASE_URL + "get_wj.php?act=list_file&page=";
    // 内网文件签收
    public static final String IN_DOC_LIST_SIGN = BASE_URL + "get_wj.php?act=do_file";
    // 内网文件详情
    public static final String IN_DOC_DETAIL = BASE_URL + "get_wj.php";

    // 内网邮件
    public static final String IN_EMAIL_LIST = BASE_URL + "get_wj.php?act=list_pipe&page=";
    // 内网邮件接收
    public static final String IN_EMAIL_LIST_SIGN = BASE_URL + "get_wj.php?act=do_pipe";
    // 内网邮件详情
    public static final String IN_EMAIL_DETAIL = BASE_URL + "get_wj.php?act=pipe_show&id=";
    // 163邮件接收
    public static final String IN_MAIL_163_LIST = BASE_URL + "mail.php?act=mail_list";
    // 163邮件详情
    public static final String IN_MAIL_163_DETAIL = BASE_URL + "mail.php?act=mail_show&id=";

    // 签到 状态
    public static final String REGISTER_STATE = BASE_URL + "sign.php?uid=";
    // 签到 发送请求
    public static final String REGISTER_SEND_REQUEST = BASE_URL + "sign.php?uid=";
    // 签到列表
    public static final String REGISTER_LIST = BASE_URL + "sign.php?uid=";





    // 综合必读
    private static final String READ = "tongzhi.php";
    public static final String READ_URL = BASE_URL + READ;
    // 综合必读添加
    public static final String READ_ADD = BASE_URL + "tongzhi.php?act=add&uid=";
    // 综合必读文章类型
    public static final String READ_ADD_TYPE = BASE_URL + "tongzhi.php?act=mtype&uid=";

    // 印鉴使用
    public static final String SIGNET_ADD = BASE_URL + "stamp.php?act=add&uid=";
    // 印鉴审批
    public static final String SIGNET_APPROVE_LIST = BASE_URL + "stamp.php?act=dolist&uid=";
    // 印鉴查询
    public static final String SIGNET_CHECK_LIST = BASE_URL + "stamp.php?act=chayue&uid=";
    public static final String SIGNET_DETAIL = BASE_URL + "stamp.php?act=show&uid=";

    // 采购审批列表
    public static final String OFFICE_BUY_APPROVE_LIST = BASE_URL + "buy.php?act=list&uid=";
    // 采购审批详情
    public static final String OFFICE_BUY_APPROVE_DETAIL = BASE_URL + "buy.php?act=show&uid=";
    // 采购用品查阅
    public static final String OFFICE_BUY_SHOW_LIST = BASE_URL + "buy.php?act=chayue&uid=";



    // 邮件
    private static final String MAIL_HAVE = "maillist.php";
    public static final String MAIL_HAVE_URL = BASE_URL + MAIL_HAVE;

    // 文件管理
    private static final String DOCUMENT_BACKLOG = "gongwen.php";
    public static final String DOCUMENT_BACKLOG_URL = BASE_URL + DOCUMENT_BACKLOG;

    // 发文
    private static final String SEND = "fawen.php";
    public static final String SEND_URL = BASE_URL + SEND;

    // 查阅文件
    private static final String DOCUMENT_DETAILS = "gongwen.php";
    public static final String DOCUMENT_DETAILS_URL = BASE_URL + DOCUMENT_DETAILS;

    // 审批状态
    private static final String DOCUMENT_STATUS = "gongwen.php";
    // 督办督查
    private static final String SUPERVISE = "duban.php";
    public static final String SUPERVISE_URL = BASE_URL + SUPERVISE;

    // 审批

    // 登录日志
    private static final String LOGGED = "loginfo.php";
    public static final String LOGGED_URL = BASE_URL + LOGGED;

    // 操作日志
    private static final String OPERATION = "handle.php";
    public static final String OPERATION_URL = BASE_URL + OPERATION;

    // 回复邮件
    // 会议
    private static final String MEETING = "meeting.php";

    // 考勤审批人
    public static final String ATTENDANCE_APPROVER = BASE_URL + "leave.php?act=appuser&uid=";
    // 休假申请
    public static final String ATTENDANCE_HOLIDAY_APPLY = BASE_URL + "leave.php?act=add&uid=";
    // 待办审批
    public static final String ATTENDANCE_APPROVAL_LIST = BASE_URL + "leave.php?act=dolist&uid=";
    // 流程
    public static final String ATTENDANCE_FLOW = BASE_URL + "leave.php";
    // 休假详情
    public static final String ATTENDANCE_HOLIDAY_DETAIL = BASE_URL + "leave.php?act=show&uid=";
    // 请求休假状态(审批页面)
    public static final String ATTENDANCE_REQUEST_STATE = BASE_URL + "leave.php?act=shenpido&uid=";
    // 审批操作
    public static final String ATTENDANCE_AUDIT_ACTION = BASE_URL + "leave.php?act=shenpido&uid=";
    // 审核联系人
    public static final String ATTENDANCE_AUDIT_USER = BASE_URL + "leave.php?act=shenpido&uid=";
    // 我的申请列表
    public static final String ATTENDANCE_MY_APPLY_LIST = BASE_URL + "leave.php?act=list&uid=";
    // 休假查阅
    public static final String ATTENDANCE_HOLIDAY_VIEW_LIST = BASE_URL + "leave.php?act=chayue&uid=";

    // 请假申请
    public static final String ATTENDANCE_LEAVE_APPLY = BASE_URL + "atten.php?act=add&uid=";
    // 请假审核人
    public static final String ATTENDANCE_LEAVE_APPROVE = BASE_URL + "atten.php?act=appuser&uid=";
    // 我的请假申请列表
    public static final String ATTENDANCE_LEAVE_MY_APPLY_LIST = BASE_URL + "atten.php?act=list&uid=";
    // 请假待办审核
    public static final String ATTENDANCE_LEAVE_LIST = BASE_URL + "atten.php?act=dolist&uid=";
    // 请假详情
    public static final String ATTENDANCE_LEAVE_DETAIL = BASE_URL + "atten.php?act=show&uid=";
    // 请假审核联系人
    public static final String ATTENDANCE_LEAVE_AUDIT_USER = BASE_URL + "atten.php?act=shenpido&uid=";
    // 请求请假状态(审批页面)
    public static final String ATTENDANCE_LEAVE_REQUEST_STATE = BASE_URL + "atten.php?act=shenpido&uid=";
    // 请假审批操作
    public static final String ATTENDANCE_LEAVE_AUDIT_ACTION = BASE_URL + "atten.php?act=shenpido&uid=";
    // 请假流程
    public static final String ATTENDANCE_LEAVE_FLOW = BASE_URL + "atten.php";
    // 请假查阅
    public static final String ATTENDANCE_LEAVE_VIEW_LIST = BASE_URL + "atten.php?act=chayue&uid=";

    // 出差申请
    public static final String ATTENDANCE_BUSINESS_APPLY = BASE_URL + "business.php?act=add&uid=";
    // 出差审核人
    public static final String ATTENDANCE_BUSINESS_APPROVE = BASE_URL + "business.php?act=appuser&uid=";
    // 我的出差申请列表
    public static final String ATTENDANCE_BUSINESS_MY_APPLY_LIST = BASE_URL + "business.php?act=list&uid=";
    // 出差待办审核
    public static final String ATTENDANCE_BUSINESS_LIST = BASE_URL + "business.php?act=dolist&uid=";
    // 出差详情
    public static final String ATTENDANCE_BUSINESS_DETAIL = BASE_URL + "business.php?act=show&uid=";
    // 出差审核联系人
    public static final String ATTENDANCE_BUSINESS_AUDIT_USER = BASE_URL + "business.php?act=shenpido&uid=";
    // 出差请假状态(审批页面)
    public static final String ATTENDANCE_BUSINESS_REQUEST_STATE = BASE_URL + "business.php?act=shenpido&uid=";
    // 出差审批操作
    public static final String ATTENDANCE_BUSINESS_AUDIT_ACTION = BASE_URL + "business.php?act=shenpido&uid=";
    // 出差流程
    public static final String ATTENDANCE_BUSINESS_FLOW = BASE_URL + "business.php";
    // 请假查阅
    public static final String ATTENDANCE_BUSINESS_VIEW_LIST = BASE_URL + "business.php?act=chayue&uid=";



    //工作餐   申请
    public final static String GONGZUOCAN_ADD = BASE_URL + "gzc.php?act=add&uid=";
    //工作餐  审批人
    public final static String GONGZUOCAN_ADD_SHENPIREN = BASE_URL + "gzc.php?act=appuser&uid=";
    //工作餐  待审批列表
    public final static String GONGZUOCAN_LIST_PENDING = BASE_URL + "gzc.php?act=dolist&uid=";
    //工作餐  查阅列表
    public final static String GONGZUOCAN_LIST_CONSULT = BASE_URL + "gzc.php?act=chayue&uid=";
    //工作餐  流程
    public final static String GONGZUOCAN_LIST_LIUCHENG = BASE_URL + "gzc.php?act=liucheng&uid=";
    //工作餐  详细查看
    public final static String GONGZUOCAN_LIST_DETAILS = BASE_URL + "gzc.php?act=show&uid=";
    //工作餐  审批状态
    public final static String GONGZUOCAN_LIST_TYPE = BASE_URL + "gzc.php?act=shenpido&do=show&uid=";
    //工作餐  审批操作
    public final static String GONGZUOCAN_LIST_EXAMINE = BASE_URL + "gzc.php?act=shenpido&do=do&uid=";

    //物品采购   申请
    public static final String WPCG_ADD_FLOW = BASE_URL + "wpcg.php";
    public final static String WPCG_ADD = BASE_URL + "wpcg.php?act=add&uid=";
    //物品采购  审批人
    public final static String WPCG_ADD_SHENPIREN = BASE_URL + "wpcg.php?act=appuser&uid=";
    //物品采购  待审批列表
    public final static String WPCG_LIST_PENDING = BASE_URL + "wpcg.php?act=dolist&uid=";
    //物品采购  查阅列表
    public final static String WPCG_LIST_CONSULT = BASE_URL + "wpcg.php?act=chayue&uid=";
    //物品采购  流程
    public final static String WPCG_LIST_LIUCHENG = BASE_URL + "wpcg.php?act=liucheng&uid=";
    //物品采购  详细查看
    public final static String WPCG_LIST_DETAILS = BASE_URL + "wpcg.php?act=show&uid=";
    //物品采购  审批状态
    public final static String WPCG_LIST_TYPE = BASE_URL + "wpcg.php?act=shenpido&do=show&uid=";
    //物品采购  审批操作
    public final static String WPCG_LIST_EXAMINE = BASE_URL + "wpcg.php?act=shenpido&uid=";

    public static final String car_ADD_FLOW = BASE_URL + "car.php";
    //物品采购   申请
    public final static String car_ADD = BASE_URL + "car.php?act=add&uid=";
    //物品采购  审批人
    public final static String car_ADD_SHENPIREN = BASE_URL + "car.php?act=appuser&uid=";
    //物品采购  待审批列表
    public final static String car_LIST_PENDING = BASE_URL + "car.php?act=dolist&uid=";
    //物品采购  查阅列表
    public final static String car_LIST_CONSULT = BASE_URL + "car.php?act=chayue&uid=";
    //物品采购  流程
    public final static String car_LIST_LIUCHENG = BASE_URL + "car.php?act=liucheng&uid=";
    //物品采购  详细查看
    public final static String car_LIST_DETAILS = BASE_URL + "car.php?act=show&uid=";
    //物品采购  审批状态
    public final static String car_LIST_TYPE = BASE_URL + "car.php?act=shenpido&do=show&uid=";
    //物品采购  审批操作
    public final static String car_LIST_EXAMINE = BASE_URL + "car.php?act=shenpido&uid=";

    public static final String carrepair_ADD_FLOW = BASE_URL + "carrepair.php";
    //物品采购   申请
    public final static String carrepair_ADD = BASE_URL + "carrepair.php?act=add&uid=";
    //物品采购  审批人
    public final static String carrepair_ADD_SHENPIREN = BASE_URL + "carrepair.php?act=appuser&uid=";
    //物品采购  待审批列表
    public final static String carrepair_LIST_PENDING = BASE_URL + "carrepair.php?act=dolist&uid=";
    //物品采购  查阅列表
    public final static String carrepair_LIST_CONSULT = BASE_URL + "carrepair.php?act=chayue&uid=";
    //物品采购  流程
    public final static String carrepair_LIST_LIUCHENG = BASE_URL + "carrepair.php?act=liucheng&uid=";
    //物品采购  详细查看
    public final static String carrepair_LIST_DETAILS = BASE_URL + "carrepair.php?act=show&uid=";
    //物品采购  审批状态
    public final static String carrepair_LIST_TYPE = BASE_URL + "carrepair.php?act=shenpido&do=show&uid=";
    //物品采购  审批操作
    public final static String carrepair_LIST_EXAMINE = BASE_URL + "carrepair.php?act=shenpido&uid=";

    public static final String wpgl_ADD_FLOW = BASE_URL + "wpgl.php";
    //物品采购   申请
    public final static String wpgl_ADD = BASE_URL + "wpgl.php?act=add&uid=";
    //物品采购  审批人
    public final static String wpgl_ADD_SHENPIREN = BASE_URL + "wpgl.php?act=appuser&uid=";
    //物品采购  待审批列表
    public final static String wpgl_LIST_PENDING = BASE_URL + "wpgl.php?act=dolist&uid=";
    //物品采购  查阅列表
    public final static String wpgl_LIST_CONSULT = BASE_URL + "wpgl.php?act=chayue&uid=";
    //物品采购  流程
    public final static String wpgl_LIST_LIUCHENG = BASE_URL + "wpgl.php?act=liucheng&uid=";
    //物品采购  详细查看
    public final static String wpgl_LIST_DETAILS = BASE_URL + "wpgl.php?act=show&uid=";
    //物品采购  审批状态
    public final static String wpgl_LIST_TYPE = BASE_URL + "wpgl.php?act=shenpido&do=show&uid=";
    //物品采购  审批操作
    public final static String wpgl_LIST_EXAMINE = BASE_URL + "wpgl.php?act=shenpido&uid=";

    public static final String GWJD_ADD_FLOW = BASE_URL + "gwjd.php";
    //物品采购   申请
    public final static String GWJD_ADD = BASE_URL + "gwjd.php?act=add&uid=";
    //物品采购  审批人
    public final static String GWJD_ADD_SHENPIREN = BASE_URL + "gwjd.php?act=appuser&uid=";
    //物品采购  待审批列表
    public final static String GWJD_LIST_PENDING = BASE_URL + "gwjd.php?act=dolist&uid=";
    //物品采购  查阅列表
    public final static String GWJD_LIST_CONSULT = BASE_URL + "gwjd.php?act=chayue&uid=";
    //物品采购  流程
    public final static String GWJD_LIST_LIUCHENG = BASE_URL + "gwjd.php?act=liucheng&uid=";
    //物品采购  详细查看
    public final static String GWJD_LIST_DETAILS = BASE_URL + "gwjd.php?act=show&uid=";
    //物品采购  审批状态
    public final static String GWJD_LIST_TYPE = BASE_URL + "gwjd.php?act=shenpido&do=show&uid=";
    //物品采购  审批操作
    public final static String GWJD_LIST_EXAMINE = BASE_URL + "gwjd.php?act=shenpido&uid=";

    public static final String meeting_ADD_FLOW = BASE_URL + "meeting.php";
    //物品采购   申请
    public final static String meeting_ADD = BASE_URL + "meeting.php?act=add&uid=";
    //物品采购  审批人
    public final static String meeting_ADD_SHENPIREN = BASE_URL + "meeting.php?act=appuser&uid=";
    //物品采购  待审批列表
    public final static String meeting_LIST_PENDING = BASE_URL + "meeting.php?act=dolist&uid=";
    //物品采购  查阅列表
    public final static String meeting_LIST_CONSULT = BASE_URL + "meeting.php?act=chayue&uid=";
    //物品采购  流程
    public final static String meeting_LIST_LIUCHENG = BASE_URL + "meeting.php?act=liucheng&uid=";
    //物品采购  详细查看
    public final static String meeting_LIST_DETAILS = BASE_URL + "meeting.php?act=show&uid=";
    //物品采购  审批状态
    public final static String meeting_LIST_TYPE = BASE_URL + "meeting.php?act=shenpido&do=show&uid=";
    //物品采购  审批操作
    public final static String meeting_LIST_EXAMINE = BASE_URL + "meeting.php?act=shenpido&uid=";

}
