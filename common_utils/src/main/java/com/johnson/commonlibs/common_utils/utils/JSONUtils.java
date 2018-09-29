package com.johnson.commonlibs.common_utils.utils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.*;
import android.util.Log;
import com.johnson.commonlibs.common_utils.contants.DateStyle;


/**
 * description JSON的工具类
 * date 2014年7月17日
 * since v2
 */
public abstract class JSONUtils {

    private static final Gson GSON = createGson();

    /**
     * create the standard configuriation never return null
     *
     * @return
     */
    private static final Gson createGson() {
        return new GsonBuilder()
                .setDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS)
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .serializeNulls()
                .create();
    }

    /**
     * JSON转换工具类附格式
     *
     * @param <T>
     * @param json {"status":1,"message":"success","results":[{..},{..}]}或
     *             {"status":1,"message":"success","results":{..}}
     * @return
     * @notice 用于转换对象及对象中的数组的JSON格式到Bean中
     */
    public static final <T> T fromJSON(Class<T> t, String json) {
        T obj = GSON.fromJson(json, t);
        return obj;
    }

    /**
     * JSON转换工具类附格式
     *
     * @param t    泛型
     * @param json JSON数据 [{"id":0,"name":"name0"},{"id":1,"name":"name1"}]
     * @return List<T>
     * @notice 用于转换纯数组JSON格式到Bean中, 在3.0的项目中基本不会使用到
     */
    public static final <T> List<T> getListFromJSON(Class<T> t, String json) {
        List<T> lists = new ArrayList<T>();
        try {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(json).getAsJsonArray();
            for (final JsonElement jsonElement : array) {
                T entity = GSON.fromJson(jsonElement, t);
                lists.add(entity);
            }
        } catch (JsonSyntaxException e) {
            Log.e("JSONUTils....解析出错", e.getMessage());
        }
        return lists;
    }

    /**
     * 重载方法、JSON转换
     * 过滤掉不带@Expose
     *
     * @param t
     * @param json
     * @param <T>
     * @return
     */
    public static final <T> List<T> getListFromJsonWithAnnotation(Class<T> t, String json) {
        List<T> lists = new ArrayList<T>();
        try {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(json).getAsJsonArray();
            for (final JsonElement jsonElement : array) {
                T entity = GSON.fromJson(jsonElement, t);
                lists.add(entity);
            }
        } catch (Exception e) {
            Log.e("解析失败：", e.getMessage());
        }
        return lists;
    }

    /**
     * JSON转List
     *
     * @param t    List泛型的ParameterizedType
     * @param json
     * @return
     * @author shaowei.ma
     * @date 2014年9月24日
     */
    public static final <T> List<T> getListFromJSON(ParameterizedType t, String json) {
        return GSON.fromJson(json, t);
    }

    /**
     * 对象转JSON
     *
     * @param obj
     * @return
     */
    public static final String toJSON(Object obj) {
        return GSON.toJson(obj);
    }

}