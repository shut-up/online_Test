<template>
    <section>
        <el-col :span="24" class="toolbar">
            <el-form ref="exam"  :model="examPaper"  label-width="100px" class="demo-ruleForm">
                <el-form-item  >
                    <el-table :data="allPaper" highlight-current-row  @selection-change="selsChange" style="width: 100%;" ref="examRef" >
                        <!--<el-table-column property="status" label="是否考试" width="200"></el-table-column>-->
                        <el-table-column property="title" label="试卷名称" width="200"></el-table-column>
                        <el-table-column property="beginTime" label="考试开始时间" :formatter="formatTime"></el-table-column>
                        <el-table-column property="endTime" label="考试结束时间" :formatter="formatTime"></el-table-column>
                        <el-table-column property="status" label="考试状态">
                            <template scope="scope">
                                <div v-if="scope.row.status === 0" style="color:blue;cursor: pointer" @click="startTest(scope.$index, scope.row)">
                                    开始考试
                                </div>
                                <div v-if="scope.row.status === 1" style="color:blue;cursor: pointer" @click="checkPaper(scope.$index, scope.row)">
                                    查看试卷
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>

                </el-form-item>
            </el-form>
            <el-dialog :title="title" :visible="paperVisible" :show-close="false" :fullscreen="true" > <p align="right" style="color: red;font-weight:bold;" >
                <div v-if="this.ifTest === 0">
                    <span align="right" style="color: red;font-weight:bold;" >剩余时间{{minutes}}:{{seconds}}</span>
                </div>
                <div v-else>
                    <span align="right" style="color: red;font-weight:bold;" >分数：{{this.score}}</span>
                </div>
                <div v-for="(question,index) in examQuestions" :key="question.title"><br>
                    {{index+1}}、<span style="font-weight:bold;">{{question.title}}</span><span style="color: red">   （{{question.score}}分）</span><br><br>
                   <div v-if="question.type === 0">
                        <el-radio v-model="selectAnswers[index]" label="A" border size="medium">{{question.selectionA}}</el-radio><br><br>
                        <el-radio v-model="selectAnswers[index]" label="B" border size="medium">{{question.selectionB}}</el-radio><br><br>
                        <el-radio v-model="selectAnswers[index]" label="C" border size="medium">{{question.selectionC}}</el-radio><br><br>
                        <el-radio v-model="selectAnswers[index]" label="D" border size="medium">{{question.selectionD}}</el-radio><br><br>
                   </div>
                    <div v-if="question.type === 1">
                        <el-radio v-model="tfAnswers[index]" label="T" border size="medium">对</el-radio><br><br>
                        <el-radio v-model="tfAnswers[index]" label="F" border size="medium">错</el-radio><br><br>
                    </div>
                    <div v-if="question.type === 2">
                        <el-input placeholder="请输入答案，多个答案之间用&隔开" v-model="fullAnswer[index]" clearable></el-input>
                    </div>
                </div>
                <div v-if="this.ifTest === 0" slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="submitPaper()">交 卷</el-button>
                </div>
                <div v-else slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="close()">关闭</el-button>
                </div>
            </el-dialog>
        </el-col>
    </section>
</template>
<script>
    import util from '../../common/js/util';
    import {addPaperAnswer, existPaperAnswer, getInfo, getPaper} from "../../api/api";
    export default {
        data(){
            return {
                studentInfo: {},
                accountNumber: localStorage.getItem('accountNumber'),
                allPaper: [
                    {
                        status: 0
                    }
                ],
                examPaper: {},
                paperVisible: false,
                examQuestions: [],
                selectAnswers: [],
                tfAnswers: [],
                fullAnswer: [],
                title: "",
                minutes:0,
                seconds:0,
                paperId: "",
                ifTest: 0,
                score: 0,
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
                        this.submitPaper();
                        _this.seconds = 0;
                        window.clearInterval(time);
                    }else{
                        _this.seconds -= 1
                    }

                },1000);
            },
            startTest: function (index, row) {
                var beginTime = $.extend(true, {}, row).beginTime;
                var endTime = $.extend(true, {}, row).endTime;
                if(new Date().getTime() < beginTime ){
                    this.$message({
                        message: '尚未到考试时间，请稍后重试',
                        type: 'error'
                    });
                    return;
                }
                if(new Date().getTime() > endTime ){
                    this.$message({
                        message: '考试时间已结束，下次请注意',
                        type: 'error'
                    });
                    return;
                }
                this.paperVisible = true;
                this.examQuestions = $.extend(true, {}, row).examQuestion;
                this.title  = $.extend(true, {}, row).title;
                this.paperId = $.extend(true, {}, row).idStr;
                this.ifTest = $.extend(true, {}, row).status;
                //毫秒数转为分钟数，并四舍五入
                this.minutes = Math.round(( endTime - beginTime ) * 0.0000167);
                if(this.minutes == 0)
                    this.seconds = Math.round(( endTime - beginTime ) * 0.001);
                this.timer();
                console.log(this.examQuestions.length+"长度"+this.minutes+"  "+this.seconds);
            },
            checkPaper: function (index, row) {
                this.paperVisible = true;
                this.examQuestions = $.extend(true, {}, row).examQuestion;
                this.title  = $.extend(true, {}, row).title;
                this.paperId = $.extend(true, {}, row).idStr;
                this.ifTest = $.extend(true, {}, row).status;

                var para = {
                    stuId: this.accountNumber,
                    paperId: this.paperId,
                }
                existPaperAnswer(para).then(res=>{
                    this.selectAnswers = res.selectAnswers;
                    this.tfAnswers = res.tfAnswers;
                    this.fullAnswer = res.fullAnswer;
                    this.score = res.score;
                })
            },
            formatTime: function (row, column) {
                var newDate = new Date();
                newDate.setTime(row[column.property]);
                return util.formatDate.format(newDate, 'yyyy-MM-dd hh:mm:ss');
            },
            selsChange: function () {

            },
            close: function () {
                this.paperVisible = false;
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
                        stuId: this.accountNumber,
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
                var para = {
                    paperId: this.paperId,
                    stuId: this.accountNumber,
                    selectAnswers: this.selectAnswers,
                    tfAnswers: this.tfAnswers,
                    fullAnswer: this.fullAnswer,
                };
                addPaperAnswer(para).then(res=>{
                    this.$message({
                        message: '交卷成功',
                        type: 'success'
                    });
                    this.getStudentPaper();
                    this.paperVisible = false;
                })
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