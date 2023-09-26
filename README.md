# my-ddd

## 项目信息
- 技术栈：Java 17、Spring Boot 3、PostgreSQL 14、Redis 6.x、MyBatis-Plus、Docker、maven
- 基于COLA的ddd四层架构
  ![image](https://github.com/lizebin0918/my-ddd/blob/main/ddd%E5%88%86%E5%B1%82.drawio.png)

## 运行条件
- 安装docker环境[下载地址](https://www.docker.com/)
- Java 17+

## 本地启动
- web
  - `start/com.lzb.Application`，通过docker-compose自动下载镜像运行PostgreSQL和Redis，再启动Spring Boot主程序
- test
  - 在命令行执行：`mvn verify`，将运行单元测试、集成测试

## lombok
- 全局配置文件：`lombok.config`
- 产品（生产）代码禁止用setter，通过全参构造+Builder实例化领域对象（聚合根、实体、值对象），推荐声明：
```java
@Slf4j
@Getter
// 方便测试构造
@Setter(AccessLevel.PACKAGE)
public class A {
    // 构造方法声明
    @JsonCreator
    public A(int id) {
    }
}
```
- 所有领域Builder都是多例的，参考：`com.lzb.domain.order.aggregate.builder.OrderBuilder`或者用lombok的`@Builder`
```java

//@SuperBuilder
@Builder
@Jacksonized
class A {}

```

## 编码规范
- 从adapter进入domain通过Builder构造领域对象，从infr进入domain通过全参构造领域对象
- rpc的请求和响应的结尾：req/rsp
- View表示返回给前端的视图对象
- Convert/Assemble不要强制只声明static方法，特别是View的场景，显示的字段需要从各个领域查询，可能要走repository或者gateway，不可能只通过参数传递

## 疑问
- controller的cmd实体在app层定义，如果要传到domain层，那就只能在domian定义了，有点别扭...(最后引入XxxxAssembler把dto转换domain对象)
- 删除OrderDetail.orderId，这是持久化层概念，领域层用不到，删掉之后整个代码逻辑清晰很多
