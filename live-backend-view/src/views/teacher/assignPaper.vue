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
        <el-dialog title="学生" :visible.sync="selectStudentVisible">
            <div slot="footer" class="dialog-footer">
                <el-button @click="selectStudentVisible = false">取 消</el-button>
                <el-button type="primary" @click="selectStudentVisible = false">确 定</el-button>
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
    export default {
        data(){
            return {
                active: "",
                selectStudentVisible: false,
                assignPaperVisible: false,
                assignSuccessVisible: false,
            };
        },
        methods: {
            next() {
                if(this.active==0&&this.selectStudentVisible==false) this.selectStudentVisible = true;
                if(this.active==1&&this.assignPaperVisible==false) this.assignPaperVisible = true;
                if(this.active==2&&this.assignSuccessVisible==false) this.assignSuccessVisible = true;
                if (this.active++ > 2) this.active = 0;

            },
        },
        mounted(){
        },
    }
</script>
<style>
</style>