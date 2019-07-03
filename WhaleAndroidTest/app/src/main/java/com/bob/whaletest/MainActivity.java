package com.bob.whaletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lody.whale.xposed.XC_MethodHook;
import com.lody.whale.xposed.XposedHelpers;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Whale";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XposedHelpers.findAndHookMethod(HookTargetClass.class, "function4", String.class, new XC_MethodHook(){
            @Override
            protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                if(param.args != null){
                    for(Object arg : param.args){
                        Log.i(TAG, "before hook change param from [" + arg + "] to [bbb]");
                        param.args[0] = "bbb";
                    }
                }
            }

            @Override
            protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                Log.i(TAG, "after hook change param to [new value]");
                param.setResult("new value");
            }
        });
    }

    public void hookFile(View view) {

        HookTargetClass hookTargetClass = new HookTargetClass();
        String ret = hookTargetClass.function4("aaa");
        Log.i(TAG, "function4 ret value " + ret);
    }
}
