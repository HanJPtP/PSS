<!-- 讲师添加子组件 -->
<template>
  <div>
    <el-drawer
      title="修改新型业务"
      :close-on-press-escape="true"
      :wrapperClosable="true"
      :size="500"
      :visible.sync="dialogUpd"
      direction="rtl"
      custom-class="demo-drawer"
      ref="drawer"
    >
      <div class="demo-drawer__content">
        <el-row type="flex" justify="center" align="middle">
          <el-col :span="20">
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item label="新型业务标题:">
                <el-input
                  placeholder="请输入新型业务标题"
                  type="text"
                  v-model="bmessage.btitle"
                ></el-input>
              </el-form-item>
              <el-form-item label="请选择新型业务类型:">
                <el-select
                  placeholder="请选择新型业务类型"
                  v-model="bmessage.busid"
                >
                  <el-option
                    v-for="business in busItem"
                    :key="business.id"
                    :label="business.busname"
                    :value="business.id"
                  ></el-option>
                </el-select>
              </el-form-item>
              <!-- 文件上传开始 -->
              <div>
                <div>
                  <input type="file" @change="fileSelect($event)" />
                  <button @click="fileSubmit">上传</button>
                </div>
                <div class="progress-wrap">
                  <p>上传进度</p>
                  <p class="progress"><span :style="style"></span></p>
                </div>
                <!-- 文件上传结束 -->
              </div>
              <el-form-item label="下载地址">
                <el-input
                  type="textarea"
                  rows="4"
                  cols="36"
                  v-model="bmessage.filecontent"
                ></el-input>
              </el-form-item>
            </el-form>
            <div class="demo-drawer__footer">
              <el-row type="flex" justify="center" align="middle">
                <el-col :span="20">
                  <el-button @click="btnexit">取 消</el-button>
                  <el-button type="primary" @click="btnUpd">添加</el-button>
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: "BusinessUpdView",
  data() {
    return {
      dialogUpd: false, // 默认不显示添加内容
      bmessage: {
        btitle: "",
        busid: "",
        filecontent: "",
        userid: "",
      },
      files: {}, //上传的文件对象
      progressBar: 0, // 记录上传进度的变量
      busItem: [], //新型业务类型
    };
  },
  methods: {
    btnexit() {
      //取消按钮
      // 清除数据内容
      (this.bmessage = {
        btitle: "",
        busid: "",
        filecontent: "",
      }),
        (this.progressBar = 0),
        (this.files = {}),
        (this.dialogUpd = false);
    },
    btnUpd() {
      // 修改新型业务
      this.$axios
        .post("/api/bmessage/upd", this.bmessage)
        .then((result) => {
          if (result.data.code === 200) {
            this.$message({
              showClose: true,
              message: "修改新型业务成功",
              type: "success",
            });
            // 隐藏窗口
            this.btnexit();
            // 刷新列表
            // this.$router.push({name:'teacherdata'});
            this.$forceUpdate();
          } else {
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "error",
            });
          }
        })
        .catch((e) => {
          console.log(e);
          this.$message({
            showClose: true,
            message: "当前网络差!",
            type: "error",
          });
        });
    },
    fileSelect(e) {
      this.files = e.target.files[0];
    },
    async fileSubmit() {
      const formData = new FormData();
      formData.append("files", this.files);
      await this.$axios
        .post("api/upload", formData, {
          onUploadProgress: (progressEvent) => {
            var percentCompleted = Math.round(
              (progressEvent.loaded * 100) / progressEvent.total
            );
            this.progressBar = percentCompleted;
          },
        })
        .then((res) => {
          console.log(res.data);
          if (res.data.code === 200) {
            this.$message({
              showClose: true,
              message: "上传成功!",
              type: "success",
            });
            this.bmessage.filecontent = res.data.msg;
          } else {
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
        });
    },
    loadBus() {
      this.$axios
        .get("/api/bmessage/type")
        .then((result) => {
          if (result.data.code === 200) {
            this.busItem = result.data.data;
          } else {
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
        })
        .catch((e) => {
          console.log(e);
          this.$message({
            showClose: true,
            message: "当前网络差!",
            type: "error",
          });
        });
    },
  },
  computed: {
    style() {
      // 进度条样式
      return {
        width: this.progressBar + "%",
      };
    },
  },
  components: {},
  created() {
    // 调用加载新型业务类型的方法
    this.loadBus();
    // 取当前用户的userid
    this.bmessage.userid = localStorage.getItem("userid");
  },
};
</script>
<style scoped>
.el-input,
.el-select {
  width: 300px;
}
.progress-wrap {
  width: 300px;
}
.progress-wrap p {
  width: 100%;
}
.progress {
  background-color: #c5c8ce;
  height: 20px;
}
.progress span {
  display: block;
  background-color: #19be6b;
  height: 100%;
  width: 0;
}
</style>