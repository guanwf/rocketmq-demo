package com.rocketmq.demo.common.beans;

import lombok.Data;

@Data
public class StockResult {
    private int flag;
    private String message;
    private Object data;
}
