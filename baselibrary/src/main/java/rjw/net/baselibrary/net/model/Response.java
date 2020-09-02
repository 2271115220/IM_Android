package rjw.net.baselibrary.net.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * http响应参数实体类
 * 通过Gson解析属性名称需要与服务器返回字段对应,或者使用注解@SerializedName
 * 备注:这里与服务器约定返回格式
 *
 * @author ZhongDaFeng
 */
public class Response implements Serializable {
    /**
     * 描述信息
     */
    @SerializedName("msg")
    private String message;

    /**
     * 状态码
     */
    @SerializedName("state")
    private int state;

    /**
     * 数据对象/成功返回对象
     */
    @SerializedName("result")
    private JsonElement result;

    /**
     * 是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return state == 0;
    }

    public String toString() {
        String response = "[http response]" + "{\"state\": " + state + ",\"msg\":" + message + ",\"result\":" + new Gson().toJson(result) + "}";
        return response;
    }


    public String getMsg() {
        return message;
    }

    public void setMsg(String message) {
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public JsonElement getResult() {
        return result;
    }

    public void setResult(JsonElement result) {
        this.result = result;
    }
}

