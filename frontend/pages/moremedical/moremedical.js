// pages/moremedical/moremedical.js

import request from "../../api/request.js"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "吃药看病",
    subtitle: "记录您的点点滴滴",
    backgroundColor: "#0F71E7",
    medicineRecord: [],
    consultRecord: []
  },

  handleInput: function (e) {
    this.setData({
      text: e.detail.value
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    request({
      url: '/medical', // 相对路径，将与 baseURL 组合
      method: 'GET' // 请求方法
    }).then(data => {
      console.log(data)
      this.setData({
        medicineRecord: data.medicineList,
        consultRecord: data.consultList
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