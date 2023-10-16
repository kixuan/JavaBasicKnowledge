package com.itheima.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 调用SpringAMQP的api进行配置
 * 就不用去rabbitmq的管理界面进行配置了
 */
@Configuration
public class FanoutConfiguration {

    // 配置交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        // ExchangeBuilder.fanoutExchange("").build();
        return new FanoutExchange("hmall.fanout2");
    }

    // 配置队列
    @Bean
    public Queue fanoutQueue3() {
        // QueueBuilder.durable("true").build();
        return new Queue("fanout.queue3");
    }

    // 配置绑定关系
    @Bean
    public Binding fanoutBinding3(Queue fanoutQueue3, FanoutExchange fanoutExchange) {
        // 把fanout.queue3和hmall.fanout2绑定在一起
        return BindingBuilder.bind(fanoutQueue3).to(fanoutExchange);
    }

    @Bean
    public Queue fanoutQueue4() {
        return new Queue("fanout.queue4");
    }

    @Bean
    public Binding fanoutBinding4() {
        // 直接传入队列的创建方法进行绑定
        // 把fanout.queue4和hmall.fanout2绑定在一起
        return BindingBuilder.bind(fanoutQueue4()).to(fanoutExchange());
    }
}
