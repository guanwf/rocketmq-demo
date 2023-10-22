package com.rocketmq.demo.common.beans;

import lombok.Data;

import java.io.Serializable;

@Data
public class Goods implements Serializable {
    private int goodsId;
    private String goodsName;
}
