# my-ddd

## 项目信息
- 技术栈：Java 17、Spring Boot 3、MySQL 8.x、Redis 6.x、MyBatis-Plus

## 运行条件
- 本机需要安装docker

## 项目结构

## 规范
- 所有Builder都是多例的，参考：`com.lzb.domain.order.aggregate.builder.OrderBuilder`
- rpc的请求和响应的结尾：req/rsp
- View表示返回给前端的视图对象
- Convert/Assemble不要强制只声明static方法，特别是View的场景，显示的字段需要从各个领域查询，可能要走repository或者gateway，不可能只通过参数传递

## 疑问
- controller的cmd实体在app层定义，如果要传到domain层，那就只能在domian定义了，有点别扭...(最后引入XxxxAssembler把dto转换domain对象)
- 删除OrderDetail.orderId，这是持久化层概念，领域层用不到，删掉之后整个代码逻辑清晰很多
