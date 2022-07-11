const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 81, //配置web服务器的访问端口
    proxy: {
      "/api": {  // 1  客户端发送的哪些请求需要被代理服务器进行替换
        target: "http://127.0.0.1:8081", // 2  服务器的访问地址
        changeOrigin: true, // 3
        pathRewrite: {
          "^/api": "/", // 4  客户端发送请求被代理的规则  http://127.0.0.1:8080//user/list
        },
      },
    },
  },
});
