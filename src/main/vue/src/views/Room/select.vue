<template>
    <div class="select-room-page">
        <el-form
                :inline="true"
                ref="form"
                :model="form"
                :rules="rules"
                label-width="80px"
                label-position="left"
        >
            <el-form-item label="房间号" prop="roomNum">
                <el-input v-model="form.roomNum"></el-input>
            </el-form-item>
            <el-form-item label="价格" prop="price">
                <el-input v-model="form.price"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">立即创建</el-button>
            </el-form-item>
        </el-form>
        <el-table
                :data="tableData"
                style="width: 100%"
        >

            <el-table-column
                    prop="roomNum"
                    label="房间号">
            </el-table-column>
            <el-table-column
                    prop="price"
                    label="价格">
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
        },
        data() {
            return {
                tableData: [],
                total: 0,
                size: 12,
                page: 1,
                form: {},
                roomList:[],
                rules: {
                    roomNum: [
                        {required: true, message: '请输入', trigger: 'blur'},
                    ],
                    price :[
                        {required: true, message: '请输入', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            getData() {
                this.$apis.RoomApi.selectPage({page: this.page, size: this.size}).then(res => {
                    console.log(res)
                    this.tableData = res.data.records
                    // this.tableData = res.data.list
                    this.total = res.data.total
                })
            },
            handlePageChange(page) {
                this.page = page
                this.getData()
            },
            editUser(row) {
                console.log(row)
                this.$router.push({
                    path: "/room/edit",
                    query: {
                        id: row.id
                    }
                })
            },
            async delUser(row) {
                console.log(row)
                this.$confirm("确认删除?").then(() => {
                    this.$apis.RoomApi.del(row).then(() => {
                        this.getData()
                    })
                })
            },
            onSubmit() {
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        this.$apis.RoomApi.add(this.form).then(res => {
                            this.$message({
                                type:"success",
                                message: "新增成功"
                            })
                            this.getData()
                            this.form = {}
                        })
                    }
                });
            }
        }
    }
</script>