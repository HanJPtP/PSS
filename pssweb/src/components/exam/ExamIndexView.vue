<!-- 考试页面首页 -->
<template>
  <div>
    <!-- 显示有多少题,每种题型有多少开始 -->
    <el-row>
      <el-col :span="24">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item>
            <span v-for="(value, key) in itemTypeCounts" :key="key">
              目前题库中有数量为&nbsp;{{ value.counts }}&nbsp;的{{
                value.pssTestType.typename
              }}&nbsp;&nbsp;
            </span>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="testUploadPaper = true"
              >上传试卷</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="dowmLoadTestPaperModul"
              >下载模板</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="dialogVisible = true"
              >生成试卷</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="showTestPaper"
              >查看试卷</el-button
            >
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <!-- 显示有多少题,每种题型有多少结束 -->
    <!-- 生成试卷弹窗页面开始 -->
    <el-dialog
      title="要生成的对应题目数量"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <!--eslint-disable-next-line-->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item
          v-for="(value, key) in itemTypeChooseCounts"
          :key="key"
          :label="value.pssTestType.typename + ':'"
        >
          <el-input
            placeholder="题目数量"
            v-model="itemTypeChooseCounts[key].counts"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="checkSub">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 生成试卷弹窗页面结束 -->
    <!-- 上传试卷弹窗开始 -->
    <el-dialog
      title="上传文件"
      :visible.sync="testUploadPaper"
      width="30%"
      :before-close="handleClose"
    >
      <el-form :inline="true" class="demo-form-inline">
        <div>
          <div>
            <input type="file" @change="fileSelect($event)" />
          </div>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="uploadTestPaper">上传</el-button>
        <el-button @click="testUploadPaper = false">返回</el-button>
      </span>
    </el-dialog>
    <!-- 上传试卷弹窗结束 -->
    <!-- 多条件查询开始 -->
    <el-row>
      <el-col :span="24">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="题目:">
            <el-input v-model="itemName" placeholder="题目"></el-input>
          </el-form-item>
          <el-form-item label="题目种类:">
            <el-select v-model="pssTestTypeid" placeholder="请选择" clearable>
              <el-option
                v-for="item in pssTestTypes"
                :key="item.id"
                :label="item.typename"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="queryAll">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addItem">添加</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <!-- 多条件查询结束 -->
    <!-- 查询之后显示的表格,和初始显示表格开始 -->
    <el-table :data="pssTestItems" height="500" border style="width: 100%">
      <el-table-column type="index" label="序号" width="80"></el-table-column>
      <el-table-column prop="testname" label="题目" width="400">
        <template slot-scope="scope"
          ><a href="#" @click.prevent="showItemById(scope.row.id)">{{
            scope.row.testname
          }}</a></template
        >
      </el-table-column>
      <el-table-column prop="pssTestType.typename" label="题目类型" width="120">
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.status === 'n' ? 'primary' : 'success'"
            disable-transitions
            ><span v-if="scope.row.status === 'n'">不可用</span
            ><span v-if="scope.row.status === 'y'">可用</span></el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template slot-scope="scope">
          <span v-if="scope.row.status === 'y'">
            <el-button @click="handleClick(scope.row.id)" type="text"
              >禁用</el-button
            >
          </span>
          <span v-if="scope.row.status === 'n'">
            <el-button @click="recoveryClick(scope.row.id)" type="text"
              >恢复</el-button
            >
          </span>
          <el-button type="text" @click="updateItem(scope.row.id)"
            >编辑</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 查询之后显示的表格,和初始显示表格结束 -->
    <!-- 分页开始 -->
    <el-row>
      <el-col :span="24">
        <div style="margin-top: 15px; text-align: center">
          <el-pagination
            background
            :page-sizes="[5, 10, 15, 20, 25]"
            :page-size="pagesizes"
            layout="prev,pager,next,sizes,total"
            :total="total"
            :current-page="currentpage"
            :page-count="pagecount"
            @size-change="changeSize"
            @current-change="changeCurrent"
            @prev-click="changeCurrent"
            @next-click="changeCurrent"
          >
          </el-pagination>
        </div>
      </el-col>
    </el-row>
    <!-- 分页结束 -->
  </div>
</template>

<script>
export default {
  name: "ExamIndexView",
  data() {
    return {
      itemName: "",
      pssTestTypes: [],
      pssTestItems: [],
      pssTestTypeid: "",
      total: 0, //分页控件总条数
      pagecount: 0, //分页控件总页数
      currentpage: 1, //分页控件当前页码
      pagesizes: 5, //分页控件每页条数
      itemTypeCounts: new Map(), //存储map集合(种类,数量)
      dialogVisible: false, //控制弹窗的变量
      itemTypeChooseCounts: new Map(), //存储用户输入的数据多少
      testUploadPaper: false, //控制弹窗
      files: [],
    };
  },
  created() {
    this.getTestType();
    this.onSubmit();
  },
  methods: {
    // 加载下拉框开始
    getTestType() {
      axios
        .get("/api/test/showtype")
        .then((result) => {
          if (result.data.code === 200) {
            this.pssTestTypes = result.data.data;
          } else {
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
        })
        .catch((e) => {
          this.$message({
            showClose: true,
            message: "网络波动异常",
            type: "error",
          });
        });
    },
    // 加载下拉框结束
    queryAll() {
      this.currentpage = 1;
      this.onSubmit();
    },
    //  点击查询,和初次页面加载开始
    onSubmit() {
      this.showItemTypeCounts();
      axios
        .get("/api/test/showitem", {
          params: {
            itemName: this.itemName,
            typeid: this.pssTestTypeid,
            pageNo: this.currentpage,
            pageSize: this.pagesizes,
          },
        })
        .then((result) => {
          if (result.data.code === 200) {
            this.pssTestItems = result.data.data.data;
            this.total = result.data.data.totalNum;
            this.pagecount = result.data.data.totalPage;
            this.currentpage = result.data.data.pageNo;
            this.pagesizes = result.data.data.pageSize;
          } else {
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
        })
        .catch((e) => {
          this.$message({
            showClose: true,
            message: "网络波动异常",
            type: "error",
          });
        });
    },
    //  点击查询,和初次页面加载结束
    //改变每页显示数量的函数开始
    changeSize(pagesizes) {
      this.currentpage = 1;
      this.pagesizes = pagesizes;
      this.onSubmit();
    },
    changeCurrent(current) {
      this.currentpage = current;
      this.onSubmit();
    },
    //改变每页显示数量的函数结束
    //根据id查询题目开始
    showItemById(id) {
      this.$router.push({ name: "examShow", params: { id } });
    },
    //根据id查询题目结束
    //添加题目开始
    addItem() {
      //测试有没有权限
      axios
        .get("/api/test/additemtest")
        .then((result) => {
          if (result.data.code === 200) {
            //如果成立跳转过去
            this.$router.push({ name: "examAdd" });
          } else {
            //不成立提示消息
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
        })
        .catch((e) => {
          this.$message({
            showClose: true,
            message: "网络波动异常",
            type: "error",
          });
        });
    },
    //添加题目结束
    //删除题目开始
    handleClick(id) {
      axios
        .get("/api/test/delitem", { params: { id } })
        .then((result) => {
          if (result.data.code === 200) {
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "success",
            });
          } else {
            //不成立提示消息
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
          this.onSubmit();
        })
        .catch((e) => {
          this.$message({
            showClose: true,
            message: "网络波动异常",
            type: "error",
          });
        });
    },
    //删除题目结束
    //恢复题目开始
    recoveryClick(id) {
      axios
        .get("/api/test/recoveryitem", { params: { id } })
        .then((result) => {
          if (result.data.code === 200) {
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "success",
            });
          } else {
            //不成立提示消息
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
          this.onSubmit();
        })
        .catch((e) => {
          this.$message({
            showClose: true,
            message: "网络波动异常",
            type: "error",
          });
        });
    },
    //恢复题目结束
    //修改题目开始
    updateItem(id) {
      axios
        .get("/api/test/updateitemtest")
        .then((result) => {
          if (result.data.code === 200) {
            this.$router.push({ name: "examUpdate", params: { id } });
          } else {
            //不成立提示消息
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
          this.onSubmit();
        })
        .catch((e) => {
          this.$message({
            showClose: true,
            message: "网络波动异常",
            type: "error",
          });
        });
    },
    //修改题目结束
    // 查询有多少种类题目,数量开始
    showItemTypeCounts() {
      axios
        .get("/api/test/showitemcounts")
        .then((result) => {
          if (result.data.code === 200) {
            //改变成赋值而不是地址引用
            this.itemTypeCounts = JSON.parse(JSON.stringify(result.data.data));
            this.itemTypeChooseCounts = { ...result.data.data };
            console.info(this.itemTypeChooseCounts);
            for (let i = 0; i < this.pssTestTypes.length; i++) {
              //遍历map集合
              this.itemTypeChooseCounts[this.pssTestTypes[i].id].counts = "";
              console.info(
                this.pssTestTypes[i].typename +
                  this.itemTypeChooseCounts[this.pssTestTypes[i].id].counts
              );
            }
          } else {
            //不成立提示消息
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
        })
        .catch((e) => {
          this.$message({
            showClose: true,
            message: "网络波动异常",
            type: "error",
          });
        });
    },
    // 查询有多少种类题目,数量结束
    // 弹窗的开关开始
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    // 弹窗的开关结束
    checkSub() {
      let msg = "";
      for (let i = 0; i < this.pssTestTypes.length; i++) {
        console.info(
          this.pssTestTypes[i].typename +
            this.itemTypeChooseCounts[this.pssTestTypes[i].id].counts
        );
        if (
          this.itemTypeChooseCounts[this.pssTestTypes[i].id].counts >
            this.itemTypeCounts[this.pssTestTypes[i].id].counts &&
          this.itemTypeChooseCounts[this.pssTestTypes[i].id].counts >= 0
        ) {
          msg =
            msg +
            this.pssTestTypes[i].typename +
            "的数量输入不正确,请输入0-" +
            this.itemTypeCounts[this.pssTestTypes[i].id].counts +
            "范围的数据;";
        }
      }
      if (msg.trim().length > 0) {
        this.$message({
          showClose: true,
          message: msg,
          type: "error",
        });
      } else {
        this.dialogVisible = false;
        this.creatTestPaper();
      }
    },
    //生成试卷开始
    creatTestPaper() {
      axios
        .post("/api/test/createTestPaper", this.itemTypeChooseCounts)
        .then((result) => {
          if (result.data.code === 200) {
            //考试题目
            this.$router.push({
              name: "examTest",
              params: { testPaper: result.data.data },
            });
          } else {
            //不成立提示消息
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
        })
        .catch((e) => {
          this.$message({
            showClose: true,
            message: "网络波动异常",
            type: "error",
          });
        });
    },
    //生成试卷结束
    //上传试卷开始
    fileSelect(e) {
      this.files = e.target.files[0];
    },
    uploadTestPaper() {
      let formData = new FormData();
      formData.append("files", this.files);
      console.info(formData);
      console.info(this.files);
      axios
        .post("/api/test/upload", formData, {
          contentType: "multipart/form-data",
        })
        .then((result) => {
          if (result.data.code === 200) {
            this.onSubmit();
            this.testUploadPaper = false;
          } else {
            //不成立提示消息
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
        })
        .catch((e) => {
          this.$message({
            showClose: true,
            message: "网络波动异常",
            type: "error",
          });
        });
    },
    //上传试卷结束
    //下载模板开始
    dowmLoadTestPaperModul() {
      axios
        .get("/api/exam/download")
        .then((result) => {
          if (result.data.code === 200) {
            console.info(1);
            window.location.href = result.data.data;
          } else {
            //不成立提示消息
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "warning",
            });
          }
        })
        .catch((e) => {
          this.$message({
            showClose: true,
            message: "网络波动异常",
            type: "error",
          });
        });
    },
    //下载模板结束
    //跳转到显示试卷页面开始
    showTestPaper() {
      this.$router.push({ name: "examShowTest" });
    },
    //跳转到显示试卷页面结束
  },
  components: {},
  computed: {},
};
</script>
<style scoped></style>
