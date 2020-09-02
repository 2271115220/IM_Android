package rjw.net.baselibrary.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.util.Enumeration;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * @author wfx
 * @date 2019/5/24 9:46
 * @desc
 */
public class AppUtil {

    @SuppressLint("MissingPermission")
    public static String getDeviceId() {
        try {
            TelephonyManager tm = (TelephonyManager) Utils.getApp().getSystemService(TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * cpu 型号
     * @return
     */
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*显示RAM的可用和总容量，RAM相当于电脑的内存条*/
    public static String showRAMInfo(){
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi=new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
//        String[] available=fileSize(mi.availMem);
        String[] total=fileSize(mi.totalMem);
//        return available[0]+available[1]+"/"+total[0]+total[1];
        return total[0];
    }

    /*显示RAM的可用和总容量，RAM相当于电脑的内存条*/
    public static double showRAMInfoRate(){
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi=new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        String[] available=fileSize(mi.availMem);
        String[] total=fileSize(mi.totalMem);
        long availMem = mi.availMem/1024;
        long totalMem = mi.totalMem/1024;
        double syl = ((totalMem - availMem) * 1.0/ totalMem) * 100;
        syl = (double) Math.round(syl * 100) / 100;
        return syl;
    }


    /*返回为字符串数组[0]为大小[1]为单位KB或者MB*/
    private static String[] fileSize(long size){
        String str="";
        if(size>=1000){
            str="KB";
            size/=1024;
//            if(size>=1000){
//                str="MB";
//                size/=1000;
//            }
        }
        /*将每3个数字用,分隔如:1,000*/
        DecimalFormat formatter=new DecimalFormat();
        formatter.setGroupingSize(3);
        String result[]=new String[2];
        result[0]=formatter.format(size);
        result[1]=str;
        return result;
    }

    /**
     * 得到当前的手机网络类型
     *
     * @return
     */
    public static int getNetType() {
        int type = 0;
        ConnectivityManager cm = (ConnectivityManager) Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            type = 4;
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            type = 5;
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = 1;
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type =2;
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                type = 3;
            }
        }
        return type;
    }

    /**
     * 获取手机ip地址
     *
     * @return
     */
    public static String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return "";
    }
}
