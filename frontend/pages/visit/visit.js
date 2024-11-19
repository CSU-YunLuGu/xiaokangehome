// pages/visit/visit.js

import request from "../../api/request.js"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "线上咨询",
    subtitle: "节约您的一分一秒",
    backgroundColor: "#9625C2",
    doctorList: []
  },

  ask: function (e) {
    console.log("ask")
    wx.navigateTo({
      url: '../chat/chat?identity=false&id=' + e.currentTarget.dataset.userid,
    })
  },

  find: function () {
    wx.navigateTo({
      url: '../add/add',
    })
  },

  delete: function (e) {
    wx.showModal({
      title: '删除医生',
      content: '确定删除该医生吗？',
      success: (res) => {
        if (res.confirm) {
          this.deleteDoctor(e)
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  deleteDoctor: function (e) {
    request({
      url: '/doctor/delete?id=' + e.currentTarget.dataset.id, // 相对路径，将与 baseURL 组合
      method: 'GET', // 请求方法
    }).then(data => {
      wx.showToast({
        title: '删除成功',
        icon: 'success'
      })
      console.log(data);
      this.setData({
        doctorList: data
      });
    }).catch(error => {
      wx.showToast({
        title: '删除失败',
        icon: 'error'
      })
      console.error('请求失败:', error); // 请求失败，处理错误
    });
  },
  
  onShow: function () {
    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      this.getTabBar().setData({
        selected: 1 //这个数字是当前页面在tabBar中list数组的索引
      })
    }

    request({
      url: '/doctor/list', // 相对路径，将与 baseURL 组合
      method: 'GET', // 请求方法
    }).then(data => {
      console.log(data);
      this.setData({
        doctorList: data
      });
      console.log(this.data)
    }).catch(error => {
      console.error('请求失败:', error); // 请求失败，处理错误
    });

  }

})