import Login from './views/Login.vue'
import Register from './views/Register.vue'
import NotFound from './views/404.vue'
import studentHome from './views/studentHome.vue'
import teacherHome from './views/teacherHome.vue'
import live from './views/live/live.vue'
import talk from './views/live/talk.vue'
import editRoom from './views/live/editRoom.vue'
import face from './components/face/testFace.vue';
import anchor from './views/anchor/anchors.vue';
import participants from './views/participant/participants.vue';
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
        name: '直播管理',
        iconCls:'fa fa-soccer-ball-o',
        isStudent: true,
        children: [
            { path: '/liveRoom', component: talk, name: '直播室',hidden:true},
            { path: '/testapi', component: face, name: '测试', hidden: true},
            { path: '/live', component: live, name: '直播室管理' },
            { path: '/editRoom', component: editRoom, name: '设置直播室',hidden:true }
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
    //映射教师用户登录
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