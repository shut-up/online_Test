<template>
    <section>
        <el-row :gutter="10">
            <el-col :span="12">
                <el-col :span="24" class="noPadding">
                    <el-col :span="4" class="noPadding">
                        <div class="blue" @click="goList">< 直播后台</div>
                    </el-col>
                    <el-col :span="16" class="text-center noPadding">
                        <span v-text="hostTeam"></span>
                        <el-input  size="mini" v-model="score.hostScore" style="width: 60px"></el-input>
                        <el-input  size="mini" v-model="score.guestScore" style="width: 60px"></el-input>
                        <span v-text="guestTeam"></span>
                    </el-col>
                    <el-col :span="4" class="text-right noPadding blue"> <div @click="goSetting">直播间设置 ></div></el-col>
                </el-col>
                <el-col :span="24" class="text-center">
                    <div style="margin-top: 8px">
                        小比分：
                        <el-input  size="mini" v-model="minLevelScore.h" style="width: 40px"></el-input>
                        <el-input  size="mini" v-model="minLevelScore.g" style="width: 40px;margin-left: 8px"></el-input>
                        <el-button size="small" class="disappear" style="margin-left: 20px">更新比分</el-button>
                    </div>
                    <div>
                        大比分：
                        <el-input  size="mini" v-model="topLevelScore.h" style="width: 40px"></el-input>
                        <el-input  size="mini" v-model="topLevelScore.g" style="width: 40px;margin-left: 8px"></el-input>
                        <el-button size="small" type="primary" @click="updateScore" style="margin-left: 20px">更新比分</el-button>
                    </div>
                </el-col>
                <el-col :span="24" class="text-center relative noPadding">
                    <span :class="[score.hideTime == 1?'hideTime':'']" v-text="score.time"></span>
                    &nbsp;&nbsp;&nbsp;
                    <el-button :type="hideButtonType" size="small" class="go-left" @click="hideTime" ref="hideText" v-text="hideText"></el-button>
                    <el-button type="danger" size="small" class="go-right" @click="closeLiveRoom" v-text="closeButtonText"></el-button>
                </el-col>
                <el-col :span="24" class="noPadding margin-vertical-15">
                    <el-col :span="4" class="noPadding">
                        <el-select v-model="currentAnchor">
                            <el-option
                                v-for="item in anchors"
                                :key="item.idStr"
                                :value="item.idStr"
                                :label="item.title"
                            ></el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="20" style="padding: 0px 0px 0px 5px">
                        <div tabindex="-1" @keydown="getKeyDown"
                             @keyup="getKeyUp"
                             ref="editBox"
                             class="editBox"
                             :class="[showEditBox?'show':'hidden']"
                             contentEditable
                             v-html="talk.content.content"></div>
                        <div class="tagBox" :class="[showEditBox?'hidden':'show']" @focus="getKeyUp">
                            <el-tag
                                    v-for="tag in tempTags"
                                    :key="tag.name"
                                    :closable="true"
                                    :type="tag.type"
                                    @close="handleClose(tag)"
                            >
                                {{tag.name}}
                            </el-tag>
                        </div>
                    </el-col>
                </el-col>
                <el-col :span="24" class="noPadding margin-bottom-15">
                    <el-col :offset="4" :span="20" class="noPadding">
                        <el-col :span="19" class="padding: 0px 0px 0px 5px">
                            <el-button icon="fa fa-smile-o" @click="showFace=!showFace">表情</el-button>
                            <el-button icon="fa fa-link" @click="addPictureDialog=true">图片连接</el-button>
                            <el-button icon="fa fa-newspaper-o" @click="addNewsDialog=true">新闻</el-button>
                            <el-button icon="fa fa-file-text-o" @click="addSeminarDialog=true">专题</el-button>
                            <div class="face-container">
                                <face :ifShow="showFace" @getFace="getFace"></face>
                            </div>
                        </el-col>
                        <el-col :span="5" class="text-right noPadding">
                            <el-button icon="fa fa-send-o" type="success" @click="sendTalk">发送</el-button>
                        </el-col>
                    </el-col>
                </el-col>

                <!-- anchor talk table --->
                <el-col :span="24" class="noPadding">
                    <el-table :data="talks" stripe @cell-dblclick="getCel" border>
                        <el-table-column label="主播" prop="anchor.title" width="80"></el-table-column>
                        <el-table-column label="内容" prop="content" min-width="200">
                            <template scope="scope">
                                <div v-if="scope.row.content.type == 2 || scope.row.content.type == 3">
                                    <el-col :span="8" style="line-height: 70px;"><img :src="scope.row.content.imageUrl" style="vertical-align: middle;width: 120px;height: 60px"/>
                                    </el-col>
                                    <el-col :span="16">
                                        <div><strong>标题</strong>:&nbsp;{{scope.row.content.content}}</div>
                                        <div v-if="scope.row.content.targetUrl">
                                            <strong>跳转地址</strong>:&nbsp;
                                            <a :href="scope.row.content.targetUrl">
                                                {{scope.row.content.targetUrl}}
                                            </a>
                                        </div>
                                    </el-col>
                                </div>
                                <div v-else-if="scope.row.content.type == 0 || scope.row.content.type == 1">
                                    <div v-html="scope.row.content.content"></div>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column label="发言时间" prop="createTime" width="175" :formatter="formatTime"></el-table-column>
                    </el-table>
                    <el-pagination layout="total,prev, pager, next" @current-change="handleTalkPage" :page-size="talkPageSize" :total="talkTotal" style="float:right;">
                    </el-pagination>
                </el-col>
            </el-col>
            <!-- comment -->
            <el-col :span="7">评论</el-col>
            <el-col :span="5">额外操作区域</el-col>
        </el-row>

        <!-- add news -->
        <el-dialog title="发送新闻" :visible.sync="addNewsDialog" size="mini">
            <el-form :model="talk.content" label-width="60px">
                <el-form-item label="新闻Id">
                    <el-input placeholder="填入新闻Id" v-model="talk.content.newsId"></el-input>
                </el-form-item>
            </el-form>
            <el-col :span="24" class="margin-vertical-15 text-right">
                <el-button @click="addNewsDialog=false">取消</el-button>
                <el-button @click="addNews" type="primary">保存</el-button>
            </el-col>
        </el-dialog>
        <!-- add Seminar -->
        <el-dialog title="发送主题" :visible.sync="addSeminarDialog" size="mini">
            <el-form :model="talk.content" label-width="60px">
                <el-form-item label="主题Id">
                    <el-input placeholder="填入主题Id" v-model="talk.content.seminarId"></el-input>
                </el-form-item>
            </el-form>
            <el-col :span="24" class="margin-vertical-15 text-right">
                <el-button @click="addSeminarDialog=false">取消</el-button>
                <el-button @click="addSeminar" type="primary">保存</el-button>
            </el-col>
        </el-dialog>
        <!-- add picture -->
        <el-dialog title="发送图片" :visible.sync="addPictureDialog" size="tiny">
            <el-form :model="talk.content" label-width="80px">
                <el-form-item label="图片上传">
                    <el-upload
                            class="upload-demo"
                            :action="uploadUrl"
                            :on-success="uploadSuccess"
                            :before-upload="beforeUpload"
                            :show-file-list="false">
                        <el-button size="small" type="primary">点击上传</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                    </el-upload>
                </el-form-item>
                <el-form-item label="图片地址">
                    <el-input placeholder="图片地址" v-model="talk.content.imgUrl"></el-input>
                </el-form-item>
                <el-form-item label="跳转地址">
                    <el-input placeholder="跳转地址" v-model="talk.content.targetUrl"></el-input>
                </el-form-item>
            </el-form>
            <el-col :span="24" class="margin-vertical-15 text-right">
                <el-button @click="addPictureDialog=false">取消</el-button>
                <el-button @click="addPic" type="primary">保存</el-button>
            </el-col>
        </el-dialog>

        <!-- 修改发言 -->
        <el-dialog title="发言内容"  :visible.sync="editContentDialog" size="tiny">
            <el-form :model="editContent">
                <div tabindex="-1" ref="editContent" class="editBox" contentEditable v-html="editContent.content"></div>
                <el-col :span="24" class="margin-vertical-15 text-right">
                    <el-button @click="editContentDialog=false">取消</el-button>
                    <el-button @click="updateContent" type="primary">保存</el-button>
                </el-col>
            </el-form>
        </el-dialog>
        <!-- 修改作者 -->
        <el-dialog title="修改主播"   :visible.sync="editAnchorDialog" size="mini">
            <el-form :model="editAnchor">
                <el-form-item prop="anchor">
                    <el-select v-model="editAnchor.anchor">
                        <el-option
                            v-for="item in anchors"
                            :key = "item.idStr"
                            :value = "item.idStr"
                            :label = "item.title"
                        ></el-option>
                    </el-select>
                    <el-col :span="24" class="margin-vertical-15 text-right">
                        <el-button @click="editAnchorDialog=false">取消</el-button>
                        <el-button @click="updateAnchor" type="primary">保存</el-button>
                    </el-col>
                </el-form-item>
            </el-form>
        </el-dialog>
    </section>
</template>
<script>
    import face from '../../components/face/face.vue'
    import Util from '../../common/js/util';

    import {scoreGetTalkList, scoreGetTalkListSize, roomUpdateAnchor, roomRegainGame,scoreDeleteContent,scoreGetRoom, scoreAddContent, scoreHideTime, scoreUpdateContent, scoreUpateScore, roomUpdateEndTime} from '../../api/api';
    export default {
        data(){
            var g = this.GLOBAL.defaultConfig;
            return {
                uploadUrl: g.baseUrl.uploadUrl,
                addNewsDialog: false,
                addSeminarDialog: false,
                addPictureDialog: false,
                showFace: false,
                showEditBox: true,
                editContentDialog: false,
                editAnchorDialog: false,

                talkTotal: 0,
                currentTalkPage: 1,
                talkPageSize: 50,

                liveStatus: true,

                score: {
                    hostScore:0,
                    guestScore:0
                },
                topLevelScore: {
                    h: "",
                    g: ""
                },
                minLevelScore: {
                    h: "",
                    g: ""
                },

                tempTags: [
                ],
                g: g,
                room: {
                    hostTeam: {
                        title: '湖人'
                    },
                    guestTeam: {
                        title: '火箭'
                    }
                },
                gameId: "",
                gameIdStr: "",
                anchors: [],
                currentAnchor: "",
                talk: {
                    content: {
                        type: "",
                        content: "",
                        contentId: "",
                        tempContent: "",
                        imgUrl: "",
                        targetUrl: "",
                        newsId: "",
                        seminarId: "",
                    }
                },
                roomId: "",
                tempContent:[],
                hideText: "隐藏时间",
                hideButtonType: "warning",

                talks:[
                ],
                editContent: {
                    id: "",
                    content: {

                    }
                },
                currentEditContent:{},
                hostTeam: "",
                guestTeam: "",

                closeButtonText: "结束直播",

                editAnchor: {
                    id: "",
                    anchor: "",
                },
            }
        },
        methods: {
            /** formatter **/
            formatTime: function (row, column) {
                let date = new Date(row[column.property]);
                return Util.formatDate.format(date,'y-M-d h:m:s');
            },


            /** get face **/
            getFace: function (imgUrl) {
                var content = '<img src="'+imgUrl+'"/>';
                this.insertContent(content);
                this.talk.content.type = 1;
                this.showFace = false;
            },

            /** dbclick **/
            getCel: function (row, column, cell, event) {
                if (column.property == "anchor.title"){
                    this.editAnchorDialog = true;
                    this.currentEditContent = row;
                    this.editAnchor.id = row.idStr;
                    this.editAnchor.anchor = row.anchor.idStr;
                }
                if (column.property == "content"){
                    if (row.content.type != 0){
                        this.$message({
                            message: "这种类型的内容不能修改，请删除后再发",
                            type: "error"
                        })
                        return;
                    }
                    this.currentEditContent = row;
                    this.editContent.id = row.idStr;
                    this.editContentDialog = true;
                    this.editContent.content= row.content.content;
                    return;
                }
                if (column.property == "createTime") {
                    this.$confirm('确认删除该记录吗?', '提示', {
                        type: 'warning'
                    }).then(()=>{
                        let id = row.idStr;
                        scoreDeleteContent("id="+id).then((res)=>{
                            if (this.checkResult(res, true, "删除成功")){
                                this.talks = this.talks.filter((r)=>r.idStr != id)
                                this.talkTotal--;
                            }
                        });
                    }).catch();
                    return;
                }

            },
            //updateContent
            updateContent: function () {
                let para = {
                    id: this.editContent.id,
                    requestDto:
                        {
                            content: this.$refs.editContent.innerHTML,
                            type: 0,
                        }
                };
                scoreUpdateContent(para).then((res)=>{
                    if (this.checkResult(res, true, "更新成功")){
                        this.currentEditContent.content.content = para.requestDto.content;
                        this.editContentDialog =false
                    }
                });
            },
            updateAnchor: function () {
                let para = {
                    id: this.editAnchor.id,
                    userId: this.editAnchor.anchor
                };
                roomUpdateAnchor(para).then((res)=>{
                    if (this.checkResult(res, true, "更新成功")){
                        console.log(para);
                        var temp = this.anchors.filter(res=>res.idStr == para.userId)
                        this.currentEditContent.anchor = temp[0];
                        this.editAnchorDialog =false;
                    }
                });
            },



            closeLiveRoom: function () {
                if (this.liveStatus){
                    this.$confirm('确认关闭直播间吗?', '警告', {
                        type: "warning"
                    }).then(()=>{
                        let para = {
                            roomId: this.room.idStr,
                            gameStatus: 2,
                        };
                        roomUpdateEndTime(para).then((res)=>{
                            if (this.checkResult(res, true, '直播间关闭成功')) {
                                this.room.gameStatus = 2;
                                this.setCloseButtonText();
                            }
                        })

                    })
                }else {
                    this.$confirm('确认开启直播间吗?', '警告', {
                        type: "warning"
                    }).then(()=>{
                        let para = {
                            roomId: this.room.idStr,
                            gameStatus: 1,
                        };
                        roomRegainGame(para).then((res)=>{
                            if (this.checkResult(res, true, '直播间恢复成功')) {
                                this.room.gameStatus = 1;
                                this.setCloseButtonText();
                            }
                        })

                    })
                }
            },

            //updateScore
            updateScore: function () {
                var hostBigScore = new Array(),
                    guestBigScore = new Array();
                if (this.topLevelScore.h != "" || this.topLevelScore.g != ""){
                    hostBigScore.push(this.topLevelScore.h == "" ? 0 : this.topLevelScore.h);
                    guestBigScore.push(this.topLevelScore.g == "" ? 0 : this.topLevelScore.g);
                }
                if (this.minLevelScore.h != "" || this.minLevelScore.g != ""){
                    hostBigScore.push(this.minLevelScore.h == "" ? 0 : this.minLevelScore.h);
                    guestBigScore.push(this.minLevelScore.g == "" ? 0 : this.minLevelScore.g);
                }

                let para = {
                    gameId: this.room.gameIdStr,
                    id: this.score.idStr,
                    hostScore: this.score.hostScore,
                    guestScore: this.score.guestScore,
                    hostBigScore: hostBigScore.join("-"),
                    guestBigScore: guestBigScore.join("-")
                }
                scoreUpateScore(para).then((res)=>{
                    if (this.checkResult(res, true, "更新比分成功")){
                        this.score = res.data;
                        this.setBigScore();
                    }
                });

            },
            hideTime: function () {
                this.$confirm('确定要隐藏时间吗?', '提示', {
                    type: 'warning'
                }).then(()=>{
                    var para = {
                        id: this.score.idStr,
                        hideTime: this.score.hideTime == 0 ? 1 : 0,
                    }
                    scoreHideTime(para).then((res)=>{
                        if (this.checkResult(res, true, '操作成功')){
                            this.score.hideTime = para.hideTime;
                            this.setHideButton();
                        }
                    });
                }).catch();
            },
            insertContent: function (n) {
                //引用微信网页版本代码
                this.$refs.editBox.focus();
                var a,nv;
                if (window.getSelection().baseNode != this.$refs.editBox
                    &&
                    window.getSelection().baseNode.parentElement != this.$refs.editBox
                ){
                    this.$message({
                        message:"请选中编辑区域",
                        type: "error",
                    });
                    return;
                }
                (a = window.getSelection(), nv = a.getRangeAt(0)),
                    nv.deleteContents();
                var i;
                if (nv.createContextualFragment) i = nv.createContextualFragment(n);
                else {
                    var o = document.createElement("div");
                    o.innerHTML = n,
                        i = document.createDocumentFragment();
                    for (var r, c; r = o.firstChild;) c = i.appendChild(r)
                }
                var s = i.lastChild;
                nv.insertNode(i),
                    nv.setStartAfter(s),
                    a.removeAllRanges(),
                    a.addRange(nv);
            },

            getKeyUp: function (ev) {
                if (ev.keyCode == 13 && ev.ctrlKey){
                    ev.preventDefault();
                    var n = "<br>";
                    if (window.getSelection) {
                        var i = window.getSelection().focusNode.nextSibling;
                        do
                            if (!i || i.nodeValue || "BR" == i.tagName) break;
                        while (i = i.nextSibling);
                        i || (n += n)
                    }
                    this.insertContent(n);
                    return;
                }
                if (ev.keyCode == 13){
                   this.sendTalk();
                }
            },
            getKeyDown: function (ev) {
                if (ev.keyCode == 13){
                    ev.preventDefault();
                }
            },
            addPic: function () {
                var img = '<a href="'+(this.talk.content.targetUrl?this.talk.content.targetUrl:'javascript:void(0)')+'"><img class="little-img" src="'+this.talk.content.imgUrl+'"/></a>';
                this.insertContent(img)
                this.$message({
                    message: "保存成功",
                    type: "success"
                })
                this.talk.content.type = 1;
                this.addPictureDialog = false;
            },
            addTag: function (title) {
                this.tempTags = [
                    {name: title,'type':'success'}
                ];
                this.$refs.editBox.innerHTML = "";
                this.showEditBox = false;
            },
            addNews: function () {
                this.$message({
                    message: "保存成功",
                    type: "success"
                });
                this.addTag(this.talk.content.newsId);
                this.talk.content.contentId = this.talk.content.newsId;
                this.talk.content.type = 2;
                this.addNewsDialog = false;
            },
            addSeminar: function () {
                this.$message({
                    message: "保存成功",
                    type: "success"
                })
                this.addTag(this.talk.content.seminarId);
                this.talk.content.contentId = this.talk.content.seminarId;
                this.talk.content.type = 3;
                this.addSeminarDialog = false;
            },

            /** send message **/
            sendTalk: function () {
                if (this.liveStatus == false){
                    this.$message({
                        type: "error",
                        message: "请先恢复直播",
                    });
                    return false;
                }
                if (_.isUndefined(this.currentAnchor) || _.isEmpty(this.currentAnchor)){
                    this.$message({
                        type: "error",
                        message: "请选择发言的主播"
                    })
                    return false;
                }
                if (_.isUndefined(this.room.idStr) || _.isEmpty(this.room.idStr)){
                    this.$message({
                        type: "error",
                        message: "直播间异常,缺少直播间id,请重新进入直播间"
                    })
                    return false;
                }
                if ((this.$refs.editBox.innerHTML == "" || _.isEmpty(_.trim(this.$refs.editBox.innerHTML)))&& _.isEmpty(this.talk.content.contentId)){
                    this.$message({
                        type: "error",
                        message: "内容是空的呀,发不出空消息!!!"
                    })
                    return false;
                }

                let para = {
                    userId: this.currentAnchor,
                    roomId: this.room.idStr,
                    requestDto: {
                        "type": this.talk.content.type?this.talk.content.type:0,
                        "imageUrl": this.talk.content.imgUrl,
                        "targetUrl": this.talk.content.targetUrl,
                        "content": this.$refs.editBox.innerHTML,
                        "contenId": this.talk.content.contentId
                    }
                };
                this.$refs.editBox.innerHTML　= this.$refs.editBox.innerHTML.replace(/　/gi," ");
                this.$refs.editBox.innerHTML = this.toCDB(this.$refs.editBox.innerHTML);
                var temp = _.trim(this.$refs.editBox.innerHTML).split(" ");
                var tSize = temp.length;
                if (tSize > 2) {
                    var guestScore = parseInt(temp[tSize-1]);
                    var hostScore = parseInt(temp[tSize-2]);
                    if ( guestScore || hostScore) {
                        this.score.hostScore = hostScore;
                        this.score.guestScore = guestScore;
                        this.updateScore();
                    }
                }
                scoreAddContent(para).then((res)=>{
                    if (this.checkResult(res, true)) {
                        this.talks.unshift(res.data);
                        this.talkTotal++;
                        this.talk = {
                            content: {
                                type: "",
                                content: "",
                                contentId: "",
                                imgUrl: "",
                                targetUrl: "",
                                newsId: "",
                                seminarId: "",
                            }
                        };
                        this.$refs.editBox.innerHTML = "";
                    }
                });
            },
            toCDB: function (str) {
                var tmp = "";
                for(var i=0;i<str.length;i++)
                {
                    if(str.charCodeAt(i)>65248&&str.charCodeAt(i)<65375)
                    {
                        tmp += String.fromCharCode(str.charCodeAt(i)-65248);
                    }
                    else
                    {
                        tmp += String.fromCharCode(str.charCodeAt(i));
                    }
                }
                return tmp
            },
            setHideButton: function () {
                if (this.score.hideTime == 1){
                    this.hideText = "显示时间";
                    this.hideButtonType = "success";
                }else {
                    this.hideText = "隐藏时间";
                    this.hideButtonType = "warning";
                }
            },

            setCloseButtonText: function () {
                if (this.room.gameStatus == 2){
                    this.liveStatus = false;
                    this.closeButtonText = "恢复直播";
                }else {
                    this.liveStatus = true;
                    this.closeButtonText = "结束直播";
                }
            },
            setBigScore: function () {
                var data = this.score;
                if (!_.isEmpty(data)){
                    if (!_.isEmpty(data.hostBigScore) || !_.isEmpty(data.hostBigScore)){
                        var hostScores = data.hostBigScore.split("-");
                        var guestScores = data.guestBigScore.split("-");
                        if (hostScores.length > 0){
                            this.topLevelScore.h = hostScores[0];
                            this.topLevelScore.g = guestScores[0];
                            if (hostScores.length > 1){
                                this.minLevelScore.h = hostScores[1];
                                this.minLevelScore.g = guestScores[1];
                            }
                        }
                    }
                }
            },

            setTeamTitle: function () {
                var left = new Array()
                var right = new Array()
                for (let i=0; i<this.room.guestTeam.length;i++) {
                    left.push(this.room.hostTeam[i].title);
                    right.push(this.room.guestTeam[i].title);
                }
                this.hostTeam = left.join("&");
                this.guestTeam = right.join("&");
            },

            /** upload img **/
            uploadSuccess: function (res) {
                this.talk.content.imgUrl = res.data;
            },
            beforeUpload: function (file) {
                const isLt2M = file.size / 1024 / 1024 < 2;
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isLt2M;
            },
            
            /** close tag **/
            handleClose: function (tag) {
                this.tempTags.splice(this.tempTags.indexOf(tag), 1);
            },

            /** Jump **/
            goList: function () {
                this.$router.push({ path: '/live'})
            },
            goSetting: function () {
                this.$router.push({ path: '/editRoom'})
            },

            /** init room **/
            initRoom: function () {
                let para = {
                    id: this.roomId
                }
                scoreGetRoom(para).then((res)=>{
                    if (this.checkResult(res)){
                        this.anchors = res.data.anchor;
                        this.room = res.data.room;
                        //this.talks = res.data.anchorTalk;
                        if (!_.isEmpty(res.data.score)){
                            this.score = res.data.score;
                        }
                        this.setTeamTitle();
                        this.setBigScore();
                        this.setHideButton();
                        this.setCloseButtonText();
                    }
                })
                this.reFreshenTalkTable();
                this.getTalkListSize();
            },

            reFreshenTalkTable: function () {
                let para = {
                    nextId: this.roomId,
                    page: this.currentTalkPage,
                    pageSize: this.talkPageSize
                }
                scoreGetTalkList(para).then((res)=>{
                    if (this.checkResult(res)){
                        this.talks = res.data;
                    }
                })
            },

            getTalkListSize: function () {
                let para = {
                    id: this.roomId
                };
                scoreGetTalkListSize(para).then((res)=>{
                    if (this.checkResult(res)){
                        this.talkTotal = res.data;
                    }
                })
            },

            handleTalkPage: function (val) {
              this.currentTalkPage = val;
              this.reFreshenTalkTable();
            },

            /** check result **/
            checkResult: function (result, noSuccessMessage, message) {
                if (result.code == "1"){
                    if (noSuccessMessage){
                        this.$message({
                            message: message?message:"发送成功",
                            type: "success",
                        })
                    }
                    return true;
                }else {
                    this.$message({
                        message: "操作失败,错误信息:"+result.desc,
                        type: "error",
                    })
                    return false;
                }
            },
        },
        watch:{
            $router(){
                this.roomId = this.$route.query.roomId;
            },
            tempTags: function (current,old) {
                if (_.isEmpty(current)) {
                    this.showEditBox = true;
                }else{
                    this.showEditBox = false;
                }
            },
            roomId: function (current, old) {
                if (!_.isUndefined(current) && !_.isEmpty(current)){
                    this.initRoom();
                }else {
                    this.roomId = "";
                }
            }
        },
        mounted(){
            this.roomId = this.$route.query.roomId;
            if (!_.isUndefined(this.roomId) && !_.isEmpty(this.roomId)){
                this.initRoom();
            }else {
                this.$router.push({ path: "/live"});
            }
        },
        components: {
            face
        }
    }
</script>
<style>
    .text-center .el-input__inner {
        text-align: center;
    }
    .editBox {
        font-size: 16px;
        line-height: 24px;
        height: 72px;
        overflow: auto;
        border: 1px solid #bfcbd9;
        border-radius: 5px;
        transition:border-color .2s cubic-bezier(.645,.045,.355,1);
        outline:none;
        padding: 5px 7px;
        user-modify: read-write-plaintext-only;
        -webkit-user-modify: read-write-plaintext-only

    }
    .editBox:focus {
        outline:none;
        border: 1px solid #20a0ff;
        border-radius: 5px;
    }
    .tagBox {
        font-size: 16px;
        line-height: 24px;
        height: 72px;
        overflow: auto;
        border: 1px solid #bfcbd9;
        border-radius: 5px;
        transition:border-color .2s cubic-bezier(.645,.045,.355,1);
        outline:none;
        padding: 5px 7px;
        display: none;
    }
    .face-container {
        position: absolute;
        z-index: 1000;
    }
    .text-center {
        text-align: center;
    }
    .text-right {
        text-align: right;
    }
    .relative {
        position: relative;
    }
    .go-right {
        position: absolute;
        right:0;
        top:0;
    }
    .go-left {
        position: absolute;
        left:0;
        top:0;
    }
    .noPadding {
        padding: 0 !important;
    }
    .blue {
        color: blue;
        cursor: pointer;
    }
    .margin-vertical-15 {
        margin: 15px 0px;
    }
    .margin-bottom-15 {
        margin-bottom: 15px;
    }
    .show {
        display: block;
    }
    .hidden {
        display: none;
    }
    .disappear {
        visibility: hidden;
    }
    .little-img {
        width: 80px;
        height:40px;
    }
    .hideTime {
        text-decoration:line-through;
        color: gray;
    }

</style>