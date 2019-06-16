package com.physical.app.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;


import com.physical.app.MyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 存储xml键值对
 * Created by pc on 2017/12/26.
 */
public class Preferences {

    private static final String SHAREDNAME 	= "prefrences";


    /**
     * put一个string键值对
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean putString(String key, String value) {

        SharedPreferences prefence = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor eidtor = prefence.edit();
        eidtor.putString(key, value);
        return eidtor.commit();
    }

    /**
     * put一个set键值对
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean putStringSet(String key, Set<String> value) {

        SharedPreferences prefence = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor eidtor = prefence.edit();
        eidtor.putStringSet(key, value);
        return eidtor.commit();
    }

    /**
     * put一个string键值对
     *
     * @param key
     * @return
     */
    public static boolean removeKey(String key) {

        SharedPreferences prefence = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor eidtor = prefence.edit();
        eidtor.remove(key);
        return eidtor.commit();
    }

    /**
     * put一个string键值对
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean putBoolean(String key, boolean value) {

        SharedPreferences prefence = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor eidtor = prefence.edit();
        eidtor.putBoolean(key, value);
        return eidtor.commit();
    }

    /**
     * 获取string 值
     * @param key
     * @return
     */
    public static String getString(String key) {
        SharedPreferences prefence = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return prefence.getString(key, "");
    }

    /**
     * 获取string Set
     * @param key
     * @return
     */
    public static Set<String> getStringSet(String key) {
        SharedPreferences prefence = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return prefence.getStringSet(key, null);
    }

    /**
     * 获取string 值
     * @param key
     * @return
     */
    public static String getString(String key, String defaultValue) {
        SharedPreferences prefence = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return prefence.getString(key, defaultValue);
    }

    /**
     * 获取int 值
     * @param key
     * @return
     */
    public static int getInt(String key, int defaultValue) {
        SharedPreferences prefence = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return prefence.getInt(key, defaultValue);
    }
    /**
     * 获取long 值
     * @param key
     * @return
     */
    public static long getLong(String key, long defaultValue) {
        SharedPreferences prefence = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return prefence.getLong(key, defaultValue);
    }

    /**
     * put一个Int键值对
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean putInt(String key, int value) {
        SharedPreferences prefence = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor eidtor = prefence.edit();
        eidtor.putInt(key, value);
        return eidtor.commit();
    }

    /**
     *  put一个Long键值对
     */
    public static boolean putLong(String key, long value) {
        SharedPreferences prefence = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor eidtor = prefence.edit();
        eidtor.putLong(key, value);
        return eidtor.commit();
    }

    /**
     * 获取string 值
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
        SharedPreferences prefence = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return prefence.getBoolean(key, false);
    }
    /**
     * 获取string 值
     * @param key
     * @return
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences prefence = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return prefence.getBoolean(key, defaultValue);
    }

    /**
     * 字符串累加
     * @param key
     * @param value
     * @param split
     */
    public static void appendString(String key, String value, String split) {
        String val = getString(key);
        if(TextUtils.isEmpty(val)){

            //首次插入值
            putString(key, value);

        }else{

            //累加值
            val = (val + split + value);
            putString(key, val);

        }

    }

    /**存储对象*/
    private static void put(Context context, String key, Object obj)
            throws IOException
    {
        if (obj == null) {//判断对象是否为空
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos  = null;
        oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        baos.close();
        oos.close();

        putString(context, key, objectStr);
    }

    /**获取对象*/
    private static Object get(Context context, String key)
            throws IOException, ClassNotFoundException
    {
        String wordBase64 = getString(context, key);
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) { //不可少，否则在下面会报java.io.StreamCorruptedException
            return null;
        }
        byte[] objBytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais     = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois      = new ObjectInputStream(bais);
        // 将byte数组转换成product对象
        Object obj = ois.readObject();
        bais.close();
        ois.close();
        return obj;
    }

    /**
     * 存储List集合
     * @param context 上下文
     * @param key 存储的键
     * @param list 存储的集合
     */
    public static void putList(Context context, String key, List<? extends Serializable> list) {
        try {
            put(context, key, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取List集合
     * @param context 上下文
     * @param key 键
     * @param <E> 指定泛型
     * @return List集合
     */
    public static <E extends Serializable> List<E> getList(Context context, String key) {
        try {
            return (List<E>) get(context, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存入字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @param value   字符串的值
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences preferences = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        //存入数据
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @return 得到的字符串
     */
    public static String getString(Context context, String key) {
        SharedPreferences preferences = MyApplication.context.getSharedPreferences(SHAREDNAME, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }


}
