// pages/diagnose/diagnose.js

import request from "../../api/request.js"
import host from "../../api/server.js"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "患者列表",
    subtitle: "节约您的一分一秒",
    backgroundColor: "#9625C2",
    patientList: [

    ]
  },

  onShow: function () {
    request({
      url: '/patient',
      method: 'GET',
    }).then(data => {
      const dataList = data;
      dataList.forEach(data => {
        data.avatar = host + data.avatar
      })
      this.setData({
        patientList: data
      })
    })
  },

  onChoosePatient: function (e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '../chat/chat?identity=true&id=' + id,
    })
  }
})