<template></template>
<script type="text/javascript">
    const defaultConfig =
        {
            gameStatus: [
                {idStr: 0, title: "未开"},
                {idStr: 1, title: "赛中"},
                {idStr: 2, title: "结束"},
            ],
            roomStatus: [
                {idStr: 0, title: "显示"},
                {idStr: 1, title: "隐藏"},
            ],
            operations: [
                {idStr: 0, title: "显示"},
                {idStr: 1, title: "隐藏"},
                {idStr: 2, title: "推荐"},
                {idStr: 3, title: "删除"},
            ],
            teamType: [
                {idStr: 0, title: "国家"},
                {idStr: 1, title: "队伍"},
                {idStr: 2, title: "个人"},
            ],
            teamTypeKv: {
                0: "国家",
                1: "队伍",
                2: "个人",
            },
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
            baseUrl: {
                "uploadUrl": "http://live-admin-api.letsport.com.cn/imgUpload/",
                //"uploadUrl": "http://localhost:8090/imgUpload/",
            },
            commonFunc: {
//                jump: function (where) {
//                    _this.$router.push({ path: where });
//                }
            },
        };
    export default {
        defaultConfig
    };
</script>
<style></style>