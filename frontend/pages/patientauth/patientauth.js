// pages/doctorauth/doctorauth.js

import request from "../../api/request.js"

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: "编辑资料",
    subtitle: "陪伴您的每时每刻",
    backgroundColor: "#48E137",
    labels: ["用户名", "姓名", "性别", "出生日期", "身高", "体重", "过敏史", "疾病史"],
    placeholders: [
      "请填入不超过 10 个字符",
      "请填入您的中文姓名",
      "请选择“男”或“女”",
      "请填入“年-月-日”",
      "请填入整数（单位：CM）",
      "请填入整数（单位：KG）",
      "请填入不超过 10 个字符",
      "请填入不超过 10 个字符"
    ],
    values: ["", "", "", "", "", "", "", ""],
  },

  onShow: function () {
    request({
      url: '/user', // 相对路径，将与 baseURL 组合
      method: 'GET', // 请求方法
      data: {
        phone: "",
        password: ""
      }
    }).then(data => {
      console.log(data);
      this.setData({
        // labels: ["用户名", "姓名", "性别", "出生日期", "身高", "体重", "过敏史", "疾病史"],
        values: [
          data.username,
          data.name ? data.name : "",
          data.gender !== null ? (data.gender === true ? "男" : "女") : "",
          data.birthday ? data.birthday : "",
          data.height ? data.height : "",
          data.weight ? data.weight : "",
          data.allergyHistory ? data.allergyHistory : "",
          data.medicalHistory ? data.medicalHistory : ""
        ]
      })
    }).catch(error => {
      wx.showToast({
        title: '加载失败',
        icon: 'error'
      })
      console.error('请求失败:', error); // 请求失败，处理错误
    });
  },

  cer: function () {
    wx.navigateTo({
      url: '../../pages/idcard/idcard',
    })
  },

  inputHandle: function (e) {
    const index = e.currentTarget.dataset.index;
    const value = e.detail.value
    const values = this.data.values
    values[index] = value
    this.setData({
      values: values
    })
  },

  bindGender: function () {
    // const index = e.currentTarget.dataset.index;
    // if (index === 2) {    
    let that = this;
    const genders = ["男", "女"];
    wx.showActionSheet({
      itemList: genders,
      success: (res) => {
        that.setData({
          [`values[2]`]: genders[res.tapIndex]
        });
      },
      fail: (err) => {
        console.error('选择操作失败', err);
      }
    });
    // }
  },

  bindDateChange: function (e) {
    this.setData({
      [`values[3]`]: e.detail.value
    });
  },

  submitHandle: function () {
    const values = this.data.values;
    const chooseGender = function (values) {
      if (values[2] === "男") {
        return true;
      } else if (values[2] === "女") {
        return false;
      } else return null;
    }
    const payload = {
      username: values[0],
      name: values[1],
      gender: chooseGender(values),
      birthday: values[3] === "" ? null : values[3],
      height: parseInt(values[4]),
      weight: parseInt(values[5]),
      allergyHistory: values[6],
      medicalHistory: values[7]
    };
    if (payload.username.length > 10 ||
      payload.name.length > 10 ||
      payload.allergyHistory.length > 10 ||
      payload.medicalHistory.length > 10) {
      wx.showModal({
        title: '信息格式有误',
        content: '请检查输入内容格式是否正确',
        showCancel: false,
      });
      return;
    } else {
      wx.showModal({
        title: '确认修改',
        content: '确认提交修改吗？',
        success: (res) => {
          if (res.confirm) {
            request({
              url: '/user', // 相对路径，将与 baseURL 组合
              method: 'PUT', // 请求方法
              data: payload
            }).then(data => {
              if (data) {
                wx.showToast({
                  title: '修改成功',
                  icon: 'success'
                });
              } else {
                wx.showModal({
                  title: '无法更改姓名',
                  content: '您已经注册过医生，不能更改姓名！',
                  showCancel: false,
                });
              }
            }).catch(error => {
              console.error('请求失败:', error); // 请求失败，处理错误
            });
          } else if (res.cancel) {
            console.log('用户点击取消')
          }
        }
      })
    }
  }
})