import host from '../api/server'

let defaultBaseURL = host;
defaultBaseURL += '/api'

// 定义一个简单的令牌存储机制
const tokenKey = 'token';

// 设置默认超时时间为 3000 毫秒（3秒）
const defaultTimeout = 3000;

// 设置默认的 baseURL
// const defaultBaseURL = 'http://127.0.0.1:4523/m1/4980076-4638643-default/api';
// const defaultBaseURL = 'http://127.0.0.1:8080/api';
// const defaultBaseURL = 'https://demo.grtsinry43.com/xiaokang/api';

// 存储令牌
function setToken(token) {
  wx.setStorageSync(tokenKey, token);
}

// 获取令牌
function getToken() {
  return wx.getStorageSync(tokenKey) || '';
}

function reLogin() {
  const pages = getCurrentPages();
  const currentPage = pages[pages.length - 1];
  const page = "/" + currentPage.route
  const pageEncode = encodeURIComponent(page)
  wx.redirectTo({
    url: '../index/index?page=' + pageEncode,
    fail: function (res) {
      console.log(res)
      console.log("fail")
    }
  })
}

// 封装 wx.request
function request(options) {
  // 设置 methodName
  const methodName = options.methodName ? options.methodName : wx.request;
  delete options.methodName;
  // 获取存储的令牌，并添加到请求头
  const token = getToken();
  if (token) {
    options.header = options.header || {};
    options.header['Authorization'] = `Bearer ${token}`;
  }
  // 设置默认的 baseURL
  const baseURL = options.baseURL || defaultBaseURL;
  delete options.baseURL; // 从 options 中删除 baseURL，以免传递给 wx.request
  // 返回一个新的 Promise 对象来处理异步请求
  return new Promise((resolve, reject) => {
    methodName({
      ...options, // 展开 options 对象，作为 wx.request 的参数
      url: baseURL + (options.url || ''), // 使用用户提供的 url，或者空字符串
      timeout: options.timeout || defaultTimeout, // 使用用户指定的超时时间，或者默认值
      success: (res) => { // 请求成功时的回调
        console.log(res)
        // 如果响应中包含新的令牌，更新存储的令牌
        if (res.header && res.header['Authorization']) {
          setToken(res.header['Authorization']);
        }
        // 根据业务需求处理响应数据
        const response = res.data
        if (res.statusCode === 200) {
          if (response.code === 0) {
            resolve(response.data); // 请求成功，调用 resolve 并传递响应数据
          } else if (response.code == 401) {
            wx.showModal({
              title: '请登录',
              content: '登录小程序并进一步使用功能',
              showCancel: false,
              complete: (res) => {
                reLogin()
              }
            })
          } else {
            const parsedResponse = JSON.parse(response)
            if (parsedResponse.code === 0) {
              resolve(parsedResponse.data)
            }
            reject(res); // 请求失败，调用 reject 并传递响应对象
          }
        } else {
          reject(res)
        }
      },
      fail: (err) => { // 请求失败时的回调
        reject(err); // 调用 reject 并传递错误对象
      }
    });
  });
}

// 导出
export default request;