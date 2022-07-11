import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import './plugins/element.js'

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

//每次都要添加 token
axios.interceptors.request.use(
  function(config) {
      //Authorization为发送到服务器请求头的键
    config.headers.rememberMe = localStorage.getItem('rememberMe');
    return config
  },
  function(error) {
    // Do something with request error
    return Promise.reject(error)
  }
)