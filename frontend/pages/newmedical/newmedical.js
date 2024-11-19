// pages/newmedical/newmedical.js

import request from "../../api/request.js"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "健康数据",
    subtitle: "记录您的点点滴滴",
    backgroundColor: "#0F71E7",
    chosen: true,
    mclass: "chosen",
    cclass: "notchosen",
    text: ""
  },

  onChooseMedicine: function () {
    this.setData({
      chosen: true,
      mclass: "chosen",
      cclass: "notchosen"
    })
  },

  onChooseConsult: function () {
    this.setData({
      chosen: false,
      mclass: "notchosen",
      cclass: "chosen"
    })
  },

  onRecordSubmit: function () {
    if (this.data.text === "") {
      wx.showModal({
        title: '输入内容不能为空！',
        showCancel: false
      })
    } else {
      wx.showModal({
        title: '确认提交？',
        content: '提交后不可撤销，请确认信息无误！',
        success: (res) => {
          if (res.confirm) {
            this.onRecordSubmitConfirm()
          }
        }
      })
    }
  },

  onRecordSubmitConfirm: function () {
    const send = {
      recordType: this.data.chosen,
      detail: this.data.text
    };
    console.log(send)
    request({
      url: '/medical', // 相对路径，将与 baseURL 组合
      method: 'POST', // 请求方法
      data: send
    }).then(data => {
      if (data === true) {
        wx.showModal({
          title: '今日已经提交过了！',
          showCancel: false,
          content: '请明天再来记录！'
        })
        return;
      }
      wx.showToast({
        title: '提交成功',
      })
    }).catch(error => {
      wx.showToast({
        title: '提交失败',
        icon: 'error'
      })
      console.error('请求失败:', error); // 请求失败，处理错误
    });
  },

  handleInput: function (e) {
    this.setData({
      text: e.detail.value
    })
  }
})