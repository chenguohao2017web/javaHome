<template>
    <div class="count-page-select">
        <el-form :inline="true" :rules="rules" ref="form" :model="formInline" class="demo-form-inline">
            <el-form-item label="选择年月" prop="date">
                <el-date-picker
                        v-model="formInline.date"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="选择年月">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="房间号">
<!--                <el-select v-model="formInline.roomNum" placeholder="选择房间号">-->
<!--                    <el-option :label="item.roomNum" :value="item.roomNum" v-for="item of roomList"></el-option>-->
<!--                </el-select>-->
                <el-input v-model="formInline.roomNum">

                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
        </el-form>

        <el-table
                :data="tableData"
                style="width: 100%"
                :row-class-name="tableRowClassName"
        >
            <el-table-column
                    label="年月">
                <template slot-scope="scope">
                    <div>{{scope.row.year + "-" + scope.row.month}}</div>
                </template>
            </el-table-column>
            <el-table-column
                    prop="roomNumCn"
                    label="房间号">
            </el-table-column>
            <el-table-column
                    prop="water"
                    label="水(吨)">
            </el-table-column>
            <el-table-column
                    prop="dian"
                    label="电(度)">
            </el-table-column>
            <el-table-column
                    prop="price"
                    label="计价(元)">
            </el-table-column>
            <el-table-column
                    label="交租状态">
                <template slot-scope="scope">
                    <span>{{scope.row.status === 0 ? '否' : '是'}}</span>
                </template>
            </el-table-column>


            <el-table-column
                    width="250px"
                    label="操作">
                <template slot-scope="scope">
                    <el-button v-if="scope.row.status === 0" @click="changeStatus(scope.row)" type="success" size="mini">交租</el-button>
                    <el-button @click="editCount(scope.row)" type="default" size="mini">编辑</el-button>
                    <el-button @click="delCount(scope.row)" type="danger" size="mini" >删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination
                style="margin-top:10px"
                background
                layout="prev, pager, next"
                @current-change="handlePageChange"
                :total="total">
        </el-pagination>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                page:1,
                size: 12,
                roomList: [],
                formInline: {
                    user: '',
                    region: '',
                    date: ''
                },
                rules: {
                    date: [
                        // {required: true, message: '请输入', trigger: 'blur'},
                    ]
                },
                tableData:[],
                total: 0
            }
        },
        created() {
            this.$apis.RoomApi.select().then(res=> {
                this.roomList = res.data

                this.onSubmit()
            })


        },
        methods: {
            tableRowClassName({row}) {
                if (row.status === 1) {
                    return 'success-row';
                }
                return '';
            },
            onSubmit() {
                this.$refs['form'].validate( v => {
                    if(v) {
                        let date = this.formInline.date
                        let year = date.split("-")[0]
                        let month = date.split("-")[1]
                        this.year = year
                        this.month = month
                        this.selectCount()
                    }
                })
            },
            selectCount() {
                let page = this.page
                let size = this.size
                let year = this.year
                let month = this.month
                let roomNum = this.formInline.roomNum
                this.$apis.CountApi.select({year, month, roomNum, page, size}).then(res => {
                    this.tableData = res.data.list
                    this.total = res.data.total
                    this.tableData.forEach(item => {
                        let roomNum = item.room.roomNum
                        let sp = roomNum.split("-")[0]
                        if(sp === "N") {
                            item.roomNumCn = "南街-" + roomNum.split("-")[1]
                        } else {
                            item.roomNumCn = "中街-" + roomNum.split("-")[1]
                        }

                    })
                })
            },
            handlePageChange(page) {
                this.pagee = page
                this.selectCount()
            },
            delCount(row) {
                this.$confirm("确认删除？").then(() => {
                    this.$apis.CountApi.del({id: row.id}).then(() => {
                        this.$message({
                            type: "success",
                            message: "操作成功"
                        })

                        this.selectCount()
                    })
                })
            },
            editCount(row) {
                this.$router.push({
                    path: "/count/insert",
                    query: {
                        id: row.id
                    }
                })
            },
            changeStatus(row) {
                this.$confirm("确认收款：" + row.price + "?").then(() => {
                    this.$apis.CountApi.update({
                        id: row.id,
                        status: 1
                    }).then(res => {
                        this.$message({
                            type: "success",
                            message: "操作成功"
                        })
                        this.selectCount()
                    })
                })
            }
        }
    }
</script>
<style>
    .el-table .success-row {
        background: #ffeb3b;
    }
</style>