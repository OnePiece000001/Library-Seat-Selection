# 📚 图书馆智能选座与图书管理系统

一个基于 Vue2 + Spring Boot 的图书馆智能选座与图书管理系统，支持学生在线选座、图书借阅、预约管理等功能。

## 🎯 项目概述

本项目是一个前后端分离的图书馆管理系统，包含以下核心功能：
- **智能座位预约**：可视化图书馆座位布局，支持在线预约、签到
- **图书管理**：图书检索、借阅、归还、收藏
- **数据可视化**：图书馆使用数据统计与展示
- **用户管理**：学生与管理员双角色系统

## 🏗️ 技术架构

### 前端技术栈
- **Vue 2.6.14** - 渐进式 JavaScript 框架
- **Vue Router 3.6.5** - 路由管理
- **Vuex 3.6.2** - 状态管理
- **Element UI 2.15.14** - UI 组件库
- **ECharts 6.0.0** - 数据可视化图表
- **DataV 2.10.0** - 大屏数据展示组件
- **Axios** - HTTP 请求库
- **Less** - CSS 预处理器

### 后端技术栈
- **Spring Boot 3.3.8** - Java 后端框架
- **MyBatis 3.0.4** - ORM 持久层框架
- **MySQL 8.0** - 关系型数据库
- **PageHelper** - 分页插件
- **阿里云 OSS** - 对象存储服务（图片上传）
- **ZXing** - 二维码生成
- **Lombok** - 代码简化工具

## 📁 项目结构

```
Library Seat Selection/
├── lib_seat-master/          # 前端项目
│   ├── src/
│   │   ├── api/              # API 接口
│   │   ├── assets/           # 静态资源
│   │   ├── components/       # 公共组件
│   │   ├── pages/            # 页面组件
│   │   ├── router/           # 路由配置
│   │   ├── store/            # Vuex 状态管理
│   │   ├── utils/            # 工具函数
│   │   └── view/             # 视图页面
│   ├── public/
│   └── package.json
│
├── web-ai-project/           # 后端项目
│   └── web-ai-management/
│       ├── src/main/java/
│       │   └── org/example/webaimanagement/
│       │       ├── controller/   # 控制器层
│       │       ├── service/      # 业务逻辑层
│       │       ├── mapper/       # 数据访问层
│       │       ├── pojo/         # 实体类
│       │       └── utils/        # 工具类
│       ├── src/main/resources/
│       │   ├── mapper/           # MyBatis XML
│       │   └── application.yml   # 配置文件
│       └── pom.xml
│
└── library_ai.sql            # 数据库初始化脚本
```

## ✨ 功能模块

### 1. 用户认证模块
- 学生登录/注册
- 管理员登录
- 角色权限控制

### 2. 座位预约模块
- 可视化图书馆楼层布局
- 座位实时状态展示（空闲/预约/使用中）
- 在线预约座位
- 二维码签到
- 历史预约记录查询

### 3. 图书管理模块
- 图书信息录入与编辑
- 图书分类管理
- 图书封面图片上传（阿里云OSS）
- 图书库存管理

### 4. 图书借阅模块
- 图书检索与筛选
- 图书详情查看
- 在线借阅申请
- 借阅记录管理
- 图书归还处理
- 图书收藏功能

### 5. 数据可视化模块
- 图书馆座位使用率统计
- 图书借阅趋势分析
- 用户活跃度统计
- 数据大屏展示

### 6. 系统管理模块
- 用户信息管理
- 预约规则设置
- 楼层座位布局配置（拖拽编辑）

## 🚀 快速开始

### 环境要求
- **Node.js** >= 14.x
- **Java** >= 17
- **MySQL** >= 8.0
- **Maven** >= 3.6

### 1. 数据库初始化

```bash
# 创建数据库
create database library_ai character set utf8mb4;

# 导入数据
mysql -u root -p library_ai < library_ai.sql
```

### 2. 后端启动

```bash
cd web-ai-project/web-ai-management

# 修改数据库配置
# 编辑 src/main/resources/application.yml
# 修改 datasource.url、username、password

# 打包运行
mvn clean package
java -jar target/web-ai-management-0.0.1-SNAPSHOT.jar

# 或使用 IDE 直接运行 WebAiManagementApplication.java
```

后端服务默认运行在 `http://localhost:8083`

### 3. 前端启动

```bash
cd lib_seat-master

# 安装依赖
npm install

# 开发环境运行
npm run serve

# 生产环境打包
npm run build
```

前端服务默认运行在 `http://localhost:8080`

### 4. 阿里云OSS配置（可选）

如需使用图片上传功能，请在 `application.yml` 中配置阿里云OSS：

```yaml
aliyun:
  oss:
    endpoint: https://oss-cn-beijing.aliyuncs.com
    bucketName: your-bucket-name
    region: cn-beijing
```

## 📱 页面功能说明

| 页面 | 路径 | 说明 | 角色 |
|------|------|------|------|
| 登录页 | `/` | 用户登录入口 | 全部 |
| 数据大屏 | `/ShowAll/DataReveal` | 图书馆数据可视化展示 | 全部 |
| 座位预约 | `/ShowAll/LibMap` | 可视化选座预约 | 学生/管理员 |
| 预约管理 | `/ShowAll/BookingManage` | 预约审核与管理 | 管理员 |
| 我的预约 | `/ShowAll/BookingMessage` | 个人预约记录 | 学生 |
| 历史预约 | `/ShowAll/HistoryBookingMsg` | 历史预约查询 | 学生 |
| 图书检索 | `/ShowAll/SelectBook` | 图书搜索与筛选 | 学生 |
| 图书详情 | `/ShowAll/BorrowCard` | 图书详情与借阅 | 学生 |
| 图书管理 | `/ShowAll/ManageBook` | 图书CRUD管理 | 管理员 |
| 借阅管理 | `/ShowAll/BookBorrowManage` | 借阅记录管理 | 管理员 |
| 用户管理 | `/ShowAll/UserMessage` | 用户信息管理 | 管理员 |
| 我的收藏 | `/ShowAll/ShouCang` | 收藏的图书 | 学生 |
| 二维码签到 | `/ShowAll/SeatQRcode` | 座位签到 | 学生 |

## 🗄️ 数据库表结构

| 表名 | 说明 |
|------|------|
| `user` | 用户表（管理员/学生） |
| `library_floor` | 图书馆楼层信息 |
| `library_seat_desk` | 座位与桌子信息 |
| `library_booking` | 座位预约记录 |
| `book_info` | 图书信息表 |
| `book_borrow` | 图书借阅记录 |
| `book_collect` | 图书收藏记录 |

## 🔌 API 接口概览

### 座位管理接口
- `GET /LibrarySeatDesk` - 获取楼层座位布局
- `POST /LibrarySeatDesk` - 添加座位/桌子
- `PUT /LibrarySeatDesk` - 更新座位信息
- `DELETE /LibrarySeatDesk/{id}` - 删除座位

### 预约管理接口
- `GET /LibraryBooking` - 获取预约列表
- `POST /LibraryBooking` - 创建预约
- `PUT /LibraryBooking` - 更新预约状态
- `DELETE /LibraryBooking/{id}` - 取消预约

### 图书管理接口
- `GET /LibraryBook` - 图书列表查询
- `POST /LibraryBook` - 添加图书
- `PUT /LibraryBook` - 更新图书信息
- `DELETE /LibraryBook/{id}` - 删除图书

### 借阅管理接口
- `GET /LibraryBorrow` - 借阅记录查询
- `POST /LibraryBorrow` - 创建借阅
- `PUT /LibraryBorrow` - 归还图书

## 🛠️ 开发说明

### 前端开发

```bash
# 代码规范检查
npm run lint

# 项目配置
# vue.config.js - Vue CLI 配置
# babel.config.js - Babel 转译配置
```

### 后端开发

```bash
# 项目依赖管理
# pom.xml - Maven 配置

# 配置文件
# application.yml - 应用主配置
# application-dev.yml - 开发环境配置
# application-prod.yml - 生产环境配置
```

## 📸 功能截图

> 待补充项目截图

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目仅供学习交流使用。

## 👨‍💻 作者

- **开发者** - [你的名字]
- **项目地址** - [GitHub 仓库链接]

## 🙏 致谢

感谢以下开源项目：
- [Vue.js](https://vuejs.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Element UI](https://element.eleme.io/)
- [ECharts](https://echarts.apache.org/)
- [DataV](http://datav.jiaminghi.com/)

---

⭐ 如果这个项目对你有帮助，请给它一个 Star！