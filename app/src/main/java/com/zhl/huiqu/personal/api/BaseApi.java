package com.zhl.huiqu.personal.api;

import com.zhl.huiqu.base.BaseInfo;
import com.zhl.huiqu.main.ticket.TickInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface BaseApi {
    @FormUrlEncoded
    @POST("appapi/personalcenter/getbrowserhistory")
    Call<BaseModel> getbrowserhistory(
            @Field("device_num") String device_num,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("appapi/personalcenter/clearbrowserhistory")
    Call<BaseInfo> clearbrowserhistory(
            @Field("device_num") String device_num
    );
}
