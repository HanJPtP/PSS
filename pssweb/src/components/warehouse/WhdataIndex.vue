<!-- 考试页面首页 -->
<template>
  <div id="dataindex">
    <!-- 导航开始 -->
    <el-row>
      <el-col :span="300">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '' }">四库管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '' }"
            >四库物品管理</el-breadcrumb-item
          >
        </el-breadcrumb>
      </el-col>
    </el-row>
    <!-- 导航结束 -->
    <!-- 多条件查询开始 -->
    <el-row>
      <el-col :span="24">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="四库物品名称:">
            <el-input v-model="dataname" placeholder="四库物品名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
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
      <el-table-column prop="whnameno" label="物品编号" width="400">
      </el-table-column>
      <el-table-column prop="dataname" label="物品名称" width="120">
      </el-table-column>
      <el-table-column prop="numbers" label="物品数量" width="120">
      </el-table-column>
      <el-table-column prop="whname" label="物品类型" width="120">
      </el-table-column>
      <el-table-column prop="standard" label="物品规格" width="120">
      </el-table-column>
      <el-table-column prop="unitname" label="所属部门" width="120">
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row.id)" type="text"
            >删除</el-button
          >
          <el-button type="text" @click="updateItem(scope.row)">编辑</el-button>
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
  name: "WhdataIndex",
  data() {
    return {
      dataname: "",
      whnameno: "",
      numbers: "",
      standard: "",
      pssTestItems: [],

      total: 0, //分页控件总条数
      pagecount: 0, //分页控件总页数
      currentpage: 1, //分页控件当前页码
      pagesizes: 5, //分页控件每页条数
    };
  },
  created() {
    this.onSubmit();
  },
  methods: {
    //  点击查询,和初次页面加载开始
    onSubmit() {
      axios
        .get("/api/data/list", {
          params: {
            dataname: this.dataname,

            pageNo: this.currentpage,
            pageSize: this.pagesizes,
          },
        })
        .then((result) => {
          if (result.data.code === 200) {
            this.pssTestItems = result.data.data.data;
            console.log(result.data.data);
            this.total = result.data.data.totalNum;
            this.pagecount = result.data.data.totalPage;
            this.currentpage = result.data.data.pageNo;
            this.pagesizes = result.data.data.pageSize;
            this.$message({
              showClose: true,
              message: result.data.msg,
              type: "success",
            });
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

    //添加四库物品开始
    addItem() {
      // 测试有没有权限;
      axios
        .get("/api/test/whdataadd")
        .then((result) => {
          if (result.data.code === 200) {
            // 如果成立跳转过去;
            this.$router.push({ name: "whdataAdd" });
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
    //添加结束
    //修改开始
    updateItem(whinfor) {
      // 测试有没有权限
      axios
        .get("/api/test/whdataupdate")
        .then((result) => {
          if (result.data.code === 200) {
            // 如果成立跳转过去
            this.$router.push({ name: "whdataUpdate", params: { whinfor } });
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

    //删除四库物品信息
    handleClick(id) {
      // 测试有没有权限
      axios
        .get("/api/test/whdatadel")
        .then((result) => {
          if (result.data.code === 200) {
            // 如果成立跳转过去
            this.$confirm("此操作将永久删除物品信息, 是否继续?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            })
              .then(() => {
                this.$axios
                  .get("/api/data/delete", { params: { id } })
                  .then((result) => {
                    if (result.data.code === 200) {
                      this.$message({
                        type: "success",
                        message: "删除成功!",
                      });
                      this.onSubmit();
                    } else {
                      this.$message({
                        type: "info",
                        message: result.data.msg,
                      });
                    }
                  })
                  .catch((e) => {
                    this.$message({
                      type: "error",
                      message: "网络环境差。。。",
                    });
                  });
              })
              .catch((e) => {
                this.$message({
                  type: "info",
                  message: "已取消删除",
                });
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
  },
  components: {},
  computed: {},
};
</script>
<style scoped>
#business-main {
  width: 100%;
  height: 100%;
  margin: 15px;
  padding: 15px;
}
</style>
