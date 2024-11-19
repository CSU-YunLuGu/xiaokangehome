import request from "../../api/request.js"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    keyword: "",
    hidden: true,
    certificatePicUrl: "",
    name: "李医生",
    title: "添加医生",
    subtitle: "节约您的一分一秒",
    backgroundColor: "#9625C2",
    environment: false,
    doctorList: []
  },

  getKey: function (e) {
    console.log(e);
    this.setData({
      keyword: e.detail.value
    })
  },

  certificate: function (e) {
    console.log(e);
    this.setData({
      hidden: false,
      certificatePicUrl: e.currentTarget.dataset.cerurl
    })
    wx.navigateTo({
      // url: '../certificate/certificate'
      url: '../certificate/certificate?certificatePicUrl=' + this.data.certificatePicUrl,
    })

  },

  confirm: function () {
    this.setData({
      hidden: true,
    })
  },

  add: function (e) {
    const id = e.currentTarget.dataset.id;
    wx.showModal({
      title: '添加医生',
      content: '确定要添加该医生吗？',
      success: (res) => {
        if (res.confirm) {
          request({
            url: '/doctor/add?id=' + id, // 相对路径，将与 baseURL 组合
            method: 'GET', // 请求方法
          }).then(data => {
            wx.showToast({
              title: '添加成功！',
              icon: 'success'
            })
            this.search()
          }).catch(error => {
            console.error('请求失败:', error); // 请求失败，处理错误
          });
        }
      }
    })
  },

  search: function () {
    if (this.data.keyword === "") {
      wx.showModal({
        title: '关键词不能为空！',
        showCancel: false
      });
      return;
    }
    request({
      url: '/doctor/search?keyword=' + this.data.keyword, // 相对路径，将与 baseURL 组合
      method: 'GET', // 请求方法
    }).then(data => {
      console.log(data);
      this.setData({
        doctorList: data
      });
    }).catch(error => {
      console.error('请求失败:', error); // 请求失败，处理错误
    });
  }

})