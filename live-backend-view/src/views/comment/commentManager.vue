<template>
    <section>

        <!--开头筛选的复选框-->
        <el-col :span="24" class="toolbar">
            <el-form :inline="true">
                <el-form-item label="评论状态">
                    <el-checkbox-group v-model="status">
                        <el-checkbox label="0" name="status">显示</el-checkbox>
                        <el-checkbox label="1" name="status">隐藏</el-checkbox>
                        <el-checkbox label="2" name="status">删除</el-checkbox>
                    </el-checkbox-group>
                </el-form-item> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                <el-form-item label="审核进度">
                    <el-checkbox-group v-model="check">
                        <el-checkbox label="0" name="check">待审核</el-checkbox>
                        <el-checkbox label="1" name="check">已审核</el-checkbox>
                    </el-checkbox-group>
                </el-form-item> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                <el-form-item label="投诉状态">
                    <el-checkbox-group v-model="complaintStatus">
                        <el-checkbox label="0" name="complaintStatus">被投诉</el-checkbox>
                        <el-checkbox label="1" name="complaintStatus">已处理</el-checkbox>
                    </el-checkbox-group>
                </el-form-item> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                <!--<el-form-item label="评论状态">-->
                    <!--<el-radio-group v-model="status">-->
                        <!--<el-radio label="" >所有</el-radio>-->
                        <!--<el-radio label="0" >显示</el-radio>-->
                        <!--<el-radio label="1">隐藏</el-radio>-->
                        <!--<el-radio label="2">删除</el-radio>-->
                    <!--</el-radio-group>-->
                <!--</el-form-item> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp-->
                <el-form-item>
                    <el-button type="primary" @click="searchComment">查询</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <!--表格，显示评论-->
        <el-table :data="comments" highlight-current-row v-loading="listLoading" @selection-change="selectComment" style="width: 100%;" ref="teamRef">
            <el-table-column type="selection" width="55" label="全选"></el-table-column>
            <el-table-column prop="status" label="状态" :formatter="formatCommtentStatus"></el-table-column>
            <el-table-column prop="userInfo.userName" label="用户名"></el-table-column>
            <el-table-column prop="content" label="评论内容"></el-table-column>
            <el-table-column prop="createTime" label="时间" :formatter="formatCommtentCreatetime"></el-table-column>
            <el-table-column prop="content" label="投诉内容" ></el-table-column>
            <el-table-column prop="check" label="进度" :formatter="formatCommtentCheck"></el-table-column>
            <el-table-column prop="check" label="审核" :formatter="formatCommtentCheck"></el-table-column>
            <el-table-column prop="content" label="处理人"></el-table-column>
        </el-table>

        <el-col :span="24" class="toolbar">
            <!--设置评论状态-->
            <el-form :inline="true">
                <el-form-item label="选中的评论状态设置为：">
                    <el-select clearable v-model="updateStatus">
                        <el-option label="显示" value="0"></el-option>
                        <el-option label="隐藏" value="1"></el-option>
                        <el-option label="删除" value="2"></el-option>
                    </el-select>
                    <el-button type="primary" @click="setStatus" :disabled="this.sels.length===0">确定</el-button>
                </el-form-item>
                <!--分页，:page-size="8"：每页8条记录，:total="total"：总记录数-->
                <el-pagination layout="total,prev, pager, next" @current-change="handlePageChange" :page-size="8" :total="total" style="float:right;">
                </el-pagination>
            </el-form>
        </el-col>
    </section>
</template>

<script>
    import {
        roomGetCommentSize,
        roomGetAllComment,
        roomGetSelectedCommentpage,
        roomUpdateSelectedComment
    } from '../../api/api'
    import Util from "../../common/js/util";
    export default {
        data(){
            return {
                status: [], //当前评论状态，0显示 1隐藏  2删除
                check: [],  //当前审核状态，0未审核 1已审核
                complaintStatus: [],    //投诉状态，0被投诉 1已处理
                page: 1,    //当前页数
                pageSize: 8,   //每页显示评论数
                total: 0,   //总评论数
                ids: [],    //需要修改状态的所有评论id
                updateStatus: '',   //需要更新的状态
                comments: [],   //评论内容，用于表格显示
                listLoading: false, //是否显示加载状态
                currentMove: 1,
                sels: [],//选中列表
            };
        },
        methods: {
            //处理页面改变，修改page属性
            handlePageChange: function (val) {
                this.page = val;
                this.searchComment();
            },
            selectComment: function (sels) {
                this.sels = sels;
            },
            //判断是否操作成功
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
            //筛选查询评论功能
            searchComment: function () {
                let paras = {
                    page: this.page,
                    pageSize: this.pageSize,
                    status: this.status,
                    check: this.check,
                    complaintStatus: this.complaintStatus,
                };
                this.listLoading = true;
                roomGetSelectedCommentpage(paras).then((res)=>{
                    this.listLoading = false;
                        this.comments = res.data.list;  //list为查询返回的数据
                        this.total = res.data.total;//修改评论数为查询的数目
                        //重置筛选状态
                })
            },
            //获取所有评论内容
            getCommentData:function () {
                let para = {
                    page: this.page,
                    pageSize: this.pageSize,
                    status: this.status,
                    check: this.check,
                    complaintStatus: this.complaintStatus,
                };
                this.listLoading = true;
                roomGetAllComment(para).then((res)=>{
                    this.listLoading = false;
                        this.comments = res.data;
                });
            },
            //获取所有评论数目
            getCommentSize: function () {
                let para = {
                };
                roomGetAllComment(para).then((res)=>{
                    this.listLoading = false;
                        this.total = res.data.length;
                });
            },
            //设置评论状态
            setStatus: function () {
                let para = {
                    status: this.updateStatus,
                    ids: this.sels.map(item => item.idStr).toString(),
                };
                this.listLoading = true;
                roomUpdateSelectedComment(para).then(()=>{
                    this.listLoading = false;
                    this.getCommentData();
                });
            },
            //格式化评论状态
            formatCommtentStatus: function (row, column) {
                return row[column.property] == 0 ? '显示' : (row[column.property] == 1) ? '隐藏' : '删除';
            },
            //格式化评论创建时间：'y-M-d h:m:s'
            formatCommtentCreatetime: function(row, column) {
                if (row[column.property] == 0 || row[column.property] == null) {
                    return "";
                }
                let date = new Date(row[column.property]);
                return Util.formatDate.format(date,'y-M-d h:m:s');
            },
            //格式化审核进度
            formatCommtentCheck: function(row, column) {
                return row[column.property] == 0 ? '待审核' : '已审核';
            }
        },
        //初始化操作
        mounted(){
            this.getCommentData();
            this.getCommentSize();
        },
    }
</script>

<style lang="scss">

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