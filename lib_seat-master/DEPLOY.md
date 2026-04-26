# GitHub Pages 部署指南

本项目已配置 Mock 数据模式，可以在 GitHub Pages 上静态展示，无需后端服务。

## 部署前准备

### 1. 修改仓库名称配置

打开 `vue.config.js`，将 `REPO_NAME` 修改为你的 GitHub 仓库名：

```javascript
// GitHub Pages 仓库名称（请修改为你的仓库名）
const REPO_NAME = '你的仓库名称'
```

例如，如果你的仓库地址是 `https://github.com/username/library-seat`，则设置为：
```javascript
const REPO_NAME = 'library-seat'
```

### 2. 确保 GitHub Pages 已启用

1. 进入 GitHub 仓库页面
2. 点击 **Settings** → **Pages**
3. **Source** 选择 **GitHub Actions**

## 部署步骤

### 方法一：自动部署（推荐）

1. 将代码推送到 `main` 或 `master` 分支
2. GitHub Actions 会自动构建并部署
3. 部署完成后，访问 `https://你的用户名.github.io/仓库名/`

### 方法二：手动部署

```bash
# 进入项目目录
cd lib_seat-master

# 安装依赖
npm install

# 构建生产版本
npm run build

# 将 dist 目录内容推送到 gh-pages 分支
# 或使用 gh-pages 工具
npx gh-pages -d dist
```

## 本地测试 Mock 模式

在本地开发时，可以通过以下方式启用 Mock 模式：

### 方式一：修改代码（临时）

在 `request.js` 中临时修改：
```javascript
const shouldUseMock = () => {
  return true; // 强制启用 Mock
};
```

### 方式二：使用 LocalStorage（推荐）

在浏览器控制台执行：
```javascript
localStorage.setItem('USE_MOCK', 'true');
location.reload();
```

关闭 Mock 模式：
```javascript
localStorage.removeItem('USE_MOCK');
location.reload();
```

## Mock 数据说明

Mock 数据文件位于 `src/mock/mockData.js`，包含以下模拟数据：

- ✅ 用户登录/注册（任意账号密码都能登录）
- ✅ 图书列表、搜索、推荐
- ✅ 座位/桌位/楼层信息
- ✅ 预约功能
- ✅ 借阅功能
- ✅ 收藏功能
- ✅ 管理功能（增删改查）

## 注意事项

1. **登录功能**：Mock 模式下任意手机号和密码都能登录
2. **数据持久化**：Mock 数据不会真正保存，刷新页面会重置
3. **图片上传**：Mock 模式下图片上传功能可能受限
4. **二维码**：使用在线二维码生成服务

## 常见问题

### Q: 部署后页面空白？
A: 检查 `vue.config.js` 中的 `REPO_NAME` 是否与仓库名一致。

### Q: 接口报错 "接口未实现"？
A: 说明该接口的 Mock 数据未定义，需要在 `mockData.js` 中添加对应接口。

### Q: 如何添加新的 Mock 接口？
A: 在 `mockData.js` 中添加新的键值对，键为接口路径，值为返回数据的函数。

```javascript
'/your-api-path': (params) => {
  return {
    code: 1,
    data: { /* 你的数据 */ },
    msg: '成功'
  };
}
```
