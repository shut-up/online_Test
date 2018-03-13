<template>
    <section>

        <!--修改信息-->
        <el-col>
            <el-form :model="managerInfo"  label-width="100px" class="demo-ruleForm">
                <el-form-item label="学号：" >
                    <el-tag type="info">{{accountNumber}}</el-tag>
                </el-form-item>
                <el-form-item label="姓名：" >
                    <el-tag type="info">{{name}}</el-tag>
                </el-form-item>
                <el-form-item label="班级：">
                    <el-tag type="info" v-for="grade1 in managerInfo.managerClasses">{{grade1.grade}}届{{grade1.school}}学院{{grade1.major}}</el-tag>
                </el-form-item>
            </el-form>
        </el-col>
        <!--修改信息-->
        <el-col>
            <el-form ref="teacherInfo"  :rules="teacherInfoRules" :model="teacherInfo"  label-width="100px" class="demo-ruleForm">
                <el-form-item label="年级：" prop="grade">
                    <el-select clearable v-model="teacherInfo.grade">
                        <el-option v-for="item in 2017-1990" :label="(item+1990)" :value="item+1990" :key="item+1990"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="学院：">
                    <el-select clearable v-model="teacherInfo.school" @change="getSelectMajor">
                        <el-option v-for="item in school" :key="item.idStr" :label="item.title" :value="item.idStr"></el-option>
                    </el-select>
                    &nbsp&nbsp&nbsp专业：
                    <el-select multiple clearable v-model="teacherInfo.major" :disabled="this.unable" filterable >
                        <el-option v-for="item in majorTemp" :key="item.idStr" :label="item.title" :value="item.title" ></el-option>
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
    import {getInfo, roomUpdateSelectedComment, updateInfo} from '../../api/api'
    import Util from "../../common/js/util";
    export default {
        data(){
            var g = this.GLOBAL.defaultConfig;
            return {
                teacherInfo: {
                    accountNumber: '',
                    name: '',
                    managerClasses: [],
                    school: '',
                    major: [],
                    grade: '',
                },
                school: g.school,
                major: g.major,
                majorTemp: [],
                unable: true,
                grade: '',
                accountNumber: sessionStorage.getItem('accountNumber')==null?localStorage.getItem('accountNumber'):sessionStorage.getItem('accountNumber'),
                name: sessionStorage.getItem('name')==null?localStorage.getItem('name'):sessionStorage.getItem('name'),
                managerInfo: {},
                teacherInfoRules: {
                    school: [
                        { type:"integer", required: true, message: '请选择学院', trigger: 'change' }
                    ],
                    grade: [
                        { type:"integer",required: true, message: '请选择年级', trigger: 'change' }
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
                            var schoolName = "";
                            for(var i = 0; i< this.school.length;i++){
                                if(this.school[i].idStr===this.teacherInfo.school){
                                    schoolName = this.school[i].title;
                                    console.log("schoolName"+schoolName);
                                    break;
                                }
                            }
                            let para = {
                                accountNumber: this.accountNumber,
                                grade: this.teacherInfo.grade,
                                school: schoolName,
                                major: this.teacherInfo.major,
                            };
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
                                this.getTeacherInfo();
                            });
                        });
                    }
                });
            },
            getTeacherInfo: function () {
              let para = {
                  accountNumber: this.accountNumber
              };
                getInfo(para).then(res=>{
                    this.managerInfo = res.data;
                })
            },
            getSelectMajor: function () {
                this.majorTemp  = this.major.filter((r) => r.parentIdStr === this.teacherInfo.school);
                this.unable = false;
            },
            //重置
            resetForm(formName) {
                this.$refs[formName].resetFields();
                this.teacherInfo.school = '';
                this.teacherInfo.grade = '';
                this.teacherInfo.major = [];

            }
        },
        //初始化操作
        mounted(){
            this.getTeacherInfo();
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