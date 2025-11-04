package edu.fudan.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

/**
 * @author fdse
 */
@Data
@ToString
public class Response<T> {

    /**
     * 1 true, 0 false
     */
    Integer status;

    String msg;
    T data;

    /**
     * Default constructor
     */
    public Response() {
    }

    /**
     * Constructor with all parameters
     * @param status the status (1 for true, 0 for false)
     * @param msg the message
     * @param data the data
     */
    public Response(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * Constructor with status and message only
     * @param status the status (1 for true, 0 for false)
     * @param msg the message
     */
    public Response(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        this.data = null;
    }

    /**
     * Constructor with status and data only
     * @param status the status (1 for true, 0 for false)
     * @param data the data
     */
    public Response(Integer status, T data) {
        this.status = status;
        this.msg = null;
        this.data = data;
    }

    // Getters and Setters
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response<?> response = (Response<?>) o;
        return Objects.equals(status, response.status) &&
                Objects.equals(msg, response.msg) &&
                Objects.equals(data, response.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, msg, data);
    }
}
