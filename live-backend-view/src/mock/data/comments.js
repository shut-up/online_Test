// // // 获取所有的评论内容
// // var self = this;
// // $.ajax({
// //     url: 'http://localhost:8015/comment/getAllComment',
// //     complete: function (data) {
// //         self.$data.comments = data
// //         console.log(self.$data.comments);
// //     }
// // })
// //
// import VueResource from 'vue-resource'
//
// const Comment = [];
//
// new Vue({
//     data:{
//       //声明空数组，进行数据接收，最后传递到前端页面
//       //   Comment: [],
//     },
// //向data数组里添加数据
//     mounted:function(){
//         this.getData();
//     },
//     methods: {
//         getData:function () {
//             var self = this;
//             this.$http.get("http://127.0.0.1:8015/comment/getAllComment").then(function (res) {
//
//                 console.log(res);
//
//                 //var lens = res.body.lists.length;
//                 //console.log(lens);
//                 //获取了当前数组的长度，为3
//                 for(var i= 0,len=res.body.data.length;i<len;i++){
//                     //已经获取json数组中的数据，接下来如何传递到前端页面中
//                     //获取全部数据
//                     var selData = res.body.data[i];
//                     //console.log(selData);
//
//                     //获取数组中的部分数据
//                     var part = res.body.data[i].name;
//                     console.log(part);
//                     //将获取的数据push到空的数组中itenList，
//                     self.Comment.push(selData);
//
//                 }
//             })
//         }
//     }
// });
// // for(let i = 0; i < comments.length; i++){
// //   comment.push(comments.)
// // }
//
//
//
// // const Users = [];
// //
// // for (let i = 0; i < 86; i++) {
// //   Users.push(Mock.mock({
// //     id: Mock.Random.guid(),
// //     name: Mock.Random.cname(),
// //     addr: Mock.mock('@county(true)'),
// //     'age|18-60': 1,
// //     birth: Mock.Random.date(),
// //     sex: Mock.Random.integer(0, 1)
// //   }));
// // }
//
// export { Comment };
