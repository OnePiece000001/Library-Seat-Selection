const { defineConfig } = require('@vue/cli-service')

// GitHub Pages 仓库名称（请修改为你的仓库名）
const REPO_NAME = 'Library-Seat-Selection'

// 判断是否是生产环境（构建时）
const isProduction = process.env.NODE_ENV === 'production'

module.exports = defineConfig({
  transpileDependencies: true,
  
  // GitHub Pages 需要配置 publicPath
  // 如果是 GitHub Pages 部署，需要设置为仓库名
  publicPath: isProduction ? `/${REPO_NAME}/` : '/',
  
  // 输出目录
  outputDir: 'dist',
  
  // 静态资源目录
  assetsDir: 'static',
  
  devServer: {
    proxy: {
      // 匹配以 /api 开头的请求
      '/api': {
        target: 'http://127.0.0.1:8083', // 目标服务器地址
        changeOrigin: true, // 是否改变请求源
        pathRewrite: {
          '^/api': '' // 重写请求路径
        }
      }
    }
  }
})
