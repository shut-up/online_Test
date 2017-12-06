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
                    <el-button @click="addQuestion" type="primary">新增</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <!--列表展示-->
        <el-table :data="questions" highlight-current-row v-loading="listLoading" style="width: 100%;" ref="teamRef">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="title" label="题目"></el-table-column>
            <el-table-column prop="selectionA" label="选项A"></el-table-column>
            <el-table-column prop="selectionB" label="选项B"></el-table-column>
            <el-table-column prop="selectionC" label="选项C"></el-table-column>
            <el-table-column prop="selectionD" label="选项D"></el-table-column>
            <el-table-column prop="answer" label="答案"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
        </el-table>
        <el-col :span="24">
            <el-pagination layout="total,prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
            </el-pagination>
        </el-col>

        <!--添加选择题-->
        <el-dialog size="tiny" :title="formUi.title" v-model="formUi.editFormVisible" :close-on-click-modal="false">
            <el-form :model="addQuestionsForm" label-width="100px" :rules="addQuestionsRules" ref="addQuestions">
                <!--<el-form-item label="ID"><div v-text="editForm.idStr"></div></el-form-item>-->
                <el-form-item label="题目" prop="title">
                    <el-input v-model="addQuestionsForm.title" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="选项A" prop="selectionA">
                    <el-input v-model="addQuestionsForm.selectionA" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="选项B" prop="selectionB">
                    <el-input v-model="addQuestionsForm.selectionB" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="选项C" prop="selectionC">
                    <el-input v-model="addQuestionsForm.selectionC" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="选项D" prop="selectionD">
                    <el-input v-model="addQuestionsForm.selectionD" style="width: 300px"></el-input>
                </el-form-item>
                <el-form-item label="答案" prop="answer">
                    <el-select v-model="addQuestionsForm.answer" multiple clearable style="width: 300px" >
                        <el-option v-for="item in answerType" :label="item.title" :key="item.value" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <el-col :span="24" style="margin: 30px;">
                <el-col :span="12" class="text-center">
                    <el-button @click="formUi.editFormVisible=false">取消</el-button>
                </el-col>
                <el-col :span="12" class="text-center">
                    <el-button type="primary" @click="saveQuestion">确认</el-button>
                </el-col>
            </el-col>
        </el-dialog>
    </section>
</template>
<script>
    import util from '../../common/js/util'
    import {
        roomGetAllCatalog,
        teamAdd,
        teamGetAll,
        teamSearch,
        teamUpate,
        teamGetSize,
        getQuestionSize,
        getQuestionList,
        addQuestion,
        searchQuestion
    } from '../../api/api'
    export default {
        data(){
            var g = this.GLOBAL.defaultConfig;
            return {
                //展示选择题数据
                questions: [],
                uploadUrl: g.baseUrl.uploadUrl,
                //添加选择题题界面数据
                addQuestionsForm: {
                    title: '',
                    selectionA: '',
                    selectionB: '',
                    selectionC: '',
                    selectionD: '',
                    answer: ['A'],
                    type: 0,
                 },
                //答案选项
                answerType:[
                    {"title":"A","value":'A'},
                    {"title":"B","value":'B'},
                    {"title":"C","value":'C'},
                    {"title":"D","value":'D'},
                ],
                //添加问题规则
                addQuestionsRules:{
                    title: [
                        { required: true, message: '请输入题目', trigger: 'blur' },
                    ],
                    selectionA: [
                        { required: true, message: '请输入A选项内容', trigger: 'blur' },
                    ],
                    selectionB: [
                        { required: true, message: '请输入B选项内容', trigger: 'blur' },
                    ],
                    selectionC: [
                        { required: true, message: '请输入C选项内容', trigger: 'blur' },
                    ],
                    selectionD: [
                        { required: true, message: '请输入D选项内容', trigger: 'blur' },
                    ],
                    answer: [
                        { type:"array", required: true, message: '请至少选择一个答案', trigger: 'change' },
                    ]
                },
                //查找条件
                keywords: '',
                type: 0,    //0为选择题
                page: 1,
                pageSize: 4,

                filters: {
                    roomType: "",
                    cType: "",
                    keyword: ""
                },

                total: 0,
                uploadTips:[
                    '上传JPG或者PNG图片',
                    '宽高为200*200像素',
                    '文件大小不超过1M'
                ],
                teams: [],
                roomTypes: [],
                cTypes: g.teamType,
                listLoading: false,

                editForm: {

                },
                teamTypeKv: g.teamTypeKv,
                //edit form
                formUi: {
                    title: "",
                    editFormVisible: false,
                },
                currentMove: 1,
                editForm: {
                    id: "",
                    idStr: "",
                    title: "",
                    catalogId: "",
                    type: "",
                    portrait: "",
                },
                editFormRules:{
                    title: [
                        { required: true, message: '请输入名称', trigger: 'blur' },
                    ],
                    catalogId: [
                        { required: true, message: '请选择直播间类型', trigger: 'change' },
                    ],
                    type: [
                        { required: true, message: '请选择参赛者类型', trigger: 'change' },
                    ]
                }
            };
        },
        methods: {
            initForm: function () {
                this.editForm = {
                    id: "",
                    idStr: "",
                    title: "",
                    catalogId: "",
                    type: "",
                    portrait: "",
                };
            },
            addQuestion: function () {
                this.currentMove = 1;
                this.formUi.title = "新增";
                this.formUi.editFormVisible = true;
                this.initForm();
            },
            editDialog: function (row, col) {
                this.currentMove = 2,
                this.formUi.title = "编辑";
                this.editForm = _.cloneDeep(row);
                this.editForm.catalogId = this.editForm.catalog.idStr;
                this.formUi.editFormVisible = true;
            },
            handleAvatarSuccessEditor: function (res) {
                this.editForm.portrait = res.data;
            },
            beforeAvatarUpload: function () {

            },
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
                    }
                })
            },
            formatType: function (row, col) {
                return this.teamTypeKv[row[col.property]];
            },
            saveQuestion: function () {
                if ( this.currentMove == 1 ) {
                    var para = $.extend({}, true, this.addQuestionsForm);
                    console.log(para.answer+"   answer");
                    addQuestion(para).then((res)=>{
                        if (this.checkResult(res, true, '添加成功')) {
                            this.initPage();
                        }
                    });
                }else {
                    let para = _.cloneDeep(this.editForm);
                    para.catalog = { id : para.catalogId};
                    para.id = para.idStr;
                    teamUpate(para).then((res)=>{
                        if (this.checkResult(res, true, "更新成功")){
                            this.getTeamList();
                            this.initForm();
                        }
                    });
                }
                this.formUi.editFormVisible = false;
            },
            //分页
            handleCurrentChange: function (val) {
                this.page = val;
                this.search();
            },

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
            getCatalogs: function () {
                roomGetAllCatalog().then((res)=>{
                    if (this.checkResult(res)){
                        this.roomTypes = res.data;
                    }
                })
            },
            getTeamList: function () {
                let para = {
                    page: this.page,
                    pageSize: this.pageSize
                }
                this.listLoading = true;
                teamGetAll(para).then((res)=>{
                    this.listLoading = false;
                    if (this.checkResult(res)){
                        this.teams = res.data;
                    }
                })
            },
            //获取当前页数选择题列表
            getQuestionList: function () {
                let para = {
                    page: this.page,
                    pageSize: this.pageSize
                };
                this.listLoading = true;
                getQuestionList(para).then((res)=>{
                    this.listLoading = false;
                    if (this.checkResult(res)){
                        console.log(res.data+"res.data1111");
                        this.questions = res.data;
                    }
                })
            },
            //获取选择题总数
            getQuestionSize: function () {
                getQuestionSize().then((res)=>{
                    if (this.checkResult(res)){
                        this.total = res.data.length;
                    }
                })
            },
            //初始化页面
            initPage: function () {
                this.getQuestionSize();
                this.getQuestionList();
            }
        },
        mounted(){
            this.initPage();
        },
    }
</script>
<style>
    .tableImg img {
        width: 80px;
        height: 40px;
    }

    .teamUpload .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .teamUpload .avatar-uploader .el-upload:hover {
        border-color: #20a0ff;
    }
    .teamUpload .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 100px;
        height: 100px;
        line-height: 100px;
        text-align: center;
    }
    .teamUpload .avatar {
        width: 100px;
        height: 100px;
        display: block;
    }
    .teamUpload ul {
        padding:0;
        margin:0;
    }
</style>