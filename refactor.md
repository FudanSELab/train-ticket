
# Refactor

- 重构 TODO：
  - 检查代码中是否根据列车代码分别调用 ts-xxx-service 和 ts-xxx-other-service 的接口，如果是，则移除 ts-xxx-other-service 的依赖，统一调用 ts-xxx-service 的接口
  - 检查哪一些接口需要管理员权限：它们通常涉及读取、删除、修改、新建不属于当前用户的数据
    - 这一类接口需要移到 AdminController 中，如果没有 AdminController，则创建一个
    - 这一类接口的路径通常由 /api/v1/serviceA/admin 开头
    - 在 SecurityConfig 中为 /api/v1/serviceA/admin 路径添加权限配置，要求有 ADMIN role
  - 检查当前重构的服务是否通过 resttemapl调用了其它服务，如果是，则使用 ts-client-sdk 中的 Client 类来调用其它服务的接口，而不是直接使用 RestTemplate 来调用。使用 ts-client-sdk 时，应尽量使用 ts-client-sdk 中的 DTO 类来作为参数和返回值。
  - CORS
    - 检查是否在 SecurityConfig 中配置了 CORS，如果没有，则统一配置允许所有域名访问。
    - 检查 controller 中是否使用注解配置了 CORS，如果配置了，则移除。
  - 检查接口路径有多个单词，则使用短横线连接，如：GET /api/v1/food-list/station-foods
  - 检查接口响应类型 Response 是否无明确写出其 data 类型，如果没有，将其补充上
  - 代码优化
    - 检查服务代码中，是否可以提前短路，可以的话就提前短路以优化代码结构
    - 检查服务代码中，是否可以利用函数式风格进行代码优化，例如使用 stream api 进行数据处理
  - 日志
    - 检查服务代码中，是否可以利用 lombok 的 @Slf4j 注解进行日志记录，使代码更简洁
    - 在 controller 或 service 方法中打印参数
    - 检查是否有 catch 异常代码，如果有的话，需要打印错误信息
    - 检查代码分支，需要在分支前打印影响分支的相关变量值
  - 单元测试：检查 Controller 或 AdminController 或 Service 中接口或方法有变动时，相关的测试用例应当做相对应的改动
