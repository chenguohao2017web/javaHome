<template>
    <div class="select-page">
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
                    label="操作">
                <template slot-scope="scope">
                    <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
                    <el-button type="text" size="small">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>
<script>
    export default {
        methods: {
            handleClick(row) {
                console.log(row);
            }
        },
        created() {
            this.$apis.UserApi.select({page: 1, size: 5}).then(res => {
                this.tableData = res.data.list
            })
        },
        data() {
            return {
                tableData: []
            }
        }
    }
</script>