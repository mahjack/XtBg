package com.sdzx.xtbg.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author:Eron
 * Date: 2016/6/28 0028
 * Time: 11:23
 */
public class Document_Intranet {
    private List<Intranet> info;

    public List<Intranet> getInfo() {
        return info;
    }

    public void setInfo(List<Intranet> info) {
        this.info = info;
    }

    public class Intranet implements Serializable {
        private String id;
        private String sender;
        private String sendername;
        private String title;
        private String content;
        private String filenum;
        private String addtime;
        private String memberlist;
        private String responlist;
        private String status;
        private int get;
        private String NewName;
        private String OldName;

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

        public String getFilenum() {
            return filenum;
        }

        public void setFilenum(String filenum) {
            this.filenum = filenum;
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

        public int getGet() {
            return get;
        }

        public void setGet(int get) {
            this.get = get;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMemberlist() {
            return memberlist;
        }

        public void setMemberlist(String memberlist) {
            this.memberlist = memberlist;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNewName() {
            return NewName;
        }

        public void setNewName(String newName) {
            NewName = newName;
        }

        public String getOldName() {
            return OldName;
        }

        public void setOldName(String oldName) {
            OldName = oldName;
        }
    }
}
