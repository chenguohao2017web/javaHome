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
                    prop="room.roomNum"
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
                    prop="lastMonthData.water"
                    label="上个月水(吨)">
            </el-table-column>
            <el-table-column
                    prop="lastMonthData.dian"
                    label="上个月电(度)">
            </el-table-column>
            <el-table-column
                    prop="roomPrice"
                    label="房租">
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
                    width="300px"
                    label="操作">
                <template slot-scope="scope">
                    <el-button v-if="scope.row.status === 0" @click="changeStatus(scope.row)" type="success" size="mini">交租</el-button>
                    <el-button @click="editCount(scope.row)" type="default" size="mini">编辑</el-button>
                    <el-button @click="delCount(scope.row)" type="danger" size="mini" >删除</el-button>
                    <el-button @click="print(scope.row)" type="warning" size="mini" >打印</el-button>
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

        <el-dialog :visible.sync="printDialog">
            <div class="printFrom" id="printRef" style="padding: 10px;">
                <div class="title" style="font-size: 24px; width: 200px;border-bottom: 1px solid #333;margin: 0 auto;padding:2px 0;text-align: center">{{printForm.date}}收据单</div>
                <div style="display: flex;">
                    <div class="row" style="margin-top: 20px;display: flex;align-items: center;flex:1;">
                        <div class="label" style="font-size:18px; margin-right: 20px;">房号:</div>
                        <div class="val" style="font-size: 24px;border-bottom: 1px solid #333;flex:1;text-align: left;">{{printForm.roomNum}}</div>
                    </div>
                    <div class="row" style="margin-top: 20px;display: flex;align-items: center;flex:1;">
                        <div class="label" style="font-size:18px; margin-right: 20px;">房租:</div>
                        <div class="val" style="font-size: 24px;border-bottom: 1px solid #333;flex:1;text-align: left;">{{printForm.roomPrice}}元</div>
                    </div>
                </div>

                <table width="400px" style="border: 1px solid #333;margin: 20px auto;">
                    <thead>
                        <tr style="font-size: 20px;borer-bottom: 1px solid #333;">
                            <th style="border: 1px solid #333;">月</th>
                            <th style="border: 1px solid #333;">水/4元</th>
                            <th style="border: 1px solid #333;">电/1.5元</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td style="border: 1px solid #333;font-size: 20px;text-align: center;">本月</td>
                            <td style="border: 1px solid #333;"><span style="font-size:22px;">{{printForm.water}}</span><span>吨</span></td>
                            <td style="border: 1px solid #333;"><span style="font-size:22px;">{{printForm.dian}}</span><span>度</span></td>
                        </tr>
                        <tr>
                            <td style="border: 1px solid #333;font-size: 20px;text-align: center;">上月</td>
                            <td style="border: 1px solid #333;"><span style="font-size:22px;">{{printForm.lastWater}}</span>吨</td>
                            <td style="border: 1px solid #333;"><span style="font-size:22px;">{{printForm.lastDian}}</span>度</td>
                        </tr>
                    </tbody>
                </table>
                <div style="font-size: 18px;">注意：每月加收2吨2度共计：11元</div>
                <div style="font-size: 30px;text-align: right;margin-top: 20px;">需缴：{{printForm.price}}元</div>
            </div>

            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="printSubmit">确 定</el-button>
              </span>
        </el-dialog>
    </div>
</template>
<script>
    import print from 'print-js'
    export default {
        data() {
            return {
                printForm: {},
                printDialog: false,
                page:1,
                size: 12,
                roomList: [],
                formInline: {
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
                // this.onSubmit()
            })
        },
        methods: {
            clearForm() {
                this.formInline = {}
                this.year = ''
                this.month = ''
            },
            tableRowClassName({row}) {
                if (row.status === 1) {
                    return 'success-row';
                }
                return '';
            },
            onSubmit() {
                this.$refs['form'].validate( v => {
                    if(v) {
                        if(this.formInline.date) {
                            let date = this.formInline.date
                            let year = date.split("-")[0]
                            let month = date.split("-")[1]
                            this.year = year
                            this.month = month
                        }

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
                        this.roomList.forEach(roomItem => {
                            if(item.roomId === roomItem.id) {
                                item.roomPrice = roomItem.price
                            }
                        })
                    })
                })
            },
            handlePageChange(page) {
                this.page = page
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
            print(row) {
                this.printDialog = true
                this.printForm.roomNum = row.room.roomNum
                this.printForm.price = row.price
                this.printForm.water = row.water
                this.printForm.dian = row.dian
                this.printForm.lastWater = row.lastMonthData.water
                this.printForm.lastDian = row.lastMonthData.dian
                this.printForm.roomPrice = row.roomPrice
                this.printForm.date = row.year + "-" + row.month
            },
            printSubmit() {
                //打印
                print({
                    printable: 'printRef',
                    type: 'html',
                    scanStyles: false
                })

                this.printDialog = false
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
    .el-dialog {
        width: 70%;
    }

</style>