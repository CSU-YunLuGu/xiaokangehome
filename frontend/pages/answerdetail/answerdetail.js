// pages/questiondetail/questiondetail.js

import request from '../../api/request.js'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "健康问答",
    subtitle: "探索您的无限可能",
    backgroundColor: "#F6A710",
    id: -1,
    detail: {},
    myAnswer: ""
  },

  handleInput: function (e){
    this.setData({
      myAnswer: e.detail.value
    })
  },

  onRecordSubmit: function () {
    if (this.data.myAnswer === "") {
      wx.showModal({
        title: '回答不能为空！',
        content: '',
        showCancel: false
      })
    } else {
      wx.showModal({
        title: '确定提交回答？',
        content: '',
        showCancel: true,
        success: res => {
          if (res.confirm) {
            this.onRecordSubmitHandler();
          }
        }
      })
    }
  },

  onRecordSubmitHandler: function () {
    request({
      url: '/question/answer/' + this.data.id, // 相对路径，将与 baseURL 组合
      method: 'POST', // 请求方法
      data: this.data.myAnswer
    }).then(data => {
      console.log(data);
      wx.showToast({
        title: '提交成功',
      })
      this.setData({
        detail: {
          username: data.username,
          id: data.id,
          time: data.time,
          question: data.question,
          answer: data.answer.reverse()
        },
        myAnswer: ""
      })
    }).catch(error => {
      wx.showToast({
        title: '提交失败',
        icon: 'error'
      })
      console.error('请求失败:', error); // 请求失败，处理错误
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    console.log(options.id)
    this.setData({
      id: options.id
    })
    request({
      url: '/question/record/' + options.id, // 相对路径，将与 baseURL 组合
      method: 'GET', // 请求方法
    }).then(data => {
      this.setData({
        detail: {
          username: data.username,
          id: data.id,
          time: data.time,
          question: data.question,
          answer: data.answer.reverse()
        },
      })
    }).catch(error => {
      wx.showToast({
        title: '加载失败',
        icon: 'error'
      })
      console.error('请求失败:', error); // 请求失败，处理错误
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})