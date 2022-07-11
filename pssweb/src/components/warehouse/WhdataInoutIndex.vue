<template>
  <div id="inoutIndex">
    <!-- 多条件查询开始 -->
    <el-row>
      <el-col :span="24">
        <el-form :inline="true" class="demo-form-inline">
          <!-- <el-form-item label="入库时间:">
            <el-input v-model="intime" placeholder="入库时间"></el-input>
          </el-form-item>
          <el-form-item label="出库时间:">
            <el-input v-model="outtime" placeholder="出库时间"></el-input>
          </el-form-item> -->

          <div class="block">
            <span class="demonstration">入库时间</span>
            <el-date-picker
              v-model="intime"
              type="date"
              placeholder="选择入库时间"
            >
            </el-date-picker>
          </div>

          <div class="block">
            <span class="demonstration">出库时间</span>
            <el-date-picker
              v-model="outtime"
              type="date"
              placeholder="选择出库时间"
            >
            </el-date-picker>
          </div>

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
    <el-table :data="pssTestItems" height="600" border style="width: 100%">
      <el-table-column type="index" label="序号" width="80"></el-table-column>
      <el-table-column prop="orderno" label="工单编号" width="120">
      </el-table-column>
      <el-table-column
        prop="intime"
        :formatter="formateDate"
        label="入库时间"
        width="200"
      >
      </el-table-column>
      <el-table-column
        prop="outtime"
        :formatter="formateDate"
        label="出库时间"
        width="200"
      >
      </el-table-column>
      <el-table-column prop="inouttype" label="类型" width="120">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.inouttype === 1 ? 'primary' : 'success'"
            disable-transitions
            ><span v-if="scope.row.inouttype === 1">出库</span
            ><span v-if="scope.row.inouttype === 2">入库</span></el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row.id)" type="text"
            >删除</el-button
          >
          <el-button type="text" @click="goinout(scope.row.orderno)"
            >查看详情</el-button
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

    <!-- 调用添加组件 -->
    <!-- <whdata-add ref="whdataAdd"></whdata-add> -->
    <!-- 调用修改组件 -->
    <!-- <whdata-update ref="whdataUpdate"></whdata-update> -->
  </div>
</template>

<script>
// import WhdataAdd from "@/components/warehouse/WhdataAdd.vue";
// import WhdataUpdate from "@/components/warehouse/WhdataUpdate.vue";

export default {
  name: "ExamIndexView",
  data() {
    return {
      orderno: "",
      intime: "",
      outtime: "",
      inouttype: "",
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
      this.$axios
        .get("/api/inout/list", {
          params: {
            intime: this.intime,
            outtime: this.outtime,
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
      // 测试有没有权限
      axios
        .get("/api/test/inoutadd")
        .then((result) => {
          if (result.data.code === 200) {
            //如果成立跳转过去
            this.$router.push({ name: "inoutadd" });
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
    goinout(orderno) {
      this.$router.push({ name: "psstool", params: { orderno } });
    },
    //删除出入库数据
    handleClick(id) {
      axios
        .get("/api/test/whdataupdate")
        .then((result) => {
          if (result.data.code === 200) {
            // 如果成立跳转过去
            this.$confirm("此操作将永久删除此条出入库信息, 是否继续?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            })
              .then(() => {
                this.$axios
                  .get("/api/inout/delete", { params: { id } })
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
    formateDate(row, column) {
      //转换时间格式
      let data = row[column.property];
      if (data == null) {
        return null;
      }
      let dt = new Date(data);
      return (
        dt.getFullYear() +
        "-" +
        (dt.getMonth() + 1) +
        "-" +
        dt.getDate() +
        " " +
        dt.getHours() +
        ":" +
        dt.getMinutes() +
        ":" +
        dt.getSeconds()
      );
    },
  },
  components: {},
  computed: {},
};
</script>
<style scoped>
#inoutIndex {
  width: 100%;
  height: 100%;
}
</style>
