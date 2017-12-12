package com.sdzx.xtbg.bean;

import java.util.List;

/**
 * 内网文件列表类
 * Author:Eron
 * Date: 2016/6/27 0027
 * Time: 15:27
 */
public class InDocList {

    /**
     * {
     * "id": "8801",//邮件ID
     * "sendername": "区委办公室",//发送科室
     * "title": "通知",//邮件标题
     * "addtime": "2016-06-23 09:18:41",//添加时间
     * "responlist": "|1085|1096|1098|1099|1100|1101|1102|1104|1105|1108|1110|1124|1158|1159|1160|1162|1165|1166|1167|1168|1169|1170|1171|1192|1217|1227|1229|1230|1018|1019|1032|1211|1232|1152|",
     * "get": 1//0：未签收，需要签收后查看，1是已签收
     * }
     */

    private List<InDocListBean> value;

    public List<InDocListBean> getValue() {
        return value;
    }

    public void setValue(List<InDocListBean> value) {
        this.value = value;
    }

    public class InDocListBean {
        private String id;
        private String sendername;
        private String title;
        private String addtime;
        private String responlist;
        private String get;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSendername() {
            return sendername;
        }

        public void setSendername(String sendername) {
            this.sendername = sendername;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getResponlist() {
            return responlist;
        }

        public void setResponlist(String responlist) {
            this.responlist = responlist;
        }

        public String getGet() {
            return get;
        }

        public void setGet(String get) {
            this.get = get;
        }
    }
}
