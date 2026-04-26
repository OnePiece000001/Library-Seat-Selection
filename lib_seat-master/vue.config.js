const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
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
