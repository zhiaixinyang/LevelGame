package com.mdove.levelgame.base.network.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * Created by MDove on 2018/12/24.
 */
public class NetworkUtils {

    public static final String NO_NETWORK_DESC = "Network Unavailable";

    public enum NetworkType {
        NONE(0),
        MOBILE(1),
        MOBILE_2G(2),
        MOBILE_3G(3),
        WIFI(4),
        MOBILE_4G(5);

        NetworkType(int ni) {
            nativeInt = ni;
        }

        public int getValue() {
            return nativeInt;
        }

        final int nativeInt;
    }

    public static boolean is2G(Context context) {
        NetworkType nt = getNetworkType(context);
        return nt == NetworkType.MOBILE || nt == NetworkType.MOBILE_2G;
    }

    public static boolean is4G(Context context) {
        NetworkType nt = getNetworkType(context);
        return nt == NetworkType.MOBILE || nt == NetworkType.MOBILE_4G;
    }

    public static boolean isWifi(Context context) {
        try {
            ConnectivityManager manager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info == null || !info.isAvailable()) {
                return false;
            }

            return (ConnectivityManager.TYPE_WIFI == info.getType());
        } catch (Exception e) {
            // ignore
        }
        return false;
    }

    /**
     * get network type.
     */
    public static NetworkType getNetworkType(Context context) {
        NetworkInfo info = getNetWorkInfo(context);
        return getNetworkType(context, info);
    }

    public static NetworkType getNetworkType(Context context, NetworkInfo info){
        try {
            if (info == null || !info.isConnected()) {
            return NetworkType.NONE;
        }
        int type = info.getType();
        if (ConnectivityManager.TYPE_WIFI == type) {
            return NetworkType.WIFI;
        } else if (ConnectivityManager.TYPE_MOBILE == type) {
            TelephonyManager mgr = (TelephonyManager) context.getSystemService(
                    Context.TELEPHONY_SERVICE);
            switch(mgr.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return NetworkType.MOBILE_2G;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return NetworkType.MOBILE_3G;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return NetworkType.MOBILE_4G;
                default:
                    return NetworkType.MOBILE;
            }
        } else {
            return NetworkType.MOBILE;
        }
    } catch (Throwable e) {
        return NetworkType.MOBILE;
    }
    }
    /** detect network available or not */
    public static boolean isNetworkAvailable(Context context) {
        try {
            NetworkInfo info = getNetWorkInfo(context);
            return (info != null && info.isConnected());
        } catch (Exception e) {
            // ignore
        }
        return false;
    }

    public static NetworkInfo getNetWorkInfo(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return null;
        }
        try {
            return manager.getActiveNetworkInfo();
        } catch (Exception e) {
            return null;
        }
    }

    /** get network access type */
    public static String getNetworkAccessType(Context context) {
        return getNetworkAccessType(getNetworkType(context));
    }

    public static String getNetworkAccessType(NetworkType nt) {
        String access = "";
        try {
            switch (nt) {
                case WIFI:
                    access = "WIFI";
                    break;
                case MOBILE_2G:
                    access = "2G";
                    break;
                case MOBILE_3G:
                    access = "3G";
                    break;
                case MOBILE_4G:
                    access = "4G";
                    break;
                case MOBILE:
                    access = "mobile";
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            // ignore
        }
        return access;
    }

    public static byte[] compress(String string) throws IOException {
        if (string == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream(string.length());
        GZIPOutputStream gos = new GZIPOutputStream(os);
        gos.write(string.getBytes());
        gos.close();
        byte[] compressed = os.toByteArray();
        os.close();
        return compressed;
    }

}
