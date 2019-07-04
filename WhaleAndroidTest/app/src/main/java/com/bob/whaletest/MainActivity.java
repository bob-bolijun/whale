package com.bob.whaletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lody.whale.xposed.XC_MethodHook;
import com.lody.whale.xposed.XposedHelpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "***Whale***";
    HookTargetClass hookTargetClass = new HookTargetClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG, ClassInfoHelper.getDeclaredMethods(FileOutputStream.class));

        XposedHelpers.findAndHookMethod(HookTargetClass.class, "functionChangeParam", String.class, new XC_MethodHook(){
            @Override
            protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                if(param.args != null){
                    for(Object arg : param.args){
                        param.args[0] = "hooked param";
                    }
                }
            }
        });

        XposedHelpers.findAndHookMethod(HookTargetClass.class, "functionChangeReturn", String.class, new XC_MethodHook(){
            @Override
            protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                param.setResult("hooked return value");
            }
        });

        XposedHelpers.findAndHookMethod(HookTargetClass.class, "functionNotCallOrg", String.class, new XC_MethodHook(){
            @Override
            protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                //if setResult in beforeHookedMethod, then will not call the org method
                param.setResult("omit org function");
            }
        });

        XposedHelpers.findAndHookMethod(File.class, "getAbsolutePath", new XC_MethodHook(){
            @Override
            protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                //if setResult in beforeHookedMethod, then will not call the org method
                param.setResult("I am changed path");
            }
        });

        XposedHelpers.findAndHookMethod(FileOutputStream.class, "write", byte[].class, int.class, int.class, new XC_MethodHook(){
            @Override
            protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                //if setResult in beforeHookedMethod, then will not call the org method
                //param.setResult("I am changed path");
                Log.e(TAG, "FileOutputStream write hooked");
                if(param.args != null){
                    byte[] hookedData = "your data is hooked!".getBytes();
                    param.args[0] = hookedData;
                    param.args[1] = 0;
                    param.args[2] = hookedData.length;
                }
            }
        });
    }

    public void changeParam(View view) {
        Log.e(TAG, "functionChangeParam: real param is [org param]");
        String ret = hookTargetClass.functionChangeParam("org param");
        Log.e(TAG, "functionChangeParam: return value is [" + ret + "]");
    }

    public void changeReturn(View view) {
        String ret = hookTargetClass.functionChangeReturn("org param");
        Log.e(TAG, "functionChangeReturn: return value is [" + ret + "]");
    }

    public void notCallOrg(View view) {
        Log.e(TAG, "functionNotCallOrg: will call");
        String ret = hookTargetClass.functionNotCallOrg("org param");
        Log.e(TAG, "functionNotCallOrg: call done [" + ret + "]");
    }

    public void file_getAbsolutePath(View view) {
        File file = new File("/sdcard/test.txt");
        String path = file.getAbsolutePath();
        Log.e(TAG, "path from [/sdcard/test.txt] change to [" + path + "]");
    }

    public void fileOutputStream_write(View view) throws IOException {
        FileOutputStream fo = new FileOutputStream("/sdcard/test.txt");
        fo.write("test".getBytes());
    }
}
