package com.r.http.cn.httpUrl;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2018/3/27.
 */

public interface POSTService {
    //,@Field("sign")String sing   MD5加密
    //net转api
    @FormUrlEncoded
    @POST("api/circle/nettoapi")
    Call<ResponseBody> netToApi(@Field("token") String token, @Field("table") String table, @Field("data") String data, @Field("type") String type);
    //抢答 举手 讲太快 听不懂
    @FormUrlEncoded
    @POST("api/efficient-classroom/v1/student/{url}")
    Call<ResponseBody> getQD(@Path("url") String url, @FieldMap Map<String, String> map);
    //学生上课接收通知（实时请求）
    @FormUrlEncoded
    @POST("api/efficient-classroom/v1/todo/student")
    Call<ResponseBody>setQQ(@Field("student_id") String studentId, @Field("classroom_id") String classId, @Field("zhi_shi_id") String zSId, @Field("praise_id") String pId);
    @Multipart
    @POST("/api/efficient-classroom/v1/student/submitAnswer")
    Call<ResponseBody>upAnswer(@Part("student_id") RequestBody student_id, @Part("answer") RequestBody answer, @Part("icon") RequestBody icon, @Part("name") RequestBody name, @Part MultipartBody.Part file);
    //老师拍照
    @Multipart
    @POST("/api/efficient-classroom/v1/teacher/teacher/tu")
    Call<ResponseBody>setPhoto(@Part("teacher_id") RequestBody teacher_id, @Part MultipartBody.Part file);
    //更新版本
    @FormUrlEncoded
    @POST("api/all/{url}")
    Call<ResponseBody> upDataVersion(@Path("url") String url, @Field("app_name") String appName, @Field("version") String version);
    @FormUrlEncoded
    @POST("api/all/{url}")
    Call<ResponseBody> getFriend(@Path("url") String url, @Field("token") String token);
    @FormUrlEncoded
    @POST("api/user/{url}")
    Call<ResponseBody> isCard(@Path("url") String url, @Field("token") String token);
    //学生登录
    //登录注册 验证码
    //全部科目列表
    @FormUrlEncoded
    @POST("api/all/{url}")
    Call<ResponseBody> resgister(@Path("url") String url, @Field("mobile") String mobile, @Field("password") String password, @Field("name") String name, @Field("user_type") String user_type, @Field("code") String code);

    //学生登录
    //登录注册 验证码
    //全部科目列表

    /**
     * 用户注册
     * @param user_login 登录名
     * @param name       昵称
     * @param password   密码
     * @return
     */
    @FormUrlEncoded
    @POST("api/all/reg")
    Call<ResponseBody> resgister1(@Field("user_login") String user_login, @Field("name") String name, @Field("password") String password);

    //学生登录
    @FormUrlEncoded
    @POST("index.php/api/all/login")
    Call<ResponseBody> login(@Field("login_name") String user_login, @Field("password") String password, @Field("terminal") String terminal);

    //微课列表
    @FormUrlEncoded
    @POST("api/course/{url}")
    Call<ResponseBody> getKc(@Path("url") String url, @FieldMap Map<String, String> map);

    //微课详细信息
    @FormUrlEncoded
    @POST("api/course/get_course_info")
    Call<ResponseBody> getXx(@Field("token") String token, @Field("cs_id") String csId);

    //收藏课程、删除课程收藏、创建订单
    @FormUrlEncoded
    @POST("api/course/{url}")
    Call<ResponseBody> kcOrderPay(@Path("url") String url, @Field("token") String token, @Field("order_id") String order_id);
    //课程收藏列表、已购课程
    @FormUrlEncoded
    @POST("api/course/{url}")
    Call<ResponseBody> collectList(@Path("url") String url, @Field("token") String token, @Field("page") String page, @Field("page_num") String page_num);
    //获取订单详情、订单支付（微课）
    @FormUrlEncoded
    @POST("api/course/{url}")
    Call<ResponseBody> collectKc(@Path("url") String url, @Field("token") String token, @Field("cs_id") String csId);
    //个人资料 修改密码 个人主页
    @FormUrlEncoded
    @POST("api/user/{url}")
    Call<ResponseBody> getUser(@Path("url") String url, @Field("token") String token, @Field("password") String pwd, @Field("newpassword") String pwdAgain, @Field("person_id") String person_id);
    //修改个人资料
    @FormUrlEncoded
    @POST("api/user/{url}")
    Call<ResponseBody> getUser2(@Path("url") String url, @Field("token") String token, @Field("nicename") String nicename, @Field("sex") String sex, @Field("signature") String signature);
    //头像上传回调
    @FormUrlEncoded
    @POST("api/user/update_avatar")
    Call<ResponseBody> upLoadCallback(@Field("token") String token, @Field("avatar_name") String avatar);
    //签到  添加签到
    @FormUrlEncoded
    @POST("api/user/{url}")
    Call<ResponseBody> getQD(@Path("url") String url, @Field("token") String token, @Field("content") String content);

    //上传头像
    @Multipart
    @POST("index.php?g=api&m=api&a=alterhead")
    Call<ResponseBody> setTx(@Part("token") RequestBody token, @Part("type") RequestBody type, @Part("user_id") RequestBody user_id, @Part MultipartBody.Part file);

    //通讯录列表
    @FormUrlEncoded
    @POST("api/user/{url}")
    Call<ResponseBody> getTXL(@Path("url") String url, @Field("token") String token, @Field("hxuser_id") String hxuser_id);

    //朋友圈列表 动态详情
    @FormUrlEncoded
    @POST("api/circle/{url}")
    Call<ResponseBody> getPyq(@Path("url") String url, @Field("token") String token, @Field("pageno") String pageno, @Field("page") String page, @Field("dy_id") String dy_id, @Field("user_id") String user_id, @Field("person_id") String person_id);

    //新班级圈,type:{1：班级圈；2：校园圈}
    @FormUrlEncoded
    @POST("api/circle/{url}")
    Call<ResponseBody> getCircle(@Path("url") String url, @Field("token") String token, @Field("pageno") String pageno, @Field("page") String page, @Field("type") String type, @Field("dy_id") String dy_id, @Field("user_id") String user_id, @Field("person_id") String person_id, @Field("role") String role);

    //个人动态
    @FormUrlEncoded
    @POST("api/circle/{url}")
    Call<ResponseBody> getCircleOther(@Path("url") String url, @Field("token") String token, @Field("pageno") String pageno, @Field("page") String page, @Field("userid") String userid);

    //取消动态收藏
    @FormUrlEncoded
    @POST("api/circle/{url}")
    Call<ResponseBody> cancelDtCollect(@Path("url") String url, @Field("token") String token, @Field("dy_id") String dy_id, @Field("person_id") String person_id);
    //关注粉丝列表
    @FormUrlEncoded
    @POST("api/user/{url}")
    Call<ResponseBody> getFs(@Path("url") String url, @Field("token") String token);
    //荣誉
    @FormUrlEncoded
    @POST("api/classc/{url}")
    Call<ResponseBody> getHonor(@Path("url") String url, @Field("token") String token, @Field("class_id") String class_id, @Field("page") String page, @Field("page_num") String pagenum, @Field("stu_id") String stuId);
    //关注粉丝详情  收藏记录
    @FormUrlEncoded
    @POST("api/circle/{url}")
    Call<ResponseBody> getFsXq(@Path("url") String url, @Field("token") String token, @Field("user_id") String user_id, @Field("pageno") String pageno, @Field("page") String page);
    //发布
    @Multipart
    @POST("api/circle/pushdynamic")
    Call<ResponseBody> setFb(@Part("token") RequestBody token, @Part("content") RequestBody content, @Part List<MultipartBody.Part> partList);

    //评论 回复
    @FormUrlEncoded
    @POST("api/circle/{url}")
    Call<ResponseBody> setPl(@Path("url") String url, @Field("token") String token, @Field("dy_id") String dy_id, @Field("user_id") String user_id, @Field("content") String content, @Field("to_id") String to_id, @Field("reply_id") String reply_id);

    //错题率统计
    @FormUrlEncoded
    @POST("api/exam/get_papers_tongji")
    Call<ResponseBody> papersTj(@Field("token") String token, @Field("class_papers_id") String class_papers_id, @Field("id") String id);


    //教师班级
    @FormUrlEncoded
    @POST("api/teacher/get_teacher_class_list")
    Call<ResponseBody> getBj(@Field("token") String token);

    //老师端布置的作业列表  老师端提交（批改）的作业列表 布置作业
    @FormUrlEncoded
    @POST("api/exam/{url}")
    Call<ResponseBody> getZY(@Path("url") String url, @Field("token") String token, @Field("class_papers_id") String class_papers_id, @Field("type") String type
            , @Field("id") String id, @Field("class_id") String class_id, @Field("data") String data);
    //作业提交列表
    @FormUrlEncoded
    @POST("api/exam/{url}")
    Call<ResponseBody> getZYList(@Path("url") String url, @Field("token") String token, @Field("class_papers_id") String class_papers_id, @Field("order_type") String order_type);
    //获取一道题的详情
    @FormUrlEncoded
    @POST("api/shiti/info")
    Call<ResponseBody> getST(@Field("token") String token, @Field("id") String id);

    //新建相册
    @FormUrlEncoded
    @POST("api/circle/{url}")
    Call<ResponseBody> createXc(@Path("url") String url, @Field("token") String token, @Field("list_img") String list_img, @Field("title") String title, @Field("content") String content);

    //相册展示
    @FormUrlEncoded
    @POST("api/circle/{url}")
    Call<ResponseBody> getXc(@Path("url") String url, @Field("token") String token, @Field("user_id") String user_id, @Field("al_id") String al_id);

    //班级作业列表
    @FormUrlEncoded
    @POST("api/exam/{url}")
    Call<ResponseBody> getBJZY(@Path("url") String url, @Field("token") String token, @Field("type") String type
            , @Field("class_id") String class_id, @Field("today") String today, @Field("finish") String finish);

    //上传图片
    @Multipart
    @POST("api/circle/{url}")
    Call<ResponseBody> sendPic(@Path("url") String url, @Part("token") RequestBody token, @Part("al_id") RequestBody al_id, @Part List<MultipartBody.Part> partList);

    //删除相册图片
    @FormUrlEncoded
    @POST("api/circle/{url}")
    Call<ResponseBody> deleteImage(@Path("url") String url, @Field("token") String token, @Field("al_id") String al_id, @Field("image") String image);

    //删除相册
    @FormUrlEncoded
    @POST("api/circle/{url}")
    Call<ResponseBody> deleteAlbum(@Path("url") String url, @Field("token") String token, @Field("al_id") String al_id);

    //获取充值类型
    @FormUrlEncoded
    @POST("api/payment/{url}")
    Call<ResponseBody> getRefillType(@Path("url") String url, @Field("token") String token);

    //兑换R点
    @FormUrlEncoded
    @POST("api/payment/{url}")
    Call<ResponseBody> toRdian(@Path("url") String url, @Field("token") String token, @Field("rdian_num") String rdian_num);

    //创建订单
    @FormUrlEncoded
    @POST("api/payment/{url}")
    Call<ResponseBody> createOrder(@Path("url") String url, @Field("token") String token, @Field("id") String id);

    //订单记录
    @FormUrlEncoded
    @POST("api/payment/{url}")
    Call<ResponseBody> getOrderList(@Path("url") String url, @Field("token") String token, @Field("page") String page, @Field("page_num") String page_num);
    //朋友圈消息列表
    @FormUrlEncoded
    @POST("index.php/api/circle/informlist")
    Call<ResponseBody> getXXList(@Field("token") String token, @Field("page") String page, @Field("pageno") String pageno);

    //绑定父母
    @FormUrlEncoded
    @POST("api/student/{url}")
    Call<ResponseBody> bindParent(@Path("url") String url, @Field("token") String token, @Field("mobile") String mobile, @Field("code") String code, @Field("relation") String relation);

    //忘记密码
    @FormUrlEncoded
    @POST("api/all/{url}")
    Call<ResponseBody> forgetPwd(@Path("url") String url, @Field("mobile") String mobile, @Field("code") String code, @Field("pwd1") String pwd1, @Field("pwd2") String pwd2);

    //班级人员
    @FormUrlEncoded
    @POST("api/classc/get_class_student_list")
    Call<ResponseBody> getBJ(@Field("token") String token, @Field("class_id") String class_id);


    //班级人员 随机点名 在线查看  全部举手
    @FormUrlEncoded
    @POST("api/efficient-classroom/v1/teacher/{url}")
    Call<ResponseBody> getBJ2(@Path("url") String url, @Field("class_id") String class_id, @Field("teacher_id") String teacher_id, @Field("number") String number, @Field("ti_wen_id") String ti_wen_id);


    //复习预习列表
    @GET("assistant/api/v1/user/board/share?")
    Call<ResponseBody> getFX(@Query("classroom_id") String class_id, @Query("skip") String skip, @Query("type") String type, @Query("limit") String limit);

    //IP获取
    @GET("assistant/api/v1/user/teaching-history/query?")
    Call<ResponseBody> getIp(@Query("from") String from, @Query("id") String id);

    @POST("assistant/api/v1/user/teaching-history/query?")
    Call<ResponseBody> getIpByPost(@Query("from") String from, @Query("id") String id);

    //IP测试
    @GET("api/efficient-classroom/v1/utils/ping?")
    Call<ResponseBody> getIpTest(@Query("from") String from, @Query("id") String id);

    //科目列表
    @FormUrlEncoded
    @POST("api/all/{url}")
    Call<ResponseBody> getSubjects(@Path("url") String url, @Field("token") String token);


    //新请求

    //课程表
    @FormUrlEncoded
    @POST("api/{url1}/{url2}")
    Call<ResponseBody> getClassS(@Path("url1") String url1, @Path("url2") String url2, @FieldMap Map<String, String> map);

    //聊天发送
    @FormUrlEncoded
    @POST("api/push/individual")
    Call<ResponseBody> sendMsg(@Field("from_id") String from_id, @Field("to_id") String to_id, @Field("msg") String msg, @Field("time") String time, @Field("class_id") String class_id);

    @FormUrlEncoded
    @POST("api/push/qun")
    Call<ResponseBody> sendMsgs(@Field("from_id") String from_id, @Field("class_id") String to_id, @Field("msg") String msg, @Field("time") String time);

    //好友列表
    @FormUrlEncoded
    @POST("api/talk/class_user_list")
    Call<ResponseBody> getFriends(@Field("classid") String classid);

    //下载文件
    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

    //点赞
    @FormUrlEncoded
    @POST("api/circle/{url}")
    Call<ResponseBody> dianZan(@Path("url") String url, @Field("token") String token, @Field("dy_id") String dyid);
    @FormUrlEncoded
    @POST("api/circle/{url}")
    Call<ResponseBody> pinglun(@Path("url") String url, @Field("token") String token, @Field("dy_id") String dyid, @Field("content") String content, @Field("from_id") String fromId, @Field("from_name") String fromName, @Field("to_id") String toId, @Field("to_name") String toName, @Field("key") String key);

    //孺智学堂
    @FormUrlEncoded
    @POST("index.php/api/all/{url}")
    Call<ResponseBody> getParentXt(@Path("url") String url, @Field("type") String type, @Field("page_num") String pagenum, @Field("object") String object, @Field("page") String page, @Field("id") String id);

    /**
     * 新人礼
     * @param name  默认: laxin
     * @return
     */
    @FormUrlEncoded
    @POST("api/all/GetActivityUrl")
    Call<ResponseBody> getXrl(@Field("name") String name);

    /**
     * 获取疑难解答老师列表
     * @return
     */

    @GET("api/talk/getAnswerList")
    Call<ResponseBody> getAnswerList();

    /**
     * 获取疑难解答老师列表
     * @return
     */
    @FormUrlEncoded
    @POST("answer/index/teacher_list")
    Call<ResponseBody> getTeacherList(@Field("token") String token);

    /**
     * 检查有效时间
     * @return
     */
    @FormUrlEncoded
    @POST("answer/index/get_answer_time")
    Call<ResponseBody> checkTime(@Field("token") String token);

    //上传form表单实例
    @FormUrlEncoded
    @POST("api/course/{url}")
    Call<ResponseBody> getDataCourse(@Path("url") String url,@FieldMap Map<String, String> map);

}
