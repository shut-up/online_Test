<template>
    <section>
        <el-col :span="24" class="toolbar">
            <el-form :inline="true" :model="filters">
                <el-form-item>
                    <el-select v-model="filters.roomType" clearable>
                        <el-option
                            v-for="item in roomTypes"
                            :key="item.idStr"
                            :value="item.idStr"
                            :label="item.title"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-select v-model="filters.cType" clearable>
                        <el-option
                                v-for="item in cTypes"
                                :key="item.idStr"
                                :value="item.idStr"
                                :label="item.title"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-input v-model="filters.keyword" placeholder="输入参赛者名称"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="search">搜索</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button @click="addDialog" type="primary">新增</el-button>
                </el-form-item>
            </el-form>
        </el-col>
        <el-table :data="teams"
                  highlight-current-row
                  v-loading="listLoading"
                  style="width: 100%;"
                  ref="teamRef"
        >
            <el-table-column prop="title" label="参赛者名称">
            </el-table-column>
            <el-table-column prop="portrait" label="图像">
                <template scope="scope">
                    <div class="tableImg">
                        <img :src="scope.row.portrait"/>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="catalog.title" label="直播间类型"></el-table-column>
            <el-table-column prop="type" label="参赛者类型" :formatter="formatType"></el-table-column>
            <el-table-column prop="idStr" label="ID"></el-table-column>
            <el-table-column>
                <template scope="scope">
                    <div @click="editDialog(scope.row,scope.index)" class="blue">编辑</div>
                </template>
            </el-table-column>
        </el-table>
        <el-col :span="24">
            <el-pagination layout="total,prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total" style="float:right;">
            </el-pagination>
        </el-col>

        <!--edit dialog-->
        <el-dialog size="tiny" :title="formUi.title" v-model="formUi.editFormVisible" :close-on-click-modal="false">
            <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
                <el-form-item label="ID"><div v-text="editForm.idStr"></div></el-form-item>
                <el-form-item label="参赛者名称" prop="title">
                    <el-input v-model="editForm.title" style="width: 218px"></el-input>
                </el-form-item>
                <el-form-item label="直播间类型" prop="catalogId">
                    <el-select v-model="editForm.catalogId">
                        <el-option
                            v-for="item in roomTypes"
                            :key="item.idStr"
                            :value="item.idStr"
                            :label="item.title"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="参赛者类型" prop="title">
                    <el-select v-model="editForm.type">
                        <el-option
                                v-for="item in cTypes"
                                :key="item.idStr"
                                :value="item.idStr"
                                :label="item.title"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="图像">
                    <el-col :span="8" class="teamUpload">
                        <el-upload class="avatar-uploader"
                                   :action="uploadUrl"
                                   :show-file-list="false"
                                   :on-success="handleAvatarSuccessEditor"
                                   :before-upload="beforeAvatarUpload"
                        >
                            <img v-if="editForm.portrait" :src="editForm.portrait" class="avatar">
                            <i v-else="true" class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-col>
                    <el-col :span="16" class="teamUpload">
                        <ul>
                            <li v-for="o in uploadTips" :key="o" class="text item">
                                {{o}}
                            </li>
                        </ul>
                    </el-col>
                </el-form-item>
            </el-form>
            <el-col :span="24" style="margin: 15px;">
                <el-col :span="12" class="text-center">
                    <el-button @click="formUi.editFormVisible=false">取消</el-button>
                </el-col>
                <el-col :span="12" class="text-center">
                    <el-button type="primary" @click="saveEdit">确认</el-button>
                </el-col>
            </el-col>
        </el-dialog>
    </section>
</template>
<script>
    import {roomGetAllCatalog, teamAdd, teamGetAll, teamSearch, teamUpate, teamGetSize} from '../../api/api'
    export default {
        data(){
            var g = this.GLOBAL.defaultConfig;
            return {
                uploadUrl: g.baseUrl.uploadUrl,
                filters: {
                    roomType: "",
                    cType: "",
                    keyword: ""
                },
                page: 1,
                pageSize: 15,
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
            addDialog: function () {
                this.currentMove = 1;
                this.formUi.title = "新增";
                this.formUi.editFormVisible = true;
                this.initForm();
            },
            editDialog: function (row, col) {
                this.currentMove = 2
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
                this.listLoading = true;
                var para = {
                    type: this.filters.cType,
                    searchTitle: this.filters.keyword
                }
                if (!_.isEmpty(this.filters.roomType)){
                    para.catalog = {id:this.filters.roomType};
                }
                teamSearch(para).then((res)=>{
                    this.listLoading = false;
                    if (this.checkResult(res, true, '搜索成功')) {
                        this.teams = res.data.list;
                        this.total = res.data.total;
                    }
                })
            },
            formatType: function (row, col) {
                return this.teamTypeKv[row[col.property]];
            },
            saveEdit: function () {
                if (this.currentMove == 1){
                    let para = _.cloneDeep(this.editForm);
                    para.catalog = { id : para.catalogId};
                    teamAdd(para).then((res)=>{
                       if (this.checkResult(res, true, '添加成功')) {
                           this.getTeamList();
                           this.initForm();
                           this.getSize();
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

            handleCurrentChange: function (val) {
                this.page = val;
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
            getSize: function () {
                teamGetSize().then((res)=>{
                    if (this.checkResult(res)){
                        this.total = res.data;
                    }
                })
            },
            initPage: function () {
                this.getCatalogs();
                this.getTeamList();
                this.getSize();
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