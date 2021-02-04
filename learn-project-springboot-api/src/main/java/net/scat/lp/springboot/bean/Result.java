package net.scat.lp.springboot.bean;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private int code;

    private String message;

    private T data;

    public Result(ResultCode code, T data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    public Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS, data);
    }

    public Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    public Result<T> error(ResultCode code) {
        return new Result<>(code, null);
    }
}
