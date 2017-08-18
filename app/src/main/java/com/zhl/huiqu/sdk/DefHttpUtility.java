package com.zhl.huiqu.sdk;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.zhl.huiqu.utils.TLog;

import org.aisen.android.common.context.GlobalContext;
import org.aisen.android.common.setting.Setting;
import org.aisen.android.common.setting.SettingUtility;
import org.aisen.android.common.utils.Logger;
import org.aisen.android.common.utils.SystemUtils;
import org.aisen.android.network.biz.ABizLogic;
import org.aisen.android.network.http.HttpConfig;
import org.aisen.android.network.http.IHttpUtility;
import org.aisen.android.network.http.OnFileProgress;
import org.aisen.android.network.http.Params;
import org.aisen.android.network.http.ParamsUtil;
import org.aisen.android.network.task.TaskException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by lyj on 2017/8/18.
 */

public class DefHttpUtility implements IHttpUtility {

    public DefHttpUtility() {
    }

    static String getTag(Setting action, String append) {
        return ABizLogic.getTag(action, append);
    }

    public <T> T doGet(HttpConfig config, Setting action, Params urlParams, Class<T> responseCls) throws TaskException {
        Request.Builder builder = this.createRequestBuilder(config, action, urlParams, "Get");
        Request request = builder.build();
        return this.executeRequest(request, responseCls, action, "Get");
    }

    public <T> T doPost(HttpConfig config, Setting action, Params urlParams, Params bodyParams, Object requestObj, Class<T> responseCls) throws TaskException {
        Request.Builder builder = this.createRequestBuilder(config, action, urlParams, "Post");
        String requestBodyStr;
        if(bodyParams != null) {
            requestBodyStr = ParamsUtil.encodeToURLParams(bodyParams);
            Logger.d(getTag(action, "Post"), requestBodyStr);
            builder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), requestBodyStr));
        } else if(requestObj != null) {
            if(requestObj instanceof String) {
                requestBodyStr = requestObj + "";
            } else {
                requestBodyStr = JSON.toJSONString(requestObj);
            }

            Logger.d(getTag(action, "Post"), requestBodyStr);
            builder.post(RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), requestBodyStr));
        }

        return this.executeRequest(builder.build(), responseCls, action, "Post");
    }

    public <T> T doPostFiles(HttpConfig config, Setting action, Params urlParams, Params bodyParams, MultipartFile[] files, Class<T> responseCls) throws TaskException {
        String method = "doPostFiles";
        Request.Builder builder = this.createRequestBuilder(config, action, urlParams, method);
        MultipartBuilder multipartBuilder = new MultipartBuilder();
        multipartBuilder.type(MultipartBuilder.FORM);
        if(bodyParams != null && bodyParams.getKeys().size() > 0) {
            Iterator requestBody = bodyParams.getKeys().iterator();

            while(requestBody.hasNext()) {
                String key = (String)requestBody.next();
                String value = bodyParams.getParameter(key);
                multipartBuilder.addFormDataPart(key, value);
                Logger.d(getTag(action, method), "BodyParam[%s, %s]", new Object[]{key, value});
            }
        }

        if(files != null && files.length > 0) {
            MultipartFile[] var14 = files;
            int var16 = files.length;

            for(int var17 = 0; var17 < var16; ++var17) {
                MultipartFile file = var14[var17];
                if(file.getBytes() != null) {
                    multipartBuilder.addFormDataPart(file.getKey(), file.getKey(), createRequestBody(file));
                    Logger.d(getTag(action, method), "Multipart bytes, length = " + file.getBytes().length);
                } else if(file.getFile() != null) {
                    multipartBuilder.addFormDataPart(file.getKey(), file.getFile().getName(), createRequestBody(file));
                    Logger.d(getTag(action, method), "Multipart file, name = %s, path = %s", new Object[]{file.getFile().getName(), file.getFile().getAbsolutePath()});
                }
            }
        }

        RequestBody var15 = multipartBuilder.build();
        builder.post(var15);
        return this.executeRequest(builder.build(), responseCls, action, method);
    }

    private Request.Builder createRequestBuilder(HttpConfig config, Setting action, Params urlParams, String method) throws TaskException {
        if(GlobalContext.getInstance() != null && SystemUtils.getNetworkType(GlobalContext.getInstance()) == SystemUtils.NetWorkType.none) {
            Logger.w(getTag(action, method), "没有网络连接");
            throw new TaskException(TaskException.TaskError.noneNetwork.toString());
        } else {
            String url = (config.baseUrl + action.getValue() + (urlParams == null?"":"?" + ParamsUtil.encodeToURLParams(urlParams))).replaceAll(" ", "");
            Logger.d(getTag(action, method), url);
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            TLog.log("dddd","Cookie = "+config.cookie);
            if(!TextUtils.isEmpty(config.cookie)) {
                builder.header("Cookie", config.cookie);
                Logger.d(getTag(action, method), "Cookie = " + config.cookie);
                TLog.log("dddd","Cookie111= "+config.cookie);
            }

            if(config.headerMap.size() > 0) {
                Set keySet = config.headerMap.keySet();
                Iterator var8 = keySet.iterator();

                while(var8.hasNext()) {
                    String key = (String)var8.next();
                    builder.addHeader(key, (String)config.headerMap.get(key));
                    Logger.d(getTag(action, method), "Header[%s, %s]", new Object[]{key, config.headerMap.get(key)});
                }
            }

            return builder;
        }
    }

    private <T> T executeRequest(Request request, Class<T> responseCls, Setting action, String method) throws TaskException {
        try {
            if(SettingUtility.getPermanentSettingAsInt("http_delay") > 0) {
                Thread.sleep((long)SettingUtility.getPermanentSettingAsInt("http_delay"));
            }
        } catch (Throwable var11) {
            ;
        }

        try {
            Response e = this.getOkHttpClient().newCall(request).execute();
            Logger.w(getTag(action, method), "Http-code = %d", new Object[]{Integer.valueOf(e.code())});
            String responseStr;
            if(e.code() != 200 && e.code() != 206) {
                responseStr = e.body().string();
                if(Logger.DEBUG) {
                    Logger.w(getTag(action, method), responseStr);
                }

                TaskException.checkResponse(responseStr);
                throw new TaskException(TaskException.TaskError.timeout.toString());
            } else {
                Request.Builder result = request.newBuilder();
                CookieHandler cookieHandler = this.getOkHttpClient().getCookieHandler();
                if (cookieHandler != null) {
                    // Capture the request headers added so far so that they can be offered to the CookieHandler.
                    // This is mostly to stay close to the RI; it is unlikely any of the headers above would
                    // affect cookie choice besides "Host".
                    Map<String, List<String>> headers = OkHeaders.toMultimap(result.build().headers(), null);
                    Map<String, List<String>> cookies = cookieHandler.get(request.uri(), headers);
                    OkHeaders.addCookies(result, cookies);
                }
                responseStr = e.body().string();
                Logger.v(getTag(action, method), "Response = %s", new Object[]{responseStr});
                return this.parseResponse(responseStr, responseCls);
            }
        } catch (SocketTimeoutException var7) {
            Logger.printExc(org.aisen.android.network.http.DefHttpUtility.class, var7);
            Logger.w(getTag(action, method), var7 + "");
            throw new TaskException(TaskException.TaskError.timeout.toString());
        } catch (IOException var8) {
            Logger.printExc(org.aisen.android.network.http.DefHttpUtility.class, var8);
            Logger.w(getTag(action, method), var8 + "");
            throw new TaskException(TaskException.TaskError.timeout.toString());
        } catch (TaskException var9) {
            Logger.printExc(org.aisen.android.network.http.DefHttpUtility.class, var9);
            Logger.w(getTag(action, method), var9 + "");
            throw var9;
        } catch (Exception var10) {
            Logger.printExc(org.aisen.android.network.http.DefHttpUtility.class, var10);
            Logger.w(getTag(action, method), var10 + "");
            throw new TaskException(TaskException.TaskError.resultIllegal.toString());
        }
    }

    protected <T> T parseResponse(String resultStr, Class<T> responseCls) throws TaskException {
        if(responseCls.getSimpleName().equals("String")) {
            return (T) resultStr;
        } else {
            Object result = JSON.parseObject(resultStr, responseCls);
            return (T) result;
        }
    }

    public synchronized OkHttpClient getOkHttpClient() {
        return GlobalContext.getOkHttpClient();
    }

    static RequestBody createRequestBody(final MultipartFile file) {
        return new RequestBody() {
            public MediaType contentType() {
                return MediaType.parse(file.getContentType());
            }

            public long contentLength() throws IOException {
                return file.getBytes() != null?(long)file.getBytes().length:file.getFile().length();
            }

            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                if(file.getFile() != null) {
                    source = Okio.source(file.getFile());
                } else {
                    source = Okio.source(new ByteArrayInputStream(file.getBytes()));
                }

                OnFileProgress onFileProgress = file.getOnProgress();
                if(onFileProgress != null) {
                    try {
                        long e = this.contentLength();
                        long writeLen = 0L;
                        long readLen = -1L;
                        Buffer buffer = new Buffer();
                        long MIN_PROGRESS_STEP = 65536L;
                        long MIN_PROGRESS_TIME = 300L;
                        long mLastUpdateBytes = 0L;
                        long mLastUpdateTime = 0L;

                        while(true) {
                            long now;
                            do {
                                if((readLen = source.read(buffer, 8192L)) == -1L) {
                                    return;
                                }

                                sink.write(buffer, readLen);
                                writeLen += readLen;
                                now = System.currentTimeMillis();
                            } while((writeLen - mLastUpdateBytes <= MIN_PROGRESS_STEP || now - mLastUpdateTime <= MIN_PROGRESS_TIME) && writeLen != e);

                            onFileProgress.onProgress(writeLen, e);
                            mLastUpdateBytes = writeLen;
                            mLastUpdateTime = now;
                        }
                    } catch (IOException var33) {
                        Logger.printExc(org.aisen.android.network.http.DefHttpUtility.class, var33);
                        throw var33;
                    } finally {
                        Util.closeQuietly(source);
                    }
                } else {
                    try {
                        sink.writeAll(source);
                    } catch (IOException var31) {
                        Logger.printExc(org.aisen.android.network.http.DefHttpUtility.class, var31);
                        throw var31;
                    } finally {
                        Util.closeQuietly(source);
                    }
                }

            }
        };
    }






















}
