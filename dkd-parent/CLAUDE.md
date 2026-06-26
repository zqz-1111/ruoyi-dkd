# dkd-parent 后端项目指南

## 项目概述

帝可得管理系统（DKD），基于 RuoYi 脚手架，Spring Boot 2.5.15 + MyBatis + JWT + Redis 前后端分离架构。
业务场景：智能售货机平台管理（设备部署、补货、维修、撤机工单）。

## 模块结构

| 模块 | 职责 |
|---|---|
| `dkd-admin` | Web 入口、Controller 层、启动类 `com.dkd.DkdApplication` |
| `dkd-common` | 公共基础：工具类、注解、常量、异常、实体基类 |
| `dkd-framework` | 框架核心：Security、JWT、AOP、配置类、拦截器 |
| `dkd-system` | 系统业务：用户/角色/菜单/部门/字典等 Service + Mapper |
| `dkd-quartz` | 定时任务管理（Quartz 动态调度） |
| `dkd-generator` | 代码生成（Velocity 模板，生成 CRUD 全套代码） |
| `dkd-manage` | 业务模块（当前为空壳占位，供售货机业务开发） |

依赖方向：`dkd-admin` → `dkd-framework` → `dkd-system` → `dkd-common`

## 开发规范

### 包名规则
- 公共：`com.dkd.common.*`
- 框架：`com.dkd.framework.*`
- 系统：`com.dkd.system.*`
- Controller：`com.dkd.web.controller.*`（在 dkd-admin 模块）
- Mapper XML：`resources/mapper/{模块名}/`

### 新增业务模块流程
1. 在对应模块的 `domain/` 下建实体类（继承 `BaseEntity`）
2. 在 `mapper/` 下建 Mapper 接口 + `resources/mapper/` 下建 XML
3. 在 `service/` 下建接口 + `service/impl/` 下建实现
4. 在 `dkd-admin` 的 `controller/` 下建 Controller（继承 `BaseController`）
5. 如需权限控制，在方法上加 `@PreAuthorize("@ss.hasPermi('xxx:xxx:list')")`
6. 如需操作日志，加 `@Log(title = "XXX", businessType = BusinessType.INSERT)`

### 常用注解
- `@Log` — 操作日志审计
- `@DataScope` — 数据权限过滤
- `@DataSource` — 主从数据源切换
- `@RateLimiter` — Redis 限流
- `@RepeatSubmit` — 防重复提交
- `@Anonymous` — 匿名访问
- `@Sensitive` — 数据脱敏
- `@Excel` — Excel 导入导出

### 安全机制
- 认证：JWT + Redis（Token 30 分钟过期，<20 分钟自动续期）
- 授权：`@PreAuthorize("@ss.hasPermi('...')")`
- 密码：BCrypt，5 次错误锁定 10 分钟

## 构建与运行

```bash
# 编译（跳过测试）
mvn clean install -DskipTests

# 运行（在 dkd-admin 模块）
cd dkd-admin && mvn spring-boot:run

# 或直接运行 DkdApplication.java
```

## 配置文件

- `dkd-admin/src/main/resources/application.yml` — 主配置
- `dkd-admin/src/main/resources/application-druid.yml` — 数据源（MySQL localhost:3306/dkd）
- JDK：1.8（Dragonwell 8）
- 端口：8080
- Swagger：`/dev-api/swagger-ui/`
- Druid 监控：`/druid/*`

## 数据库

- MySQL `localhost:3306/dkd`，账号 root/123456
- 主表 SQL：`sql/ry_20231130.sql`
- Quartz 表 SQL：`sql/quartz.sql`

## Git 规范

- 推代码前必须 `git pull`（前后端共用一个仓库）
- 只改 `dkd-parent/` 下的文件，不碰 `dkd-vue/`
- 远程：`git@github.com:zqz-1111/ruoyi-dkd.git`
