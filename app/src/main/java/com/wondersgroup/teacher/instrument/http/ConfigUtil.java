package com.wondersgroup.teacher.instrument.http;


/**
 * Created by zhangwt on 18/9/20..<br/>
 *
 * @author zhangwt
 * @version 1.0
 * @date 16/1/28
 */
public interface ConfigUtil {
    //是否为正式环境
    boolean isRelease = false;
    //正式环境地址
    String BASE_URL_RELEASE = "https://www.eduincloud.net/smartcampus/";//智慧校园
    //测试环境地址
    String BASE_URL_DEBUG = "http://10.16.16.12/";//智慧校园

    String BASE_URL = isRelease ? BASE_URL_RELEASE : BASE_URL_DEBUG;

    String ONLINE = BASE_URL + "api/mobile/";

    //版本更新地址
    String BASE_URL_VERSION = "http://www.eduincloud.net/";//智慧校园

    //天气接口
    String API_WEATHER = "http://apicloud.mob.com/v1/weather/query";

    //公共空间
    String IMAGE_URL = "http://oq6t96i6t.bkt.clouddn.com/";//七牛地址
    String FILE_MANAGER = "fileSources/filesManagerment/";//七牛地址
    String FILE_URL = IMAGE_URL + FILE_MANAGER;//文件管理七牛地址

    //数据库名
    String DB_NAME = "smileschool.db";
    //数据库版本号
    int DB_VERSION = 1;

    String FRAGMENT_INDEX = "fragmentIndex";// 首页加载fragment 索引


    String IS_SELECT = "isSelect";//add是否选中
}
