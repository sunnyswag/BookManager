# 书籍管理系统



## 技术路线

Spring Boot、Mysql、Mybatis

## 项目架构简述

#### 1、数据库

user表：用来记录用户的信息(id, name, email, password[MD5加密])

ticket表：用来记录用户登录登出的状态(id, user_id, ticket, expired_at(到期时间))

book表：用于记录书籍的信息(id，name，author，price，status[借入借出状态])

#### 2、实体层（Entity）

定义了三个类（Book、Ticket、User）的 get、set 方法

#### 3、持久层（Dao）

定义了三个接口（BookDAO、TicketDAO、UserDAO）实现对数据库的增删改查操作

#### 4、服务层（Service）

定义了三个类（BookService、TicketService、UserService），对持久层的操作进行整合，转化为业务上的逻辑操作

#### 5、控制层（Controller）

a. BookController：获取并处理前端传回对书籍进行处理的请求

​	比如：加载所有书籍、增加删除、借入借出等等

b.LoginController：处理登录、登出和注册等操作

## 其他组件

#### 1、拦截器

a. HostInfoInterceptor

​	拦截范围：整个前端页面

​	功能：若 Cookie 中保存了用户信息，则直接登录

b.LoginInterceptor

​	拦截范围：对书籍进行操作时实现拦截

​	功能：从 Cookie 中获取到 ticket 信息后，对其进行检测，若没有 ticket，ticket 无效或者 ticket 过期，跳转到登录页面重新登录

#### 2、登录的流程

a.检查登录信息（邮箱、密码）

b.检查数据库中是否存在当前 user_id 的 ticket 

​	如果没有 ticket，则生成一个

​	如果过期则删除，再生成一个

c.将 ticket 写到 Cookie 中并返回

#### 3、注册的流程

a.检查邮箱是否已存在

b.对密码进行 md5 加密

c. 向数据库中添加 用户，添加 ticket 信息

d. 将 ticket 写到 Cookie 中并返回

#### 4、退出登录

删除数据库中 ticket 信息

#### 5、通过 ticket 对用户状态进行控制

每一个在线用户对应一个 ticket，ticket 信息保存在数据库中

ticket 通过 UUID.randomUUID() 生成

确保的是在一台机器上生成的数字，它保证在同一时空中的所有机器都是唯一的

#### 6、用 MD5 对密码进行加密

MD5 加密是不可逆的，对原始数据进行有损压缩计算

无论消息的长度是多少，都会生成一个固定长度的消息摘要(16字节 )

## TODO

1、将 ticket 信息保存在缓存中