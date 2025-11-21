package edu.fudan.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.function.Function;

/**
 * @author fdse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Response<T> {

    /**
     * 1 true, 0 false
     */
    Integer status;

    String msg;
    T data;

    public <R> Response<R> map(Function<? super T, ? extends R> mapper) {
        R mapped = data == null ? null : mapper.apply(data);
        return new Response<>(status, msg, mapped);
    }
}
