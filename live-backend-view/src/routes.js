import Login from './views/Login.vue'
import Register from './views/Register.vue'
import NotFound from './views/404.vue'
import studentHome from './views/studentHome.vue'
import teacherHome from './views/teacherHome.vue'
import anchor from './views/anchor/anchors.vue';
import participants from './views/participant/participants.vue';

import myExam from './views/student/myExam.vue';
import myPractice from './views/student/myPractice.vue';
import myMark from './views/student/myMark.vue';
import editStudentInfo from './views/student/editStudentInfo.vue';

import choiceQuestion from './views/teacher/choiceQuestion.vue';
import blankFillingQuestion from './views/teacher/blankFillingQuestion.vue';
import trueFalseQuestion from './views/teacher/trueFalseQuestion.vue';
import editTeacherInfo from './views/teacher/editTeacherInfo.vue';
import commentManager from './views/comment/commentManager.vue';   //评论管理页面

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
            { path: '/editStudentInfo', component: editStudentInfo,  name: "信息管理"}
        ]
    },
    {
        path: '/student',
        component: studentHome,
        name: '主播管理',
        iconCls: 'fa fa-microphone',
        isStudent: true,
        children: [
            { path: '/anchor', component: anchor,  name: "主播管理"}
        ]
    },
    //映射教师用户界面
    {
        path: '/teacher',
        component: teacherHome,
        name: '布置考试题目',
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
        name: '修改信息',
        iconCls: 'fa fa-address-card',
        isTeacher: true,
        children: [
            { path: '/editTeacherInfo', component: editTeacherInfo,  name: "信息管理"}
        ]
    },
    {
        path: '/teacher',
        component: teacherHome,
        name: '参赛管理',
        iconCls: 'fa fa-user-o',
        isTeacher: true,
        children: [
            { path: '/participants', component: participants,  name: "参赛管理"}
        ]
    },
    {
        path: '/teacher',
        component: teacherHome,
        name: '评论管理',
        iconCls: 'fa fa-comment-o',
        isTeacher: true,
        children: [
            { path: '/commentManager', component: commentManager,  name: "评论管理"}
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;