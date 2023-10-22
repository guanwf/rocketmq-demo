package com.rocketmq.demo.consume.serviceImpl;

import com.rocketmq.demo.common.beans.Goods;
import com.rocketmq.demo.consume.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockServiceImpl implements StockService {
    @Override
    public boolean handler(Goods goods) throws Exception {
        log.info("记账{}",goods.getGoodsName());
        if (goods.getGoodsId()==10){
            throw new Exception("库存不足！");
        }
        return true;
    }
}
