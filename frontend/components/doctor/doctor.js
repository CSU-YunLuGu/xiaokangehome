// components/doctor/docto.js

import host from "../../api/server.js"

Component({

  /**
   * 组件的属性列表
   */
  properties: {
    userId: {
      type: Number
    },
    id: {
      type: Number
    },
    certificatePicUrl: {
      type: String
    },
    photoUrl: {
      type: String
    },
    name: {
      type: String
    },
    gender: {
      type: Boolean
    },
    major: {
      type: String
    },
    job: {
      type: String
    },
    // rating: {
    //   type: Number
    // },
    environment: {
      type: Number
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    server: host
  },

  /**
   * 组件的方法列表
   */
  methods: {
    showUrl: function () {
      console.log(this.properties.photoUrl)
    },
    delete: function() {
      this.triggerEvent('delete', {
        id: this.properties.id
      })
    },
    ask: function() {
      this.triggerEvent('ask', {})
    },
    add: function() {
      this.triggerEvent('add', {})
    },
    certificate: function() {
      this.triggerEvent('cer', {})
    }
  }
})