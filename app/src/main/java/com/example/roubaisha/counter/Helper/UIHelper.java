package com.example.roubaisha.counter.Helper;

import android.os.Handler;
import android.os.Looper;

public class UIHelper{
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    public static void runOnUiThread(Runnable action){
        mHandler.postDelayed(action, 1000 * 40 * 1);
    }

}
