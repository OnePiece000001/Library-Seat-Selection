import axios from "axios";
import { mockData, parseParams, getApiPath, mockDelay } from "@/mock/mockData";

// 检测是否在 GitHub Pages 环境
const isGitHubPages = window.location.hostname.includes('github.io') || 
                      window.location.hostname.includes('gitee.io') ||
                      import.meta.env?.VITE_USE_MOCK === 'true';

// 检测是否应该使用 Mock
const shouldUseMock = () => {
  // 1. GitHub Pages 环境
  if (isGitHubPages) return true;
  // 2. 本地开发时可以通过 localStorage 开启 Mock 模式测试
  if (localStorage.getItem('USE_MOCK') === 'true') return true;
  return false;
};

//创建axios实例对象
const request = axios.create({
 baseURL: '/api',
 timeout: 600000
})

// Mock 请求处理函数
const handleMockRequest = async (config) => {
  const { url, method, data } = config;
  const apiPath = getApiPath(url);
  const params = parseParams(url);
  
  // 合并 URL 参数和请求体参数
  const allParams = { ...params };
  if (data && typeof data === 'string') {
    try {
      const bodyParams = JSON.parse(data);
      Object.assign(allParams, bodyParams);
    } catch (e) {
      // 如果不是 JSON，可能是 form-data，忽略
    }
  }
  
  // 查找对应的 mock 数据
  const mockHandler = mockData[apiPath];
  
  if (mockHandler) {
    await mockDelay(300); // 模拟网络延迟
    const result = mockHandler(allParams);
    return {
      data: result,
      status: 200,
      statusText: 'OK',
      headers: {},
      config: config
    };
  }
  
  // 如果没有找到对应的 mock 数据，返回 404
  console.warn(`[Mock] 未找到接口: ${apiPath}`);
  return {
    data: { code: 0, msg: '接口未实现（Mock 模式）', data: null },
    status: 404,
    statusText: 'Not Found',
    headers: {},
    config: config
  };
};

//axios的请求 request 拦截器
request.interceptors.request.use(
 async (config) => {
  // 如果使用 Mock 模式，拦截请求
  if (shouldUseMock()) {
   // 标记这是 mock 请求
   config.isMock = true;
  }
  return config;
 },
 (error) => {
  return Promise.reject(error);
 }
);

//axios的响应 response 拦截器
request.interceptors.response.use(
 async (response) => { //成功回调
  // 如果是 mock 请求，直接返回 mock 数据
  if (response.config?.isMock) {
   const mockResponse = await handleMockRequest(response.config);
   return mockResponse.data;
  }
  return response.data;
 },
 async (error) => { //失败回调
  // 检查是否是 mock 模式下的请求
  if (error.config?.isMock) {
   const mockResponse = await handleMockRequest(error.config);
   return mockResponse.data;
  }
  return Promise.reject(error);
 }
);

// 包装请求方法，支持 Mock 模式
const originalRequest = request;
const wrappedRequest = async (config) => {
 if (shouldUseMock()) {
  const mockResponse = await handleMockRequest(config);
  return Promise.resolve(mockResponse.data);
 }
 return originalRequest(config);
};

// 复制 axios 的方法到包装器
['get', 'post', 'put', 'delete', 'patch'].forEach(method => {
 wrappedRequest[method] = async (url, data, config = {}) => {
  if (shouldUseMock()) {
   const fullConfig = { ...config, url, method: method.toUpperCase(), data };
   const mockResponse = await handleMockRequest(fullConfig);
   return Promise.resolve(mockResponse.data);
  }
  return originalRequest[method](url, data, config);
 };
});

export default wrappedRequest;