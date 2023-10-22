package com.rocketmq.demo.consume.service;

import com.rocketmq.demo.common.beans.Goods;

public interface StockService {
    boolean handler(Goods goods) throws Exception;
}
