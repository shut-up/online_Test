<template>
    <section>
        <el-col class="toolbar">
            <el-steps :active="active" finish-status="success">
                <el-step title="选择班级"></el-step>
                <el-step title="生成试卷"></el-step>
                <el-step title="生成成功"></el-step>
                <transition name="fade">
                    <div v-if="this.active == 0">page left</div>
                    <div v-if="this.active == 1">page right</div>
                </transition>
            </el-steps>
            <el-button style="margin-top: 12px;" @click="next" type="primary">下一步</el-button>
        </el-col>
        <el-dialog title="班级" :visible.sync="selectClassVisible">
            <el-select v-model="currentGrade" placeholder="请选择年级" remote @change="getSelectedClass">
                <el-option
                        v-for="item in grade"
                        :key="item"
                        :value="item"
                        :label="item"
                ></el-option>
            </el-select>
            <el-select v-model="currentClass" multiple placeholder="请选择班级">
                <el-option
                        v-for="item in myClass"
                        :key="item"
                        :value="item"
                        :label="item"
                ></el-option>
            </el-select>
            <div slot="footer" class="dialog-footer">
                <el-button @click="selectClassVisible = false">取 消</el-button>
                <el-button type="primary" @click="selectClassVisible = false">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="试卷" :visible.sync="assignPaperVisible">
            <div slot="footer" class="dialog-footer">
                <el-button @click="assignPaperVisible = false">取 消</el-button>
                <el-button type="primary" @click="assignPaperVisible = false">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="完成" :visible.sync="assignSuccessVisible">
            <div slot="footer" class="dialog-footer">
                <el-button @click="assignSuccessVisible = false">取 消</el-button>
                <el-button type="primary" @click="assignSuccessVisible = false">确 定</el-button>
            </div>
        </el-dialog>
    </section>
</template>
<script>
    import util from '../../common/js/util'
    import {} from '../../api/api'
    import {getInfo} from "../../api/api";
    export default {
        data(){
            return {
                active: 0,
                selectClassVisible: false,
                assignPaperVisible: false,
                assignSuccessVisible: false,
                accountNumber: sessionStorage.getItem('accountNumber')==null?localStorage.getItem('accountNumber'):sessionStorage.getItem('accountNumber'),
                managerClass: {},
                currentGrade: "",
                currentClass: [],
                grade: [],
                myClass: []
            };
        },
        methods: {
            next() {
                if(this.active==0&&this.selectClassVisible==false) this.selectClassVisible = true;
                if(this.active==1&&this.assignPaperVisible==false) this.assignPaperVisible = true;
                if(this.active==2&&this.assignSuccessVisible==false) this.assignSuccessVisible = true;
                if (this.active++ > 2) this.active = 0;
            },
            getClass: function () {
                let para = {
                    accountNumber: this.accountNumber
                };
                getInfo(para).then(res=>{
                    this.managerClass = res.data.managerClasses;
                    for(var i = 0; i< this.managerClass.length; i++){
                        this.grade.push(this.managerClass[i].grade)
                    }
                });
            },
            getSelectedClass: function () {
                this.myClass = [];
                for(var i = 0; i< this.managerClass.length; i++){
                    if(this.currentGrade == this.managerClass[i].grade){
                        for(var j = 0; j < this.managerClass[i].school.length; j++) {
                            this.myClass.push(this.managerClass[i].school[j]);
                        }
                        return;
                    }
                }
            },
        },
        mounted(){
            this.getClass();
        },
    }
</script>
<style>
</style>