<template>
    <div class="count-view">
        <el-form :inline="true" ref="form" :model="form" class="demo-form-inline">
            <el-form-item label="选择年月" >
                <el-date-picker
                        v-model="form.date"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="选择年月">
                </el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
        </el-form>

        <div class="status-list">状态说明：
            <div class="list-item">
                <div class="icon succ"></div>
                已缴
            </div>
            <div class="list-item pri">
                <div class="icon pri"></div>
                未缴
            </div>
            <div class="list-item">
                <div class="icon"></div>
                空房
            </div>
        </div>

        <div class="container">
            <div :class="['list-item',{hasUser: item.hasUser}, {status: item.status}]" v-for="item of dataList">{{item.room.roomNum}}</div>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                form:{},
                dataList: []
            }
        },

        methods: {
            onSubmit() {
                if(!this.form.date) {
                    this.$message.error("请填写查询日期")
                    return
                }
                this.dataList = []
                this.getRoomList()
            },
            getRoomList() {
                this.$apis.RoomApi.select().then(res=> {
                    const roomList = res.data
                    roomList.forEach( room => {
                        this.dataList.push({
                            room,
                            hasUser: false, //是否住人
                            status: false //当月缴纳情况
                        })
                    })

                    //查询住人情况
                    this.$apis.UserApi.select({page:1, size: 9999}).then(res => {
                        let userList = res.data.list
                        userList.forEach(user => {
                            this.dataList.forEach(dateItem => {
                                if(user.roomId === dateItem.room.id) {
                                    dateItem.hasUser = true
                                }
                            })
                        })
                    })

                    //查询缴纳情况
                    let year = this.form.date.split("-")[0]
                    let month = this.form.date.split("-")[1]
                    this.$apis.CountApi.select({year, month, page: 1, size: 9999}).then(res => {
                        let countList = res.data.list
                        countList.forEach(count => {
                            this.dataList.forEach(dataItem => {
                                if(count.roomId === dataItem.room.id && count.status === 1) {
                                    dataItem.status = true
                                }
                            })
                        })
                    })

                })
            }
        }
    }
</script>
<style lang="less" scoped>
    .container {
        .list-item {
            display: inline-block;
            width: 50px;
            height: 50px;
            line-height: 50px;
            text-align: center;
            border: 1px solid #ccc;
            margin: 2px;

            &.hasUser {
                background: #f44336;
                color: #fff;
            }
            &.status {
                background: #3a8ee6;
                color: #fff;
            }
        }
    }

    .status-list {
        display: flex;
        justify-content: center;
        align-items: center;
        font-size:12px;
        margin-bottom: 20px;
        .list-item {
            display: flex;
            align-items: center;
            margin-right: 20px;
            .icon {
                width: 20px;
                height: 20px;
                background: #fff;
                border:1px solid #ccc;
                &.succ {
                    background: #3a8ee6;
                }
                &.pri {
                    background: #f44336;
                }
            }
        }
    }
</style>