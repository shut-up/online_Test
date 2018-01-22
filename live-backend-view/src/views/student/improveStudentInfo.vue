<template>
    <section>

        <!--修改信息-->
        <el-col>
            <el-form ref="studentInfo"  :rules="studentInfoRules" :model="studentInfo"  label-width="100px" class="demo-ruleForm">
                <el-form-item label="学号：" >
                    <el-tag type="info">{{accountNumber}}</el-tag>
                </el-form-item>
                <el-form-item label="姓名：" >
                    <el-tag type="info">{{name}}</el-tag>
                </el-form-item>
                <el-form-item label="年级：">
                    <el-select clearable v-model="studentInfo.grade">
                        <el-option v-for="item in 2017-1990" :label="(item+1990)" :value="item+1990" :key="item+1990"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="学院：">
                    <el-cascader :options="option" @change="handleChange" v-model="studentInfo.school"></el-cascader>
                </el-form-item>
                <el-form-item label="学院：">
                    <el-select clearable v-model="studentInfo.school">
                        <el-option label="信息学院" value="信息学院"></el-option>
                        <el-option label="物理学院" value="物理学院"></el-option>
                        <el-option label="外语学院" value="外语学院"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="updateInfo()">立即修改</el-button>
                    <el-button @click="resetForm('studentInfo')">重置</el-button>
                </el-form-item>
            </el-form>
        </el-col>

    </section>
</template>

<script>
    import {roomUpdateSelectedComment, updateInfo} from '../../api/api'
    import Util from "../../common/js/util";
    export default {
        data(){
            var g = this.GLOBAL.defaultConfig;
            return {
                studentInfo: {
                    accountNumber: '',
                    name: '',
                    password: '',
                    newPassword: '',
                    checknewPassword: '',
                    school: [],
                    grade: '',
                },
                option: g.options,
                accountNumber: localStorage.getItem('accountNumber'),
                name: localStorage.getItem('name'),
                password: '',
                newPassword: '',
                checknewPassword: '',
                school: '',
                grade: '',
            };
        },
        methods: {
            //判断是否操作成功
            checkResult: function (res, noMessage, message) {
                if (res.code == "1"){
                    if (noMessage){
                        this.$message({
                            message: message?message:"操作成功!",
                            type: "success"
                        });
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
            updateInfo: function () {
                this.$refs.studentInfo.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            let para = {
                                accountNumber: this.accountNumber,
                                grade: this.studentInfo.grade,
                                school: this.studentInfo.school,
                            };
                            updateInfo(para).then((res) => {
                                if (res.data !== undefined) {
                                    this.$message({
                                        message: '更新成功',
                                        type: 'success'
                                    });
                                    this.resetForm('studentInfo');
                                }else{
                                    this.$message({
                                        message: '更新失败',
                                        type: 'error'
                                    });
                                }
                                this.$router.push({path: '/editStudentInfo'});
                            });
                        });
                    }
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
            //格式化审核进度
            formatCommtentCheck: function(row, column) {
                return row[column.property] == 0 ? '待审核' : '已审核';
            },
            handleChange(value) {
                console.log(value);
            },
            //重置
            resetForm(formName) {
                this.$refs[formName].resetFields();
                this.studentInfo.school = '';
                this.studentInfo.grade = '';
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