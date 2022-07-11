<!-- 新型业务首页模块 -->
<template>
  <div id="business-main">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>新型业务管理</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/business' }"
        >新型业务列表</el-breadcrumb-item
      >
    </el-breadcrumb>
    <!-- 新型业务表单查询开始 -->
    <div id="form-main">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="发表人姓名:">
          <el-input
            v-model="username"
            placeholder="请输入发表人姓名:"
          ></el-input>
        </el-form-item>
        <el-form-item label="业务标题:">
          <el-input v-model="btitle" placeholder="请输入业务标题"></el-input>
        </el-form-item>
        <el-form-item label="业务类型:">
          <el-select placeholder="请选择业务类型" v-model="busname">
            <el-option
              v-for="business in busItem"
              :key="business.id"
              :label="business.busname"
              :value="business.busname"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item id="search-main">
          <el-button type="button" icon="el-icon-search" @click="queryBmessage"
            >查询</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-circle-plus"
            id="add-main"
            @click="showAddBmessage"
            >添加</el-button
          >
        </el-form-item>
      </el-form>
    </div>
    <!-- 新型业务表单查询结束 -->

    <!-- 新型业务表单开始 -->
    <div id="form-data">
      <el-table
        :data="bmessageItem.data"
        style="width: 100%"
        fit="fit" max-height="470"
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
          prop="btitle"
          label="业务标题"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="creationtime"
          label="创建时间"
          width="200"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="pssUsers.username"
          label="发表人"
          width="200"
        ></el-table-column>
        <el-table-column
          align="center"
          prop="pssBusiness.busname"
          label="业务类型"
        ></el-table-column>
        <el-table-column align="center" fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button
              type="danger"
              icon="el-icon-delete"
              title="删除业务" size="small"
              @click="btndelbmessage(scope.row.id)"
            ></el-button>
            <el-button
              type="warning"
              icon="el-icon-edit"
              title="查看详情" size="small"
              @click="showupdbmessage(scope.row)"
            ></el-button>
            <el-button
              type="primary"
              icon="el-icon-download"
              title="下载业务" size="small"
              @click="showdownloadmessage(scope.row.filecontent)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 新型业务表单结束 -->

    <!-- 分页开始 -->
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
    <!-- 分页结束 -->
    <business-add-view ref="businessAdd"></business-add-view>
    <business-upd-view ref="businessUpd"></business-upd-view>
  </div>
</template>

<script>
import BusinessAddView from "@/components/business/BusinessAddView.vue";
import BusinessUpdView from "@/components/business/BusinessUpdView.vue";
export default {
  name: "BusinessIndex",
  data() {
    return {
      busItem: [], //新型业务类型
      bmessageItem: [], // 新型业务列表集合
      username: "", //查询条件1
      btitle: "", //查询条件2
      busname: "", //查询条件3
      total: 0, //分页控件总条数
      pagecount: 0, //分页控件总页数
      currentpage: 1, //分页控件当前页码
      pagesize: 5, //分页控件每页条数
      account: "",
    };
  },
  methods: {
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
    indexMethod(index) {
      //表格自定义序号
      return index + 1;
    },
    queryBmessage() {
      this.$axios
        .get("/api/bmessage/list", {
          params: {
            username: this.username,
            btitle: this.btitle,
            busname: this.busname,
            pageNo: this.currentpage,
            pageSize: this.pagesize,
            account: this.account,
          },
        })
        .then((result) => {
          console.log(result.data);
          if (result.data.code === 200) {
            this.bmessageItem = result.data.data;
            //设置分页数据
            this.total = result.data.data.totalNum;
            this.pagecount = result.data.data.totalPage;
            this.currentpage = result.data.data.pageNo;
            this.pagesize = result.data.data.pageSize;
            localStorage.setItem("userid", result.data.msg);
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
      this.queryBmessage();
    },
    chageCurrent(current) {
      //改变页码时事件，接收一个被改变后的页码
      this.currentpage = current; //当前页码改为1
      //调用查询方法
      this.queryBmessage();
    },
    // 修改组件
    showupdbmessage(bmessage) {
      this.$axios
        .post("/api/bmessage/isUpd")
        .then((result) => {
          if (result.data.code === 200) {
            this.$refs.businessUpd.bmessage = bmessage;
            this.$refs.businessUpd.dialogUpd = true;
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
    showAddBmessage() {
      this.$axios
        .post("/api/bmessage/isAdd")
        .then((result) => {
          if (result.data.code === 200) {
            // 通过子组件的引用传递,获得子组件中的变量
            this.$refs.businessAdd.dialogAdd = true;
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
    btndelbmessage(id) {
      this.$confirm("此操作将永久删除该新型业务类型, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axios
            .get("/api/bmessage/del", { params: { id } })
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
              this.queryBmessage();
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
    // 下载
    showdownloadmessage(filecontent){
      window.location.href = filecontent;
    }
  },
  computed: {},
  components: { BusinessAddView, BusinessUpdView },
  created() {
    // 调用加载新型业务类型的方法
    this.loadBus();
    this.account = localStorage.getItem("username");
    // 调用分页多条件的方法
    this.queryBmessage();
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