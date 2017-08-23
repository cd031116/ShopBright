package com.zhl.huiqu.login.entity;

import com.zhl.huiqu.base.BaseInfo;

/**
 * Created by dw on 2017/8/17.
 */

public class RegisterEntity extends BaseInfo {


    /**
     * data : {"password":"96e79218965eb72c92a549dd5a330112","mobile":"18658858468","nickname":"18658858468","login_ip":"192.168.18.114","last_ip":"192.168.18.114","login_time":1502953618,"last_time":1502953618,"add_time":1502953618,"status":1,"member_id":"1863068"}
     * session_id : 18658858468zhlhqsc
     * check_sign : feb7fabd3595d755d89917190dc07b41
     */

    private RegisterInfo body;
    private String session_id;
    private String check_sign;

    public RegisterInfo getBody() {
        return body;
    }

    public void setBody(RegisterInfo body) {
        this.body = body;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getCheck_sign() {
        return check_sign;
    }

    public void setCheck_sign(String check_sign) {
        this.check_sign = check_sign;
    }


}
