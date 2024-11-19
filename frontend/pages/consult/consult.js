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
    consultHistory: [
      // {
      //   time: "今天10:33",
      //   question: "一天喝多少水比较合适？",
      //   answer: ""
      // },
      // {
      //   time: "今天10:13",
      //   question: "正常血压应该是多少？",
      //   answer: "一般来说，正常低压在60-100毫米汞柱之间，正常高压在100-140毫米汞柱之间。"
      // },
      // {
      //   time: "今天9:55",
      //   question: "老年人一日几餐比较合适？",
      //   answer: "老年人的吸收能力不如青年人群，但不能因此减少每天吃饭的次数，还是应当遵循一日三餐，同时注意每餐的摄入量不能太多。"
      // }
    ]
  },

  onConsultPressed: function (e) {
    if (e.currentTarget.dataset.answerlength === 0) {
      wx.showModal({
        title: '该问题暂无回答！',
        content: '请耐心等待',
        showCancel: false
      });
      return;
    }
    let id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/questiondetail/questiondetail?id=' + id,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    request({
      url: '/question/record', // 相对路径，将与 baseURL 组合
      method: 'GET', // 请求方法
    }).then(data => {
      console.log(data);
      this.setData({
        consultHistory: data.reverse()
      });
    }).catch(error => {
      wx.showToast({
        title: '加载失败',
        icon: false
      })
      console.error('请求失败:', error); // 请求失败，处理错误
    });
  },

  getQuestion: function (e) {
    console.log(e);
    this.setData({
      question: e.detail.value
    })
  },

  search: function () {
    if (this.data.question === "") {
      wx.showModal({
        title: '问题不能为空！',
        content: '',
        showCancel: false
      })
    } else {
      wx.showModal({
        title: '确定发布该问题？',
        content: '',
        showCancel: true,
        success: res => {
          if (res.confirm) {
            request({
              url: '/question/add', // 相对路径，将与 baseURL 组合
              method: 'POST', // 请求方法
              data: this.data.question
            }).then(data => {
              console.log(data);
              wx.showToast({
                title: '问题发布成功',
              })
              this.setData({
                consultHistory: data.reverse()
              })
            }).catch(error => {
              wx.showToast({
                title: '问题发布失败',
                icon: 'error'
              })
              console.error('请求失败:', error); // 请求失败，处理错误
            });
          }
        }
      })
    }
  }
})