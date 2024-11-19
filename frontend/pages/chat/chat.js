// pages/chat/chat.js

import request from "../../api/request"
import host from "../../api/server"

Page({
  data: {
    id: -1,
    identity: null,
    title: "线上咨询",
    subtitle: "节约您的一分一秒",
    backgroundColor: "#9625C2",
    doctor: "医生",
    messages: [],
    inputValue: '',
    toView: '',
    myAvatar: '',
    otherAvatar: '',
    myName: '',
    otherName: '',
    pollingInterval: null // 用于存储定时器ID
  },

  onHide: function () {
    // 清除定时器
    if (this.data.pollingInterval) {
      clearInterval(this.data.pollingInterval);
      this.data.pollingInterval = null; // 清除定时器ID
    }
  },

  onUnload: function () {
    // 清除定时器
    if (this.data.pollingInterval) {
      clearInterval(this.data.pollingInterval);
      this.data.pollingInterval = null; // 清除定时器ID
    }
  },

  onLoad(options) {
    console.log(options)
    this.setData({
      id: options.id,
      identity: options.identity
    })
    request({
      url: '/user/' + options.id,
      method: "GET"
    }).then(data => {
      console.log(data)
      this.setData({
        myAvatar: host + data[0].avatar,
        myName: data[0].name,
        otherAvatar: host + data[1].avatar,
        otherName: data[1].name
      })
      this.getChatMessages(options, true);
      this.data.pollingInterval = setInterval(() => {
        this.getChatMessages(options, false)
      }, 2000);
      // this.loadLocalMessages(); // 加载本地消息
      // this.connectToWebSocket();
    }).catch(error => {
      console.err(error)
      wx.showToast({
        title: '加载失败！',
        icon: 'error'
      })
    })
  },

  getChatMessages: function (options, init) {
    const that = this;
    request({
      url: '/chat?identity=' + options.identity + "&id=" + options.id,
      method: "GET"
    }).then(data => {
      data.forEach(d => {
        if (d.receiver === parseInt(options.id)) {
          d.send = true
        } else d.send = false;
      })
      that.setData({
        messages: data
      })
      if (init) {
        this.scrollToBottom();
      }
      // 处理接收到的数据，例如更新页面显示的消息
    }).catch(error => {
      console.error(error);
      wx.showToast({
        title: '加载失败！',
        icon: 'error'
      })
    });
  },

  // // 从本地存储加载聊天记录
  // loadLocalMessages() {
  //   const messages = wx.getStorageSync('messages') || [];
  //   this.setData({ messages });
  // },

  // // 将消息保存到本地存储
  // saveMessages() {
  //   wx.setStorageSync('messages', this.data.messages);
  // },

  //发送消息滑动到底部
  scrollToBottom() {
    if (this.data.messages.length > 0) {
      this.setData({
        toView: 'msg-' + (this.data.messages.length - 1)
      });
    }
  },

  // connectToWebSocket() {
  //   const socket = wx.connectSocket({
  //     url: 'wss://websocket-url',
  //   });
  //   socket.onOpen(() => {
  //     console.log('WebSocket连接已打开');
  //   });
  //   socket.onMessage((message) => {
  //     const data = JSON.parse(message.data);
  //     this.setData({
  //       messages: [...this.data.messages, data],
  //     });
  //     this.saveMessages(); // 保存消息
  //   });
  //   socket.onError((error) => {
  //     console.error('WebSocket错误', error);
  //   });
  //   socket.onClose(() => {
  //     console.log('WebSocket连接已关闭');
  //   });
  // },

  onInputChange(event) {
    this.setData({
      inputValue: event.detail.value,
    });
  },

  sendMessage() {
    if (!this.data.inputValue) {
      wx.showToast({
        title: '消息不能为空！',
        icon: 'error'
      })
      return;
    }
    const message = {      
      identity: this.data.identity,
      otherId: this.data.id,
      content: this.data.inputValue
    };
    request({
      url: '/chat',
      method: 'POST',
      data: message
    }).then(data => {
      data.forEach(d => {
        if (d.receiver === parseInt(this.data.id)) {
          d.send = true
        } else d.send = false;
      })
      this.setData({
        messages: data,
        inputValue: ''
      })
      this.scrollToBottom()
    }).catch(error => {
      wx.showToast({
        title: '发送失败！',
        icon: 'error'
      })
    })
  },

  //   // 发送图片或视频
  //   toggleExtraOptions() {
  //     wx.showActionSheet({
  //       itemList: ['发送图片', '发送视频'],
  //       success: (res) => {
  //         if (res.tapIndex === 0) {
  //           this.chooseMedia('image');
  //         } else if (res.tapIndex === 1) {
  //           this.chooseMedia('video');
  //         }
  //       },
  //       fail: (err) => {
  //         console.error('选择操作失败', err);
  //       }
  //     });
  //   },

  //   // 选择媒体文件
  //   chooseMedia(type) {
  //     wx.chooseMedia({
  //       count: 1,
  //       mediaType: [type],
  //       sourceType: ['album'],
  //       success: (res) => {
  //         const filePath = res.tempFiles[0].tempFilePath;
  //         this.sendMediaMessage(filePath, type);
  //       },
  //       fail: (err) => {
  //         console.error('选择媒体文件失败', err);
  //       }
  //     });
  //   },

  //   // 发送媒体消息
  //   sendMediaMessage(filePath, type) {
  //     wx.uploadFile({
  //       url: 'url', 
  //       filePath,
  //       name: 'file',
  //       success: (res) => {
  //         const data = JSON.parse(res.data);
  //         const message = {
  //           sender: 'me',
  //           content: `[${type === 'image' ? '图片' : '视频'}]`,
  //           time: new Date().toLocaleTimeString(),
  //           mediaUrl: data.url,
  //         };
  //         wx.sendSocketMessage({
  //           data: JSON.stringify(message),
  //         });
  //         this.setData({
  //           messages: [...this.data.messages, message],
  //         },() => {
  //           this.scrollToBottom(); // 发送消息后滚动到底部
  //         });
  //         this.saveMessages(); // 保存消息
  //       },
  //       fail: (err) => {
  //         console.error('上传媒体文件失败', err);
  //       },
  //     });
  //   }

});