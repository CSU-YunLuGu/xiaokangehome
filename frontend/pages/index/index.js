// index.js
// const defaultAvatarUrl = 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0'

import request from "../../api/request.js"

Page({
  data: {
    identity: "",
    pclass: "notchosen",
    dclass: "notchosen",
    isIdentityChosen: false,
    page: ""
  },
  onDoctorChosen: function () {
    this.setData({
      dclass: "chosen",
      pclass: "notchosen",
      isIdentityChosen: true,
      identity: "doctor"
    })
  },
  onPatientChosen: function () {
    this.setData({
      pclass: "chosen",
      dclass: "notchosen",
      isIdentityChosen: true,
      identity: "patient"
    })
  },
  // onLoginButtonPressed: function () {
  //   wx.navigateTo({
  //     url: '/pages/chat/chat',
  //   })
  // }
  onLoginButtonPressed: function () {
    wx.login({
      success: (res) => {
        console.log(res);
        if (res.errMsg === 'login:ok') {
          console.log(res.code);
          request({
            url: '/user/wxLogin?code=' + res.code,
            method: 'POST',
          }).then(res => {
            console.log(res);
            // wx.showToast({
            //   title: '登录成功',
            // })
            // setInterval(() => {
              wx.switchTab({
                url: '/pages/data/data',
              });
            // }, 1000);
          }).catch(error => {
            wx.showToast({
              title: '登录失败',
              icon: 'error'
            })
            console.error('请求失败:', error); // 请求失败，处理错误
          })
        }
      },
    });
  },

  onLoad: function (options) {
    const page = decodeURIComponent(options.page);
    this.setData({
      page: page
    })
  },

  onLoginElse: function () {
    console.log("***")
    wx.switchTab({
      url: "/pages/ask/ask",
    })
    wx.navigateTo();
  }
})

// Page({
//   data: {
//     motto: 'Hello World',
//     userInfo: {
//       avatarUrl: defaultAvatarUrl,
//       nickName: '',
//     },
//     hasUserInfo: false,
//     canIUseGetUserProfile: wx.canIUse('getUserProfile'),
//     canIUseNicknameComp: wx.canIUse('input.type.nickname'),
//   },
//   bindViewTap() {
//     wx.navigateTo({
//       url: '../logs/logs'
//     })
//   },
//   onChooseAvatar(e) {
//     const { avatarUrl } = e.detail
//     const { nickName } = this.data.userInfo
//     this.setData({
//       "userInfo.avatarUrl": avatarUrl,
//       hasUserInfo: nickName && avatarUrl && avatarUrl !== defaultAvatarUrl,
//     })
//   },
//   onInputChange(e) {
//     const nickName = e.detail.value
//     const { avatarUrl } = this.data.userInfo
//     this.setData({
//       "userInfo.nickName": nickName,
//       hasUserInfo: nickName && avatarUrl && avatarUrl !== defaultAvatarUrl,
//     })
//   },
//   getUserProfile(e) {
//     // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认，开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
//     wx.getUserProfile({
//       desc: '展示用户信息', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
//       success: (res) => {
//         console.log(res)
//         this.setData({
//           userInfo: res.userInfo,
//           hasUserInfo: true
//         })
//       }
//     })
//   },
// })