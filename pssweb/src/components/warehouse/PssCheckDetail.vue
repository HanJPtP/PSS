<!-- 出入库详情 -->
<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/checklist' }"
        >校验库管理</el-breadcrumb-item
      >
      <el-breadcrumb-item>校验详情</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 查询之后显示的表格,和初始显示表格开始 -->
    <el-table :data="inoutdetail" height="500" border style="width: 100%">
      <el-table-column type="index" label="序号" width="80"></el-table-column>
      <el-table-column prop="checktime" label="校验时间" width="120">
      </el-table-column>
      <el-table-column prop="checkdata" label="校验信息" width="120">
      </el-table-column>
      <el-table-column prop="checkstatus" label="校验状态" width="120">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.inouttype === 1 ? 'primary' : 'success'"
            disable-transitions
            ><span v-if="scope.row.checkstatus === 1">已校验</span>
            <span v-if="scope.row.checkstatus === 2">
              <el-tag type="warning" effect="dark">未校验</el-tag></span
            ></el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="orderno" label="工单编号" width="120">
      </el-table-column>
      <el-table-column prop="dataname" label="物品名称" width="120">
      </el-table-column>
      <el-table-column prop="standard" label="物品规格" width="120">
      </el-table-column>
      <el-table-column prop="nums" label="物品数量" width="120">
      </el-table-column>
      <el-table-column prop="borrowtime" label="领取时间" width="120">
      </el-table-column>
      <el-table-column prop="username" label="领取人" width="120">
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-button type="text" @click="updateItem(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 查询之后显示的表格,和初始显示表格结束 -->

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
  name: "PssCheckDetail",
  data() {
    return {
      dataname: "",
      nums: "",
      borrowtime: "",
      checktime: "",
      checkdata: "",
      checkstatus: "",
      orderno: "",
      username: "",
      standard: "",
      inoutdetail: [],
    };
  },
  created() {
    this.onSubmit(this.$route.params.orderno);
  },
  methods: {
    onSubmit(orderno) {
      axios
        .get("/api/psscheck/detail", {
          params: {
            orderno,
          },
        })
        .then((result) => {
          if (result.data.code === 200) {
            this.inoutdetail = result.data.data;
            console.log(result.data.data);
          } else {
            this.$message({
              showClose: true,
              message: "网络环境差。。。",
              type: "error",
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

    //添加四库物品开始
    addItem() {
      //测试有没有权限
      //   axios
      //     .get("/api/test/whdataadd")
      //     .then((result) => {
      //       if (result.data.code === 200) {
      //如果成立跳转过去
      this.$router.push({ name: "whdataAdd" });
      //   } else {
      //     //不成立提示消息
      //     this.$message({
      //       showClose: true,
      //       message: result.data.msg,
      //       type: "warning",
      //     });
      //   }
      // })
      // .catch((e) => {
      //   this.$message({
      //     showClose: true,
      //     message: "网络波动异常",
      //     type: "error",
      //   });
      // });
    },
    //添加结束

    updateItem(whinfor) {
      axios
        .get("/api/test/checkupdate")
        .then((result) => {
          if (result.data.code === 200) {
            this.$router.push({ name: "checkupdate", params: { whinfor } });
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
  },
  components: {},
  computed: {},
};
</script>
<style scoped></style>
