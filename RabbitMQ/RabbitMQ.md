# RabbitMQ

## todo

1. 生产者确认机制没太明白
2. 失败重试机制和失败处理策略有什么不同呢？

## 简单介绍

### 为什么要异步

同步调用的方式存在下列问题：

- **拓展性差**
- **性能下降**
- **级联失败**

异步调用的优势包括：

- **耦合度更低**
- **性能更好**
- **业务拓展性强**
- **故障隔离，避免级联失败**

异步通信也并非完美无缺，它存在下列缺点：

- **完全依赖于Broker的可靠性、安全性和性能**
- **架构复杂，后期维护和调试麻烦**

### 常见MQ

几种常见MQ的对比：

|       | **RabbitMQ**             | **ActiveMQ**                  | **RocketMQ** | **Kafka**  |
|-------|--------------------------|-------------------------------|--------------|------------|
| 公司/社区 | Rabbit                   | Apache                        | 阿里           | Apache     |
| 开发语言  | Erlang                   | Java                          | Java         | Scala&Java |
| 协议支持  | **AMQP，XMPP，SMTP，STOMP** | OpenWire,STOMP，REST,XMPP,AMQP | **自定义协议**    | 自定义协议      |
| 可用性   | **高**                    | 一般                            | **高**        | 高          |
| 单机吞吐量 | 一般                       | 差                             | 高            | 非常高        |
| 消息延迟  | **微秒级**                  | 毫秒级                           | 毫秒级          | 毫秒以内       |
| 消息可靠性 | **高**                    | 一般                            | **高**        | 一般         |

追求可用性：Kafka、 RocketMQ 、RabbitMQ
追求可靠性：RabbitMQ、RocketMQ
追求吞吐能力：RocketMQ、Kafka
追求消息低延迟：RabbitMQ、Kafka

据统计，目前国内消息队列使用最多的还是RabbitMQ，再加上其各方面都比较均衡，稳定性也好

### RabbitMQ介绍

两个映射的端口：

- 15672：RabbitMQ提供的管理控制台的端口
- 5672：RabbitMQ的消息发送处理接口

![image.png](https://cdn.nlark.com/yuque/0/2023/png/27967491/1687136827222-52374724-79c9-4738-b53f-653cc0805d22.png#averageHue=%23e8d7b3&clientId=u6a529863-cf4b-4&from=paste&height=495&id=ub8dd8df6&originHeight=614&originWidth=1458&originalType=binary&ratio=1.2395833730697632&rotation=0&showTitle=false&size=104273&status=done&style=none&taskId=uc0c132a5-73a3-4024-819f-61241da2511&title=&width=1176.2016429676203)

其中包含几个概念：

- `publisher`：生产者，也就是发送消息的一方
- `consumer`：消费者，也就是消费消息的一方
- `queue`：队列，存储消息。生产者投递的消息会暂存在消息队列中，等待消费者处理
- `exchange`：交换机，负责消息路由。生产者发送的消息由交换机决定投递到哪个队列。
- `virtual host`：虚拟主机，起到**数据隔离**的作用。每个虚拟主机相互独立，有各自的exchange、queue

### 基本操作

收发消息操作：

- 创建交换机和队列
- 绑定关系
- 发送消息

数据隔离操作：

- 新建用户
- 创建`virtual host`
- 点击页面右上角的`virtual host`下拉菜单，切换`virtual host`

## SpringAMQP

官方地址：[Spring AMQP](https://spring.io/projects/spring-amqp)
SpringAMQP提供了三个功能：

- **自动声明队列、交换机及其绑定关系**
- **基于注解的监听器模式，异步接收消息**
- **封装了RabbitTemplate工具，用于发送消息**

### 快速入门

#### 发送消息

1. 添加依赖

```xml
  <!--AMQP依赖，包含RabbitMQ-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
```

2. `application.yml`配置MQ地址

```yml
spring:
  rabbitmq:
    host: 127.0.0.1 # 你的虚拟机IP
    port: 5672 # 端口
    virtual-host: /hmall # 虚拟主机
    username: hmall # 用户名
    password: 123 # 密码
```

3. 新建发送Test

```java
@SpringBootTest
public class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSimpleQueue() {
        // 队列名称
        String queueName = "simple.queue";
        // 消息
        String message = "hello, spring amqp!";
        // 发送消息
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
```

#### 添加消息

1. 加依赖、配MQ
2. 新建接收类

```java
@Component
public class SpringRabbitListener {
	// 利用RabbitListener来声明要监听的队列信息
    // 将来一旦监听的队列中有了消息，就会推送给当前服务，调用当前方法，处理消息。
    // 可以看到方法体中接收的就是消息体的内容
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueueMessage(String msg) throws InterruptedException {
        System.out.println("spring 消费者接收到消息：【" + msg + "】");
    }
}
```

### WorkQueues模型

任务模型。简单来说就是**让多个消费者绑定到一个队列，共同消费队列中的消息**。

![](https://cdn.nlark.com/yuque/0/2023/jpeg/27967491/1687261956699-4b3c9999-ee86-4dda-a795-1ea5f4f9eef3.jpeg)

配置能者多劳

```yml
spring:
  rabbitmq:
    listener:
      simple:
        prefetch: 1 # 每次只能获取一条消息，处理完成才能获取下一个消息
```

### 交换机

Exchange（交换机）只负责转发消息，不具备存储消息的能力

交换机的类型有四种：

- **Fanout**：广播，将消息交给所有绑定到交换机的队列。我们最早在控制台使用的正是Fanout交换机
  ![image.png](https://cdn.nlark.com/yuque/0/2023/png/27967491/1687181415478-ea4bb17b-48bf-4303-9242-27703efb39d8.png#averageHue=%23fbf6f6&clientId=u0fe93ba5-a0ba-4&from=paste&height=389&id=u41b3ec34&originHeight=482&originWidth=1598&originalType=binary&ratio=1.2395833730697632&rotation=0&showTitle=false&size=84491&status=done&style=none&taskId=u0db849d5-c734-41f3-87c2-d1fe9ec7575&title=&width=1289.1428158177346)
- **Direct**：订阅，基于RoutingKey（路由key）发送给订阅了消息的队列
  ![image.png](https://cdn.nlark.com/yuque/0/2023/png/27967491/1687182404437-027a5191-b037-4033-baab-6bafd998161d.png#averageHue=%23fbf5f5&clientId=u0fe93ba5-a0ba-4&from=paste&height=430&id=uf5b6a678&originHeight=533&originWidth=1686&originalType=binary&ratio=1.2395833730697632&rotation=0&showTitle=false&size=93278&status=done&style=none&taskId=ud6ffb209-4207-40a6-a7ab-4977cab3b5d&title=&width=1360.1344101806637)
- **Topic**：通配符订阅，与Direct类似，只不过RoutingKey可以使用通配符
  ![image.png](https://cdn.nlark.com/yuque/0/2023/png/27967491/1687183148068-ad50ba76-0024-460b-9b24-3cf7a0fe172e.png#averageHue=%23f9f4f3&clientId=u0fe93ba5-a0ba-4&from=paste&height=305&id=u74a65bd0&originHeight=378&originWidth=1337&originalType=binary&ratio=1.2395833730697632&rotation=0&showTitle=false&size=57084&status=done&style=none&taskId=u90f6bfb4-4f10-4ebe-8edb-70856565f27&title=&width=1078.5882007185928)
- **Headers**：头匹配，基于MQ的消息头匹配，用的较少。【不讲】

描述下Direct交换机与Topic交换机的差异？

- Topic交换机接收的消息RoutingKey必须是多个单词，以 `**.**` 分割
- Topic交换机与队列绑定时的bindingKey可以指定通配符
- `#`：代表0个或多个词
- `*`：代表1个词

### 代码声明队列和交换机

#### 创建类

在consumer中创建一个类，声明队列和交换机：

```java
package com.itheima.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {
    /**
     * 声明交换机
     * @return Fanout类型交换机
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("hmall.fanout");
    }

    /**
     * 第1个队列
     */
    @Bean
    public Queue fanoutQueue1(){
        return new Queue("fanout.queue1");
    }

    /**
     * 绑定队列和交换机
     */
    @Bean
    public Binding bindingQueue1(Queue fanoutQueue1, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }
}
```

#### 注解声明

```java
// direct模式
@RabbitListener(bindings = @QueueBinding(
    value = @Queue(name = "direct.queue1"),
    exchange = @Exchange(name = "hmall.direct", type = ExchangeTypes.DIRECT),
    key = {"red", "blue"}
))
public void listenDirectQueue1(String msg){
    System.out.println("消费者1接收到direct.queue1的消息：【" + msg + "】");
}

// topic模式
@RabbitListener(bindings = @QueueBinding(
    value = @Queue(name = "topic.queue1"),
    exchange = @Exchange(name = "hmall.topic", type = ExchangeTypes.TOPIC),
    key = "china.#"
))
public void listenTopicQueue1(String msg){
    System.out.println("消费者1接收到topic.queue1的消息：【" + msg + "】");
}
```

### 消息转换器

默认转换器对map格式不友好，配置json转换器

在`publisher`和`consumer`两个服务中都引入依赖：

```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
    <version>2.9.10</version>
</dependency>
```

注意，如果项目中引入了`spring-boot-starter-web`依赖，则无需再次引入`Jackson`依赖。

## 发送者的可靠性

### 生产者重试机制

修改`publisher`模块的`application.yaml`文件，添加下面的内容：

```yaml
spring:
  rabbitmq:
    connection-timeout: 1s # 设置MQ的连接超时时间
    template:
      retry:
        enabled: true # 开启超时重试机制
        initial-interval: 1000ms # 失败后的初始等待时间
        multiplier: 1 # 失败后下次的等待时长倍数，下次等待时长 = initial-interval * multiplier
        max-attempts: 3 # 最大重试次数
```

如果对于业务性能有要求，建议禁用重试机制。如果一定要使用，请合理配置等待时长和重试次数，当然也可以考虑使用异步线程来执行发送消息的代码。

### 生产者确认机制

#### 开启生产者确认

在publisher模块的`application.yaml`中添加配置：

```yaml
spring:
  rabbitmq:
    publisher-confirm-type: correlated # 开启publisher confirm机制，并设置confirm类型
    publisher-returns: true # 开启publisher return机制
```

这里`publisher-confirm-type`有三种模式可选：

- `none`：关闭confirm机制
- `simple`：同步阻塞等待MQ的回执
- **`correlated`：MQ异步回调返回回执**【选这个】

#### 定义ReturnCallback

每个`RabbitTemplate`只能配置一个`ReturnCallback`，因此我们可以在配置类中统一设置。我们在publisher模块定义一个配置类：
![image.png](https://cdn.nlark.com/yuque/0/2023/png/27967491/1687341529298-150b401d-67f9-4958-acdb-0d3147b0532b.png#averageHue=%23f9fbf8&clientId=ue4302575-73b6-4&from=paste&height=278&id=u6d09b8df&originHeight=345&originWidth=808&originalType=binary&ratio=1.2395833730697632&rotation=0&showTitle=false&size=33987&status=done&style=none&taskId=uf816a0ec-4ff4-4c09-bffc-766884fb5e7&title=&width=651.8319118778032)
内容如下：

```java
@Slf4j
@Configuration
public class MqConfirmConfig implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        // 配置回调
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                log.debug("收到消息的return callback，exchange:{}, key:{}, msg:{}, code:{}, text:{}",
                        returned.getExchange(), returned.getRoutingKey(), returned.getMessage(),
                        returned.getReplyCode(), returned.getReplyText());
            }
        });
    }
}
```

#### 定义ConfirmCallback

由于每个消息发送时的处理逻辑不一定相同，因此ConfirmCallback需要在每次发消息时定义。具体来说，是在调用RabbitTemplate中的convertAndSend方法时，多传递一个参数：
![image.png](https://cdn.nlark.com/yuque/0/2023/png/27967491/1687348187394-21a3698a-277a-478b-8cb8-2ee5bc79207f.png#averageHue=%23f1efed&clientId=ue4302575-73b6-4&from=paste&height=167&id=ubbb0d508&originHeight=207&originWidth=939&originalType=binary&ratio=1.2395833730697632&rotation=0&showTitle=false&size=26725&status=done&style=none&taskId=u0ffba104-eb55-4f79-be54-f249333680d&title=&width=757.5125807589818)
这里的CorrelationData中包含两个核心的东西：

- `id`：消息的唯一标示，MQ对不同的消息的回执以此做判断，避免混淆
- `SettableListenableFuture`：回执结果的Future对象

将来MQ的回执就会通过这个`Future`来返回，我们可以提前给`CorrelationData`中的`Future`添加回调函数来处理消息回执：
![image.png](https://cdn.nlark.com/yuque/0/2023/png/27967491/1687348449866-dee08277-6bc9-4463-9cb8-95013e05a6a2.png#averageHue=%23f3f2f0&clientId=ue4302575-73b6-4&from=paste&height=194&id=u536cb2c1&originHeight=241&originWidth=940&originalType=binary&ratio=1.2395833730697632&rotation=0&showTitle=false&size=26129&status=done&style=none&taskId=u1d347aea-f7e9-43e2-875e-773b69e1bdf&title=&width=758.3193034221969)

我们新建一个测试，向系统自带的交换机发送消息，并且添加`ConfirmCallback`：

```java
    @Test
    void testConfirmCallback() throws InterruptedException {
        // 1.创建cd
        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        // 2.添加ConfirmCallback
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("消息回调失败", ex);
            }

            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                log.debug("收到confirm callback回执");
                if(result.isAck()){
                    // 消息发送成功
                    log.debug("消息发送成功，收到ack");
                }else{
                    // 消息发送失败
                    log.error("消息发送失败，收到nack， 原因：{}", result.getReason());
                }
            }
        });

        rabbitTemplate.convertAndSend("hmall.direct123", "red", "hello", cd);

        Thread.sleep(2000);
    }
```

执行结果如下：
![image.png](https://cdn.nlark.com/yuque/0/2023/png/27967491/1687351726363-c27337c1-cd6e-497e-96ad-ac55fe4cb9e4.png#averageHue=%23f9f6df&clientId=ue4302575-73b6-4&from=paste&height=321&id=u37548b33&originHeight=398&originWidth=1657&originalType=binary&ratio=1.2395833730697632&rotation=0&showTitle=false&size=224878&status=done&style=none&taskId=u5b86f40b-a7f8-4c5f-8fae-4edb9ab2cee&title=&width=1336.7394529474257)
可以看到，由于传递的`RoutingKey`是错误的，路由失败后，触发了`return callback`，同时也收到了ack。
当我们修改为正确的`RoutingKey`以后，就不会触发`return callback`了，只收到ack。
而如果连交换机都是错误的，则只会收到nack。

**注意：**
**开启生产者确认比较消耗MQ性能，一般不建议开启。因为一般都是编程错误导致的。**大家思考一下触发确认的几种情况：

- 路由失败：一般是因为RoutingKey错误导致，往往是编程导致
- 交换机名称错误：同样是编程错误导致
- MQ内部故障：这种需要处理，但概率往往较低。因此只有对消息可靠性要求非常高的业务才需要开启，而且仅仅需要开启ConfirmCallback处理nack就可以了。

## MQ可靠性

### 数据持久化

包括：

- **交换机持久化**：配置交换机的`Durability`参数
- **队列持久化**：配置队列的`Durability`参数
- **消息持久化**：配置一个`properties`，把`delivery_mode`改成2

数据持久化速度也会慢一点，因为还要往磁盘写就会慢，所以我们引入LazyQueue

### LazyQueue

1. 控制台配置
   在添加队列的时候，添加`x-queue-mod=lazy`参数即可设置队列为Lazy模式
   ![image.png](https://cdn.nlark.com/yuque/0/2023/png/27967491/1687421366634-1dfca4a6-2407-43c2-8e65-fd7ba9e660dc.png#averageHue=%23f8f6f5&clientId=ud69cf815-2833-4&from=paste&height=361&id=auTfj&originHeight=447&originWidth=1127&originalType=binary&ratio=1.2395833730697632&rotation=0&showTitle=false&size=34943&status=done&style=none&taskId=ue22630f9-97c2-47c9-989b-e1963580eb6&title=&width=909.1764414434211)


2. 代码配置Lazy模式

   在利用SpringAMQP声明队列的时候，添加`x-queue-mod=lazy`参数也可设置队列为Lazy模式：

```java
@Bean
public Queue lazyQueue(){
    return QueueBuilder
            .durable("lazy.queue")
            .lazy() // 开启Lazy模式
            .build();
}
```

当然，我们也可以**基于注解**来声明队列并设置为Lazy模式：

```java
@RabbitListener(queuesToDeclare = @Queue(
        name = "lazy.queue",
        durable = "true",
        arguments = @Argument(name = "x-queue-mode", value = "lazy")
))
public void listenLazyQueue(String msg){
    log.info("接收到 lazy.queue的消息：{}", msg);
}
```

## 消费者可靠性

### 消费者确认机制

RabbitMQ提供了消费者确认机制（**Consumer Acknowledgement**）。即：当消费者处理消息结束后，应该向RabbitMQ发送一个回执，告知RabbitMQ自己消息处理状态。回执有三种可选值：

- **ack：成功处理消息，RabbitMQ从队列中删除该消息**
- **nack：消息处理失败，RabbitMQ需要再次投递消息**
- reject：消息处理失败并拒绝该消息，RabbitMQ从队列中删除该消息

由于消息回执的处理代码比较统一，因此SpringAMQP帮我们实现了消息确认。并允许我们通过配置文件设置ACK处理方式，有三种模式：

- `none`：不处理。即消息投递给消费者后立刻ack，消息会立刻从MQ删除。非常不安全，不建议使用
- `manual`：手动模式。需要自己在业务代码中调用api，发送`ack`或`reject`，存在业务入侵，但更灵活
- `auto`：**自动模式。SpringAMQP利用AOP对我们的消息处理逻辑做了环绕增强，当业务正常执行时则自动返回`ack`.
  当业务出现异常时，根据异常判断返回不同结果：**
    - 如果是**业务异常**，会自动返回`nack`；
    - 如果是**消息处理或校验异常**，自动返回`reject`;

代码配置

```yml
spring:
  rabbitmq:
    listener:
      simple:
        acknowledge-mode: auto # 抛异常情况重新投递，直到成功为止返回ack
```

Test

```java
@RabbitListener(queues = "simple.queue")
public void listenSimpleQueueMessage(String msg) throws InterruptedException {
    log.info("spring 消费者接收到消息：【" + msg + "】");
    if (true) {
        throw new RuntimeException("故意的");
    }
    log.info("消息处理完成");
}
```

### 失败重试机制

修改consumer服务的application.yml文件，添加内容：

```yml
spring:
  rabbitmq:
    listener:
      simple:
        retry:
          enabled: true # 开启消费者失败重试
          initial-interval: 1000ms # 初识的失败等待时长为1秒
          multiplier: 1 # 失败的等待时长倍数，下次等待时长 = multiplier * last-interval
          max-attempts: 3 # 最大重试次数
          stateless: true # true无状态；false有状态。如果业务中包含事务，这里改为false
```

### 失败处理策略

Spring允许我们**自定义重试次数耗尽后的消息处理策略，这个策略是由`MessageRecovery`接口来定义的**，它有3个不同实现：

- `RejectAndDontRequeueRecoverer`：重试耗尽后，直接`reject`，丢弃消息。默认就是这种方式
- `ImmediateRequeueMessageRecoverer`：重试耗尽后，返回`nack`，消息重新入队
- `RepublishMessageRecoverer`：重试耗尽后，将失败消息投递到指定的交换机

比较优雅的一种处理方案是`RepublishMessageRecoverer`，失**败后将消息投递到一个指定的，专门存放异常消息的队列，后续由人工集中处理
**。

在consumer服务中定义处理失败消息的交换机和队列并绑定

```java
@Configuration
@ConditionalOnProperty(name = "spring.rabbitmq.listener.simple.retry.enabled", havingValue = "true")
public class ErrorMessageConfig {
    @Bean
    public DirectExchange errorMessageExchange(){
        return new DirectExchange("error.direct");
    }
    @Bean
    public Queue errorQueue(){
        return new Queue("error.queue", true);
    }
    @Bean
    public Binding errorBinding(Queue errorQueue, DirectExchange errorMessageExchange){
        return BindingBuilder.bind(errorQueue).to(errorMessageExchange).with("error");
    }

    @Bean
    public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate){
        return new RepublishMessageRecoverer(rabbitTemplate, "error.direct", "error");
    }
}
```

### 业务幂等性

**在程序开发中，则是指同一个业务，执行一次或多次对业务状态的影响是一致的。**例如：

- 根据id删除数据
- 查询数据
- 新增数据（相同id的）

。这里给出两种方案保证消息处理的幂等性：**【怎么这么像锁的】**

- **唯一消息ID**

SpringAMQP的MessageConverter自带了MessageID的功能，我们只要开启这个功能即可。
以Jackson的消息转换器为例：

```java
@Bean
public MessageConverter messageConverter(){
    // 1.定义消息转换器
    Jackson2JsonMessageConverter jjmc = new Jackson2JsonMessageConverter();
    // 2.配置自动创建消息id，用于识别不同消息，也可以在业务中基于ID判断是否是重复消息
    jjmc.setCreateMessageIds(true);
    return jjmc;
}
```

- **业务状态判断**

相比较而言，消息ID的方案需要改造原有的数据库，所以更推荐使用业务判断的方案。

### 兜底方案

【总结：定时任务主动查询解决问题】

### 总结

支付服务与交易服务之间的订单状态一致性是如何保证的？

- 首先，支付服务会正在用户支付成功以后**利用MQ消息通知交易服务**，完成订单状态同步。
- 其次，为了保证MQ消息的可靠性，我们采用了**生产者确认机制、消费者确认、消费者失败重试**等策略，确保消息投递的可靠性
- 最后，我们还在交易服务设置了**定时任务，定期查询订单支付状态**。这样即便MQ通知失败，还可以利用定时任务作为兜底方案，确保订单支付状态的最终一致性。

## 延迟消息

### 死信交换机

![image.png](https://cdn.nlark.com/yuque/0/2023/png/27967491/1687574086294-106fe14b-6652-4783-a6c3-3d722d1f5232.png#averageHue=%23fbf5f5&clientId=u76b62a19-f8dc-4&from=paste&height=373&id=ub429262c&originHeight=462&originWidth=1633&originalType=binary&ratio=1.2395833730697632&rotation=0&showTitle=false&size=81718&status=done&style=none&taskId=u244f2a30-7d51-48a2-969b-b880a7f2442&title=&width=1317.3781090302632)

publisher发送了一条消息，但最终consumer在5秒后才收到消息，这样实现延迟消息【比较麻烦】

**当一个消息的TTL到期以后不一定会被移除或投递到死信交换机，而是在消息恰好处于队首时才会被处理。**

#### DelayExchange插件

官方文档说明：
[Scheduling Messages with RabbitMQ | RabbitMQ - Blog](https://blog.rabbitmq.com/posts/2015/04/scheduling-messages-with-rabbitmq)

插件下载地址：
[GitHub - rabbitmq/rabbitmq-delayed-message-exchange: Delayed Messaging for RabbitMQ](https://github.com/rabbitmq/rabbitmq-delayed-message-exchange)

#### 声明延迟交换机

1. 基于注解方式：

```java
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(name = "delay.queue", durable = "true"),
        exchange = @Exchange(name = "delay.direct", delayed = "true"),
        key = "delay"
))
public void listenDelayMessage(String msg){
    log.info("接收到delay.queue的延迟消息：{}", msg);
}
```

2. 基于`@Bean`的方式：

```java
package com.itheima.consumer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DelayExchangeConfig {

    @Bean
    public DirectExchange delayExchange(){
        return ExchangeBuilder
                .directExchange("delay.direct") // 指定交换机类型和名称
                .delayed() // 设置delay的属性为true
                .durable(true) // 持久化
                .build();
    }

    @Bean
    public Queue delayedQueue(){
        return new Queue("delay.queue");
    }
    
    @Bean
    public Binding delayQueueBinding(){
        return BindingBuilder.bind(delayedQueue()).to(delayExchange()).with("delay");
    }
}

```

#### 发送延迟消息

发送消息时，必须通过x-delay属性设定延迟时间：

```java
@Test
void testPublisherDelayMessage() {
    // 1.创建消息
    String message = "hello, delayed message";
    // 2.发送消息，利用消息后置处理器添加消息头
    rabbitTemplate.convertAndSend("delay.direct", "delay", message, new MessagePostProcessor() {
        @Override
        public Message postProcessMessage(Message message) throws AmqpException {
            // 添加延迟消息属性
            message.getMessageProperties().setDelay(5000);
            return message;
        }
    });
}
```

**注意：**
延迟消息插件内部会维护一个本地数据库表，同时使用Elang
Timers功能实现计时。如果消息的延迟时间设置较长，可能会导致堆积的延迟消息非常多，会带来较大的CPU开销，同时延迟消息的时间会存在误差。
因此，**不建议设置延迟时间过长的延迟消息**。