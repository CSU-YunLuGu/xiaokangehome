Component({
  data: {
    selected: 0,
    color: "#2c2c2c",
    list: [{
      pagePath: "../../pages/data/data",
      iconPath: "../tabbaricons/first/icon.png",
      selectedIconPath: "../tabbaricons/first/selectedicon.png",
      selectedColor: "#597AFF",
      text: "健康数据"
    }, {
      pagePath: "../../pages/ask/ask",
      iconPath: "../tabbaricons/second/icon.png",
      selectedIconPath: "../tabbaricons/second/selectedicon.png",
      text: "线上咨询",
      selectedColor: "#d049eb"
    }, {
      pagePath: "../../pages/knowledge/knowledge",
      iconPath: "../tabbaricons/third/icon.png",
      selectedIconPath: "../tabbaricons/third/selectedicon.png",
      text: "健康知识",
      selectedColor: "#ff9931"
    }, {
      pagePath: "../../pages/authentication/authentication",
      iconPath: "../tabbaricons/fourth/icon.png",
      selectedIconPath: "../tabbaricons/fourth/selectedicon.png",
      text: "身份信息",
      selectedColor: "#48E137"
    }]
  },
  methods: {
    switchTab(e) {
      const data = e.currentTarget.dataset;
      const url = data.path;
      wx.switchTab({
        url
      });
      this.setData({
        selected: data.index
      });
    }
  }
});
