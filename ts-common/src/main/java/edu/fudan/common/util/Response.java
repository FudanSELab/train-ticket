package edu.fudan.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Response<T> {
//    public Response(Integer status, String msg, T data) {
//        this.status = status;
//        this.msg = msg;
//        this.data = data;
//    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getStatus() {
        return status;
    }

    Integer status; // 1 true, 0 false
    String msg;
    T data;
}
