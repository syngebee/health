package com.itheima.health.dto;

import lombok.Data;

@Data
public class ErrorInfoDTO {
    private Integer state;
    private String message;
    private Boolean flag;
}
