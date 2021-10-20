<template>
    <div class="select-page">
        <el-form
                :inline="true"
                ref="form"
                :model="form"
                :rules="rules"
                label-width="80px"
                label-position="left"
        >
            <el-form-item label="姓名" prop="name">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="电话">
                <el-input v-model="form.phone"></el-input>
            </el-form-item>
            <el-form-item label="房间号" prop="roomNum">
                <el-select v-model="form.roomId" placeholder="请选择房间号" @change="handleRoomChange">
                    <el-option :label="item.roomNum" :value="item.id" v-for="item of roomList"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="success" @click="onSubmit">新增</el-button>
            </el-form-item>
        </el-form>
        <el-table
                :data="tableData"
                style="width: 100%"
                >
            <el-table-column
                    prop="name"
                    label="姓名">
            </el-table-column>
            <el-table-column
                    prop="phone"
                    label="电话">
            </el-table-column>
            <el-table-column
                    prop="roomNum"
                    label="房间号">
            </el-table-column>
            <el-table-column
                    prop="createTime"
                    label="创建时间">
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
        created() {
            this.getData()
            this.getRoomList()
        },
        data() {
            return {
                tableData: [],
                total: 0,
                size: 8,
                page: 1,
                form: {},
                roomList:[],
                rules: {
                    name: [
                        {required: true, message: '请输入', trigger: 'blur'},
                    ],
                    roomId :[
                        {required: true, message: '请输入', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            getData() {
                this.$apis.UserApi.select({page: this.page, size: this.size}).then(res => {
                    this.tableData = res.data.list
                    this.total = res.data.total
                })
            },
            handlePageChange(page) {
                this.page = page
                this.getData()
            },
            editUser(row) {
                this.$router.push({
                    path: "/user/edit",
                    query: {
                        id: row.id
                    }
                })
            },
            async delUser(row) {
                this.$confirm("确认删除?").then(() => {
                    this.$apis.UserApi.del(row).then(() => {
                        this.getData()
                    })
                })
            },
            onSubmit() {
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        this.$apis.UserApi.add(Object.assign({},this.form, {
                            createdTime: new Date()
                        })).then(res => {
                            this.$message({
                                type:"success",
                                message: "新增成功"
                            })
                            this.getData()
                            this.form = {}
                        })
                    }
                });
            },
            getRoomList() {
                this.$apis.RoomApi.select().then(res => {
                    this.roomList = res.data
                })
            },
            handleRoomChange() {
                if(this.form.name === '' || !this.form.name) {
                    this.roomList.forEach(item => {
                        if(item.id === this.form.roomId) {
                            this.form.name = item.roomNum
                        }
                    })
                }

            }
        }
    }
</script>
<style>
    .select-page .el-form--inline .el-form-item {
        margin-right: 0!important;
        width: 50%;
    }
</style>