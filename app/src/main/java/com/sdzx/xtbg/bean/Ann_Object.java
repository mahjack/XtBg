package com.sdzx.xtbg.bean;

/**
 * Author：Mark
 * Date：2015/12/8 0008
 * Tell：15006330640
 */
public class Ann_Object {
    private static final String TAG = "Ann_Object";
    private String id;
    private String mid;
    private String title;
    private String content;
    private String memberlist;
    private String responlist;
    private String adduser;
    private String addtime;
    private String NewName;
    private String OldName;

    public String getOldName() {
        return OldName;
    }

    public void setOldName(String oldName) {
        OldName = oldName;
    }

    public String getNewName() {
        return NewName;
    }

    public void setNewName(String newName) {
        NewName = newName;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getResponlist() {
        return responlist;
    }

    public void setResponlist(String responlist) {
        this.responlist = responlist;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }
}
