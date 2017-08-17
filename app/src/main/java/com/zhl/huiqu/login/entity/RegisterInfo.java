package com.zhl.huiqu.login.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/17.
 */

public class RegisterInfo implements Serializable{
    /**
     * password : 96e79218965eb72c92a549dd5a330112
     * mobile : 18658858468
     * nickname : 18658858468
     * login_ip : 192.168.18.114
     * last_ip : 192.168.18.114
     * login_time : 1502953618
     * last_time : 1502953618
     * add_time : 1502953618
     * status : 1
     * member_id : 1863068
     */

    private String password;
    private String mobile;
    private String nickname;
    private String login_ip;
    private String last_ip;
    private int login_time;
    private int last_time;
    private int add_time;
    private int status;
    private String member_id;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLogin_ip() {
        return login_ip;
    }

    public void setLogin_ip(String login_ip) {
        this.login_ip = login_ip;
    }

    public String getLast_ip() {
        return last_ip;
    }

    public void setLast_ip(String last_ip) {
        this.last_ip = last_ip;
    }

    public int getLogin_time() {
        return login_time;
    }

    public void setLogin_time(int login_time) {
        this.login_time = login_time;
    }

    public int getLast_time() {
        return last_time;
    }

    public void setLast_time(int last_time) {
        this.last_time = last_time;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
}