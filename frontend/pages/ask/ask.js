import request from "../../api/request.js"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    isIdentityChosen: false,
    isUserIdentified: false,
    isDoctorIdentified: false,
    identity: "",
    title: "线上咨询",
    subtitle: "节约您的一分一秒",
    backgroundColor: "#9625C2",
    environment: true,
    doctorList: [],
    patientList: []
  },

  onChooseDoctor: function () {
    request({
      url: '/identity',
      method: 'GET'
    }).then(data => {
      console.log(data)
      if (data === true) {
        wx.navigateTo({
          url: '../diagnose/diagnose'
        })
      } else {
        wx.showModal({
          title: '无法使用医生功能！',
          content: '请您先进行医生身份认证',
          showCancel: false
        })
      }
    }).catch(err => {
      console.error(err);
    })
  },

  onChoosePatient: function () {
    wx.navigateTo({
      url: '../visit/visit',
    })
  },

  onClickButton: function () {
    wx.navigateTo({
      url: '../add/add',
    })
  },

  ask: function () {
    console.log("ask")
    wx.navigateTo({
      url: '../../pages/askchat/askchat',
    })
  },

  onShow: function () {
    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      this.getTabBar().setData({
        selected: 1 //这个数字是当前页面在tabBar中list数组的索引
      })
    }
  }

})