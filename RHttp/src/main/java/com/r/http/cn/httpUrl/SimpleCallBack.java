package com.r.http.cn.httpUrl;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/4/2.
 */

public class SimpleCallBack implements Callback<ResponseBody> {

    private String result="";
    private static String TAG;
    public SimpleCallBack(String tag) {
        TAG = tag;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            if (response.body() != null) {
                result = response.body().string();
            }
            if (TextUtils.isEmpty(result)||result.equals("")) {
                showError(0, new RuntimeException("亲！取得数据为空"));
            }
            Object json = new JSONTokener(result).nextValue();
            if(json instanceof JSONArray){
                JSONArray jsonArray = (JSONArray)json;
                showData(0,result);
            }else if (json instanceof JSONObject){
                JSONObject jsonObject = (JSONObject)json;
                if (jsonObject.getInt("state")==0) {
                    showData(1, result);
                } else {
                    if (jsonObject.getString("msg") != null && !jsonObject.getString("msg").equals("")) {
                        Log.e(TAG,result);
                        showData(jsonObject.getString("msg"));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
        showError(-1, t);
    }

    protected void showError(int i, Throwable t) {
        Log.e("error", t.toString());
    }

    protected void showData(int i, String json) {
        Log.e(TAG, json);
    }
    protected void showData(String msg) {
        Log.e("msg",msg);
    }
}
