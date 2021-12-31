<template>
  <div id="app">
    <el-container>
      <el-header>
        <div class="my-hearder" style="height: 100%;background:#3a8ee6;color:#fff;font-size:20px;font-weight: bold;display: flex;">
          <el-button style="width: 200px;" type="primary" icon="el-icon-menu" @click="opco" >展开/收起</el-button>
        </div>
      </el-header>
      <el-container>
        <el-aside :width="currentAsideWidth">
          <el-menu
                  :default-active="activeIndex"
                  class="el-menu-vertical-demo"
                  :collapse="isCollapse"
                  @select="select"
                  :unique-opened="true"
                  :router="true"

          >
            <el-menu-item index="/login">
              <span slot="title">登录</span>
            </el-menu-item>
            <el-menu-item index="/home">
              <span slot="title">首页</span>
            </el-menu-item>
            <el-submenu index="/user">
              <template slot="title">
                <span slot="title">用户管理</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/user/select">查询用户</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="/room">
              <template slot="title">
                <span slot="title">房间管理</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/room/select">查询房间</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="/count">
              <template slot="title">
                <span slot="title">报价管理</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/count/select">查询所有</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group>
                <el-menu-item index="/count/view">视图速看</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group>
                <el-menu-item index="/count/insert">数据录入</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
              <router-view/>
        </el-main>
      </el-container>
    </el-container>


  </div>
</template>
<script>
  export default {
    data() {
      return {
        isCollapse: false,
        currentAsideWidth: "200px"
      }
    },
    computed: {
      activeIndex() {
        const {name} = this.$route
        switch (name) {
          case 'login':
            return '/login';
          case 'home':
            return '/home';
          case 'userSelect':
            return '/user/select'
          case 'userEdit':
            return '/user/edit';
          case 'roomSelect':
            return '/room/select';
          case 'roomEdit':
            return '/room/edit';
          case 'countSelect':
            return '/count/select';
          case 'countInsert':
            return '/count/insert';
          case 'countView':
            return '/count/view'
        }
      }
    },
    methods: {
      opco() {
        this.isCollapse = !this.isCollapse
        if(this.isCollapse) {
          this.currentAsideWidth = "0px"
        } else{
          this.currentAsideWidth = "200px"
        }
      },
      select(index) {
        this.$router.push(index)
      }
    }
  }
</script>
<style lang="less">
  .el-header {
    padding: 0!important;
  }
  .el-container {
    height: 100%;
  }
  html,body {
    height: 100%;
  }
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100%;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
