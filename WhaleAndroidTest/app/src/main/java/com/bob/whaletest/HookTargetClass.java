package com.bob.whaletest;

import android.util.Log;

public class HookTargetClass {
    private static final String TAG = "Whale";

    public void function1(){

    }

    public boolean function2(){
        return true;
    }

    public void function3(String param1){

    }

    public String function4(String param1){
        Log.i(TAG, "function4 get param is " + param1);
        return param1;
    }
}
