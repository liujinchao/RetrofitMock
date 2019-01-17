package com.android.apimock.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ClassName:  AssetsUtil
 * @author: liujc
 * @date: 2019/1/15
 * @Description: assets工具类
 */
public class AssetsUtil {
    /**
     * @param context
     * @param path  assets文件夹下的文件名
     * @return
     */
    public static String getAssetsAsString(Context context, String path) {
        BufferedReader bufferedReader = null;
        try {
            StringBuilder buf = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(
                    context.getAssets().open(path), "UTF-8"));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            return null;
        } finally {
            close(bufferedReader);
        }
    }

    private static void close(Closeable c) {
        try {
            c.close();
        } catch (Exception e) {
        }
    }
}
