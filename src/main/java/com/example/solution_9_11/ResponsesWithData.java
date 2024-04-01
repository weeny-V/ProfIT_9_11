package com.example.solution_9_11;

import com.example.solution_9_11.domain.Client;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsesWithData {
    private int code;
    private String message;
    private Object data;

    public ResponsesWithData(int code, String message, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }
}
