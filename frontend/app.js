// app.js

// 目前是没有后台管理系统的版本，实现了健康数据分析、吃药看病数据管理、健康知识问答、个人资料管理；下一步开发后台管理系统，恢复医生认证、健康知识文章推送、医生/患者查询等功能，开发聊天问诊功能。

App({
  data: {
    isIdentityChosen: false,
    isUserIdentified: false,
    isDoctorIdentified: false,
    identity: "",
  },
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
  },
  globalData: {
    userInfo: null
  }
})
