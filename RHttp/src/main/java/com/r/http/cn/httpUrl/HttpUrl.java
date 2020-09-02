package com.r.http.cn.httpUrl;

/**
 * Created by Administrator on 2018/3/27.
 */

public interface HttpUrl {
    /**
     * 服务器端一共多少条数据
     */
    int TOTAL_COUNTER = 1000;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断

    /**
     * 每一页展示多少条数据
     */
    int REQUEST_COUNT = 10;

    /**
     * 已经获取到多少条数据了
     */
    int mCurrentCounter = 0;
    String BASE_URLAPI = "http://api.rujiaowang.net/";
    String QDId = "0";
    String BASE_URL = "http://";
    String BASE_URL2 = "http://api.rujiaowang.net/";
    String BASE_URL3 = "http://www.rujiaowang.net/";
    String BASE_ZB_URL = "rtmp://";
    //上课直播
    String ZB_URL = "/live/test";

    //聊天IP
    //String CHAT_IP="58.59.29.42";
    String CHAT_IP = "14.116.152.239";
    String SHARED = "http://114.217.148.212:62081/";
    //教师发布作业页面用到，参数拼接token
    //http://www.rujiaowang.net/app/zuoye/teacher/index.html#/?token=57f1545e5cb7e4b0ce0dcdecbde045ee
    String PUSH_HOME_WORKER = "http://www.rujiaowang.net/app/zuoye/teacher/index.html#/?token=";

    //学生完成作业页面用到，参数拼接token
    //http://www.rujiaowang.net/app/zuoye/student/index.html#/?token=bacd15a797733f2d788fcd1deed5e4b5
    String PULL_HOME_WORKER = "http://www.rujiaowang.net/app/zuoye/student/index.html#/?token=";
}
