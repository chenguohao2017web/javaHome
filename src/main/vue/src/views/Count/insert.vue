<template>
    <div>
        <div class="roomLIst">
            <div :class="['roomItem',{'active': form.roomId == item.id}]" v-for="item of roomList" @click="hc(item.id)">{{item.roomNum}}</div>
        </div>
    <div class="count-inert-page" style="display: flex; ">
        <el-form ref="form" :rules="rules" :model="form" label-width="80px" style="max-width: 300px">
            <el-form-item label="选择年月">
                <el-date-picker
                        v-model="date"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="选择年月" @change="chooseRoom">
                </el-date-picker>
            </el-form-item>
<!--            <el-form-item label="房号" prop="roomId">-->
<!--                <el-select v-model="form.roomId" placeholder="选择房号" @change="chooseRoom">-->
<!--                    <el-option :label="item.roomNum" :value="item.id" v-for="item of roomList"></el-option>-->
<!--                </el-select>-->
<!--            </el-form-item>-->

            <el-form-item label="水" prop="water">
                <el-input v-model="form.water"></el-input>
            </el-form-item>
            <el-form-item label="电" prop="dian">
                <el-input v-model="form.dian"></el-input>
            </el-form-item>

            <el-form-item label="应缴">
                <el-input v-model="form.price" disabled placeholder="？？？">
                    <template slot="append">元</template>
                </el-input>
            </el-form-item>

            <el-form-item>
                <el-button type="success" @click="handlePrice" v-if="this.lastForm.water">计价</el-button>
                <el-button type="primary" @click="onSubmit">保存</el-button>
            </el-form-item>
        </el-form>

        <i v-if="form.roomId && showLastData" type="primary" class="el-icon-d-arrow-left" style="color: dodgerblue; margin: 100px 0 0 20px;"></i>
        <i v-if="form.roomId && showLastData" type="primary" class="el-icon-d-arrow-left" style="color: dodgerblue; margin: 100px 10px 0 0;"></i>
        <el-form v-if="form.roomId && showLastData" :model="lastForm" label-width="80px" style="max-width: 300px" disabled>
            <el-form-item label="选择年月">
                <el-date-picker
                        v-model="lastDate"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="选择年月">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="房号" prop="roomId">
                <el-select v-model="form.roomId">
                    <el-option :label="item.roomNum" :value="item.id" v-for="item of roomList"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="水" prop="water">
                <el-input v-model="lastForm.water"></el-input>
            </el-form-item>
            <el-form-item label="电" prop="dian">
                <el-input v-model="lastForm.dian"></el-input>
            </el-form-item>

            <el-form-item label="应缴">
                <el-input v-model="lastForm.price" disabled placeholder="？？？">
                    <template slot="append">元</template>
                </el-input>
            </el-form-item>
        </el-form>
    </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                showLastData: false,
                date: null,
                form: {
                    water: null,
                    dian: null,
                    price: null
                },
                currentRoomId: null,
                roomList: [],
                rules: {
                    roomId: [
                        { required: true, message: '请选择', trigger: 'blur' }
                    ],
                    water: [
                        { required: true, message: '不能为空', trigger: 'blur' },
                    ],
                    dian: [
                        { required: true, message: '不能为空', trigger: 'blur' },
                    ]
                },
                lastForm: {}
            }
        },
        created() {
            this.getRoomList()
            let id = this.$route.query.id
            if(id) {
                this.getRoomList()
                this.$apis.CountApi.selectById({id}).then(res => {
                    this.form = res.data
                    this.date = res.data.year + "-" + res.data.month
                })
            }else {
                let now = new Date()
                let year = now.getFullYear()
                let month = now.getMonth() + 1
                this.date = year + "-"+ month
            }
        },
        computed: {
            lastDate() {
                let date = this.date
                let year = date.split("-")[0] - 0
                let month = date.split("-")[1] - 0
                if(month === 1) {
                    month = 12
                    year -= 1
                } else {
                    month -=1
                }
                return `${year}-${month}`
            }
        },
        methods: {
            hc(id) {
                this.form.roomId = id
                this.chooseRoom()
            },
            getRoomList() {
                this.$apis.RoomApi.select().then( res => {
                    this.roomList = res.data
                })
            },
            onSubmit() {
                this.$refs['form'].validate(v => {
                    if(v) {
                        let params = {
                            year: this.date.split("-")[0],
                            month: this.date.split("-")[1],
                            roomId: this.form.roomId,
                            price: this.form.price,
                            page: 1,
                            size: 999
                        }
                        this.$apis.CountApi.select(params).then( res => {
                            if(res.data.list && res.data.list.length > 0) {
                                this.$confirm("该月该房间已录入数据，是否覆盖？").then(() => {
                                    this.$apis.CountApi.update({
                                        id: res.data.list[0].id,
                                        year: this.date.split("-")[0],
                                        month: this.date.split("-")[1],
                                        water: this.form.water,
                                        dian: this.form.dian,
                                        price: this.form.price,
                                        roomId: this.form.roomId,
                                        userId: res.data.list[0].userId
                                    }).then(() => {
                                        this.$message({
                                            type: "success",
                                            message: "操作成功"
                                        })
                                        this.form = {
                                            water: null,
                                            dian: null,
                                            price: null,
                                            roomId: this.form.roomId
                                        }
                                    })
                                })
                            } else {
                                let userId = null
                                this.$apis.UserApi.selectByRoomId({id: this.form.roomId}).then(res => {
                                    if(!res.data) {
                                        this.$alert("该房间还没有用户数据!")
                                    } else {
                                        userId = res.data.id
                                        this.$apis.CountApi.add({
                                            year: this.date.split("-")[0],
                                            month: this.date.split("-")[1],
                                            water: this.form.water,
                                            dian: this.form.dian,
                                            price: this.form.price,
                                            roomId: this.form.roomId,
                                            userId: userId
                                        }).then( res => {
                                            this.$message({
                                                type: "success",
                                                message: "操作成功"
                                            })
                                            this.form = {
                                                water: null,
                                                dian: null,
                                                price: null,
                                                roomId: this.form.roomId
                                            }
                                        })
                                    }

                                })

                            }
                        })
                    }
                })
            },
            handlePrice() {
                // 计算价格
                this.$refs['form'].validate(v => {
                    if(v) {
                        this.$apis.RoomApi.selectById({id: this.form.roomId}).then(res => {
                            let price = res.data.price
                            let result = 0
                            let water = this.lastForm.water
                            let dian = this.lastForm.dian
                            let diffDian = (this.form.dian - dian) * 1.5
                            let diffWater = (this.form.water - water) * 4
                            if(diffDian + diffWater === 0) {
                                result = price
                            } else {
                                result = price + diffDian + diffWater + 11
                            }
                            this.$set(this.form, "price", result)
                        })
                    }
                })
            },
            /**
             * 每当房间号变更时查询是否存在上个月数据
             */
            chooseRoom() {
                let params = {
                    year: this.lastDate.split("-")[0],
                    month: this.lastDate.split("-")[1],
                    roomId: this.form.roomId,
                    page: 1,
                    size: 999
                }
                this.$apis.CountApi.select(params).then(res => {
                    if(res.data.list && res.data.list.length > 0) {
                        this.showLastData = true
                        let result = res.data.list[0]
                        this.lastForm = {
                            dian: result.dian,
                            water: result.water,
                            roomId: result.roomId,
                            price: result.price
                        }
                    } else {
                        this.showLastData = false
                        this.lastForm = {}
                    }
                })

                //回填当月数据
                if(!this.form.roomId) return
                let currentParams = {
                    year: this.date.split("-")[0],
                    month: this.date.split("-")[1],
                    roomId: this.form.roomId,
                    price: this.form.price,
                    page: 1,
                    size: 999
                }
                this.$apis.CountApi.select(currentParams).then(res => {
                    if(res.data.list && res.data.list.length > 0) {
                        let item = res.data.list[0]
                        this.form.dian = item.dian
                        this.form.water = item .water
                        this.form.price = item.price
                    } else {
                        this.form.water = null
                        this.form.dian = null
                        this.form.price = null
                    }
                })
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
<style lang="less" scoped>
    .roomLIst {
        display: flex;
        flex-wrap: wrap;
        margin-bottom: 20px;
        .roomItem {
            padding: 5px 10px;
            font-size:12px;
            cursor: pointer;
            border-radius: 3px;
            &.active {
                background: #66b1ff;
                color: #fff;
            }
        }
    }
</style>
