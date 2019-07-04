package com.bob.whaletest;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ClassInfoHelper {

    private static String getParametersString( Class<?>[] clazzes) {
        final StringBuilder sb = new StringBuilder("(");
        boolean first = true;
        for (final Class<?> clazz : clazzes) {
            if (first)
                first = false;
            else
                sb.append(",");

            if (clazz != null)
                sb.append(clazz.getCanonicalName());
            else
                sb.append("null");
        }
        sb.append(")");
        return sb.toString();
    }

    public static String getDeclaredMethods(Class<?> clazz){
        Method[] methods = clazz.getDeclaredMethods();
        String ret = "  \n" + clazz.getName() + ":\n\t\t";
        int i = 0;
        for(Method method : methods) {
            ret = ret + method.getName() + getParametersString(method.getParameterTypes()) + "\n\t\t";
        }
        return ret;
    }

}
