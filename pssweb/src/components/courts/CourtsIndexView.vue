<!-- 台区首页模块 -->
<template>
  <div id="business-main">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>台区管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/courts' }"
        >台区列表</el-breadcrumb-item
      >
    </el-breadcrumb>
    <!-- 台区表单查询开始 -->
    <div id="form-main">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="台区名称：">
          <el-input v-model="cname" placeholder="请输入台区名称"></el-input>
        </el-form-item>
        <el-form-item label="台区路线：">
          <el-input v-model="circuit" placeholder="请输入台区路线"></el-input>
        </el-form-item><br/>
        <el-form-item label="台区行政村：">
          <el-input v-model="village" placeholder="请输入台区行政村"></el-input>
        </el-form-item>
        <el-form-item label="管理人：">
          <el-input
            v-model="username"
            placeholder="请输入台区管理人"
          ></el-input>
        </el-form-item>
        <!-- <el-form-item label="台区行政村:">
          <el-select placeholder="请选择台区行政村" v-model="busname">
            <el-option v-for="business in busItem" :key="business.id" :label="business.name" :value="business.busname"></el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item id="search-main">
          <el-button type="button" icon="el-icon-search" @click="queryCourts">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-circle-plus"
            id="add-main"
            @click="showAddCourts"
            >添加</el-button
          >
        </el-form-item>
      </el-form>
    </div>
    <!-- 台区表单查询结束 -->

    <!-- 台区表单开始 -->
    <div id="form-data">
      <el-table
        :data="villageItem.data"
        style="width: 100%"
        max-height="400" fit="fit"
        stripe="stripe"
      >
        <el-table-column
          align="center"
          type="index"
          :index="indexMethod"
          label="序号"
          width="50"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="pssCourts.cname"
          label="台区名称"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="pssCircuit.circuit"
          label="台区路线"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="village"
          label="台区行政村"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="pssUsers.username"
          label="管理人"
        ></el-table-column>
        <el-table-column align="center" fixed="right" label="操作" width="300px">
          <template slot-scope="scope">
            <el-button
              type="info"
              icon="el-icon-delete-solid"
              title="删除台区" size="small"
              @click="btndelcourts(scope.row)"
            ></el-button>
            <el-button
              type="primary"
              icon="el-icon-delete"
              title="删除台区路线" size="small"
              @click="btndelcircuit(scope.row)"
            ></el-button>
            <el-button
              type="danger"
              icon="el-icon-error"
              title="删除台区行政村" size="small"
              @click="btndelvillage(scope.row.id)"
            ></el-button>
            <el-button
              type="warning"
              icon="el-icon-edit"
              title="修改台区" size="small"
              @click="showupdcourts(scope.row)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 台区表单结束 -->

    <!-- 台区分页开始 -->
    <el-row>
      <el-col :span="24">
        <div style="margin-top: 15px; text-align: center">
          <el-pagination
            background
            :page-sizes="[5, 10, 15, 20, 25]"
            :page-size="pagesize"
            layout="prev,pager,next,sizes,total"
            :total="total"
            :page-count="pagecount"
            :current-page="currentpage"
            @size-change="chageSize"
            @current-change="chageCurrent"
            @prev-click="chageCurrent"
            @next-click="chageCurrent"
          >
          </el-pagination>
        </div>
      </el-col>
    </el-row>
    <!-- 台区分页结束 -->
    <courts-add-view ref="courtsAdd"></courts-add-view>
    <courts-upd-view ref="courtsUpd"></courts-upd-view>
  </div>
</template>

<script>
import CourtsAddView from "@/components/courts/CourtsAddView.vue";
import CourtsUpdView from "@/components/courts/CourtsUpdView.vue";

export default {
  name: "BusinessIndex",
  data() {
    return {
      courtsItem: [], //台区集合
      circuitItem: [], // 路线集合
      villageItem: [], // 行政村集合
      username: "", //管理人
      cname: "", //台区
      circuit: "", //路线
      village: "", // 行政村
      total: 0, //分页控件总条数
      pagecount: 0, //分页控件总页数
      currentpage: 1, //分页控件当前页码
      pagesize: 5, //分页控件每页条数
      account: "",
    };
  },
  methods: {
    indexMethod(index) {
      //表格自定义序号
      return index + 1;
    },
    queryCourts() {
      this.$axios
        .get("/api/village/list", {
          params: {
            village: this.village,
            username: this.username,
            cname: this.cname,
            circuit: this.circuit,
            pageNo: this.currentpage,
            pageSize: this.pagesize,
            account: this.account,
          },
        })
        .then((result) => {
          console.log(result.data);
          if (result.data.code === 200) {
            this.villageItem = result.data.data;
            //设置分页数据
            this.total = result.data.data.totalNum;
            this.pagecount = result.data.data.totalPage;
            this.currentpage = result.data.data.pageNo;
            this.pagesize = result.data.data.pageSize;
            localStorage.setItem("usersid", result.data.msg);
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
            message: "网络环境差。。。",
            type: "error",
          });
          console.log(e);
        });
    },
    chageSize(pagesize) {
      //改变每页条数下拉框事件，通过形参获得当前选中的条数
      this.currentpage = 1; //当前页码改为1
      this.pagesize = pagesize;
      //调用查询方法
      this.queryCourts();
    },
    chageCurrent(current) {
      //改变页码时事件，接收一个被改变后的页码
      this.currentpage = current; //当前页码改为1
      //调用查询方法
      this.queryCourts();
    },
    // 修改组件
    showupdcourts(pssvillage) {
      this.$axios
        .post("/api/courts/isUpd")
        .then((result) => {
          if (result.data.code === 200) {
            this.$refs.courtsUpd.pssvillage = pssvillage;
            this.$refs.courtsUpd.dialogUpd = true;
            this.getCourtsId(pssvillage.pssCourts.cname);
            this.getCircuitId(pssvillage.pssCircuit.circuit);
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
            message: "网络环境差。。。",
            type: "error",
          });
          console.log(e);
        });
    },
    showAddCourts() {
      this.$axios
        .post("/api/courts/isAdd")
        .then((result) => {
          if (result.data.code === 200) {
            // 通过子组件的引用传递,获得子组件中的变量
            this.$refs.courtsAdd.dialogAdd = true;
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
            message: "网络环境差。。。",
            type: "error",
          });
          console.log(e);
        });
    },
    // 删除台区,路线,及行政村
    btndelcourts(village) {
      this.$confirm("此操作将永久删除该台区业务, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axios
            .post("/api/courts/del", village)
            .then((result) => {
              if (result.data.code === 200) {
                this.$message({
                  type: "success",
                  message: "删除成功!",
                });
              } else {
                this.$message({
                  type: "info",
                  message: result.data.msg,
                });
              }
              this.queryCourts();
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
    },
    // 删除行政村
    btndelvillage(id) {
      if (confirm("确定删除台区行政村吗?")) {
        this.$axios
          .get("/api/village/del", { params: { id } })
          .then((result) => {
            if (result.data.code === 200) {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
            } else {
              this.$message({
                type: "info",
                message: result.data.msg,
              });
            }
            this.queryCourts();
          })
          .catch((e) => {
            this.$message({
              type: "error",
              message: "网络环境差。。。",
            });
          });
      }
    },
    // 删除台区路线
    btndelcircuit(village) {
      if (confirm("确定删除台区路线吗?")) {
        this.$axios
          .post("/api/circuit/del", village)
          .then((result) => {
            if (result.data.code === 200) {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
            } else {
              this.$message({
                type: "info",
                message: result.data.msg,
              });
            }
            this.queryCourts();
          })
          .catch((e) => {
            this.$message({
              type: "error",
              message: "网络环境差。。。",
            });
          });
      }
    },
    //根据台区的名称查询台区的id
    getCourtsId(cname) {
      this.$axios
        .get("/api/courts/getbyId", { params: { cname } })
        .then((result) => {
          localStorage.setItem("courtsid", result.data.data);
          console.log("台区的id为" + result.data.data);
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
    // 根据台区路线的名称查询台区路线的id
    getCircuitId(circuit) {
      this.$axios
        .get("/api/circuit/byId", {
          params: { circuit, courtsid: localStorage.getItem("courtsid") },
        })
        .then((result) => {
          localStorage.setItem("circuitid", result.data.data);
          console.log("台区路线的id为" + result.data.data);
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
  computed: {},
  components: { CourtsAddView, CourtsUpdView },
  created() {
    this.account = localStorage.getItem("username");
    // 调用分页多条件的方法
    this.queryCourts();
  },
};
</script>
<style scoped>
#business-main {
  width: 100%;
  height: 100%;
}
#form-main {
  padding-top: 25px;
}
#add-main {
  margin-left: 15px;
}
.el-row {
  text-align: center;
}
/* .el-table-column{
  align-content: center;
} */
</style>