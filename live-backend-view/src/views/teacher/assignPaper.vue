<template>
    <section>
        <br><br>
        <el-tabs v-model="visiable" @tab-click="handleClick">
            <el-tab-pane label="选择班级" name="first"></el-tab-pane>
            <el-tab-pane label="考试时间" name="second"></el-tab-pane>
            <el-tab-pane label="选择题目" name="third"></el-tab-pane>
            <!--<el-tab-pane label="生成试卷" name="fourth"></el-tab-pane>-->
        </el-tabs>
        <br><br>
        <el-form ref="exam"  :rules="examRules" :model="examForm"  label-width="100px" class="demo-ruleForm">
            <el-form-item label="试卷名称：" >
                <el-input v-model="examForm.title" placeholder="请输入试卷名称"></el-input>
            </el-form-item>
            <el-form-item label="考试班级：" >
                <el-tag v-for="examClass in examForm.checkedClass" :key="examClass" closable @close="handleCloseClass(examClass)">
                    {{examClass}}
                </el-tag>
            </el-form-item>
            <el-form-item label="考试时间：" >
                {{examForm.examTimeShow}}
            </el-form-item>
            <el-form-item label="考试题目：" >
                <el-table :data="examForm.selectQuestion" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;" ref="examRef" >
                    <el-table-column property="type" sortable label="题目类型" width="150" :formatter="formatQuestionType"></el-table-column>
                    <el-table-column property="title" label="题目" width="200"></el-table-column>
                    <el-table-column property="selectionA" label="选项A"></el-table-column>
                    <el-table-column property="selectionB" label="选项B"></el-table-column>
                    <el-table-column property="selectionC" label="选项C"></el-table-column>
                    <el-table-column property="selectionD" label="选项D"></el-table-column>
                    <el-table-column property="score" label="分值"></el-table-column>
                </el-table>
            </el-form-item>
            <el-form-item align="center">
                <el-button type="primary" @click="createPaper" v-text="buttonName"></el-button>
            </el-form-item>
        </el-form>

        <el-dialog title="考试班级" :visible.sync="selectClassVisible">
            <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
            <div style="margin: 15px 0;"></div>
            <el-checkbox-group v-model="checkedClass" @change="handleCheckedClassChange">
                <el-checkbox v-for="city in myClass" :label="city" :key="city">{{city}}</el-checkbox>
            </el-checkbox-group>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectClass">取消</el-button>
                <el-button type="primary" @click="confirmSelectClass">确定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="考试时间" :visible.sync="examTimeVisiable">
            <el-date-picker v-model="examTime" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelExamTime">取 消</el-button>
                <el-button type="primary" @click="confirmExamTime">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="选择题目" :visible.sync="selectQuestionVisible">
            <el-table :data="questions" highlight-current-row v-loading="listLoading" @selection-change="selsChange" height="100%" style="width: 100%;" ref="examRef">
                <el-table-column type="selection" width="35"></el-table-column>
                <el-table-column property="type" label="题目类型" width="80" fixed :formatter="formatQuestionType"></el-table-column>
                <el-table-column property="title" label="题目" width="200"></el-table-column>
                <el-table-column property="selectionA" label="选项A" width="200"></el-table-column>
                <el-table-column property="selectionB" label="选项B" width="200"></el-table-column>
                <el-table-column property="selectionC" label="选项C" width="200"></el-table-column>
                <el-table-column property="selectionD" label="选项D" width="200"></el-table-column>
                <el-table-column property="choiceanswer" label="选择题答案"></el-table-column>
                <el-table-column property="tfAnswer" label="判断题答案"></el-table-column>
                <el-table-column property="blankAnswer" label="填空题答案" width="200"></el-table-column>
                <el-table-column property="score" label="分值"></el-table-column>
            </el-table>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelSelectQuestions">取 消</el-button>
                <el-button type="primary" @click="confirmSelectQuestions">确 定</el-button>
            </div>
        </el-dialog>
        <!--<el-dialog title="生成试卷" :visible.sync="createPaperVisible">-->
            <!--<el-form ref="exam"  :rules="examRules" :model="examDetail"  label-width="100px" class="demo-ruleForm">-->
                <!--<el-form-item label="考试班级：" >-->
                    <!--<el-tag v-for="examClass in checkedClass" :key="examClass" closable @close="handleCloseClass(examClass)">-->
                        <!--{{examClass}}-->
                    <!--</el-tag>-->
                <!--</el-form-item>-->
                <!--<el-form-item label="考试时间：" >-->
                    <!--{{examTimeShow}}-->
                <!--</el-form-item>-->
                <!--<el-form-item label="考试题目：" >-->
                    <!--<el-table :data="selectQuestion" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;" ref="examRef" >-->
                        <!--<el-table-column property="type" sortable label="题目类型" width="150" :formatter="formatQuestionType"></el-table-column>-->
                        <!--<el-table-column property="title" label="题目" width="200"></el-table-column>-->
                        <!--<el-table-column property="selectionA" label="选项A"></el-table-column>-->
                        <!--<el-table-column property="selectionB" label="选项B"></el-table-column>-->
                        <!--<el-table-column property="selectionC" label="选项C"></el-table-column>-->
                        <!--<el-table-column property="selectionD" label="选项D"></el-table-column>-->
                    <!--</el-table>-->
                <!--</el-form-item>-->
            <!--</el-form>-->
            <!--<div slot="footer" class="dialog-footer">-->
                <!--<el-button @click="cancelCreatePaper">取 消</el-button>-->
                <!--<el-button type="primary" @click="confirmCreatePaper">确 定</el-button>-->
            <!--</div>-->
        <!--</el-dialog>-->
    </section>
</template>
<script>
    import util from '../../common/js/util'
    import {} from '../../api/api'
    import {getInfo} from "../../api/api";
    import {getQuestionList} from "../../api/api";
    import {getExamPaperById} from "../../api/api";
    import {createOrUpdatePaper} from "../../api/api";
    export default {
        data(){
            return {
                selectClassVisible: false,
                assignPaperVisible: false,
                selectQuestionVisible: false,
                examTimeVisiable: false,
                createPaperVisible: false,
                accountNumber: sessionStorage.getItem('accountNumber')==null?localStorage.getItem('accountNumber'):sessionStorage.getItem('accountNumber'),
                managerClass: {},
                myClass: [],
                checkedClass: [],
                title: "",
                checkAll: false,
                isIndeterminate: true,
                examForm:{
                    title: "",
                    checkedClass: [],
                    examTimeShow: "",
                    selectQuestion: [],
                },
                examDetail: {},
                examRules: {},
                questions: [],
                selectQuestion: [],
                listLoading: false,
                sels: [],
                ids: [],
                visiable: '',
                examTime: [new Date(), new Date()],
                examTimeShow: "",
                examId: "",
                buttonName: "生成试卷",
            };
        },
        methods: {
            //选择考试班级
            handleCheckAllChange(val) {
                this.checkedClass = val ? this.myClass : [];
                this.isIndeterminate = false;
            },
            handleCheckedClassChange(value) {
                let checkedCount = value.length;
                this.checkAll = checkedCount === this.myClass.length;
                this.isIndeterminate = checkedCount > 0 && checkedCount < this.myClass.length;
            },
            //取消考试班级
            handleCloseClass: function (examClass) {
                this.checkedClass.splice(this.checkedClass.indexOf(examClass), 1);
            },
            //班级
            cancelSelectClass: function () {
                this.checkedClass = [];
                this.selectClassVisible = false;
            },
            confirmSelectClass: function () {
                this.selectClassVisible = false;
                this.examForm.checkedClass = this.checkedClass;
            },
            //考试时间
            cancelExamTime: function () {
                this.examTime = [new Date(2018, 1, 1, 9, 0), new Date(2018, 1, 1, 11, 0)];
                this.examTimeVisiable = false;
            },
            confirmExamTime: function () {
                if (!_.isEmpty(this.examTime)){
                    var beginTime = util.formatDate.format(new Date(this.examTime[0]),'yyyy-MM-dd hh:mm:ss');
                    var endTime = util.formatDate.format(new Date(this.examTime[1]),'yyyy-MM-dd hh:mm:ss');
                    this.examTimeShow = beginTime + " -- " + endTime;
                }
                this.examForm.examTimeShow = this.examTimeShow;
                this.examTimeVisiable = false;
            },
            //考试题目
            cancelSelectQuestions: function () {
                this.selectQuestion = [];
                this.selectQuestionVisible = false;
            },
            compare: function(val1,val2){
                return val1-val2;
            },
            confirmSelectQuestions: function () {
                this.selectQuestion = [];
                console.log(JSON.stringify(this.sels)+"aaa");
                this.ids = this.sels.map(item => item.idStr).toString();
                var id = this.ids.split(",");
                id.sort(this.compare);
                for(var i = 0; i < id.length; i++) {
                    var s = this.questions.filter((r) => r.idStr == id[i]);
                    this.selectQuestion.push(s[0]);
                }
                this.examForm.selectQuestion = this.selectQuestion;
                this.active = 2;
                this.selectQuestionVisible = false;
            },
            //生成试卷
            cancelCreatePaper: function () {
                this.createPaperVisible = false;
            },
            confirmCreatePaper: function () {
                this.createPaperVisible = false;
            },
            createPaper: function () {
                if(this.examForm.title==""){
                    this.$message({
                        message: '请输入试卷名称',
                        type: 'error'
                    });
                    return;
                }else if( this.examForm.checkedClass==""){
                    this.$message({
                        message: '请先选择考试班级',
                        type: 'error'
                    });
                    return;
                }else if(this.examForm.examTimeShow==""){
                    this.$message({
                        message: '请选择考试时间',
                        type: 'error'
                    });
                    return;
                }else if(this.examForm.selectQuestion==""){
                    this.$message({
                        message: '请选择试卷题目',
                        type: 'error'
                    });
                    return;
                }
                let para = {
                    id: this.examId,
                    title: this.examForm.title,
                    examClass: this.checkedClass,
                    beginTime: (new Date(this.examTime[0])).getTime(),
                    endTime: (new Date(this.examTime[1])).getTime(),
                    examQuestion: this.examForm.selectQuestion,
                };
                createOrUpdatePaper(para).then(res=>{
                    this.$message({
                        message: this.buttonName + '成功',
                        type: 'success'
                    });
                    this.$router.push({ path: '/assignPaper'})
                    this.resetForm();
                });
            },
            //获取数据库所有题目
            getQuestionList: function () {
                let para = {
                    type: 3,
                };
                this.listLoading = true;
                getQuestionList(para).then((res)=>{
                    this.questions = res.data;
                    this.listLoading = false;
                })
            },
            getClass: function () {
                let para = {
                    accountNumber: this.accountNumber
                };
                getInfo(para).then(res=>{
                    this.managerClass = res.data.managerClasses;
                    for(var i = 0; i< this.managerClass.length; i++){
                        for(var j = 0; j< this.managerClass[i].major.length; j++){
                            var s = this.managerClass[i].grade + this.managerClass[i].school+this.managerClass[i].major[j];
                            this.myClass.push(s);
                        }
                    }
                });
            },
            formatQuestionType: function (row, column) {
                return row[column.property] == 0 ? '选择题' : (row[column.property] == 1 ? '判断题' : '填空题');
            },
            selsChange: function (sels) {
                this.sels = sels;
            },
            initPage: function () {
                let para = {
                    examId: this.examId,
                };
                getExamPaperById(para).then(res=>{
                   this.examForm = res;
                   this.examForm.checkedClass = res.examClass;
                   this.checkedClass = res.examClass;
                   var beginTime = this.formatTime(res.beginTime);
                   var endTime = this.formatTime(res.endTime);
                   this.examForm.examTimeShow = beginTime + " -- " + endTime;
                   this.examForm.selectQuestion = res.examQuestion;
                   this.buttonName = "更新试卷";
                });
            },
            formatTime: function (time) {
                var newDate = new Date();
                newDate.setTime(time);
                return util.formatDate.format(newDate, 'yyyy-MM-dd hh:mm:ss');
            },
            resetForm: function () {
                this.examForm.title = "";
                this.examForm.checkedClass=[];
                this.checkedClass=[];
                this.examForm.examTimeShow = "";
                this.examForm.selectQuestion = [];
                this.sels = [];
                this.buttonName = "生成试卷";
            },
            handleClick(tab) {
                if(tab.name == "first"){
                    this.selectClassVisible = true;
                }else if(tab.name == "second"){
                    this.examTimeVisiable = true;
                } else if(tab.name == "third"){
                    this.selectQuestionVisible = true;
                } else if(tab.name == "fourth"){
                    if( this.checkedClass==""){
                        this.$message({
                            message: '请先选择考试班级',
                            type: 'error'
                        });
                    }else if(this.examTimeShow==""){
                        this.$message({
                            message: '请选择考试时间',
                            type: 'error'
                        });
                    }else if(this.selectQuestion==""){
                        this.$message({
                            message: '请选择试卷题目',
                            type: 'error'
                        });
                    }else {
                        this.createPaperVisible = true;
                    }
                }
            },
        },
        watch:{
            $route(){
                this.examId = this.$route.params.examId;
            },
            examId:function (current,old) {
                setTimeout(() => {
                    if ( this.examId != "" && this.examId != null && !_.isUndefined(this.examId)) {
                        this.initPage();
                    }else {
                        this.resetForm();
                    }
                }, 500);
            }
        },
        mounted(){
            this.examId = this.$route.query.examId;
            if(!_.isUndefined(this.examId) && !_.isEmpty(this.examId)){
                this.initPage();
            }else{
                this.resetForm();
            }
            this.getClass();
            this.getQuestionList();
        },
    }
</script>
<style>
</style>