package com.rocketmq.demo.produce.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocketmq.demo.common.beans.Goods;
import com.rocketmq.demo.common.beans.StockResult;
import com.rocketmq.demo.common.beans.StockType;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQLocalRequestCallback;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RestController
@Slf4j
public class DemoController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${demo.rocketmq.topic}")
    private String springTopic;

    @Value("${demo.rocketmq.goodsTopic}")
    private String goodsTopic;

    @Value("${demo.rocketmq.stockTopic}")
    private String stockTopic;

    /**
     * http://127.0.0.1:8080/message?str=1111111111
     *
     * @param str
     * @param response
     * @param req
     * @return
     */
    @GetMapping("message")
    public String get(@RequestParam("str") String str, HttpServletResponse response, HttpServletRequest req) {
        SendResult sendResult = rocketMQTemplate.syncSend(springTopic, str);
        System.out.printf("发送数据 topic %s content=%s ;sendResult=%s %n", springTopic, str, sendResult);

        return sendResult.getMsgId();
    }

    /**
     * http://127.0.0.1:8080/goods
     *
     * @return
     */
    @PostMapping("goods")
    public String putGoods(@RequestBody JSONObject jsonObject) {

        Goods goods = new Goods();
        goods.setGoodsId(jsonObject.getInteger("goodsid"));
        goods.setGoodsName(jsonObject.getString("goodsname"));

        rocketMQTemplate.asyncSend(goodsTopic, goods, new SendCallback() {
            @Override
            public void onSuccess(SendResult var1) {
                System.out.printf("Sucess SendResult=%s %n", var1);
            }

            @Override
            public void onException(Throwable var1) {
                System.out.printf("error Throwable=%s %n", var1);
            }

        });
        return "OK";
    }

    @PostMapping("stock")
    public String putStock(@RequestBody JSONObject jsonObject) {
        Goods goods = new Goods();
        goods.setGoodsId(jsonObject.getInteger("goodsid"));
        goods.setGoodsName(jsonObject.getString("goodsname"));
        log.info("处理stock={}", goods.getGoodsId());
        Long timeOut = 2000L;

        rocketMQTemplate.sendAndReceive(stockTopic, goods, new RocketMQLocalRequestCallback<StockResult>() {

            @Override
            public void onSuccess(StockResult o) {
                log.info("stockReulst.flag={},message={}", o.getFlag(), o.getMessage());
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("异常！{}", throwable);
            }

        }, timeOut);
        return "OK";
    }

    @RequestMapping(value = "date", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
    @ResponseBody
    public StockType submit(@RequestBody String json) throws IOException {
        StockType stockType = objectMapper.readValue(json, StockType.class);
        stockType.setCreatedTime(new Date());
        return stockType;
    }

}
