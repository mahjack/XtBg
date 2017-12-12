package com.sdzx.xtbg.bean;

/**
 * Author：Mark
 * Date：2015/11/28 0028
 * Tell：15006330640
 */
public class User {
    private static final String TAG = "User";
    private String id;
    private String sort;
    private String realname;
    private String password;
    private String depart;
    private String position;
    private String islock;
    private String isdong;
    private String tel;
    private String htel;
    private String lasttime;
    private String lastip;
    private String lastsession;
    private String adduser;
    private String addtime;
    private String addip;
    private String isadmin;
    private String iszg;
    private String iszb;
    private String header;
    private String username;
    private String name;
    private String image;
    private String mobile;
    // 容联
    private int unreadMsgCount;
    private String usernick;
    private String sex;
    private String fxid;
    private String region;
    private String avatar;
    private String sign;
    private String beizhu;
    private String user_id;
    private String star_level;
    /**
     friend_id 好友ID
     name 好友姓名
     image 好友头像
     introduction 好友简介
     status 状态，1，已同意加好友。2未同意加好友。
     accept_message 1.正常接受消息 2. 不同意接收，3.被群主禁言
     sub_account_id 好友容联子账户id
     sub_token 好友容联子账户token
     voip_account 好友容联语音子账户
     voip_pwd 好友容联子账户密码
     */
    private String friend_id;
    private String introduction;
    private String status;
    private String accept_message;
    private String sub_account_id;
    private String sub_token;
    private String voip_account;
    private String voip_pwd;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIslock() {
        return islock;
    }

    public void setIslock(String islock) {
        this.islock = islock;
    }

    public String getIsdong() {
        return isdong;
    }

    public void setIsdong(String isdong) {
        this.isdong = isdong;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHtel() {
        return htel;
    }

    public void setHtel(String htel) {
        this.htel = htel;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getLastip() {
        return lastip;
    }

    public void setLastip(String lastip) {
        this.lastip = lastip;
    }

    public String getLastsession() {
        return lastsession;
    }

    public void setLastsession(String lastsession) {
        this.lastsession = lastsession;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip;
    }

    public String getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(String isadmin) {
        this.isadmin = isadmin;
    }

    public String getIszg() {
        return iszg;
    }

    public void setIszg(String iszg) {
        this.iszg = iszg;
    }

    public String getIszb() {
        return iszb;
    }

    public void setIszb(String iszb) {
        this.iszb = iszb;
    }

    public int getUnreadMsgCount() {
        return unreadMsgCount;
    }

    public void setUnreadMsgCount(int unreadMsgCount) {
        this.unreadMsgCount = unreadMsgCount;
    }

    public String getNick() {
        return usernick;
    }

    public void setNick(String usernick) {
        this.usernick = usernick;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFxid() {
        return fxid;
    }

    public void setFxid(String fxid) {
        this.fxid = fxid;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStar_level() {
        return star_level;
    }

    public void setStar_level(String star_level) {
        this.star_level = star_level;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccept_message() {
        return accept_message;
    }

    public void setAccept_message(String accept_message) {
        this.accept_message = accept_message;
    }

    public String getSub_account_id() {
        return sub_account_id;
    }

    public void setSub_account_id(String sub_account_id) {
        this.sub_account_id = sub_account_id;
    }

    public String getSub_token() {
        return sub_token;
    }

    public void setSub_token(String sub_token) {
        this.sub_token = sub_token;
    }

    public String getVoip_account() {
        return voip_account;
    }

    public void setVoip_account(String voip_account) {
        this.voip_account = voip_account;
    }

    public String getVoip_pwd() {
        return voip_pwd;
    }

    public void setVoip_pwd(String voip_pwd) {
        this.voip_pwd = voip_pwd;
    }
}
