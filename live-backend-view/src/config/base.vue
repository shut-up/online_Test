<template></template>
<script type="text/javascript">
    const defaultConfig =
        {
            school: [
                {idStr: 0, title: "信息工程学院"},
                {idStr: 1, title: "文学与传媒学院"},
                {idStr: 2, title: "法政学院"},
                {idStr: 3, title: "外国语学院"},
                {idStr: 4, title: "教育科学学院"},
                {idStr: 5, title: "音乐与舞蹈学院"},
                {idStr: 6, title: "美术与设计学院"},
                {idStr: 7, title: "数学与统计学院"},
                {idStr: 8, title: "物理科学与技术学院"},
                {idStr: 9, title: "机电工程学院"},
                {idStr: 10, title: "化学化工学院"},
                {idStr: 11, title: "生命科学与技术学院"},
                {idStr: 12, title: "商学院"},
                {idStr: 13, title: "体育科学学院"},
            ],
            major: [
                {parentIdStr:0, idStr: 0, title: "软件工程"}, {parentIdStr:0, idStr: 1, title: "计算机科学与技术"},{parentIdStr:0, idStr: 2, title: "电子信息工程"},{parentIdStr:0, idStr: 3, title: "电气工程及其自动化"},{parentIdStr:0, idStr: 4, title: "教育技术"},
                {parentIdStr:1, idStr: 0, title: "汉语言文学（师范）专业"}, {parentIdStr:1, idStr: 1, title: "汉语国际教育（师范）专业"},{parentIdStr:1, idStr: 2, title: "广播电视学专业"},{parentIdStr:1, idStr: 3, title: "网络与新媒体专业"},
                {parentIdStr:2, idStr: 0, title: "法学"}, {parentIdStr:2, idStr: 1, title: "思想政治教育"},{parentIdStr:2, idStr: 2, title: "历史学"},{parentIdStr:2, idStr: 3, title: "人力资源管理"},{parentIdStr:2, idStr: 4, title: "社会工作"},{parentIdStr:2, idStr: 5, title: "公共事业管理"},
                {parentIdStr:3, idStr: 0, title: "英语"}, {parentIdStr:3, idStr: 1, title: "日语"},{parentIdStr:3, idStr: 2, title: "商务英语"},{parentIdStr:3, idStr: 3, title: "翻译"},
                {parentIdStr:4, idStr: 0, title: "心理学"}, {parentIdStr:4, idStr: 1, title: "小学教育"},{parentIdStr:4, idStr: 2, title: "学前教育"},{parentIdStr:4, idStr: 3, title: "特殊教育"},
                {parentIdStr:5, idStr: 0, title: "音乐学"}, {parentIdStr:5, idStr: 1, title: "音乐表演"},{parentIdStr:5, idStr: 2, title: "舞蹈学"},
                {parentIdStr:6, idStr: 0, title: "美术学"}, {parentIdStr:6, idStr: 1, title: "书法学"},{parentIdStr:6, idStr: 2, title: "视觉传达设计"},{parentIdStr:6, idStr: 3, title: "环境设计"},{parentIdStr:6, idStr: 4, title: "服装与服饰设计"},
                {parentIdStr:7, idStr: 0, title: "数学与应用数学"}, {parentIdStr:7, idStr: 1, title: "信息与计算科学"},{parentIdStr:7, idStr: 2, title: "统计学"},
                {parentIdStr:8, idStr: 0, title: "物理学（师范）"}, {parentIdStr:8, idStr: 1, title: "应用物理学"},{parentIdStr:8, idStr: 2, title: "地理科学（师范）"},{parentIdStr:8, idStr: 3, title: "科学教育（师范）"},
                {parentIdStr:9, idStr: 0, title: "机电技术教育"}, {parentIdStr:9, idStr: 1, title: "机械设计制造及其自动化"},{parentIdStr:9, idStr: 2, title: "汽车服务工程"},{parentIdStr:9, idStr: 3, title: "工业设计"},
                {parentIdStr:10, idStr: 0, title: "化学"}, {parentIdStr:10, idStr: 1, title: "应用化学"},{parentIdStr:10, idStr: 2, title: "制药工程"},{parentIdStr:10, idStr: 3, title: "食品科学与工程"},
                {parentIdStr:11, idStr: 0, title: "生物科学"}, {parentIdStr:11, idStr: 2, title: "生物技术"},{parentIdStr:11, idStr: 2, title: "园林"},{parentIdStr:11, idStr: 3, title: "烹饪与营养教育"},{parentIdStr:11, idStr: 4, title: "海洋资源开发技术"},
                {parentIdStr:12, idStr: 0, title: "国际经济与贸易"}, {parentIdStr:12, idStr: 1, title: "财务会计教育"},{parentIdStr:12, idStr: 2, title: "电子商务"},{parentIdStr:12, idStr: 3, title: "市场营销"},{parentIdStr:12, idStr: 4, title: "信息管理与信息系统"},{parentIdStr:12, idStr: 5, title: "工商管理"},
                {parentIdStr:13, idStr: 0, title: "体育教育（师范）"}, {parentIdStr:13, idStr: 1, title: "社会体育指导与管理"},{parentIdStr:13, idStr: 2, title: "运动人体科学"},{parentIdStr:15, idStr: 3, title: "运动康复"},

            ],
            defaultImg: {
                icon: '/static/images/default/defaultIcon.jpg',
                face: [
                        {
                            baseUrl: 'http://judong-sport.oss-cn-shenzhen.aliyuncs.com/static/img/emoticon/default/',
                            quantity:  74,
                            title: '默认',
                            id: 0,
                        },{
                        baseUrl: 'http://judong-sport.oss-cn-shenzhen.aliyuncs.com/static/img/emoticon/default/',
                        quantity:  75,
                        title: '测试',
                        id: 1,
                    },
                ]
            },
        };
    export default {
        defaultConfig
    };
</script>
<style></style>