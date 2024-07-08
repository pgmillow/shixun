package com.penguin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private int code;  //业务状态码 0-成功 1-失败
    private String msg;
    private T data;


    public static <T> Result<T> success(T data) {
        return new Result<>(0,"操作成功",data);
    }

    public static  Result success() {
        return new Result(0,"操作成功",null);
    }

    public static Result error(String msg) {
        return new Result(1,msg,null);
    }
}
