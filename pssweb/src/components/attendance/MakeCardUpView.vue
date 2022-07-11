<!--考勤和打卡界面-->
<template>
  <div id="main">
    <div>
      <el-date-picker v-model="value1" type="date" placeholder="选择日期">
      </el-date-picker>
    </div>
    <div id="updiv">

    </div>
    <div id="downddiv">

    </div>
    <div>
      <el-button icon="el-icon-date" size="large" @click="btn" id="upbtn">上班打卡</el-button>
      <el-button icon="el-icon-date" size="large" @click="resetQuery" id="downbtn">下班打卡</el-button>
      <el-button icon="el-icon-date" size="large" @click="resetQuery">补卡</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "",
  data() {
    return {
      value1: new Date(),
    };
  },
  methods: {

  },
  components: {},
  computed: {},
  mounted: {},
};
</script>
<style scoped>
</style>