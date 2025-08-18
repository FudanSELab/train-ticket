# Train Ticket Client SDK

这个模块提供了统一的客户端SDK，用于调用Train Ticket系统中的各个微服务。

## 功能特点

- 封装了各个服务的HTTP调用
- 使用Spring Cloud的服务发现机制
- 统一的错误处理
- 类型安全的DTO对象

## 使用方法

1. 在你的服务的`pom.xml`中添加依赖：

```xml
<dependency>
    <groupId>org.services</groupId>
    <artifactId>ts-client-sdk</artifactId>
    <version>0.1.0</version>
</dependency>
```

2. 在你的Spring Boot应用中注入需要使用的客户端：

```java
@Autowired
private AuthClient authClient;
```

3. 调用相应的方法：

```java
// 创建默认用户
Response response = authClient.createDefaultUser(userId, userName, password);

// 用户登录
Response loginResponse = authClient.login(userName, password, verificationCode, headers);
```

## 示例代码

### 创建用户

```java
@Service
public class UserService {
    @Autowired
    private AuthClient authClient;
    
    public void registerUser(String userId, String userName, String password) {
        // 调用auth服务创建默认用户
        Response response = authClient.createDefaultUser(userId, userName, password);
        if (response.getCode() == 1) {
            // 创建成功
        } else {
            // 处理错误
        }
    }
}
```

### 用户登录

```java
@Service
public class LoginService {
    @Autowired
    private AuthClient authClient;
    
    public Response login(String userName, String password, String verificationCode) {
        return authClient.login(userName, password, verificationCode, new HttpHeaders());
    }
}
```

## 当前支持的服务

- Auth Service (`AuthClient`)
  - 创建默认用户
  - 用户登录
