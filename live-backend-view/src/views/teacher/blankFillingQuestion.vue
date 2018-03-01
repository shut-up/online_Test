<template>
    <section>
        <el-col :span="24" class="toolbar">
            <el-form :inline="true">
                <el-form-item>
                    <el-input v-model="keywords" placeholder="输入题目关键字"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="search">搜索</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button @click="addQuestions" type="primary">新增</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <!--列表展示-->
        <el-table :data="questions" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;" ref="teamRef">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="status" label="状态">
                <template scope="scope">
                    <span v-if="scope.row.status=== 1">显示</span>
                    <span v-else style="color: red">隐藏</span>
                </template>
            </el-table-column>
            <el-table-column prop="title" label="题目">
                <template scope="scope">
                    <div  style="color: blue;cursor: pointer" @click="EditChoiceQuestion(scope.$index, scope.row)">{{scope.row.title}}</div>
                </template>
            </el-table-column>
            <el-table-column prop="blankAnswer" label="答案" ></el-table-column>
            <el-table-column prop="createTime" label="创建时间" :formatter="formatDate"></el-table-column>
            <el-table-column label="操作">
                <template scope="scope">
                    <el-button type="danger" size="small" @click="DelQuestion(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--工具条-->
        <el-col :span="24">
            <el-form :inline="true">
                <el-form-item label="将题目：">
                    <el-select v-model="status" :disabled="this.sels.length===0" style="width: 40%;">
                        <el-option label="显示" value="1"></el-option>
                        <el-option label="隐藏" value="0"></el-option>
                        <el-option label="删除" value="2"></el-option>
                    </el-select>
                    <el-button type="primary" @click="setStatus" :disabled="this.sels.length===0">确定</el-button>
                </el-form-item>
                <el-pagination layout="total,prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;"></el-pagination>
            </el-form>
        </el-col>

        <!--添加、更新填空题-->
        <el-dialog size="tiny" :title="formUi.title" v-model="formUi.editFormVisible" :close-on-click-modal="false">
            <el-form :model="addQuestionsForm" label-width="100px" :rules="addQuestionsRules" ref="addQuestionsForm">
                <el-form-item label="题目" prop="title">
                    <el-input v-model="addQuestionsForm.title"></el-input>
                </el-form-item>
                <el-form-item label="分值" prop="score">
                    <el-input v-model="addQuestionsForm.score"></el-input>
                </el-form-item>
                <el-form-item label="答案" prop="blankAnswer">
                    <el-input type="textarea" :rows="3" placeholder="多个答案之间用'&'隔开" v-model="addQuestionsForm.blankAnswer">
                    </el-input>
                </el-form-item>
            </el-form>
            <el-col :span="24" style="margin: 30px;">
                <el-col :span="12" class="text-center">
                    <el-button @click="formUi.editFormVisible=false">取消</el-button>
                </el-col>
                <el-col :span="12" class="text-center">
                    <el-button type="primary" @click="saveQuestion" :loading="addLoading">确认</el-button>
                </el-col>
            </el-col>
        </el-dialog>
    </section>
</template>
<script>
    import util from '../../common/js/util'
    import {
        getQuestionSize, getQuestionList, addQuestion, searchQuestion, updateQuestion, deleteQuestion
    } from '../../api/api'
    export default {
        data(){
            return {
                //展示选择题数据
                questions: [],
                //添加选择题题界面数据
                addQuestionsForm: {
                    title: '',
                    blankAnswer: '',
                    type: 2,
                    score: 0,
                    status: 1,
                },
                //添加问题规则
                addQuestionsRules:{
                    title: [
                        { required: true, message: '请输入题目', trigger: 'blur' },
                    ],
                    blankAnswer: [
                        { required: true, message: '请至少填入一个答案', trigger: 'blur' },
                    ]
                },
                //查找条件
                keywords: '',
                type: 2,    //2为填空题
                page: 1,
                pageSize: 8,
                total: 0,
                addLoading: false,
                //设置题目状态
                status: "",
                ids: [],
                sels: [],
                listLoading: false,
                formUi: {
                    title: "",
                    editFormVisible: false,
                },
            };
        },
        methods: {
            //初始化添加问题的表单数据
            initForm: function () {
                this.addQuestionsForm = {
                    title: '',
                    blankAnswer: '',
                    type: 2,
                    status: "1",
                };
            },
            //点击选中时执行
            selsChange: function (sels) {
                this.sels = sels;
            },
            //新增题目
            addQuestions: function () {
                this.formUi.title = "新增";
                this.formUi.editFormVisible = true;
                this.initForm();
            },
            //更新问题状态
            setStatus: function () {
                let para = {
                    status: this.status,
                    ids: this.sels.map(item => item.idStr).toString(),
                };
                console.log(this.ids.length+"aaa");
                updateQuestion(para).then(res=>{
                    this.$confirm('确认执行该操作吗?', '提示', {
                        type: 'warning'
                    }).then(() => {
                        if (res.data !== undefined) {
                            this.$message({
                                message: '操作成功',
                                type: 'success'
                            });
                        }
                        this.initPage();
                    });
                }).catch(() => {
                });
            },
            //编辑题目
            EditChoiceQuestion: function (index, row) {
                this.formUi.title = "编辑";
                this.addQuestionsForm = $.extend(true, {}, row);
                this.formUi.editFormVisible = true;
            },
            //删除题目
            DelQuestion: function (index, row) {
                this.$confirm('确认删除该记录吗?', '提示', {
                    type: 'warning'
                }).then(() => {
                    this.listLoading = true;
                    deleteQuestion("id="+row.idStr).then((res) => {
                        if (res.data != undefined) {
                            this.listLoading = false;
                            this.$message({
                                message: '删除成功',
                                type: 'success'
                            });
                            this.initPage();
                        }else {
                            this.$message({
                                message: '题目不存在',
                                type: 'error'
                            });
                            this.initPage();
                        }
                    });
                }).catch(() => {
                });
            },
            //模糊搜索问题
            search: function () {
                var para = {
                    page: this.page,
                    pageSize: this.pageSize,
                    keywords: this.keywords,
                    type: this.type,
                };
                searchQuestion(para).then((res)=>{
                    if (this.checkResult(res, true, '搜索成功')) {
                        this.questions = res.data.list;
                        this.total = res.data.total;
                        this.keywords = '';
                    }
                })
            },
            //新增问题
            saveQuestion: function () {
                this.$refs.addQuestionsForm.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            this.addLoading = true;
                            var para = $.extend({}, true, this.addQuestionsForm);
                            para.id = para.idStr;
                            console.log(para.blankAnswer + "   answer");
                            addQuestion(para).then((res) => {
                                this.addLoading = false;
                                if (this.checkResult(res, true, '添加成功')) {
                                    this.addLoading = false;
                                    this.initPage();
                                }
                            });
                            this.formUi.editFormVisible = false;
                        });
                    }
                });
            },
            //分页查找
            handleCurrentChange: function (val) {
                this.page = val;
                this.search();
            },
            //判断方法操作后状态
            checkResult: function (res, noMessage, message) {
                if (res.code == "1"){
                    if (noMessage){
                        this.$message({
                            message: message?message:"操作成功!",
                            type: "success"
                        })
                    }
                    return true;
                }else {
                    this.$message({
                        message: "操作失败,失败信息为:" + res.desc,
                        type: "error"
                    })
                }
                return false;
            },
            //获取当前页数选择题列表
            getQuestionList: function () {
                let para = {
                    page: this.page,
                    pageSize: this.pageSize,
                    type: 2,
                };
                this.listLoading = true;
                getQuestionList(para).then((res)=>{
                    this.listLoading = false;
                    if (this.checkResult(res)){
                        this.questions = res.data;
                    }
                })
            },
            //获取选择题总数
            getQuestionSize: function () {
                let para = {
                    type: 2,
                };
                getQuestionSize(para).then((res)=>{
                    if (this.checkResult(res)){
                        this.total = res.data.length;
                    }
                })
            },
            //初始化页面
            initPage: function () {
                this.getQuestionSize();
                this.getQuestionList();
            },
            //格式化日期转换
            formatDate: function (row, column) {
                var newDate = new Date();
                newDate.setTime(row[column.property]);
                return util.formatDate.format(newDate, 'yyyy-MM-dd hh:mm:ss');
            },
            //格式化答案
//            formatAnswer: function (row, column) {
//                return row[column.property].join("|");
//            },
        },
        mounted(){
            this.initPage();
        },
    }
</script>
<style>
</style>