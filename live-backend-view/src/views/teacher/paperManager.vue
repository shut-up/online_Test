<template>
    <section>
        <el-form ref="exam"  :model="examPaper"  label-width="100px" class="demo-ruleForm">
            <el-form-item  >
                <el-table :data="allPaper" highlight-current-row  @selection-change="selsChange" style="width: 100%;" ref="examRef" >
                    <el-table-column property="title" label="试卷名称" width="200"></el-table-column>
                    <el-table-column property="beginTime" label="考试开始时间" :formatter="formatTime"></el-table-column>
                    <el-table-column property="endTime" label="考试结束时间" :formatter="formatTime"></el-table-column>
                    <el-table-column property="examClass" label="考试班级" :formatter="formatExamClass"></el-table-column>
                    <el-table-column property="examQuestion" label="考试题目">
                        <template scope="scope">
                            <div style="color:blue;cursor: pointer" @click="showQuestion(scope.$index, scope.row)">
                                查看题目
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </el-form-item>
            <!--<el-form-item align="center">-->
                <!--<el-button type="primary" @click="" >生成试卷</el-button>-->
            <!--</el-form-item>-->
        </el-form>

    </section>
</template>
<script>
    import util from '../../common/js/util'
    import {getExamPaper} from "../../api/api";
    export default {
        data(){
            return {
                examPaper: {},
                allPaper: [],
            };
        },
        methods: {
            getAllPaper: function () {
                getExamPaper().then(res=>{
                   this.allPaper = res;
                });
            },
            selsChange: function () {

            },
            formatExamClass: function (row, column) {
                let ret = new Array();
                for (let i=0; i<row[column.property].length; i++){
                    ret.push(row[column.property][i])
                }
                return ret.join("、")
            },
            showQuestion: function (index, row) {
                this.$router.push({ path: '/assignPaper/?examId=' + row.idStr})
            },
            formatTime: function (row, column) {
                var newDate = new Date();
                newDate.setTime(row[column.property]);
                return util.formatDate.format(newDate, 'yyyy-MM-dd hh:mm:ss');
            },
        },
        mounted(){
           this.getAllPaper();
        },
    }
</script>
<style>
</style>