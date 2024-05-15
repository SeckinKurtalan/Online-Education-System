package com.egeuniversity.onlineeducationsystem.utility;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Date;

public abstract class Utility {

    public void validatePageAndSize(int page, int size) throws Exception {
        if (page <= 0) {
            throw new RuntimeException("Error: Invalid page number. Page must be greater than 0.");
        } else if (size <= 0) {
            throw new RuntimeException("Error: Invalid size number. Size must be greater than 0.");
        }
    }

    public static Date getNow() {
        return Date.from(Instant.now());
    }

}
