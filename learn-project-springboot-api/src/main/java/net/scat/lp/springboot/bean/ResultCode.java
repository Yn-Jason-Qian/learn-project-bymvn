package net.scat.lp.springboot.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200, "Success."),
    NOT_FOUND(404, "Not found."),
    INTERNAL_ERROR(500, "Internal Server error.");

    private final int code;

    private final String message;
}
