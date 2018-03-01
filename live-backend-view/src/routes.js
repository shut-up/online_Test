import Login from './views/Login.vue'
import Register from './views/Register.vue'
import NotFound from './views/404.vue'
import studentHome from './views/studentHome.vue'
import teacherHome from './views/teacherHome.vue'
import anchor from './views/anchor/anchors.vue';
import participants from './views/participant/participants.vue';
//student
import myExam from './views/student/myExam.vue';
import myPractice from './views/student/myPractice.vue';
import myMark from './views/student/myMark.vue';
import editStudentPSW from './views/student/editStudentPSW.vue';
import editStudentInfo from './views/student/improveStudentInfo.vue';
//teacher
import choiceQuestion from './views/teacher/choiceQuestion.vue';
import blankFillingQuestion from './views/teacher/blankFillingQuestion.vue';
import trueFalseQuestion from './views/teacher/trueFalseQuestion.vue';
import editTeacherPSW from './views/teacher/editTeacherPSW.vue';
import improveTeacherInfo from './views/teacher/improveTeacherInfo.vue';
import assignPaper from './views/teacher/assignPaper.vue';
import paperManager from './views/teacher/paperManager.vue';

let routes = [
    //映射登录
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    //映射注册
    {
        path: '/register',
        component: Register,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    //映射学生用户界面
    {
        path: '/student',
        component: studentHome,
        name: '我的考试',
        iconCls:'fa el-icon-edit',
        isStudent: true,
        children: [
            { path: '/myExam', component: myExam, name: '我的考试' },
        ]
    },
    {
        path: '/student',
        component: studentHome,
        name: '我的练习',
        iconCls:'fa fa-file-o',
        isStudent: true,
        children: [
            { path: '/myPractice', component: myPractice, name: '我的练习' },
        ]
    },
    {
        path: '/student',
        component: studentHome,
        name: '我的成绩',
        iconCls:'fa fa-file-text',
        isStudent: true,
        children: [
            { path: '/myMark', component: myMark, name: '我的成绩' },
        ]
    },
    {
        path: '/student',
        component: studentHome,
        name: '修改信息',
        iconCls:'fa fa-address-card',
        isStudent: true,
        children: [
            { path: '/editStudentInfo', component: editStudentInfo,  name: "完善信息"},
            { path: '/editStudentPSW', component: editStudentPSW,  name: "修改密码"}
        ]
    },
    //映射教师用户界面
    {
        path: '/teacher',
        component: teacherHome,
        name: '布置题目',
        iconCls: 'fa el-icon-edit',
        isTeacher: true,
        children: [
            { path: '/choiceQuestion', component: choiceQuestion,  name: "选择题"},
            { path: '/trueFalseQuestion', component: trueFalseQuestion,  name: "判断题"},
            { path: '/blankFillingQuestion', component: blankFillingQuestion,  name: "填空题"},
            { path: '/assignExam', component: blankFillingQuestion,  name: "主观题", hidden: true},
        ]
    },
    {
        path: '/teacher',
        component: teacherHome,
        name: '考试管理',
        iconCls: 'fa fa-user-o',
        isTeacher: true,
        children: [
            { path: '/assignPaper', component: assignPaper,  name: "生成试卷"},
            { path: '/paperManager', component: paperManager,  name: "试卷管理"}
        ]
    },
    {
        path: '/teacher',
        component: teacherHome,
        name: '信息管理',
        iconCls: 'fa fa-address-card',
        isTeacher: true,
        children: [
            { path: '/improveTeacherInfo', component: improveTeacherInfo,  name: "完善信息"},
            { path: '/editTeacherPSW', component: editTeacherPSW,  name: "修改密码"}
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;