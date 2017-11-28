<style scoped>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #20a0ff;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>
<template>
    <section>
        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters">
                <el-form-item>
                    <el-input v-model="filters.name" placeholder="作者名称"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" v-on:click="getSearch">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="handleAdd">新增</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <!--列表-->
        <el-table :data="authors" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column type="index" width="60">
            </el-table-column>
            <el-table-column prop="idStr" label="ID" width="180">
            </el-table-column>
            <el-table-column prop="title" label="作者名称" width="180">
            </el-table-column>
            <el-table-column prop="description" label="签名介绍" min-width="240" sortable>
            </el-table-column>
            <el-table-column prop="portrait" label="头像" width="180">
                <template scope="scope">
                    <img :src="scope.row.portrait" width="100" height="100"/>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
                <template scope="scope">
                    <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <!--<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>-->
                </template>
            </el-table-column>
        </el-table>

        <!--工具条-->
        <el-col :span="24" class="toolbar">
            <el-pagination layout="total,prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total" style="float:right;">
            </el-pagination>
        </el-col>

        <!--编辑界面-->
        <el-dialog size="small" title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
            <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
                <el-form-item label="ID" prop="idStr">
                    <el-input v-model="editForm.idStr" disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="作者名称" prop="title">
                    <el-input v-model="editForm.title" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="签名介绍">
                    <el-input
                            type="textarea"
                            :autosize="{minRows: 3, maxRows: 4}"
                            placeholder="请输入内容"
                            v-model="editForm.description">
                    </el-input>
                </el-form-item>
                <el-form-item label="头像">
                    <el-col :span="8">
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
                    <el-col :span="14">
                        <ul>
                            <li v-for="o in uploadTips" :key="o" class="text item">
                                {{o}}
                            </li>
                        </ul>
                    </el-col>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
            </div>
        </el-dialog>

        <!--新增界面-->
        <el-dialog size="small" title="新增" v-model="addFormVisible" :close-on-click-modal="false">
            <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
                <el-form-item label="作者名称" prop="title">
                    <el-input v-model="addForm.title" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="签名介绍">
                    <el-input
                            type="textarea"
                            :autosize="{minRows: 3, maxRows: 4}"
                            placeholder="请输入内容"
                            v-model="addForm.description">
                    </el-input>
                </el-form-item>
                <el-form-item label="头像">
                    <el-col :span="8">
                        <el-upload class="avatar-uploader"
                                   :action="uploadUrl"
                                   :show-file-list="false"
                                   :on-success="handleAvatarSuccessAdd"
                                   :before-upload="beforeAvatarUpload"
                        >
                            <img v-if="addForm.portrait" :src="addForm.portrait" class="avatar">
                            <i v-else="true" class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-col>
                    <el-col :span="14">
                        <ul>
                            <li v-for="o in uploadTips" :key="o" class="text item">
                                {{o}}
                            </li>
                        </ul>
                    </el-col>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
            </div>
        </el-dialog>
    </section>
</template>

<script>
    import util from '../../common/js/util'
    //import NProgress from 'nprogress'
    import {anchorAdd, anchorEdit, anchorGetAll, anchorGetSize, anchorSearch, anchorUpdate} from '../../api/api';
    export default {
        data() {
            var g = this.GLOBAL.defaultConfig;
            return {
                uploadUrl: g.baseUrl.uploadUrl,
                filters: {
                    name: ''
                },
                uploadTips:[
                    '上传JPG或者PNG图片',
                    '宽高为200*200像素',
                    '文件大小不超过1M'
                ],
                fileList:[],
                authors: [],
                total: 0,
                page: 1,
                listLoading: false,
                sels: [],//列表选中列
                editFormVisible: false,//编辑界面是否显示
                editLoading: false,
                editFormRules: {
                    title: [
                        { required: true, message: '请输入姓名', trigger: 'blur' },
                        { min: 1, max: 12, message: '长度在 1 到 12 个字符', trigger: 'blur' }
                    ]
                },
                dialogVisible:false,
                //编辑界面数据
                editForm: {
                    id: '',
                    title: '',
                    description: '',
                    portrait: '',
                },

                addFormVisible: false,//新增界面是否显示
                addLoading: false,
                addFormRules: {
                    title: [
                        { required: true, message: '请输入姓名', trigger: 'blur' },
                        { min: 1, max: 12, message: '长度在 1 到 12 个字符', trigger: 'blur' }
                    ]
                },
                //新增界面数据
                addForm: {
                    title: '',
                    description: '',
                    portrait: '',
                },
                pageSize:15,
                currentAvatar:'',

            }
        },
        methods: {
            getSearch: function () {
                let para = {
                    searchTitle: this.filters.name
                }
                this.listLoading = true;
                anchorSearch(para).then((res)=>{
                    this.listLoading = false;
                    if (res.code == "1"){
                        this.authors = res.data.list;
                        this.total = res.data.total;
                        this.$message({
                            message: "查询成功",
                            type: "success"
                        })
                    }else {
                        this.$message({
                            message: "查询失败" + res.desc,
                            type: "error"
                        })
                    }
                })
            },
            handleCurrentChange(val) {
                this.page = val;
            },
            //获取用户列表
            getAuthorsList() {
                let para = {
                    page: this.page,
                    pageSize: this.pageSize
                };
                this.listLoading = true;
                //NProgress.start();
                anchorGetAll(para).then((res) => {
                    this.authors = res.data;
                    this.listLoading = false;
                    //NProgress.done();
                });
            },
            //显示编辑界面
            handleEdit: function (index, row) {
                this.editFormVisible = true;
                this.editForm = Object.assign({}, row);
                this.currentAvatar = row.portrait;
            },
            //显示新增界面
            handleAdd: function () {
                this.addFormVisible = true;
                this.addForm = {
                    title: '',
                    description: '',
                    portrait: '',
                };
            },
            //编辑
            editSubmit: function () {
                this.$refs.editForm.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            this.editLoading = true;
                            //NProgress.start();
                            let para = Object.assign({}, this.editForm);
                            para.id = para.idStr;
                            anchorUpdate(para).then((res) => {
                                this.editLoading = false;
                                //NProgress.done();
                                this.$message({
                                    message: '提交成功',
                                    type: 'success'
                                });
                                this.$refs['editForm'].resetFields();
                                this.editFormVisible = false;
                                this.getAuthorsList();
                            });
                        });
                    }
                });
            },
            getSize(){
                anchorGetSize().then((res)=>{
                    this.total = res.data;
                });
            },
            //新增
            addSubmit: function () {
                this.$refs.addForm.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            this.addLoading = true;
                            //NProgress.start();
                            let para = Object.assign({}, this.addForm);
                            anchorAdd(para).then((res) => {
                                this.addLoading = false;
                                //NProgress.done();
                                this.$message({
                                    message: '提交成功',
                                    type: 'success'
                                });
                                this.$refs['addForm'].resetFields();
                                this.addFormVisible = false;
                                this.getAuthorsList();
                                this.getSize();
                            });

                        });
                    }
                });
            },
            selsChange: function (sels) {
                console.log(sels)
                this.sels = sels;
            },
            cSelect:function (value) {
                console.log(value);
            },
            handleAvatarSuccessEditor: function(response, file, fileList) {
                this.editForm.portrait = response.data;
                this.currentAvatar = response.data;
            },
            handleAvatarSuccessAdd: function(response, file, fileList) {
                this.addForm.portrait = response.data;
                this.currentAvatar = response.data;
            },
            beforeAvatarUpload: function (file) {
                const isLt2M = file.size / 1024 / 1024 < 1;
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 1MB!');
                }
                return isLt2M;
            },

        },
        mounted() {
            this.getAuthorsList();
            this.getSize();
        },
    }

</script>