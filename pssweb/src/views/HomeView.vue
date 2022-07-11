<template>
   <div id="index-main">
      <el-container>
        <!-- 头部 -->
        <el-header>
          <el-row type="flex" justify="center" align="middle">
            <el-col :span="6"><img height="60px" src="@/assets/logo.png"></el-col>
            <el-col :span="12"><h3 style="color: white;">WONIU供电所后台管理系统</h3></el-col>
            <el-col :span="4"><h3 style="color: white;">欢迎你：{{username}}</h3></el-col>
            <el-col :span="2">
              <el-button type="danger" icon="el-icon-switch-button" @click="btnexit">退出</el-button>
            </el-col>
          </el-row>
        </el-header>

        <el-container>
            <!-- 导航 -->
            <el-aside width="200px">
                <el-menu
                  default-active="2"
                  class="el-menu-vertical-demo"
                  text-color="#000000"
                  active-text-color="#d0d071"
                  router="router">

                <el-submenu :index="menu.id+''" v-for="menu in menuItem" :key="menu.id+''">
                  <template slot="title">
                    <i :class="menu.icon"></i>
                    <span>{{menu.name}}</span>
                  </template>
                  <el-menu-item v-for="ch in menu.childList" :index="ch.link" v-text="ch.name" class="el-icon-s-data"></el-menu-item>
                </el-submenu>
              </el-menu>
            </el-aside>

            <!-- 竖直边框 -->
            <el-divider direction="vertical"></el-divider>

            <!-- 内容信息 -->
            <el-main style="margin-left:10px">  
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>

  </div>
</template>

<script>
export default {
 name:'HomeView',
  data () {
    return {
        username:'',
        menuItem:[]
    };
  },
  methods: {
    
    //菜单导航
    loadMenu(){
      let rememberMe=localStorage.getItem("rememberMe");
      this.$axios.get("/api/menu",{params:{}})
      .then(result=>{

        console.log(result.data);
    
        if(result.data.code===200){
          this.menuItem=result.data.data;
        }

      }).catch(e=>{
        this.$message({
          showClose: true,
          message: '服务器去火星了...',
          type: 'error'
        });
        console.log(e);
      })
    },

    //退出按钮
    btnexit() {
      this.$confirm("确定退出本系统吗?", "系统提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$axios
          .post("/api/logout")
          .then((result) => {
            if (result.data.code === 200) {
              localStorage.removeItem("rememberMe");
              localStorage.removeItem("username");
              this.$router.push({ name: "login" });
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
      });
    },
  },
  components: {},
  computed: {},
  created(){
    this.username=localStorage.getItem("username");
    this.loadMenu();
  }
}

</script>
<style scoped>
#index-main{
  width: 100%;
  height: 100%;
  
}
.el-header{
  height: 60px;
  background-color: #409EFF;
}
.el-col{
  text-align: center; 
}
.el-aside{
  height: 660px;
}
/* 解决菜单下拉毛刺 */
.el-menu {
  border-right: none;
}
.el-menu-item{
  text-align: center;
}
.el-divider{
  width: 8px;
  height: auto;
  color: black;
  margin-left: 0px;
}
</style>