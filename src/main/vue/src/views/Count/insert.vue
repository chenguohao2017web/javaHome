<template>
    <div class="count-inert-page">
        <el-form ref="form" :model="form" label-width="80px" style="max-width: 300px">
            <el-form-item label="选择年月">
                <el-date-picker
                        v-model="date"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="选择年月">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="房号">
                <el-select v-model="form.roomId" placeholder="选择房号">
                    <el-option :label="item.roomNum" :value="item.id" v-for="item of roomList"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="水">
                <el-input v-model="form.water"></el-input>
            </el-form-item>
            <el-form-item label="电">
                <el-input v-model="form.dian"></el-input>
            </el-form-item>

            <el-form-item label="应缴">
                <el-input v-model="form.price" disabled placeholder="？？？">
                    <tempalate slot="append">元</tempalate>
                </el-input>
            </el-form-item>

            <el-form-item>
                <el-button type="success" @click="handlePrice">计价</el-button>
                <el-button type="primary" @click="onSubmit">创建</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                date: new Date(),
                form: {},
                roomList: []
            }
        },
        created() {
            this.getRoomList()
        },
        methods: {
            getRoomList() {
                this.$apis.RoomApi.select().then( res => {
                    this.roomList = res.data
                })
            },
            onSubmit() {
                console.log('submit!');
            },
            handlePrice() {
                console.log("test")
            }
        }
    }
</script>
<style>
    .count-inert-page .el-input-group__append {
        background: #00bcd4;
        color: #fff;
        cursor: pointer;
    }
</style>
<style lang="less" scoped></style>