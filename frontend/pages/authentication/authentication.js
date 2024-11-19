// pages/authentication/authentication.js

import request from "../../api/request.js";
import host from "../../api/server.js";

const notice = "【请完善资料】";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "身份信息",
    subtitle: "陪伴您的每时每刻",
    backgroundColor: "#48E137",
    diseaseHistory: notice,
    allergicHistory: notice,
    height: notice,
    weight: notice,
    avatarUrl: notice,
    username: notice,
    birthday: notice,
    userId: notice,
    name: notice,
    gender: notice,
    identity: '患者',
    account: 123456789
  },

  onShow: function () {

    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      this.getTabBar().setData({
        selected: 3 //这个数字是当前页面在tabBar中list数组的索引
      })
    }

    request({
      url: '/user', // 相对路径，将与 baseURL 组合
      method: 'GET', // 请求方法
      data: {
        phone: "",
        password: ""
      }
    }).then(data => {
      console.log(data);
      this.setData({
        username: data.username,
        userId: data.id,
        name: data.name ? data.name : notice,
        gender: data.gender !== null ? (data.gender === true ? "男" : "女") : notice,
        avatarUrl: data.avatar ? host + data.avatar : notice,
        birthday: data.birthday ? data.birthday : notice,
        height: data.height ? data.height + "CM" : notice,
        weight: data.weight ? data.weight + "KG" : notice,
        allergicHistory: data.allergyHistory ? data.allergyHistory : notice,
        diseaseHistory: data.medicalHistory ? data.medicalHistory : notice
      })
      console.log(this.data)
    }).catch(error => {
      wx.showToast({
        title: '加载失败',
        icon: 'error'
      })
      console.error('请求失败:', error); // 请求失败，处理错误
    });
  },

  onChooseAvatar: function (e) {
    const avatar = e.detail.avatarUrl;
    this.setData({
      avatarUrl: avatar
    })
    this.uploadAvatar(avatar);
  },

  onChangeAvatar: function () {
    wx.chooseMedia({
      count: 1, // 默认9，设置为1表示只能选择一张图片或视频
      mediaType: ['image'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      sizeType: ['compressed'],
      success: function (res) {
        const filePath = res.tempFiles[0].tempFilePath;
        wx.showModal({
          title: '上传头像',
          content: '确认上传头像？',
          success: function (res) {
            if (res.confirm) {
              // 上传头像
              this.uploadAvatar(filePath);
            }
          }
        })
      }
    })
  },

  uploadAvatar: function (filePath) {
    request({
      methodName: wx.uploadFile,
      url: '/user', // 相对路径
      filePath: filePath, // 要上传文件资源的路径
      name: 'avatar', // 必填，后台用来解析文件的key
    }).then(data => {
      console.log(data)
      wx.showToast({
        title: '头像上传成功',
      })
    }).catch(err => {
      wx.showToast({
        title: '头像上传失败',
        icon: 'error'
      })
      console.error('上传失败', err);
    });
  },

  onEdit: function () {
    wx.navigateTo({
      // url: '../../pages/doctorauth/doctorauth',
      url: '../../pages/patientauth/patientauth'
    })
  },

  onIdentify: function () {
    request({
      url: '/user',
      method: 'PATCH',
    }).then(data => {
      console.log(data)
      if (!data) {
        wx.showModal({
          title: '请先完善姓名性别信息！',
          showCancel: false
        })
      } else {
        wx.navigateTo({
          url: '../../pages/doctorauth/doctorauth'
        })
      }
    })
  }

})