package com.rocketmq.demo.consume.support;

import com.rocketmq.demo.common.beans.Goods;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.goodsTopic}", consumerGroup = "goods-consumer")
public class GoodsEventConsumer implements RocketMQListener<Goods> {

    @Override
    public void onMessage(Goods goods) {
        log.info("--> 接收商品: {},{}", goods.getGoodsId(),goods.getGoodsName());
    }
}
