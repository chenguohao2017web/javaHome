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
        >
            <el-table-column
                    label="年月">
                <template slot-scope="scope">
                    <div>{{scope.row.year + "年-" + scope.row.month}}月</div>
                </template>
            </el-table-column>
            <el-table-column
                    prop="room.roomNum"
                    label="房间号">
            </el-table-column>
            <el-table-column
                    prop="water"
                    label="水">
            </el-table-column>
            <el-table-column
                    prop="dian"
                    label="电">
            </el-table-column>
            <el-table-column
                    prop="price"
                    label="计价">
            </el-table-column>

            <el-table-column
                    label="操作">
                <template slot-scope="scope">
                    <el-button @click="editUser(scope.row)" type="text" size="small">编辑</el-button>
                    <el-button type="text" size="small" @click="delUser(scope.row)">删除</el-button>
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
            })
        },
        methods: {
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
                })
            },
            handlePageChange(page) {
                this.pagee = page
                this.selectCount()
            }
        }
    }
</script>
<style lang="less" scoped></style>