<!--  -->
<template>
  <div id="main">
    
    <el-dialog
      title="遇到的问题"
      :visible.sync="dialogVisible"
      width="80%"
      :before-close="handleClose"
    > 
      <div id="div2">
        <p id="texts">问题描述：</p>
        <el-input
          type="textarea"
          :rows="8"
          placeholder="请输入内容"
          v-model="textarea"
        >
        </el-input>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-row type="flex" justify="center" align="middle">
          <el-col :span="15">
            <el-button @click="btnexit">取 消</el-button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <el-button type="primary" @click="doit">完成</el-button>
          </el-col>
        </el-row>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "",
  data() {
    return {
      dialogVisible: false,
      textarea: "",
      images: [], //存储图片路径
      imgs: [], //存储图片文件内容
    };
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    btnexit() {
      //取消按钮
      this.dialogVisible = false;
    },
     doit(){
          //完成填写，退出
            let fileList = this.fileList;
            let texts = this.textarea;
            //将值传回父组件
            this.$emit("getfile1",fileList);
            this.$emit("gettext1",texts);
            this.btnexit();
      }

  },
  components: {},
  computed: {},
};
</script>
<style scoped>
#main {
  width: 100%;
  height: 100%;
}
#div1 {
  width: 80%;
  height: 200px;
  margin: 0 auto;
  border: 1px solid beige;
}
#div2 {
  width: 80%;
  height: auto;
  margin: 0 auto;
}
#texts {
  font-family: "微软雅黑";
  font-size: large;
}
</style>
