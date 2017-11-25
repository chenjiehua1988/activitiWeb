
/*下拉框渲染高度重写,size可以传具体高度*/

Vue.component('el-select', {
  extends: Vue.options.components['ElSelect'],
  methods: {
    resetInputHeight: function resetInputHeight() {
      var _this = this;

      sizeMap = {
        'large': 42,
        'small': 30,
        'mini': 22
      };
      var inputSize = parseInt(this.size) ? parseInt(this.size) : sizeMap[this.size];
      this.$nextTick(function () {
        if (!_this.$refs.reference) return;
        var inputChildNodes = _this.$refs.reference.$el.childNodes;
        var input = [].filter.call(inputChildNodes, function (item) {
          return item.tagName === 'INPUT';
        })[0];
        input.style.height = Math.max(_this.$refs.tags.clientHeight + 6, inputSize || 36) + 'px';
        if (_this.visible && _this.emptyText !== false) {
          _this.broadcast('ElSelectDropdown', 'updatePopper');
        }
      });
    }
  }
});