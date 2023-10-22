package com.rocketmq.demo.consume.support;

import com.rocketmq.demo.common.beans.Goods;
import com.rocketmq.demo.common.beans.StockResult;
import com.rocketmq.demo.consume.service.StockService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.stockTopic}", consumerGroup = "stock-consumer", selectorExpression = "${demo.rocketmq.tag}")
public class StockEventConsumer implements RocketMQReplyListener<Goods, StockResult> {

    @Autowired
    StockService stockService;

    @Override
    public StockResult onMessage(Goods goods) {
        StockResult stockResult = new StockResult();
        stockResult.setFlag(0);
        if (goods.getGoodsId()==10){
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            if (!stockService.handler(goods)) {
                stockResult.setFlag(-1);
                stockResult.setMessage("记账失败！");
            }
        } catch (Exception ex) {
            stockResult.setFlag(-1);
            stockResult.setMessage(ex.getMessage());
        } finally {
            return stockResult;
        }
    }

}
