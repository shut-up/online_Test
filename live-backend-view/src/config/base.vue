<template></template>
<script type="text/javascript">
    const defaultConfig =
        {
            options: [
                {
                    value: 'zhinan',
                    label: '信息学院',
                    children: [{
                        value: 'shejiyuanze',
                        label: '软件工程',
                    }, {
                        value: 'daohang1',
                        label: '网络工程',
                    },
                    {
                        value: 'daohang',
                        label: '软件服务外包',
                    },
                    {
                        value: 'daohang',
                        label: '教育技术',
                    },
                    {
                        value: 'daohang',
                        label: '计算机科学技术',
                    },
                    {
                        value: 'daohang',
                        label: '电子信息工程',
                    }]
                },
                {
                    value: 'zhinan',
                    label: '物理学院',
                    children: [{
                        value: 'shejiyuanze',
                        label: '设计原则',
                    }, {
                        value: 'daohang',
                        label: '导航',
                    }]
                },
                {
                    value: 'zhinan',
                    label: '体育学院',
                    children: [{
                        value: 'shejiyuanze',
                        label: '游泳',
                    }, {
                        value: 'daohang',
                        label: '水上运动',
                    }]
                }
            ],
            gameStatus: [
                {idStr: 0, title: "未开"},
                {idStr: 1, title: "赛中"},
                {idStr: 2, title: "结束"},
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