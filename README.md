# my-ddd

## 项目信息
- 技术栈：Java 21、Spring Boot 3.2、PostgreSQL 14、Redis 6.x、MyBatis-Plus、Docker、maven
- 模块说明
  - component、test-component相关工具类
  - adapter
    - `web`：web层，包含controller、dto、assembler
    - `rpc`：远程调用
  - app：应用层，cmd-写、query-查
  - domain：领域层，包含聚合根、实体、值对象、领域服务、领域事件、枚举定义
    - `repository`:~~只做新增、修改、删除~~，可以做crud，返回聚合根或者值对象，domain不需要gateway
    - ~~`gateway`:只做查询（理由：domain层只面向仓储）~~
  - infr：基础设施，依赖倒置其它层，主要用于持久化
  - start：启动模块
- 基于COLA的ddd四层架构
  ![image](https://github.com/lizebin0918/my-ddd/blob/main/ddd%E5%88%86%E5%B1%82.drawio.png)

## 运行条件
- 安装docker环境[下载地址](https://www.docker.com/)
- Java 21+

## 本地启动
- web
  - `Application`，通过docker-compose自动下载镜像运行PostgreSQL和Redis，再启动Spring Boot主程序
- test
  - 在命令行执行：`mvn verify`，将运行单元测试、集成测试

## lombok
- 全局配置文件：`lombok.config`
- 领域模型禁止用setter，通过全参构造+Builder实例化领域对象（聚合根、实体、值对象），推荐声明：
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
- 所有领域Builder都是多例的，参考：`OrderBuilder`或者用lombok的`@Builder`

## mybatis-plus
- 配置类：`MybatisPlusConfig`，注意：全局的更新和插入策略
  - `dbConfig.setUpdateStrategy(FieldStrategy.ALWAYS);`
  - `dbConfig.setInsertStrategy(FieldStrategy.NOT_EMPTY);`
- 所有持久化模型以`Po`结尾，并且继承`BasePo`，包含`addTime`、`updateTime`
- Po的枚举字段类型，统一继承`EnumValue`，在`DefaultEnumValueTypeHandler`转换

## 测试相关
- 单元测试继承：`BaseUnitTest`
- mapper单元测试：`BaseMapperUnitTest`
- controller单元测试：`BaseControllerUnitTest`
- 集成测试继承：`BaseIntegrationTest`
- 命名规范：`被测类名 + 被测方法名 + Unit/Integration + Test`，参考`OrderUnitTest`、`OrderQueryAppServiceIntegrationTest`

## 编码规范
- 架构检测参考：`ArchUnitTest`
- 从adapter进入domain通过Builder构造领域对象，从infr进入domain通过全参构造领域对象
- rpc的请求和响应的结尾：Req/Rsp
- View表示返回给前端的视图对象
- Convert/Assemble不强制声明static方法，特别是View的场景，显示的字段需要从各个领域查询，可能要走repository或者gateway，不可能只通过参数传递

## 疑问
- controller的cmd实体在app层定义，如果要传到domain层，那就只能在domian定义了，有点别扭
  - 结论：最后引入XxxxAssembler把dto转换domain对象（cmd里面的各个参数可能涉及多个逻辑，不仅仅是领域对象，可能是领域服务的传参）
  - domain层可以定义领域内的dto或者值对象，但是不定义外层的dto。内部定义的dto，现在用于返回值，外部的返回值需要由domain定义
- 删除OrderDetail.orderId，这是持久化层用到的字段，领域层用不到，删掉之后整个代码逻辑清晰很多
  - 结论：领域层只关注业务字段即可
- testcontainer是否可以去掉？直接用docker-compose运行测试？现在会报错
- diff识别差异的字段，只更新变更的字段是否可行？
- 所有Builder都要写`public static OrderBuilder newInstance() {
  return SpringHelper.getBean(OrderBuilder.class);
  }`是否可以通过避免这种重复？
- 感觉手动builder更简洁
- 聚合根、实体、值对象的校验应该用validation，支持更加复杂的规则
