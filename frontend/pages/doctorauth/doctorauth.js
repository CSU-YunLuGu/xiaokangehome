// pages/doctorauth/doctorauth.js

import request from "../../api/request.js"
import host from "../../api/server.js"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    valid: undefined,
    title: "医生认证",
    subtitle: "陪伴您的每时每刻",
    backgroundColor: "#48E137",
    certificatePicUrl: "",
    photoUrl: "",
    labels: ["专业", "职称"],
    placeholders: ["请填入不超过 10 个字符", "请选择您的职称"],
    values: ["", ""],
    button: "",
    certificatePicPath: "",
    photoPath: ""
  },

  cer: function () {
    wx.navigateTo({
      url: '../../pages/idcard/idcard',
    })
  },

  inputHandle: function (e) {
    const index = e.currentTarget.dataset.index;
    const value = e.detail.value
    const values = this.data.values
    values[index] = value
    this.setData({
      values: values
    })
    console.log(this.data.values)
  },

  onCertificatePic: function () {
    const that = this;
    wx.chooseMedia({
      count: 1, // 默认9，设置为1表示只能选择一张图片或视频
      mediaType: ['image'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      sizeType: ['compressed'],
      success: function (res) {
        const filePath = res.tempFiles[0].tempFilePath;
        that.uploadCertificate(filePath);
      },
      fail: function (err) {
        console.error(err);
      }
    });
  },

  uploadCertificate: function (filePath) {
    request({
      methodName: wx.uploadFile,
      url: '/identity/certificate', // 相对路径
      filePath: filePath, // 要上传文件资源的路径
      name: 'certificate', // 必填，后台用来解析文件的key
    }).then(data => {
      wx.showToast({
        title: '上传成功',
        icon: 'success'
      })
      this.setData({
        certificatePicUrl: host + data
      })
      console.log(this.data)
    }).catch(err => {
      wx.showToast({
        title: '上传失败',
        icon: 'error'
      })
      console.error('上传失败', err);
    });
  },

  onPhoto: function () {
    const that = this;
    wx.chooseMedia({
      count: 1, // 默认9，设置为1表示只能选择一张图片或视频
      mediaType: ['image'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      sizeType: ['compressed'],
      success: function (res) {
        const filePath = res.tempFiles[0].tempFilePath
        that.uploadPhoto(filePath);
      },
    });
  },

  uploadPhoto: function (filePath) {
    request({
      methodName: wx.uploadFile,
      url: '/identity/photo', // 相对路径
      filePath: filePath, // 要上传文件资源的路径
      name: 'photo', // 必填，后台用来解析文件的key
    }).then(data => {
      console.log(data)
      wx.showToast({
        title: '上传成功',
        icon: 'success'
      })
      this.setData({
        photoUrl: host + data
      })
    }).catch(err => {
      wx.showToast({
        title: '上传失败',
        icon: 'error'
      })
      console.error('上传失败', err);
    });
  },

  submitHandle: function () {
    const that = this;
    if (this.data.valid === null) {
      wx.showModal({
        title: '无法提交认证',
        content: '请等待后台审核，请勿重复提交',
        showCancel: false
      });
      return;
    }
    const values = this.data.values;
    const payload = {
      certificatePicUrl: this.data.certificatePicUrl,
      photoUrl: this.data.photoUrl,
      major: values[0],
      job: values[1]
    };
    if (payload.major === null || payload.major === "" ||
      payload.job === null || payload.job === "" ||
      payload.certificatePicUrl === null || payload.certificatePicUrl === "" ||
      payload.photoUrl === null || payload.photoUrl === "") {
      wx.showModal({
        title: '上传失败',
        content: "认证信息不完整",
        showCancel: false
      })
      return;
    }
    wx.showModal({
      title: '提交认证信息',
      content: '确认提交？',
      success: (res) => {
        if (res.confirm) {
          console.log(payload);
          request({
            url: '/identity/submit',
            method: 'PUT',
            data: payload
          }).then(data => {
            console.log(data)
            wx.showToast({
              title: '上传成功',
              icon: 'success'
            })
            that.setData({
              valid: null
            })
          }).catch(error => {
            console.error(error);
          })
        }
      }
    })
  },

  onLoad: function () {
    request({
      url: '/identity/info',
      method: 'GET'
    }).then(data => {
      console.log("-")
      console.log(data)
      console.log("-")
      if (data === null) {
        return;
      }
      this.setData({
        certificatePicUrl: data.certificatePicUrl === "" ? "" : data.certificatePicUrl,
        photoUrl: data.photoUrl === "" ? "" : data.photoUrl,
        values: [data.major, data.job],
        valid: data.valid
      });
    }).catch(err => {
      console.error(err);
    })
  },

  bindJob: function () {
    let that = this;
    const jobs = ["医师", "主治医师", "副主任医师", "主任医师"];
    wx.showActionSheet({
      itemList: jobs,
      success: (res) => {
        that.setData({
          [`values[1]`]: jobs[res.tapIndex]
        })
      },
      fail: (err) => {
        console.error('选择操作失败', err);
      }
    });
    // }
  },


})