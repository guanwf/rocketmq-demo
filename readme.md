
rocketmq-demo
演示mq发送与消费的demo


启动方式

1、
sh ./bin/mqnamesrv &

2、
sh ./bin/mqbroker -n 127.0.0.1:9876 autoCreateTopicEnable=true -c ./conf/broker.conf &
sh ./bin/mqbroker -n 127.0.0.1:9876 autoCreateTopicEnable=true autoCreateSubscriptionGroup=true -c ./conf/broker.conf &


SpringCloud集成RocketMQ实现事务消息方案
https://blog.csdn.net/weixin_44062339/article/details/100180487

