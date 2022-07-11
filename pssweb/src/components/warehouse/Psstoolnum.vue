<!-- 出入库详情 -->
<template>
  <div id="psstoolnum-list">
    <!-- 查询之后显示的表格,和初始显示表格开始 -->
    <el-table :data="inoutdetail" height="500" border style="width: 100%">
      <el-table-column type="index" label="序号" width="80"></el-table-column>
      <el-table-column prop="orderno" label="工单申请编号" width="200">
      </el-table-column>
      <el-table-column prop="typename" label="工单类型" width="120">
      </el-table-column>
      <el-table-column prop="username" label="领用人" width="120">
      </el-table-column>
      <el-table-column prop="borrowtime" label="领用时间" width="240">
      </el-table-column>
      <el-table-column prop="returntime" label="归还时间" width="240">
      </el-table-column>
      <el-table-column prop="dataname" label="物品名称" width="120">
      </el-table-column>
      <el-table-column prop="nums" label="物品数量" width="120">
      </el-table-column>
      <el-table-column prop="standard" label="物品规格" width="120">
      </el-table-column>
      <el-table-column prop="inouttype" label="类型" width="120">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.inouttype === 1 ? 'primary' : 'success'"
            disable-transitions
            ><span v-if="scope.row.inouttype === 1">入库</span
            ><span v-if="scope.row.inouttype === 2">出库</span></el-tag
          >
        </template>
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
  name: "ExamIndexView",
  data() {
    return {
      dataname: "",
      nums: "",
      borrowtime: "",
      returntime: "",
      orderno: "",
      typename: "",
      username: "",
      standard: "",
      inouttype: "",
      inoutdetail: [],
    };
  },
  created() {
    this.onSubmit(this.$route.params.orderno);
  },
  methods: {
    onSubmit(orderno) {
      axios
        .get("/api/inout/detail", {
          params: {
            orderno,
          },
        })
        .then((result) => {
          if (result.data.code === 200) {
            this.inoutdetail = result.data.data;
            console.log(2);
            console.log(result.data.data);
            console.log(2);
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

    updateItem(whinfor) {
      axios
        .get("/api/test/inoutupdate")
        .then((result) => {
          if (result.data.code === 200) {
            this.$router.push({ name: "inoutupdate", params: { whinfor } });
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
<style scoped>
#psstoolnum-list {
  width: 100%;
  height: 100%;
}
</style>
