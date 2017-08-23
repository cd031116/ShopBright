package com.zhl.huiqu.sdk;

import android.app.Activity;
import android.text.TextUtils;

import com.zhl.huiqu.BuildConfig;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.login.entity.RegisterInfo;
import com.zhl.huiqu.main.ProductPartListBean;
import com.zhl.huiqu.main.bean.DetailBean;
import com.zhl.huiqu.main.bean.DetailMainBean;
import com.zhl.huiqu.main.bean.GradeBean;
import com.zhl.huiqu.main.bean.MainBean;
import com.zhl.huiqu.main.bean.SearchBean;
import com.zhl.huiqu.main.ticket.SpotTBean;
import com.zhl.huiqu.main.ticket.TickMainBean;
import com.zhl.huiqu.personal.bean.AllOrderBean;
import com.zhl.huiqu.personal.bean.OrderBean;
import com.zhl.huiqu.personal.bean.OrderEntity;
import com.zhl.huiqu.sdk.http.DTODataParseHttp;
import com.zhl.huiqu.utils.Constants;
import com.zhl.huiqu.utils.SaveObjectUtils;
import com.zhl.huiqu.utils.TLog;

import org.aisen.android.common.setting.Setting;
import org.aisen.android.network.biz.ABizLogic;
import org.aisen.android.network.http.HttpConfig;
import org.aisen.android.network.http.IHttpUtility;
import org.aisen.android.network.http.Params;
import org.aisen.android.network.task.TaskException;
import org.json.JSONObject;

/**
 * @author lyj
 * @description 服务端接口api
 * @date 2017/7/26
 */

public class SDK extends ABizLogic {
    private static Activity context;

    private SDK() {
        this(CacheMode.disable);
    }

    private SDK(CacheMode mode) {
        super(mode);
    }

    public static SDK newInstance(Activity context) {
        SDK.context = context;
        return newInstance(CacheMode.disable);
    }

    public static SDK newInstance(CacheMode mode) {
        return new SDK(mode);
    }

    /**
     * 封装基础参数
     *
     * @param paramsJson
     * @return
     */
    public JSONObject getBasicParams(JSONObject paramsJson) {
        try {
            Params basicParams = basicParams(null);
            for (String key : basicParams.getKeys()) {
                paramsJson.put(key, basicParams.getParameter(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramsJson;
    }

    /*
       * 获取验证码
        * @param phone
        * @param
        * @return
        * @throws TaskException
        */
    public BaseInfo getCode(String mobile, String type) throws TaskException {
        Setting action = newSetting("getCheckCode", "appapi/Memberpub/getCheckCode", "验证码");
        Params params = new Params();
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?mobile=" + mobile + "&type=" + type);
        params.addParameter("mobile", mobile);
        params.addParameter("type", type);
        // 这个接口，是将form表单数据，按照json格式走post协议，请使用requestObject这个参数。
        return doPost(configHttpConfig(), action, params, null, null, BaseInfo.class);
    }

    /**
     * 注册
     *
     * @param mobile   //作为账号的有效手机号码
     * @param code     //接收到的短信验证码 6位随机数字
     * @param password //6-16数字组合的密码
     * @return
     * @throws TaskException
     */
    public RegisterEntity register(String mobile, String code, String password) throws TaskException {
        Setting action = newSetting("insertMemberInfo", "appapi/Memberpub/insertMemberInfo", "注册");
        Params params = new Params();
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?mobile=" + mobile + "&code=" + code + "&password=" + password);
        params.addParameter("mobile", mobile);
        params.addParameter("code", code);
        params.addParameter("password", password);
        return doPost(configHttpConfig(), action, null, params, null, RegisterEntity.class);
    }

    /**
     * 修改密码
     *
     * @param oldPsw 原密码
     * @param newPsw 老密码
     * @return
     * @throws TaskException
     */
    public BaseInfo changePsw(String oldPsw, String newPsw, String newSurePsw, String memberId) throws TaskException {
        Setting action = newSetting("changePassword", "appapi/Personalcenter/changePassword", "修改密码");
        Params params = new Params();
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?oldPsw=" + oldPsw + "&newPsw=" + newPsw);
        params.addParameter("oldPassword", oldPsw);
        params.addParameter("newPassword1", newPsw);
        params.addParameter("newPassword2", newSurePsw);
        params.addParameter("memberId", memberId);
        return doPost(configHttpConfig(), action, basicParams(params), null, null, BaseInfo.class);
    }


    /**
     * 登录
     *
     * @param mobile   //作为账号的有效手机号码
     * @param code     //接收到的短信验证码 6位随机数字
     * @param password //6-16数字组合的密码
     * @param type     //	登陆类型：0为账号密码1为动态验证码
     * @return
     * @throws TaskException
     */
    public RegisterEntity login(String mobile, String password, String type, String code) throws TaskException {
        Setting action = newSetting("sendLoginInfo", "appapi/Memberpub/sendLoginInfo", "登录");
        Params params = new Params();
        params.addParameter("mobile", mobile);
        if ("0".equals(type)) {
            params.addParameter("password", password);
        } else {
            params.addParameter("code", code);
        }
        params.addParameter("type", type);
        return doPost(configHttpConfig(), action, params, null, null, RegisterEntity.class);
    }

    /**
     * 景点门票页面获取门票分类信息
     *
     * @param theme_id //	主题id
     * @param page     //页码数
     * @return
     * @throws TaskException
     */
    public SearchBean getTicketData(String theme_id, String page) throws TaskException {
        Setting action = newSetting("getTicketInfo", "appapi/Spotticket/getTicketByThemeId", "景点门票页面获取门票分类信息");
        Params params = new Params();
        params.addParameter("theme_id", theme_id);
        params.addParameter("page", page);
        return doGet(action, basicParams(params), SearchBean.class);
    }


    /**
     * 门票列表页面获取大部分数据
     *
     * @param type //	type
     * @return
     * @throws TaskException
     */
    public TickMainBean getTicketInfo(String type) throws TaskException {
        Setting action = newSetting("getTicketInfo", "appapi/Ticketlist/getTicketInfo", "门票列表页面获取大部分数据");
        Params params = new Params();
        params.addParameter("type", type);
        return doGet(action, basicParams(params), TickMainBean.class);
    }


    /**
     * 获取首页上方数据
     *
     * @return
     * @throws TaskException
     */
    public MainBean getMainTop() throws TaskException {
        Setting action = newSetting("getMainTop", "appapi/Index/getShopTop", "获取app首页上方数据");
        return doGet(action, null, MainBean.class);
    }


    /**
     * 获取首页下发数据
     *
     * @param page //页码数
     * @param type //商品类型
     * @return
     * @throws TaskException
     */
    public ProductPartListBean getMainbottum(String type, String page) throws TaskException {
        Setting action = newSetting("getMainbottum", "appapi/Index/getShopBottom", "获取首页下发数据");
        Params params = new Params();
        params.addParameter("type", type);
        params.addParameter("page", page);
        return doGet(action, basicParams(params), ProductPartListBean.class);
    }


    /**
     * 根据经纬度获取周边
     *
     * @param longitude //页码数
     * @param latitude  //商品类型
     * @return
     * @throws TaskException
     */
    public String getCityAround(String longitude, String latitude) throws TaskException {
        Setting action = newSetting("getCityAround", "/appapi/SpotTicket/getCityAround", "通过经纬度获取景点门票总页面周边城市");
        Params params = new Params();
        params.addParameter("longitude", longitude);
        params.addParameter("latitude", latitude);
        return doGet(action, basicParams(params), String.class);
    }


    /**
     * 商品详情
     *
     * @param id         //所请求商品的id
     * @return
     * @throws TaskException
     */
    public DetailMainBean getGoodsDetail(String id) throws TaskException {
        Setting action = newSetting("getGoodsDetail", "appapi/Goods/getGoodsDetail", "获取商品详情");
        Params params = new Params();
        RegisterEntity info = SaveObjectUtils.getInstance(context).getObject(Constants.USER_INFO, RegisterEntity.class);
        if (info != null) {
            params.addParameter("check_sign", info.getCheck_sign());
            params.addParameter("session_id", info.getSession_id());
        }
        params.addParameter("shop_spot_id", id);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?session_id=" + info.getSession_id() + "&check_sign=" + info.getCheck_sign());
        return doPost(configHttpConfig(), action, params, null, null, DetailMainBean.class);
    }


    /**
     * TODO 重置密码接口
     *
     * @param phone
     * @param code
     * @param psw
     * @param pswSure
     * @return
     * @throws TaskException
     */
    public String resetCommit(String phone, String code, String psw, String pswSure) throws TaskException {
        Setting action = newSetting("insertMemberInfo", "/appapi/Memberpub/insertMemberInfo", "重置密码接口");
        Params params = new Params();
        params.addParameter("mobile", phone);
        params.addParameter("code", code);
        params.addParameter("password", psw);
        params.addParameter("password", pswSure);
        return doPost(configHttpConfig(), action, null, null, null, String.class);
    }


    /**
     * 景点主题
     *
     * @param type //景点分类
     * @return
     * @throws TaskException
     */
    public SpotTBean getSpotTheme(String type) throws TaskException {
        Setting action = newSetting("getSpotTheme", "appapi/Spotticket/getSpotTheme", "景点主题");
        Params params = new Params();
        params.addParameter("type", type);
        return doGet(action, basicParams(params), SpotTBean.class);
    }

    /**
     * 景点主题
     *
     * @param type //景点分类
     * @return
     * @throws TaskException
     */
    public GradeBean getSpotTheme1(String type) throws TaskException {
        Setting action = newSetting("getSpotTheme", "appapi/Spotticket/getSpotTheme", "景点主题");
        Params params = new Params();
        params.addParameter("type", type);
        return doGet(action, basicParams(params), GradeBean.class);
    }

    /**
     * 个人设置
     *
     * @param memberId //会员Id
     * @return
     * @throws TaskException
     */
    public RegisterEntity personalSetting(String memberId) throws TaskException {
        Setting action = newSetting("personalSetting", "appapi/Personalcenter/personalSetting", "个人设置");
        Params params = new Params();
        params.addParameter("memberId", memberId);
        return doGet(action, basicParams(params), RegisterEntity.class);
    }

    /**
     * 更改旧手机
     *
     * @param oldMobile //旧手机号码
     * @param code      //验证码
     * @return
     * @throws TaskException
     */
    public BaseInfo changeMobile(String oldMobile, String code) throws TaskException {
        Setting action = newSetting("personalSetting", "appapi/Personalcenter/changeMobile", "更改旧手机");
        Params params = new Params();
        params.addParameter("oldMobile", oldMobile);
        params.addParameter("code", code);
        return doGet(action, basicParams(params), BaseInfo.class);
    }

    /**
     * 设置新手机
     *
     * @param newMobile //新手机号码
     * @param code      //验证码
     * @param memberId  //会员Id
     * @return
     * @throws TaskException
     */
    public BaseInfo setMobile(String newMobile, String code, int memberId) throws TaskException {
        Setting action = newSetting("setMobile", "appapi/Personalcenter/setMobile", "设置新手机");
        Params params = new Params();
        params.addParameter("newMobile", newMobile);
        params.addParameter("code", code);
        params.addParameter("memberId", memberId + "");
        return doGet(action, basicParams(params), BaseInfo.class);
    }

    /**
     * 修改昵称
     *
     * @param nickName //昵称
     * @return
     * @throws TaskException
     */
    public BaseInfo changeNickName(String nickName, String memberId) throws TaskException {
        Setting action = newSetting("changeNickName", "appapi/Personalcenter/changeNickName", "修改昵称");
        Params params = new Params();
        params.addParameter("nickName", nickName);
        params.addParameter("memberId", memberId);
        return doPost(configHttpConfig(), action, params, null, null, BaseInfo.class);
    }

    /**
     * 修改密码
     *
     * @return
     * @throws TaskException
     */
    public BaseInfo changePassword(String oldPassword, String newPassword1, String newPassword2, String memberId) throws TaskException {
        Setting action = newSetting("changePassword", "appapi/Personalcenter/changePassword", "修改密码");
        Params params = new Params();
        params.addParameter("nickName", oldPassword);
        params.addParameter("newPassword1", newPassword1);
        params.addParameter("newPassword2", newPassword2);
        params.addParameter("memberId", memberId);
        return doPost(configHttpConfig(), action, params, null, null, BaseInfo.class);
    }

    /**
     * 修改邮箱
     *
     * @return
     * @throws TaskException
     */
    public BaseInfo changeEmail(String email, String mobile, String code, String memberId) throws TaskException {
        Setting action = newSetting("changeEmail", "appapi/Personalcenter/changeEmail", "修改邮箱");
        Params params = new Params();
        params.addParameter("email", email);
        params.addParameter("mobile", mobile);
        params.addParameter("code", code);
        params.addParameter("memberId", memberId);
        return doPost(configHttpConfig(), action, params, null, null, BaseInfo.class);
    }

    /**
     * 重置密码
     *
     * @return
     * @throws TaskException
     */
    public BaseInfo resetPassword(String mobile, String code, String password1, String password2, String memberId) throws TaskException {
        Setting action = newSetting("resetPassword", "appapi/Personalcenter/resetPassword", "重置密码");
        Params params = new Params();
        params.addParameter("mobile", mobile);
        params.addParameter("code", code);
        params.addParameter("password1", password1);
        params.addParameter("password2", password2);
        params.addParameter("memberId", memberId);
        return doPost(configHttpConfig(), action, params, null, null, BaseInfo.class);
    }


    /**
     * 查看全部订单
     *
     * @return
     * @throws TaskException
     */
    public AllOrderBean getAllOrder(String member_id) throws TaskException {
        Setting action = newSetting("getAllOrder", "appapi/Personalcenter/getAllOrder", "查看全部订单");
        Params params = new Params();
        params.addParameter("member_id", member_id);
        return doPost(configHttpConfig(), action, params, null, null, AllOrderBean.class);
    }

    /**
     * 订单生成
     *
     * @return
     * @throws TaskException
     */
    public OrderBean insertOrderInfo(String status, String use_date, String use_name, String use_card, String mobile,
                                     String code, String member_id, String ticket_id, String num) throws TaskException {
        Setting action = newSetting("insertOrderInfo", "appapi/Order1/insertOrderInfo", "订单生成");
        Params params = new Params();
        params.addParameter("status", status);
        params.addParameter("use_date", use_date);
        params.addParameter("use_name", use_name);
        params.addParameter("use_card", use_card);
        params.addParameter("mobile", mobile);
        params.addParameter("code", code);
        params.addParameter("member_id", member_id);
        params.addParameter("ticket_id", ticket_id);
        params.addParameter("num", num);
        return doPost(configHttpConfig(), action, params, null, null, OrderBean.class);
    }
    /**
     * 订单生成
     *
     * @return
     * @throws TaskException
     */
    public OrderBean insertOrderInfo(String status, String use_date, String use_name, String use_card, String mobile,
                                       String code, String ticket_id, String num) throws TaskException {
        Setting action = newSetting("insertOrderInfo", "appapi/Order1/insertOrderInfo", "订单生成");
        Params params = new Params();
        params.addParameter("status", status);
        params.addParameter("use_date", use_date);
        params.addParameter("use_name", use_name);
        params.addParameter("use_card", use_card);
        params.addParameter("mobile", mobile);
        params.addParameter("code", code);
        params.addParameter("ticket_id", ticket_id);
        params.addParameter("num", num);
        return doPost(configHttpConfig(), action, params, null, null, OrderBean.class);
    }


    /**
     * 通过筛选条件查询门票
     *
     * @return
     * @throws TaskException
     */
    public SearchBean getSpotByCondition(String theme_id, String grade, String order, String page) throws TaskException {
        Setting action = newSetting("getSpotByCondition", "appapi/Spotticket/getSpotByCondition", "通过筛选条件查询门票");
        Params params = new Params();
        if(!TextUtils.isEmpty(theme_id)){
            params.addParameter("theme_id", theme_id);
        }
        if(!TextUtils.isEmpty(grade)){
            params.addParameter("grade", grade);
        }
        if(!TextUtils.isEmpty(order)){
            params.addParameter("order", order);
        }
        params.addParameter("page", page);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?theme_id=" + theme_id + "&grade=" + grade+ "&order=" + order);
        return doGet(configHttpConfig(), action, params,SearchBean.class);
    }



    @Override
    protected IHttpUtility configHttpUtility() {

        return new DTODataParseHttp(context);
    }

    @Override
    protected HttpConfig configHttpConfig() {
        HttpConfig config = new HttpConfig();
        // 服务端请求地址
        config.baseUrl = BuildConfig.BASE_URL;
//        http://192.168.10.115:9100
        config.addHeader("Content-Type", "application/json");
        return config;
    }

    // 服务端参数基础封装
    private Params basicParams(Params params) {
        if (params == null) {
            params = new Params();
        }
//        params.addParameter("version", BuildConfig.VERSION_NAME);
        return params;
    }

}
