<template>
    <div class="edit-user-page">
        <el-form
                ref="form"
                :model="form"
                label-width="80px"
                label-position="left"
                style="max-width: 300px"
        >
            <el-form-item label="姓名" prop="name">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="电话">
                <el-input v-model="form.phone"></el-input>
            </el-form-item>
            <el-form-item label="房间号" prop="roomNum">
                <el-select v-model="form.roomId" placeholder="请选择房间号">
                    <el-option :label="item.roomNum" :value="item.id" v-for="item of roomList"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="edit">确认修改</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                form: {},
                roomList:[]
            }
        },
        created() {
            this.getRoomList()
            this.getUser()
        },
        methods: {
            getRoomList() {
                this.$apis.RoomApi.select().then(res => {
                    console.log(res)
                    this.roomList = res.data
                })
            },
            getUser() {
                this.$apis.UserApi.selectById({id: this.$route.query.id}).then(res => {
                    this.form = res.data
                })
            },
            edit() {
                this.$apis.UserApi.update(this.form).then(res => {
                    this.$message({
                        type: "success",
                        message: "操作成功"
                    })
                    this.$router.push("/user/select")
                })
            }
        }
    }
</script>
<style lang="less" scoped></style>