package rjw.net.baselibrary.utils;

import android.text.TextUtils;

/**
 * @author wfx
 * @date 2019/2/25 11:31
 * @desc
 */
public class StringUtils {

    /**
     * 验证用户名是否合法 [a-zA-Z0-9]$
     * @param userName 用户名
     * @return true 合法;false 不合法
     */
    public static boolean regexUserName(String userName) {

        //String regex = "^[a-zA-Z0-9]+$"; ^[A-Za-z][A-Za-z0-9]{5,11}$
        String regex = "^[A-Za-z][A-Za-z0-9]{3,15}$";
        return !TextUtils.isEmpty(userName) && userName.matches(regex);
    }

    /**
     * 校验密码：大于6位，不能纯数字
     * @param pwd
     * @return
     */
    public static boolean regexPwd(String pwd) {

        return pwd.length() > 6 && pwd.length() < 30 && !pwd.matches("^[0-9]+$");
    }
}
