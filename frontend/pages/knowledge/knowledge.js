import request from '../../api/request.js'
import host from '../../api/server.js'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    count: 10,
    title: "健康知识",
    subtitle: "探索您的无限可能",
    backgroundColor: "#F6A710",
    articles: [
    ]
  },

  getArticles: function () {
    request({
      url: '/article/list?count=' + this.data.count, // 相对路径，将与 baseURL 组合
      method: 'GET', // 请求方法
    }).then(data => {
      console.log(data);
      const articles = data;
      articles.forEach(article => {
        article.pictureUrl = host + article.pictureUrl;
      })
      this.setData({
        articles: articles
      });
    }).catch(error => {
      wx.showToast({
        title: '加载失败',
        icon: 'error'
      })
      console.error('请求失败:', error); // 请求失败，处理错误
    });
  },

  onShow: function () {
    this.getArticles();
    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      this.getTabBar().setData({
        selected: 2 //这个数字是当前页面在tabBar中list数组的索引
      })
    }
  },

  onReachBottom: function () {
    this.getArticles();
  },

  onAsk: function () {
    wx.navigateTo({
      url: '../consult/consult',
    })
  },

  onAnswer: function () {
    wx.navigateTo({
      url: '../answer/answer',
    })
  },

  onDetail: function(e) {
    console.log(e);
    wx.navigateTo({
      url: '../articledetail/articledetail?id=' + e.currentTarget.dataset.id
    });
  }

})