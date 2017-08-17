package com.zhl.huiqu.sdk;

import android.app.Activity;

import com.zhl.huiqu.BuildConfig;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.main.ProductPartListBean;
import com.zhl.huiqu.main.bean.DetailBean;
import com.zhl.huiqu.main.bean.DetailMainBean;
import com.zhl.huiqu.main.bean.MainBean;
import com.zhl.huiqu.main.bean.MainTopInfo;
import com.zhl.huiqu.main.ticket.TickListInfo;
import com.zhl.huiqu.sdk.http.DTODataParseHttp;
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
    public String getCode(String mobile) throws TaskException {
        Setting action = newSetting("getCheckCode", "appapi/Memberpub/getCheckCode", "验证码");
        Params params = new Params();
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?mobile=" + mobile);
        params.addParameter("mobile", mobile);
        // 这个接口，是将form表单数据，按照json格式走post协议，请使用requestObject这个参数。
        return doPost(configHttpConfig(), action, params, null, null, String.class);

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
        return doPost(configHttpConfig(), action,null, params, null, RegisterEntity.class);
    }

    /**
     * 修改密码
     *
     * @param oldPsw 原密码
     * @param newPsw 老密码
     * @return
     * @throws TaskException
     */
    public String changePsw(String oldPsw, String newPsw) throws TaskException {
        Setting action = newSetting("insertMemberInfo", "appapi/Memberpub/insertMemberInfo", "修改密码");
        Params params = new Params();
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?oldPsw=" + oldPsw + "&newPsw=" + newPsw);
        params.addParameter("oldPsw", oldPsw);
        params.addParameter("newPsw", newPsw);
        return doPost(configHttpConfig(), action, basicParams(params), null, null, String.class);
    }


    /**
     * 登录
     *
     * @param mobile   //作为账号的有效手机号码
     * @param code     //接收到的短信验证码 6位随机数字
     * @param password //6-16数字组合的密码
    * @param type //	登陆类型：0为账号密码1为动态验证码
     * @return
     * @throws TaskException
     */
    public String login(String mobile, String password, String type,String code) throws TaskException {
        Setting action = newSetting("sendLoginInfo", "appapi/Memberpub/sendLoginInfo", "登录");
        Params params = new Params();
        params.addParameter("mobile", mobile);
        if("0".equals(type)){
            params.addParameter("password", password);
        }else {
            params.addParameter("code", code);
        }
        params.addParameter("type", type);
        return doPost(configHttpConfig(), action, params, null, null, String.class);
    }

    /**
     * 获取景点门票页面门票数据
     *
     * @param theme_id   //	主题id
     * @param page     //页码数
     * @return
     * @throws TaskException
     */
    public TickListInfo getTicketData(String theme_id, String page) throws TaskException {
        Setting action = newSetting("getTicketInfo", "/appapi/Spotticket/getTicketInfo", "获取景点门票页面门票数据");
        Params params = new Params();
        params.addParameter("theme_id", theme_id);
//        params.addParameter("page", page);
        return doGet(action, basicParams(params), TickListInfo.class);
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
     *@param page     //页码数
     *@param type     //商品类型
     * @return
     * @throws TaskException
     */
    public ProductPartListBean getMainbottum(String type, String page) throws TaskException {
        Setting action = newSetting("getMainbottum", "/appapi/Index/getShopBottom", "获取首页下发数据");
        Params params = new Params();
        params.addParameter("type", type);
        params.addParameter("page", page);
        return doGet(action, basicParams(params), ProductPartListBean.class);
    }


    /**
     * 根据经纬度获取周边
     *@param longitude     //页码数
     *@param latitude     //商品类型
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
     *@param id     //所请求商品的id
     *@param check_sign     //登陆时收到的返回签名
     *@param session_id     //登陆时收到的返回签名的sesionid
     * @return
     * @throws TaskException
     */
    public DetailMainBean getGoodsDetail(String id, String check_sign, String session_id) throws TaskException {
        Setting action = newSetting("getGoodsDetail", "appapi/Goods/getGoodsDetail", "获取商品详情");
        Params params = new Params();
        params.addParameter("id", id);
//        params.addParameter("check_sign", check_sign);
//        params.addParameter("session_id", session_id);
        return doPost(configHttpConfig(), action, params, null, null, DetailMainBean.class);
    }


    /**
     * TODO 重置密码接口
     * @param phone
     * @param code
     * @param psw
     * @param pswSure
     * @return
     * @throws TaskException
     */
    public String resetCommit(String phone, String code, String psw,String pswSure) throws TaskException {
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
     *@param type     //景点分类
     * @return
     * @throws TaskException
     */
    public String getSpotTheme(String type) throws TaskException {
        Setting action = newSetting("getGoodsDetail", "appapi/Spotticket/getSpotTheme", "获取商品详情");
        Params params = new Params();
        params.addParameter("type",type);
//        params.addParameter("check_sign", check_sign);
//        params.addParameter("session_id", session_id);
        return doGet(action, basicParams(params), String.class);
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
