<template>
    <div class="edit-user-page">
        <el-form
                ref="form"
                :model="form"
                label-width="80px"
                label-position="left"
                style="max-width: 300px"
        >
            <el-form-item label="房间号" prop="roomNum">
                <el-input v-model="form.roomNum"></el-input>
            </el-form-item>
            <el-form-item label="价格">
                <el-input v-model="form.price"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="default" @click="back">返回</el-button>
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
            this.getRoom()
        },
        methods: {
            getRoom() {
                this.$apis.RoomApi.selectById({id: this.$route.query.id}).then(res => {
                    this.form = res.data
                })
            },
            edit() {
                this.$apis.RoomApi.update(this.form).then(res => {
                    this.$message({
                        type: "success",
                        message: "操作成功"
                    })
                    this.$router.push("/room/select")
                })
            },
            back() {
                this.$router.back()
            }
        }
    }
</script>
<style lang="less" scoped></style>