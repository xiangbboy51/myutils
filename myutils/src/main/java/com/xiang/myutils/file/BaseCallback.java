package com.xiang.myutils.file;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/9/8.
 */
public abstract class BaseCallback<T> {

    //new TypeToken<>()//  通过这个来获取的
    //通过定义类型  用来处理
    private Type type;

    public BaseCallback() {
        this.type = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }

        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public final Type getType() {
        return type;
    }

    //封装两个回调函数
    public abstract void onFailure(Request request, IOException e);

    public abstract void onSuccess(Response response, T t);

}
