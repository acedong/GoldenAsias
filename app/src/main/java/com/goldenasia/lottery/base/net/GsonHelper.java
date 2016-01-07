package com.goldenasia.lottery.base.net;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

public class GsonHelper {
    private static Gson gson = newGson();

    public static HashMap<String, String> convert2Map(JsonObject object) {
        if (null == object) {
            return new HashMap<>();
        }

        return (HashMap<String, String>) new Gson().fromJson(object.toString(),
                new TypeToken<HashMap<String, String>>() {
                }.getType());
    }

    public static JsonObject convert2gson(Object o) {
        String json = toJson(o);
        JsonParser jsonParser = new JsonParser();
        return (JsonObject)jsonParser.parse(json);
    }

    /** 将‘实例’转成一般的json字符串 */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /** 将json字符串转换成‘实例’ */
    public static <T> T fromJson(String json, Class<T> clz) {
        return gson.fromJson(json, clz);
    }
    /**
     * Spring framwork使用的Jackson json与Google Gson不兼容，Timestamp json and Data json 导致JsonSyntaxException
     * @return
     */
    public static Gson newGson() {
        GsonBuilder builder = new GsonBuilder();
        //builder.registerTypeAdapter(Date.class, new GsonJacksonDateAdapter());
        //builder.registerTypeAdapter(Timestamp.class, new GsonJacksonTimestampAdapter());
        return builder.create();
    }

    public static <T> T fromJson(String json, TypeToken<T> token, String datePattern) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if (TextUtils.isEmpty(datePattern)) {
            datePattern = "yyyy-MM-dd HH:mm:ss.SSSZ";
        }
        builder.setDateFormat(datePattern);

        Gson gson = builder.create();
        try {
            return (T) gson.fromJson(json, token.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("GsonHelper", json + " 无法转换为 " + token.getRawType().getName() + " 对象!", ex);
            return null;
        }
    }

    /** 将JSON转换成对应的{@link RestResponse}对象，适用于{@link RestResponse#response}非泛型，如：
     *  <pre>
     *  String json = "{\"errorCode\":200,\"response\":\"OK\"}";
     *  RestResponse&lt;String&gt; response = getRestResponse(json, String.class);
     *  </pre>
     *  若是带泛型的，需要手写实现代码，或用{@link GsonHelper#fromJson}如：
     *  <pre>
     *   RestResponse&lt;ArrayList&lt;String&gt;&gt; response = new Gson().fromJson(json,
     new TypeToken&lt;RestResponse&lt;ArrayList&lt;String&gt;&gt;&gt;(){}.getType());
     *  </pre>
     *  */
    public static <T> RestResponse<T> getRestResponse(String json, final Class<T> clazz) {
        ParameterizedType type = new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{ clazz };
            }

            @Override
            public Type getOwnerType() {
                return null;
            }

            @Override
            public Type getRawType() {
                return RestResponse.class;
            }
        };

        return new Gson().fromJson(json, type);
    }
}
