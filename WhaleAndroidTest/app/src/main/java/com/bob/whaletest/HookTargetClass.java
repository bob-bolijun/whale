package com.bob.whaletest;

import android.util.Log;

public class HookTargetClass {
    private static final String TAG = "***Whale***";

    public String functionChangeParam(String param1){
        Log.e(TAG, "functionChangeParam: function got param is " + param1);
        return param1;
    }
    public String functionChangeReturn(String param1){
        Log.e(TAG, "functionChangeReturn: return value should be " + param1);
        return param1;
    }

    public String functionNotCallOrg(String param1){
        Log.e(TAG, "functionNotCallOrg is called");
        return param1;
    }

}
