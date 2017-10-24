package com.zhl.huiqu.sdk;

import android.app.Activity;
import android.text.TextUtils;

import com.zhl.huiqu.BuildConfig;
import com.zhl.huiqu.R;
import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.bean.WeiChatBean;
import com.zhl.huiqu.login.entity.RegisterEntity;
import com.zhl.huiqu.login.entity.RegisterInfo;
import com.zhl.huiqu.main.ProductPartListBean;
import com.zhl.huiqu.main.bean.DetailBean;
import com.zhl.huiqu.main.bean.DetailMainBean;
import com.zhl.huiqu.main.bean.GradeBean;
import com.zhl.huiqu.main.bean.MainBean;
import com.zhl.huiqu.main.bean.SearchBean;
import com.zhl.huiqu.main.bean.SearchEntity;
import com.zhl.huiqu.main.bean.SearchTickEntity;
import com.zhl.huiqu.main.team.bean.FilterBase;
import com.zhl.huiqu.main.team.bean.GoalBean;
import com.zhl.huiqu.main.team.bean.GroupListBase;
import com.zhl.huiqu.main.team.bean.LikeEntity;
import com.zhl.huiqu.main.team.bean.SearBase;
import com.zhl.huiqu.main.team.bean.TeamBase;
import com.zhl.huiqu.main.team.bean.TeamDetailBean;
import com.zhl.huiqu.main.team.bean.TeamDetailEntity;
import com.zhl.huiqu.main.team.bean.TeamSearchInfo;
import com.zhl.huiqu.main.team.bean.TeamPriceEntity;
import com.zhl.huiqu.main.team.bean.TeamTopMain;
import com.zhl.huiqu.main.ticket.CityInfo;
import com.zhl.huiqu.main.ticket.SpotTBean;
import com.zhl.huiqu.main.ticket.TickBase;
import com.zhl.huiqu.main.ticket.TickMainBean;
import com.zhl.huiqu.personal.bean.AllOrderBean;
import com.zhl.huiqu.personal.bean.CollectBean;
import com.zhl.huiqu.personal.bean.OrderBean;
import com.zhl.huiqu.personal.bean.OrderDetailBean;
import com.zhl.huiqu.personal.bean.OrderDetailEntity;
import com.zhl.huiqu.personal.bean.OrderEntity;
import com.zhl.huiqu.personal.bean.UrLikeBean;
import com.zhl.huiqu.personal.bean.UrLikeEntity;
import com.zhl.huiqu.personal.bean.UsePersonBean;
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
        Setting action = newSetting("getMainTop", "appapi/Index/getShopIndex", "获取app首页上方数据");
        return doGet(action, null, MainBean.class);
    }

    /**
     * 获取跟团游上方数据
     *
     * @return
     * @throws TaskException
     */
    public TeamTopMain getTeamTop() throws TaskException {
        Setting action = newSetting("getTeamIndexTop", "appapi/Team/getTeamIndexTop", "获取跟团游首页上方数据");
        return doPost(configHttpConfig(), action, null, null, null, TeamTopMain.class);
    }


    /**
     * 获取跟团游列表数据
     *
     * @return
     * @throws TaskException
     */
    public TeamBase getListTop(String type, String page) throws TaskException {
        Setting action = newSetting("getTeamIndexBottom", "appapi/Team/getTeamIndexBottom", "获取跟团游首页列表");
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?type=" + type + "page=" + page);
        Params params = new Params();
        params.addParameter("type", type);
        params.addParameter("page", page);
        return doPost(configHttpConfig(), action, params, null, null, TeamBase.class);
    }


    /**
     * 筛选条件
     *
     * @return
     * @throws TaskException
     */
    public FilterBase getCondition(String type) throws TaskException {
        Setting action = newSetting("getFilter", "appapi/Common/getCondition", "获取筛选条件");
        Params params = new Params();
        params.addParameter("type", type);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue());
        return doPost(configHttpConfig(), action, params, null, null, FilterBase.class);
    }

    /**
     * 筛选条件
     *
     * @return
     * @throws TaskException
     */
    public TickBase getTickTheme(String type) throws TaskException {
        Setting action = newSetting("getFilter", "appapi/Common/getCondition", "获取筛选条件");
        Params params = new Params();
        params.addParameter("type", type);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue());
        return doPost(configHttpConfig(), action, params, null, null, TickBase.class);
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
     * 跟团游详情页面
     *
     * @param productId //	跟团游id
     * @return
     * @throws TaskException
     */
    public TeamDetailEntity obtainGroupDetail(String productId, String deviceId) throws TaskException {
        Setting action = newSetting("getGoodsDetail", "appapi/Team/getTeamDetail", "跟团游详情页面");
        Params params = new Params();
        params.addParameter("productId", productId);
        params.addParameter("deviceNum", deviceId);

        return doGet(action, basicParams(params), TeamDetailEntity.class);
    }

    /**
     * 跟团游详情页面底部数据
     *
     * @return
     * @throws TaskException
     */
    public LikeEntity obtainLike() throws TaskException {
        Setting action = newSetting("getLike", "Api/Group/getLike", "跟团游详情页面底部数据");
        return doGet(action, null, LikeEntity.class);
    }

    /**
     * 跟团游产品价格日历
     *
     * @param productId //	跟团游id
     * @return
     * @throws TaskException
     */
    public TeamPriceEntity obtainProductPrice(String productId) throws TaskException {
        Setting action = newSetting("obtainProductPrice", "appapi/Team/getProductPrice", "跟团游产品价格日历");
        Params params = new Params();
        params.addParameter("productId", productId);

        return doGet(action, basicParams(params), TeamPriceEntity.class);
    }

    /**
     * 根据经纬度获取周边
     *
     * @param longitude //页码数
     * @param latitude  //商品类型
     * @return
     * @throws TaskException
     */
    public CityInfo getCityAround(String longitude, String latitude) throws TaskException {
        Setting action = newSetting("getCityAround", "appapi/Ticketlist/getCityAround", "门票列表页面获取周边城市");
        Params params = new Params();
        params.addParameter("longitude", longitude);
        params.addParameter("latitude", latitude);
        return doGet(action, basicParams(params), CityInfo.class);
    }


    /**
     * 商品详情
     *
     * @param id //所请求商品的id
     * @return
     * @throws TaskException
     */
    public DetailMainBean getGoodsDetail(String id, String device_num) throws TaskException {
        Setting action = newSetting("getGoodsDetail", "appapi/Goods/getGoodsDetail", "获取商品详情");
        Params params = new Params();
        RegisterEntity info = SaveObjectUtils.getInstance(context).getObject(Constants.USER_INFO, RegisterEntity.class);
        if (info != null) {
            params.addParameter("check_sign", info.getCheck_sign());
            params.addParameter("session_id", info.getSession_id());
            params.addParameter("member_id", info.getBody().getMember_id());
        }
        params.addParameter("shop_spot_id", id);
        params.addParameter("device_num", device_num);
//        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?session_id=" + info.getSession_id() + "&check_sign=" + info.getCheck_sign());
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
     * 获取热门搜索
     *
     * @return
     * @throws TaskException
     */
    public SearchEntity getHotSearch() throws TaskException {
        Setting action = newSetting("getHotSearch", "appapi/search/getHotSearch", "获取热门搜索");
        return doGet(action, null, SearchEntity.class);
    }

    /**
     * 获取搜索历史
     *
     * @param device_num //手机设备号
     * @return
     * @throws TaskException
     */
    public SearchEntity getSearchHistory(String device_num) throws TaskException {
        Setting action = newSetting("getSearchHistory", "appapi/Search/getSearchHistory", "获取搜索历史");
        Params params = new Params();
        params.addParameter("device_num", device_num);
        return doGet(action, basicParams(params), SearchEntity.class);
    }

    /**
     * 清空搜索历史
     *
     * @return
     * @throws TaskException
     */
    public BaseInfo clearSearchHistory(String device_num) throws TaskException {
        Setting action = newSetting("clearSearchHistory", "appapi/search/clearSearchHistory", "清空搜索历史");
        Params params = new Params();
        params.addParameter("device_num", device_num);
        return doGet(action, basicParams(params), BaseInfo.class);
    }

    /**
     * 通过搜索条件获取景点
     *
     * @return
     * @throws TaskException
     */
    public SearchTickEntity getSearchInfoByCondition(String condition, String device_num, String page) throws TaskException {
        Setting action = newSetting("getSearchInfoByCondition", "appapi/Search/getSearchInfoByCondition", "通过搜索条件获取景点");
        Params params = new Params();
        params.addParameter("condition", condition);
        params.addParameter("device_num", device_num);
        params.addParameter("page", page);
        return doGet(action, basicParams(params), SearchTickEntity.class);
    }


    /**
     * 获取热门搜索(跟团游)
     *
     * @return
     * @throws TaskException
     */
    public SearBase getTeamgetHotSearch() throws TaskException {
        Setting action = newSetting("getHotSearch", "appapi/Team/getHotSearch", "获取热门搜索");
        return doGet(action, null, SearBase.class);
    }

    /**
     * 获取搜索历史(跟团游)
     *
     * @param device_num //手机设备号
     * @return
     * @throws TaskException
     */
    public SearBase getTeamSearchHistory(String device_num) throws TaskException {
        Setting action = newSetting("getSearchHistory", "appapi/Team/getSearchHistory", "获取搜索历史");
        Params params = new Params();
        params.addParameter("deviceNum", device_num);
        return doGet(action, basicParams(params), SearBase.class);
    }


    /**
     * 清空搜索历史(跟团游)
     *
     * @return
     * @throws TaskException
     */
    public BaseInfo clearTeamSearchHistory(String device_num) throws TaskException {
        Setting action = newSetting("clearSearchHistory", "appapi/Team/clearSearchHistory", "清空搜索历史");
        Params params = new Params();
        params.addParameter("deviceNum", device_num);
        return doGet(action, basicParams(params), BaseInfo.class);
    }

    /**
     * 通过搜索条件获取跟团游
     *
     * @return
     * @throws TaskException
     */
    public TeamSearchInfo getSearchTeamByCondition(String condition, String device_num, String page) throws TaskException {
        Setting action = newSetting("getSearchInfoByCondition", "appapi/Team/getSearchInfoByCondition", "通过搜索条件获取景点");
        Params params = new Params();
        params.addParameter("condition", condition);
        params.addParameter("deviceNum", device_num);
        params.addParameter("page", page);
        return doGet(action, basicParams(params), TeamSearchInfo.class);
    }

    /**
     * 获取浏览历史
     *
     * @return
     * @throws TaskException
     */
    public SearchBean getbrowserhistory(String device_num, String page) throws TaskException {
        Setting action = newSetting("getbrowserhistory", "appapi/personalcenter/getbrowserhistory", "获取浏览历史");
        Params params = new Params();
        params.addParameter("device_num", device_num);
        params.addParameter("page", page);
        return doGet(action, basicParams(params), SearchBean.class);
    }

    /**
     * 清空浏览历史
     *
     * @return
     * @throws TaskException
     */
    public BaseInfo clearbrowserhistory(String device_num) throws TaskException {
        Setting action = newSetting("clearbrowserhistory", "appapi/personalcenter/clearbrowserhistory", "清空浏览历史");
        Params params = new Params();
        params.addParameter("device_num", device_num);
        return doGet(action, basicParams(params), BaseInfo.class);
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
     * 设置新手机
     *
     * @return
     * @throws TaskException
     */
    public BaseInfo setMobile(String newMobile, String code, String memberId) throws TaskException {
        Setting action = newSetting("setMobile", "appapi/Personalcenter/setMobile", "设置新手机");
        Params params = new Params();
        params.addParameter("newMobile", newMobile);
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
    public AllOrderBean getAllOrder(String member_id, int page, int status) throws TaskException {
        Setting action = newSetting("getAllOrder", "appapi/Personalcenter/getOrder", "查看全部订单");
        Params params = new Params();
        params.addParameter("member_id", member_id);
        params.addParameter("page", page + "");
        params.addParameter("status", status + "");

        return doPost(configHttpConfig(), action, params, null, null, AllOrderBean.class);
    }

    /**
     * 取消订单
     *
     * @return
     * @throws TaskException
     */
    public BaseInfo cancelOrder(String order_id) throws TaskException {
        Setting action = newSetting("cancelOrder", "appapi/order1/cancelorder", "取消订单");
        Params params = new Params();
        params.addParameter("order_id", order_id);
        return doPost(configHttpConfig(), action, params, null, null, BaseInfo.class);
    }

    /**
     * 删除订单
     *
     * @return
     * @throws TaskException
     */
    public BaseInfo deleteOrder(String order_id) throws TaskException {
        Setting action = newSetting("deleteOrder", "appapi/personalcenter/deleteorder", "删除订单");
        Params params = new Params();
        params.addParameter("order_id", order_id);
        return doPost(configHttpConfig(), action, params, null, null, BaseInfo.class);
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
        if (!TextUtils.isEmpty(theme_id)) {
            params.addParameter("theme_id", theme_id);
        }
        if (!TextUtils.isEmpty(grade)) {
            params.addParameter("grade", grade);
        }
        if (!TextUtils.isEmpty(order)) {
            params.addParameter("order", order);
        }
        params.addParameter("page", page);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?theme_id=" + theme_id + "&grade=" + grade + "&order=" + order);
        return doGet(configHttpConfig(), action, params, SearchBean.class);
    }


    /**
     * 调用微信下单接口
     *
     * @return
     * @throws TaskException
     */
    public WeiChatBean getPrePayOrder(String body, String out_trade_no, String total_free) throws TaskException {
        Setting action = newSetting("getPrePayOrder", "appapi/Wx/getPrePayOrder", "调用微信下单接口");
        Params params = new Params();
        params.addParameter("body", body);
        params.addParameter("out_trade_no", out_trade_no);
        params.addParameter("app_name", context.getResources().getString(R.string.app_name));
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?body=" + body + "&out_trade_no=" + out_trade_no);
        return doGet(configHttpConfig(), action, params, WeiChatBean.class);
    }

    /**
     * 调用微信查询订单
     *
     * @return
     * @throws TaskException
     */
    public BaseInfo getQueryOrder(String out_trade_no) throws TaskException {
        Setting action = newSetting("getQueryOrder", "appapi/Wx/getQueryOrder", "调用微信查询订单");
        Params params = new Params();
        params.addParameter("out_trade_no", out_trade_no);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "&out_trade_no=" + out_trade_no);
        return doGet(configHttpConfig(), action, params, BaseInfo.class);
    }

    /**
     * 调用支付宝下单接口
     *
     * @return
     * @throws TaskException
     */
    public String getAliPay(String order_sn) throws TaskException {
        Setting action = newSetting("public", "tp5/public", "调用支付宝下单接口");
        Params params = new Params();
        params.addParameter("order_sn", order_sn);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?order_sn=" + order_sn);
        return doGet(configHttpConfig(), action, params, String.class);
    }


    /**
     * 获取订单详情
     *
     * @return
     * @throws TaskException
     */
    public OrderDetailBean getOrderinfo(String member_id, String order_id) throws TaskException {
        Setting action = newSetting("getOrderinfo", "appapi/Personalcenter/getOrderInfo", "获取订单详情");
        Params params = new Params();
        params.addParameter("member_id", member_id);
        params.addParameter("order_id", order_id);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "&member_id=" + member_id + "&order_id=" + order_id);
        return doGet(configHttpConfig(), action, params, OrderDetailBean.class);
    }


    /**
     * 收藏
     *
     * @return
     * @throws TaskException
     */
    public String getCollect(String member_id, String shop_spot_id) throws TaskException {
        Setting action = newSetting("getOrderinfo", "appapi/Goods/collect", "获取订单详情");
        Params params = new Params();
        params.addParameter("member_id", member_id);
        params.addParameter("shop_spot_id", shop_spot_id);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?member_id=" + member_id + "&shop_spot_id=" + shop_spot_id);
        return doGet(configHttpConfig(), action, params, String.class);
    }

    /**
     * 查看收藏商品
     *
     * @return
     * @throws TaskException
     */
    public CollectBean getCollectList(String member_id) throws TaskException {
        Setting action = newSetting("getCollect", "appapi/Personalcenter/getCollect", "查看收藏商品");
        Params params = new Params();
        params.addParameter("member_id", member_id);
//        params.addParameter("page", page+"");
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?member_id=" + member_id);
        return doGet(configHttpConfig(), action, params, CollectBean.class);
    }

    /**
     * 删除收藏
     *
     * @return
     * @throws TaskException
     */
    public BaseInfo deleteCollect(String member_id, String shop_spot_id) throws TaskException {
        Setting action = newSetting("deleteCollect", "appapi/Personalcenter/deleteCollect", "删除收藏");
        Params params = new Params();
        params.addParameter("member_id", member_id);
        params.addParameter("shop_spot_id", shop_spot_id);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?member_id=" + member_id + "&shop_spot_id:" + shop_spot_id);
        return doGet(configHttpConfig(), action, params, BaseInfo.class);
    }

    /**
     * 获取猜你喜欢
     *
     * @return
     * @throws TaskException
     */
    public UrLikeBean getYouLike(String device_num) throws TaskException {
        Setting action = newSetting("getYouLike", "appapi/personalcenter/getYouLike", "获取猜你喜欢");
        Params params = new Params();
        params.addParameter("device_num", device_num);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?device_num=" + device_num);
        return doGet(configHttpConfig(), action, params, UrLikeBean.class);
    }

    /**
     * 获取常用联系人信息
     *
     * @return
     * @throws TaskException
     */
    public UsePersonBean getContactInfo(String member_id) throws TaskException {
        Setting action = newSetting("getContactInfo", "appapi/Personalcenter/getContactInfo", "获取常用联系人信息");
        Params params = new Params();
        params.addParameter("member_id", member_id);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?member_id=" + member_id);
        return doGet(configHttpConfig(), action, params, UsePersonBean.class);
    }

    /**
     * 添加联系人信息
     *
     * @return
     * @throws TaskException
     */
    public String addContact(String member_id, String name, String certificate, String mobile, String sex, String email, String type, String contact_id) throws TaskException {
        Setting action = newSetting("addContact", "appapi/Personalcenter/addContact", "获取常用联系人信息");
        Params params = new Params();
        params.addParameter("member_id", member_id);
        params.addParameter("name", name);
        params.addParameter("certificate", certificate);
        params.addParameter("mobile", mobile);
        params.addParameter("type", type);
        params.addParameter("sex", sex);
        if ("1".equals(type)) {
            params.addParameter("contact_id", contact_id);
        }
        if (TextUtils.isEmpty(email)) {
            params.addParameter("email", email);
        }
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?member_id=" + member_id);
        return doGet(configHttpConfig(), action, params, String.class);
    }


    /**
     * 获取常用联系人信息
     *
     * @return
     * @throws TaskException
     */
    public String getDeleteInfo(String member_id, String contact_id) throws TaskException {
        Setting action = newSetting("getContactInfo", "appapi/Personalcenter/delContact", "获取常用联系人信息");
        Params params = new Params();
        params.addParameter("member_id", member_id);
        params.addParameter("contact_id", contact_id);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?member_id=" + member_id);
        return doGet(configHttpConfig(), action, params, String.class);
    }


    /**
     * @return
     * @throws TaskException
     */
    public GoalBean getDestination(String desProvinceId) throws TaskException {
        Setting action = newSetting("getDestination", "appapi/Team/getDestination", "获取常用联系人信息");
        Params params = new Params();
        if (!TextUtils.isEmpty(desProvinceId)) {
            params.addParameter("desProvinceId", desProvinceId);
        }
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?desProvinceId=" + desProvinceId);
        return doGet(configHttpConfig(), action, params, GoalBean.class);
    }


    /**
     * @return
     * @throws TaskException 获取门票游列表页数据
     */
    public ProductPartListBean getTickByCondition(String type, String themeId, String gradeId, String desCityId, String price, String sales, String page) throws TaskException {
        Setting action = newSetting("getProductByCondition", "appapi/Common/getProductByCondition", "获取跟团游列表页数据");
        Params params = new Params();
        params.addParameter("type", type);
        if (!TextUtils.isEmpty(themeId)) {
            params.addParameter("themeId", themeId);
        }
        if (!TextUtils.isEmpty(gradeId)) {
            params.addParameter("gradeId", gradeId);
        }
        if (!TextUtils.isEmpty(desCityId)) {
            params.addParameter("desCityId", desCityId);
        }
        if (!TextUtils.isEmpty(price)) {
            params.addParameter("price", price);
        }
        if (!TextUtils.isEmpty(sales)) {
            params.addParameter("sales", sales);
        }
        params.addParameter("page", page);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?themeId=" + themeId + "gradeId=" + gradeId + "desCityId=" + desCityId + "price=" + price + "sales=" + sales + "page=" + page);
        return doPost(configHttpConfig(), action, params, null, null, ProductPartListBean.class);
    }

    /**
     * @return
     * @throws TaskException 获取跟团游列表页数据
     */
    public GroupListBase getProductByCondition(String type, String themeId, String gradeId, String desCityId, String price, String sales, String page) throws TaskException {
        Setting action = newSetting("getProductByCondition", "appapi/Common/getProductByCondition", "获取跟团游列表页数据");
        Params params = new Params();
        params.addParameter("type", type);
        if (!TextUtils.isEmpty(themeId)) {
            params.addParameter("themeId", themeId);
        }
        if (!TextUtils.isEmpty(gradeId)) {
            params.addParameter("gradeId", gradeId);
        }
        if (!TextUtils.isEmpty(desCityId)) {
            params.addParameter("desCityId", desCityId);
        }
        if (!TextUtils.isEmpty(price)) {
            params.addParameter("price", price);
        }
        if (!TextUtils.isEmpty(sales)) {
            params.addParameter("sales", sales);
        }
        params.addParameter("page", page);
        TLog.log("tttt", "--url:" + configHttpConfig().baseUrl + action.getValue() + "?themeId=" + themeId + "gradeId=" + gradeId + "desCityId=" + desCityId + "price=" + price + "sales=" + sales + "page=" + page);
        return doPost(configHttpConfig(), action, params, null, null, GroupListBase.class);
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
