# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目简介

帝可得（dkd）点餐系统前端，基于若依（RuoYi-Vue）后台管理框架，Vue 3 + Vite + Element Plus。

## 常用命令

```bash
pnpm install          # 安装依赖
pnpm dev              # 启动开发服务器（端口 80，自动打开浏览器）
pnpm build:prod       # 生产环境打包
pnpm build:stage      # 预发布环境打包
pnpm preview          # 预览打包结果
```

## 开发环境

- 开发服务器端口：80
- 后端 API 代理：`/dev-api` → `http://127.0.0.1:8080`（vite.config.js 配置）
- 环境变量文件：`.env.development`、`.env.production`、`.env.staging`
- 关键环境变量：`VITE_APP_BASE_API`（API 前缀）、`VITE_APP_TITLE`（页面标题）

## 架构要点

### 路由机制

路由分两层：`constantRoutes`（静态公共路由，定义在 `src/router/index.js`）+ 后端返回的动态路由。用户登录后，`permission.js` 中的路由守卫调用 `getRouters()` 接口获取菜单，由 `store/modules/permission.js` 的 `filterAsyncRouter()` 将后端返回的 component 字符串（如 `"system/user/index"`）映射为实际的 Vue 组件（通过 `import.meta.glob` 扫描 `views/**/*.vue`）。特殊值 `"Layout"` 和 `"ParentView"` 有专门处理。

### 状态管理（Pinia）

Store 模块在 `src/store/modules/` 下，使用 `defineStore`（非 setup 语法），通过 auto-import 插件自动引入，无需手动 import `defineStore`。

核心模块：`user`（登录/登出/用户信息）、`permission`（动态路由生成）、`settings`（布局配置）、`app`（设备/尺寸）、`dict`（字典缓存）、`tagsView`（标签页）。

### 请求封装

`src/utils/request.js` 封装了 axios 实例，包含：
- 自动携带 Token（Bearer）到 `Authorization` 头
- 401 状态码弹出重新登录对话框
- 防重复提交机制（1 秒内相同 URL+参数视为重复）
- `download()` 方法处理文件下载（blob 响应）

### 全局组件

以下组件在 `main.js` 中全局注册，页面中可直接使用无需 import：`DictTag`、`Pagination`、`TreeSelect`、`FileUpload`、`ImageUpload`、`ImagePreview`、`RightToolbar`、`Editor`、`svg-icon`。

### 全局方法

通过 `app.config.globalProperties` 挂载，可在模板中直接使用：`useDict`、`download`、`parseTime`、`resetForm`、`handleTree`、`addDateRange`、`selectDictLabel`、`selectDictLabels`。

### API 模块

按功能拆分在 `src/api/` 下：`login.js`（认证）、`menu.js`（菜单路由）、`system/`（系统管理）、`monitor/`（监控）、`tool/`（代码生成）、`page.js`（页面相关）。

### 路径别名

- `@` → `./src`
- `~` → `./`（项目根目录）

### Vite 插件

配置在 `vite/plugins/` 下：`auto-import`（API 自动导入）、`compression`（gzip 压缩）、`svg-icon`（SVG 图标）、`setup-extend`（script setup name 属性扩展）。

## 权限控制

- 指令级：`v-hasPermi`、`v-hasRole`（定义在 `src/directive/permission/`）
- 函数级：`auth.hasPermi()`、`auth.hasRole()`（`src/plugins/auth.js`）
- 路由级：`permissions` 和 `roles` 字段定义在路由 meta 中，由 `permission.js` store 过滤

## 组件开发规范

- 使用 `<script setup>` 语法，通过 `unplugin-vue-setup-extend-plus` 支持 name 属性
- 样式使用 SCSS，全局样式在 `src/assets/styles/`
- 字典数据通过 `useDict('dict_type')` 获取，在模板中配合 `<dict-tag>` 使用
