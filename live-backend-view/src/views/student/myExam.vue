<template>
    <section>
        <el-col :span="24" class="toolbar">
            <el-form ref="exam"  :model="examPaper"  label-width="100px" class="demo-ruleForm">
                <el-form-item  >
                    <el-table :data="allPaper" highlight-current-row  @selection-change="selsChange" style="width: 100%;" ref="examRef" >
                        <el-table-column property="title" label="试卷名称" width="200"></el-table-column>
                        <el-table-column property="beginTime" label="考试开始时间" :formatter="formatTime"></el-table-column>
                        <el-table-column property="endTime" label="考试结束时间" :formatter="formatTime"></el-table-column>
                        <el-table-column property="examQuestion" label="考试状态">
                            <template scope="scope">
                                <div style="color:blue;cursor: pointer" @click="startTest(scope.$index, scope.row)">
                                    开始考试
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>

                </el-form-item>
            </el-form>
            <el-dialog :title="title" :visible="paperVisible" :show-close="false" :fullscreen="true" > <p align="right" style="color: red;font-weight:bold;" >剩余时间{{minutes}}:{{seconds}}</p>
                <div v-for="(question,index) in examQuestions" :key="question.title"><br>
                    {{index+1}}、<span style="font-weight:bold;">{{question.title}}</span><span style="color: red">   （{{question.score}}分）</span><br><br>
                   <div v-if="question.type === 0">
                        <el-radio v-model="selectAnswers[index]" label="A" border size="medium">{{question.selectionA}}</el-radio><br><br>
                        <el-radio v-model="selectAnswers[index]" label="B" border size="medium">{{question.selectionB}}</el-radio><br><br>
                        <el-radio v-model="selectAnswers[index]" label="C" border size="medium">{{question.selectionC}}</el-radio><br><br>
                        <el-radio v-model="selectAnswers[index]" label="D" border size="medium">{{question.selectionD}}</el-radio><br><br>
                   </div>
                    <div v-if="question.type === 1">
                        <el-radio v-model="tfAnswers[index]" label="t" border size="medium">对</el-radio><br><br>
                        <el-radio v-model="tfAnswers[index]" label="f" border size="medium">错</el-radio><br><br>
                    </div>
                    <div v-if="question.type === 2">
                        <el-input placeholder="请输入答案，多个答案之间用&隔开" v-model="fullAnswer[index]" clearable></el-input>
                    </div>
                </div>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="submitPaper()">交 卷</el-button>
                </div>
            </el-dialog>
        </el-col>
    </section>
</template>
<script>
    import util from '../../common/js/util';
    import {getInfo, getPaper} from "../../api/api";
    export default {
        data(){
            return {
                studentInfo: {},
                accountNumber: localStorage.getItem('accountNumber'),
                allPaper: [],
                examPaper: {},
                paperVisible: false,
                examQuestions: [],
                selectAnswers: [],
                tfAnswers: [],
                fullAnswer: [],
                title: "",
                minutes:0,
                seconds:0,
            };
        },
        methods:{
            num:function (n) {
                return n<10 ? "0" + n : "" + n
            },
            //倒计时
            timer:function () {
                var _this = this;
                var time = window.setInterval(function () {
                    if (_this.seconds == 0 && _this.minutes != 0) {
                        _this.seconds = 59;
                        _this.minutes -= 1;
                    }else if(_this.minutes == 0 && _this.seconds == 0){
                        _this.seconds = 0;
                        window.clearInterval(time);
                        alert('完美')
                    }else{
                        _this.seconds -= 1
                    }

                },1000);
            },
            startTest: function (index, row) {
                this.paperVisible = true;
                this.examQuestions = $.extend(true, {}, row).examQuestion;
                this.title  = $.extend(true, {}, row).title;
                //毫秒数转为分钟数，并四舍五入
                this.minutes = Math.round(( $.extend(true, {}, row).endTime - $.extend(true, {}, row).beginTime ) * 0.0000167);
                this.timer();
                console.log(this.examQuestions.length+"长度"+this.minutes);
            },
            formatTime: function (row, column) {
                var newDate = new Date();
                newDate.setTime(row[column.property]);
                return util.formatDate.format(newDate, 'yyyy-MM-dd hh:mm:ss');
            },
            selsChange: function () {

            },
            getStudentPaper: function () {
                let para = {
                    accountNumber: this.accountNumber
                };
                getInfo(para).then(res=>{
                    this.studentInfo = res.data;
                });
                setTimeout(() => {
                    let paperPara = {
                        examClass: this.studentInfo.managerClasses[0].grade + this.studentInfo.managerClasses[0].school + this.studentInfo.managerClasses[0].major[0],
                    };
                    getPaper(paperPara).then(res => {
                        this.allPaper = res.data;
                    })
                },100);
            },
            getStudentInfo: function () {
                let para = {
                    accountNumber: this.accountNumber
                };
                getInfo(para).then(res=>{
                    this.studentInfo = res.data;
                })
            },
            submitPaper: function () {
                this.minutes = 0;
                this.seconds = 0;
                this.paperVisible = false;
            },
        },
        watch:{
            second:{
                handler(newVal){
                    this.num(newVal)
                }
            },
            minute:{
                handler(newVal){
                    this.num(newVal)
                }
            }
        },
        computed:{
            second:function () {
                return this.num(this.seconds)
            },
            minute:function () {
                return this.num(this.minutes)
            }
        },
        mounted(){
            this.getStudentInfo();
            this.getStudentPaper();
        },
    }
</script>
<style scoped>
    .moreContent {
        color: blue;
        cursor: pointer
    }
</style>