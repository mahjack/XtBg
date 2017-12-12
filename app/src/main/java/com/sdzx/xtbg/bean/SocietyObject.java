package com.sdzx.xtbg.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/4/13.
 */
public class SocietyObject implements Serializable {
    private String owner_name;
    private String owner_image;
    private String member_num;
    private String create_time;
    private String type;
    private String owner;
    private String permisson;
    private String group_id_ronglian;
    private String image;
    private String introduction;
    private String managers;
    private String star_id;
    private String accept_message;
    private String id;
    private String founder;
    private String name;
    private String number;

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPermisson() {
        return permisson;
    }

    public void setPermisson(String permisson) {
        this.permisson = permisson;
    }

    public String getGroup_id_ronglian() {
        return group_id_ronglian;
    }

    public void setGroup_id_ronglian(String group_id_ronglian) {
        this.group_id_ronglian = group_id_ronglian;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getManagers() {
        return managers;
    }

    public void setManagers(String managers) {
        this.managers = managers;
    }

    public String getStar_id() {
        return star_id;
    }

    public void setStar_id(String star_id) {
        this.star_id = star_id;
    }

    public String getAccept_message() {
        return accept_message;
    }

    public void setAccept_message(String accept_message) {
        this.accept_message = accept_message;
    }

    public String getOwner_image() {
        return owner_image;
    }

    public void setOwner_image(String owner_image) {
        this.owner_image = owner_image;
    }

    public String getMember_num() {
        return member_num;
    }

    public void setMember_num(String member_num) {
        this.member_num = member_num;
    }
}
