<template>
    <div class="login-page">
        <el-form label-position="right" label-width="80px" :model="form">
            <el-form-item label="username">
                <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="password">
                <el-input type="password" v-model="form.password"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                form: {}
            }
        },
        methods: {
            onSubmit() {
                this.$apis.LoginApi.login(this.form).then(res => {
                    if(res.code === 200) {
                        // this.$store.commit("setToken", res.data)
                        localStorage.setItem("token", res.data)
                        this.$router.push("/home")
                    } else {

                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                    }
                })
            }
        }
    }
</script>