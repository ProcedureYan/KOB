<template>
  <NavBar />
  <router-view></router-view>
</template>

<script>
import NavBar from './components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"

export default {
  components: {
    NavBar
  },
  setup() {


    // $.ajax({
    //   url: "https://app3216.acapp.acwing.com.cn/api/user/account/register/",
    //   type: "post",
    //   data: {
    //     username: "yxc2",
    //     password: "123",
    //     confirmedPassword: "123",
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   }
    // })
  }
}
</script>

<style>
body {
  background-image: url("@/assets/images/background.png");
  background-size: cover;
}
</style>
