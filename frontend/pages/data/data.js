import request from '../../api/request.js';

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "健康数据",
    subtitle: "了解您的点点滴滴",
    backgroundColor: "#0F71E7",
    ani: 0,
    anoptions: [
      "运动时长", "心率", "睡眠时间", "血压"
    ],
    units: [
      "分钟", "次/分钟", "小时", "毫米汞柱"
    ],
    dateList: ["8/20", "8/21", "8/22", "8/23", "8/24", "8/25", "8/26"],
    dataList: [
      // [60, 80, 100, 0, 60, 30, 50],
      // [60, 70, 60, 90, 80, 60, 80],
      // [8, 9, 9, 10, 8, 10, 9],
      // [80120, 90120, 70130, 80130, 80120, 70120, 90140]
    ],
    foi: 0,
    fooptions: ["早餐", "午餐", "晚餐"],
    recommend: [
      // "鸡蛋2颗，面包100克，苹果1只",
      // "鸡胸肉200克，西兰花100克，酸奶100毫升",
      // "三文鱼煎片150克，烤甜薯100克，清炒菠菜100克，清汤200毫升"
    ],
    fochangable: false,
    latestMedicineDate: "",
    latestMedicine: "",
    latestConsultDate: "",
    latestConsult: ""
  },

  onNew1: function () {
    wx.navigateTo({
      url: '../newrecord/newrecord',
    })
  },

  onNew2: function () {
    wx.navigateTo({
      url: '../newmedical/newmedical',
    })
  },

  changeOption: function () {
    var that = this
    wx.showActionSheet({
      itemList: that.data.anoptions,
      success: (res) => {
        that.setData({
          ani: res.tapIndex
        })
      },
      fail: (err) => {
        console.error('选择操作失败', err);
      }
    });
  },

  // anChangeOption: function (e) {
  //   this.setData({
  //     anchangable: false,
  //     ani: e.currentTarget.dataset.ani
  //   })
  // },

  // foStartChange: function () {
  //   this.setData({
  //     fochangable: !this.data.fochangable
  //   })
  // },

  // foChangeOption: function (e) {
  //   this.setData({
  //     fochangable: false,
  //     foi: e.currentTarget.dataset.foi
  //   })
  // },

  onClickButton: function () {
    wx.navigateTo({
      url: '../connect/connect',
    })
  },

  onShow: function () {
    // tabBar 相关
    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      this.getTabBar().setData({
        selected: 0 //这个数字是当前页面在tabBar中list数组的索引
      })
    }
    // // 膳食推荐
    // request({
    //   url: '/diet/recommend', // 相对路径，将与 baseURL 组合
    //   method: 'GET', // 请求方法
    // }).then(data => {
    //   console.log(data)
    //   this.setData({
    //     recommend: [data.breakfast, data.lunch, data.dinner],
    //   });
    // }).catch(error => {
    //   console.error('请求失败:', error); // 请求失败，处理错误
    // });
    // 七天记录
    request({
      url: '/analysis', // 相对路径，将与 baseURL 组合
      method: 'GET', // 请求方法
    }).then(data => {
      console.log(data)
      this.setData({
        dataList: [
          data.map(item => item.exerciseDuration).reverse(),
          data.map(item => item.heartRate).reverse(),
          data.map(item => item.sleepDuration).reverse(),
          data.map(item => {
            if (item.systolicPressure != null && item.diastolicPressure != null) {
              return item.systolicPressure + item.diastolicPressure * 1000;
            } else {
              return null;
            }
          }).reverse()
        ],
        dateList: data
          .map(item => item.date.split("-")[1] + "/" + item.date.split("-")[2])
          .reverse()
      })
      console.log(this.data.dataList)
    }).catch(error => {
      console.error('请求失败:', error); // 请求失败，处理错误
    });
    // 吃药看病
    request({
      url: '/medical', // 相对路径，将与 baseURL 组合
      method: 'GET', // 请求方法
    }).then(data => {
      console.log(data)
      this.setData({
        latestMedicineDate: (data.medicineList.length === 0 ? "无吃药记录" : data.medicineList[0].date),
        latestMedicine: (data.medicineList.length === 0 ? "" : data.medicineList[0].detail),
        latestConsultDate: (data.consultList.length === 0 ? "无看病记录" : data.consultList[0].date),
        latestConsult: (data.consultList.length === 0 ? "" : data.consultList[0].detail)
      })
    }).catch(error => {
      console.error('请求失败:', error); // 请求失败，处理错误
    });
  },

  onMore: function () {
    wx.navigateTo({
      url: '../moremedical/moremedical',
    })
  }


})