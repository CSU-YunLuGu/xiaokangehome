import request from '../../api/request.js'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "健康问答",
    subtitle: "探索您的无限可能",
    backgroundColor: "#F6A710",
    question: "",
    keyword: "",
    consultHistory: [
    ]
  },

  onConsultPressed: function (e) {
    let id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/answerdetail/answerdetail?id=' + id,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
  },

  getQuestion: function(e) {
    this.setData({
      keyword: e.detail.value
    })
  },

  search: function() {
    if (this.data.keyword === ""){
      wx.showModal({
        title: '关键词不能为空！',
        content: '',
        showCancel: false
      })
      return;
    }
    request({
      url: '/question/search?keyword=' + this.data.keyword, // 相对路径，将与 baseURL 组合
      method: 'GET', // 请求方法
    }).then(data => {
      console.log(data);
      this.setData({
        consultHistory: data.reverse()
      })
    }).catch(error => {
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