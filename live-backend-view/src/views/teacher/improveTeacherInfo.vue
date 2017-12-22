<template>
    <section>

        <!--修改信息-->
        <el-col>
            <el-form ref="teacherInfo"  :rules="teacherInfoRules" :model="teacherInfo"  label-width="100px" class="demo-ruleForm">
                <el-form-item label="学号：" >
                    <el-tag type="info">{{accountNumber}}</el-tag>
                </el-form-item>
                <el-form-item label="姓名：" >
                    <el-tag type="info">{{name}}</el-tag>
                </el-form-item>
                <el-form-item label="年级：" prop="grade">
                    <el-select clearable v-model="teacherInfo.grade">
                        <el-option label="2014" value="2014"></el-option>
                        <el-option label="2015" value="2015"></el-option>
                        <el-option label="2016" value="2016"></el-option>
                        <el-option label="2017" value="2017"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="学院：" prop="school">
                    <el-select clearable v-model="teacherInfo.school">
                        <el-option label="信息学院" value="信息学院"></el-option>
                        <el-option label="物理学院" value="物理学院"></el-option>
                        <el-option label="外语学院" value="外语学院"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="updateInfo()">立即修改</el-button>
                    <el-button @click="resetForm('teacherInfo')">重置</el-button>
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
            return {
                teacherInfo: {
                    accountNumber: '',
                    name: '',
                    checknewPassword: '',
                    school: '',
                    grade: '',
                },
                accountNumber: sessionStorage.getItem('accountNumber')==null?localStorage.getItem('accountNumber'):sessionStorage.getItem('accountNumber'),
                name: sessionStorage.getItem('name')==null?localStorage.getItem('name'):sessionStorage.getItem('name'),
                school: '',
                grade: '',
                teacherInfoRules: {
                    school: [
                        { required: true, message: '请选择学院', trigger: 'change' }
                    ],
                    grade: [
                        { required: true, message: '请选择年级', trigger: 'change' }
                    ],
                }
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
                this.$refs.teacherInfo.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            let para = {
                                accountNumber: this.accountNumber,
                                grade: this.teacherInfo.grade,
                                school: this.teacherInfo.school,
                            }
                            //let para = Object.assign({}, this.studentInfo);
                            updateInfo(para).then((res) => {
                                if (res.data !== undefined) {
                                    this.$message({
                                        message: '更新成功',
                                        type: 'success'
                                    });
                                    this.resetForm('teacherInfo');
                                }else{
                                    this.$message({
                                        message: '更新失败',
                                        type: 'error'
                                    });
                                }
                                this.$router.push({path: '/improveTeacherInfo'});
                            });
                        });
                    }
                });
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