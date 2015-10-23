package com.geekband.moran.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.geekband.moran.R;


public class UIUtils {

    /**
     *  启动Activity
     * @param context
     * @param cls
     */
    public static void startActivity(Context context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.push_right_in,
                R.anim.push_left_out);
    }

    /**
     * 启动Activity ，并携带参数Bundle
     * @param context
     * @param cls
     * @param bundle
     */
    public static void startActivity(Context context, Class<?> cls,
                                     Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.push_right_in,
                R.anim.push_left_out);
    }

    /**
     * 关闭Activity
     * @param context
     */
    public static void closeActivity(Context context) {
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                R.anim.push_right_out);
    }
}
