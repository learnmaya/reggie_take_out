package com.itheima.reggie.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Generic return results, the server-side response data will eventually be encapsulated into this object
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    private Map map = new HashMap();

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public boolean isSuccess() {
        return this.code == 1;
    }
}
