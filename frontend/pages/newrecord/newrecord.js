// pages/newrecord/newrecord.js

import request from "../../api/request.js"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "健康数据",
    subtitle: "记录您的点点滴滴",
    backgroundColor: "#0F71E7",
    exerciseDuration: null,
    heartRate: null,
    sleepDuration: null,
    diastolicPressure: null,
    systolicPressure: null
  },

  handleInput1: function (e) {
    this.setData({
      exerciseDuration: parseInt(e.detail.value)
    })
  },

  handleInput2: function (e) {
    this.setData({
      heartRate: parseInt(e.detail.value)
    })
  },

  handleInput3: function (e) {
    this.setData({
      sleepDuration: parseInt(e.detail.value)
    })
  },

  handleInput4: function (e) {
    this.setData({
      diastolicPressure: parseInt(e.detail.value)
    })
  },

  handleInput5: function (e) {
    this.setData({
      systolicPressure: parseInt(e.detail.value)
    })
  },

  onRecordSubmit: function () {
    // 检查所有条件
    if ((this.data.exerciseDuration === "" || !Number.isInteger(this.data.exerciseDuration) || this.data.exerciseDuration < 0 || this.data.exerciseDuration > 1440) ||
      (this.data.heartRate === "" || !Number.isInteger(this.data.heartRate) || this.data.heartRate < 0 || this.data.heartRate > 999) ||
      (this.data.sleepDuration === "" || !Number.isInteger(this.data.sleepDuration) || this.data.sleepDuration < 0 || this.data.sleepDuration > 24) ||
      (this.data.diastolicPressure === "" || !Number.isInteger(this.data.diastolicPressure) || this.data.diastolicPressure < 0 || this.data.diastolicPressure > 999) ||
      (this.data.systolicPressure === "" || !Number.isInteger(this.data.systolicPressure) || this.data.systolicPressure < 0 || this.data.systolicPressure > 999)) {
      wx.showModal({
        title: '数据格式有误',
        content: '请检查输入的数据格式是否正确',
        showCancel: false
      });
    } else {
      wx.showModal({
        title: '确认提交',
        content: '确认提交健康数据？',
        success: (res) => {
          if (res.confirm) {
            this.onRecordSubmitHandler();
          }
        }
      });
    }
  },

  onRecordSubmitHandler: function () {
    // 如果所有检查都通过，则可以继续处理数据
    console.log('健康数据已提交：', this.data);
    request({
      url: '/analysis', // 相对路径，将与 baseURL 组合
      method: 'POST', // 请求方法
      data: {
        exerciseDuration: this.data.exerciseDuration,
        heartRate: this.data.heartRate,
        sleepDuration: this.data.sleepDuration,
        diastolicPressure: this.data.diastolicPressure,
        systolicPressure: this.data.systolicPressure
      }
    }).then(data => {
      console.log(data)
      if (data === true) {
        wx.showModal({
          title: '今日已经提交过了',
          content: '请勿重复提交',
          showCancel: false
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


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

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