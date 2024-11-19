import request from '../../api/request.js'

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "健康知识",
    subtitle: "探索您的无限可能",
    backgroundColor: "#F6A710",
    id: -1,
    author: "",
    time: "",
    articleTitle: "",
    content: ""
  },
  

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    console.log(options.id)
    request({
      url: '/article/read?id=' + options.id, // 相对路径，将与 baseURL 组合
      method: 'GET', // 请求方法
    }).then(data => {
      console.log(data);
      this.setData({
        author: data.author,
        articleTitle: data.title,
        time: data.time,
        content: data.content
      });
    }).catch(error => {
      console.error('请求失败:', error); // 请求失败，处理错误
    });
  },

})