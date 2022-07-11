<!--  -->
<template>
  <div id="main">
    
    <el-dialog
      title="遇到的问题"
      :visible.sync="dialogVisible"
      width="80%"
      :before-close="handleClose"
    > 
        <!-- <p id="texts">上传图片：</p>
        <el-upload
          class="upload-demo"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :on-success="uploadSuccess"
          :before-remove="beforeRemove"
          :file-list="fileList"
          :limit="3"
          :on-exceed="handleExceed"
          list-type="picture"
        >
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload> -->
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
      fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
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
     handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      }, handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },doit(){
          //完成填写，退出
            let fileList = this.fileList;
            let texts = this.textarea;
            console.log("------");
            console.log(texts);
            console.log("--------");
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
