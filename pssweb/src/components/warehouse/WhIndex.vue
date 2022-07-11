<!-- 首页 -->
<template>
  <div id="home-main">
    <el-container>
      <el-header>
        <el-row type="flex" justify="center" align="middle">
          <el-col :span="5"></el-col>
          <el-col :span="3"> </el-col>
        </el-row>
      </el-header>
      <el-container>
        <!-- 讲师表单开始 -->
        <!-- <div id="form-data">
          <el-table
            :data="trainItem"
            style="width: 100%"
            fit="fit"
            stripe="stripe"
          >
            <el-table-column
              align="center"
              prop="sname"
              label="学生姓名"
            ></el-table-column>
            <el-table-column
              align="center"
              prop="title"
              label="论文题目"
            ></el-table-column>
            <el-table-column
              align="center"
              prop="tid"
              label="指导老师"
            ></el-table-column>
            <el-table-column align="center" fixed="right" label="操作">
              <template slot-scope="scope">
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  title="提交"
                  @click="btnsub()"
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </div> -->
        <!-- 讲师表单结束 -->

        <!-- <el-aside width="100%"> -->
          <!-- 讲师表单开始 -->
          <!-- <div id="form-data">
            <el-table
              :data="studentList"
              style="width: 100%"
              fit="fit"
              stripe="stripe"
            >
              <el-table-column
                align="center"
                type="index"
                :index="indexMethod"
                label="ID"
                width="50"
              ></el-table-column>
              <el-table-column
                align="center"
                prop="student.sname"
                label="学生姓名"
              ></el-table-column>
              <el-table-column
                align="center"
                prop="student.title"
                label="论文题目"
              ></el-table-column>
              <el-table-column
                align="center"
                prop="student.tid"
                label="指导老师"
              ></el-table-column>
              <el-table-column align="center" fixed="right" label="操作">
                <template slot-scope="scope">
                  <el-button
                    type="danger"
                    icon="el-icon-delete"
                    title="删除"
                    @click="btndel(scope.row.id)"
                  ></el-button>
                </template>
              </el-table-column>
            </el-table>
          </div> -->
          <!-- 讲师表单结束 -->

          <!-- 左侧导航开始 -->
          <el-menu
            default-active="1"
            class="el-menu-vertical-demo"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            router="router"
          >
            <el-submenu
              :index="menu.id + ''"
              v-for="menu in menuItem"
              :key="menu.id + ''"
            >
              <template slot="title">
                <i :class="menu.icon"></i>
                <span>{{ menu.name }}</span>
              </template>
              <el-menu-item-group>
                <el-menu-item
                  v-for="ch in menu.childerList"
                  :index="ch.link"
                  :key="ch.id + ''"
                  v-text="ch.name"
                ></el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
          <!-- 左侧导航结束 -->
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "WhIndex",
  data() {
    return {
      // username: "",
      studentList: [],
      // teacherList: [], //接收用户的菜单数据
      student: {
        sname: "",
        title: "",
        tid: "",
      },
    };
  },
  methods: {
    // //退出按钮
    // btnexit() {
    //   this.$confirm("确定退出本系统吗?", "系统提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning",
    //   }).then(() => {
    //     this.$axios
    //       .post("/api/logout")
    //       .then((result) => {
    //         if (result.data.code === 200) {
    //           localStorage.removeItem("rememberMe");
    //           localStorage.removeItem("username");
    //           this.$router.push({ name: "login" });
    //         }
    //       })
    //       .catch((e) => {
    //         this.$message({
    //           showClose: true,
    //           message: "网络环境差。。。",
    //           type: "error",
    //         });
    //         console.log(e);
    //       });
    //   });
    // },
    loadMunu() {
      //加载用户菜单的方法
      //获得浏览保存的token
      this.$axios
        .get("/api/warehouse/list", { params: {} })
        .then((result) => {
          if (result.data.code === 200) {
            this.studentList = result.data.data;
            console.log(this.studentList);
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
   
  },
  components: {},
  computed: {},
  created() {
    // this.username = localStorage.getItem("username");
    // //调用加载菜单的函数
    this.loadMunu();
  },
};
</script>
<style scoped>
#home-main {
  width: 100%;
  height: 100%;
}
.el-header {
  background-color: #515c64;
}
.el-container {
  height: 100%;
}
.el-aside {
  background-color: #515c64;
  height: 100%;
}
.el-col {
  height: 60px;
  color: white;
  text-align: center;
  line-height: 60px;
}
</style>
