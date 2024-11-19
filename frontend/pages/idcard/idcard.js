// pages/idcard/idcard.js
Page({
  data: {
    //身份证
    front: "/images/front.png",
    // front_suc: "/images/front_suc.png",
    // front_err: "/images/front_err.png",
    back: "/images/back.png",
    // back_suc: "/images/back_suc.png",
    // back_err: "/images/back_err.png",
    isFrontValid: false,
    isBackValid: false,
    isAgreed: true,
    canUpload: false,
    openid: '',
  },

  onLoad() {
    this.getOpenId();
  },

  // 获取用户的openid
  getOpenId() {
    const that = this;
    wx.login({
      success(res) {
        if (res.code) {
          // 调用服务器端获取openid
          wx.request({
            url: 'url/getOpenId', //服务器地址
            method: 'POST',
            data: {
              code: res.code
            },
            success: (response) => {
              that.setData({
                openid: response.data.openid
              });
            }
          });
        } else {
          console.log('登录失败！' + res.errMsg);
        }
      }
    });
  },
  
  onAgreementChange(e) {
    this.setData({
      isAgreed: e.detail.value.length > 0
    });
  },

  upLoadTestA:function() { // test
    const that = this;
    that.setData({
      isFrontValid: true, 
      front: '/images/front_suc.png', 
      //front: '/images/front_err.png', 
    });
    that.checkUploadCondition();
  },
  upLoadTestB:function() { // test
    const that = this;
    that.setData({
      isBackValid: false, 
      //back: '/images/back_suc.png',
      back: '/images/back_err.png', 
    });
    that.checkUploadCondition();
  },

  chooseFront: function() { //人像面
    const that = this;
    wx.chooseMedia({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success(res) {
        const tempFilePath = res.tempFilePaths[0];
        that.upLoadImage(tempFilePath, 'Front');
      }
    });  
  },

  chooseBack: function() { //国徽面
    const that = this;
    wx.chooseMedia({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success(res) {
        const tempFilePath = res.tempFilePaths[0];
        that.upLoadImage(tempFilePath, 'Back');
      }
    });  
  },

  // 上传图片至服务器并判断是否符合条件
  // 上传服务器：openid, image, type(Front, Back)
  // 返回：response_data, isValid(True, False)
  uploadImage(filePath, type) {
    const that = this;
    wx.uploadFile({
      url: 'url', // 服务器地址
      filePath: filePath,
      name: 'file',
      formData: {
        type: type,
        openid: this.data.openid
      },
      success(res) {
        const data = JSON.parse(res.data);
        if (type === 'Front') {
          if (data.isValid) { //ture
            that.setData({
              front: '/images/front_suc.png', 
              isFrontValid: true
            });
          } else {
            that.setData({
              front: '/images/front_err.png', 
              isFrontValid: false
            });
          }
        } else if (type === 'Back') {
          if (data.isValid) {  //true
            that.setData({
              back: '/images/back_suc.png', 
              isBackValid: true
            });
          } else {
            that.setData({
              back: '/images/back_err.png', 
              isBackValid: false
            });
          }
        }
        that.checkUploadCondition();
      }
    });
  },

  // 检查图片是否均符合条件，控制上传按钮状态
  checkUploadCondition() {
    this.setData({
      canUpload: this.data.isFrontValid && this.data.isBackValid
    });
  },

  // 上传符合条件的图片
  uploadImages: function() {
    if (this.data.canUpload) {
      wx.request({
        url: 'url', 
        method: 'POST',
        data: {
          openid: this.data.openid,
          front: this.data.front,
          back: this.data.back
        },
        success: (res) => {
          wx.showToast({
            title: '上传成功',
            icon: 'success'
          });
        },
        fail: () => {
          wx.showToast({
            title: '上传失败，请重试',
            icon: 'none'
          });
        }
      });
    } else {
      wx.showToast({
        title: '图片不符合要求，无法上传',
        icon: 'none'
      });
    }
  },

  methods: {

  },

  openCerticationLink: function() {
    wx.navigateTo({
      url: 'url' // 人脸识别服务协议链接
    });
  }

});