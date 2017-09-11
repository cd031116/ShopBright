package com.zhl.huiqu.personal.bean;

import java.io.Serializable;

/**
 * Created by dw on 2017/8/22.
 */

public class OrderEntity implements Serializable {


    /**
     * order_id : 1931
     * order_sn : 170855025855269853
     * name : 石燕湖丛林穿越套票
     * price : 168.00
     * num : 1
     * use_date : 2017-8-26
     * use_name : 刘洋
     * pay_way :
     * add_time : 1503644335
     * mobile : 17688986810
     * order_total : 168.00
     * status : 0
     * spot_name : 石燕湖生态旅游景区
     * take : 前台取票
     */

    private int order_id;
    private String order_sn;
    private String name;
    private String price;
    private int num;
    private String use_date;
    private String use_name;
    private String pay_way;
    private int add_time;
    private String mobile;
    private String order_total;
    private int status;
    private String spot_name;
    private String take;
    /**
     * memberInfo : {"role":0,"type":0,"mobile":"18569502645","email":"","password":"5b053007add1fcdbcb2422748c702194","nickname":"18569502645","litpic":"","score":0,"sex":0,"money":"0.00","recode":"","unionid":"","sinaid":"","openid":"","status":1,"login_time":1503890981,"login_ip":"127.0.0.1","last_time":1503890981,"last_ip":"127.0.0.1","add_time":1503890981,"hongbao":0,"member_id":1863092}
     */

    private MemberInfoBean memberInfo;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUse_date() {
        return use_date;
    }

    public void setUse_date(String use_date) {
        this.use_date = use_date;
    }

    public String getUse_name() {
        return use_name;
    }

    public void setUse_name(String use_name) {
        this.use_name = use_name;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrder_total() {
        return order_total;
    }

    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSpot_name() {
        return spot_name;
    }

    public void setSpot_name(String spot_name) {
        this.spot_name = spot_name;
    }

    public String getTake() {
        return take;
    }

    public void setTake(String take) {
        this.take = take;
    }

    public MemberInfoBean getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfoBean memberInfo) {
        this.memberInfo = memberInfo;
    }

    public static class MemberInfoBean implements Serializable{
        /**
         * role : 0
         * type : 0
         * mobile : 18569502645
         * email :
         * password : 5b053007add1fcdbcb2422748c702194
         * nickname : 18569502645
         * litpic :
         * score : 0
         * sex : 0
         * money : 0.00
         * recode :
         * unionid :
         * sinaid :
         * openid :
         * status : 1
         * login_time : 1503890981
         * login_ip : 127.0.0.1
         * last_time : 1503890981
         * last_ip : 127.0.0.1
         * add_time : 1503890981
         * hongbao : 0
         * member_id : 1863092
         */

        private int role;
        private int type;
        private String mobile;
        private String email;
        private String password;
        private String nickname;
        private String litpic;
        private int score;
        private int sex;
        private String money;
        private String recode;
        private String unionid;
        private String sinaid;
        private String openid;
        private int status;
        private int login_time;
        private String login_ip;
        private int last_time;
        private String last_ip;
        private int add_time;
        private int hongbao;
        private int member_id;

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getLitpic() {
            return litpic;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getRecode() {
            return recode;
        }

        public void setRecode(String recode) {
            this.recode = recode;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getSinaid() {
            return sinaid;
        }

        public void setSinaid(String sinaid) {
            this.sinaid = sinaid;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getLogin_time() {
            return login_time;
        }

        public void setLogin_time(int login_time) {
            this.login_time = login_time;
        }

        public String getLogin_ip() {
            return login_ip;
        }

        public void setLogin_ip(String login_ip) {
            this.login_ip = login_ip;
        }

        public int getLast_time() {
            return last_time;
        }

        public void setLast_time(int last_time) {
            this.last_time = last_time;
        }

        public String getLast_ip() {
            return last_ip;
        }

        public void setLast_ip(String last_ip) {
            this.last_ip = last_ip;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getHongbao() {
            return hongbao;
        }

        public void setHongbao(int hongbao) {
            this.hongbao = hongbao;
        }

        public int getMember_id() {
            return member_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }
    }
}
