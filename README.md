# my-ddd

## 项目信息
- 技术栈：Java 17、Spring Boot 3、MySQL 8.x、Redis 6.x、MyBatis-Plus

## 项目结构

## 规范
- 所有Builder都是多例的，参考：`com.lzb.domain.order.aggregate.builder.OrderBuilder`
- rpc的请求和响应的结尾：req/rsp

## 疑问
- controller的cmd实体在app层定义，如果要传到domain层，那就只能在domian定义了，有点别扭...

