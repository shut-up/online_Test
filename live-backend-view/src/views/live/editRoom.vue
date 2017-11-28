<template>
    <section>
        <el-row :gutter="10">
            <el-col :span="9">
                <el-form :model="room" label-width="80px" status-icon="true">
                    <el-col :span="12">
                        <el-form-item label="直播ID">
                            <div v-html="room.idStr"></div>
                        </el-form-item>
                        <el-form-item label="名称" prop="title">
                            <el-input placeholder="输入名称" v-model="room.title"></el-input>
                        </el-form-item>
                        <el-form-item label="房间状态">
                            <el-select v-model="room.status">
                                <el-option
                                    v-for="item in status"
                                    :key="item.idStr"
                                    :value="item.idStr"
                                    :label="item.title"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="房间类型">
                            <el-select v-model="room.catalog.idStr" @change="changeTeamListByRoomType">
                                <el-option
                                        v-for="item in catalogs"
                                        :key="item.idStr"
                                        :value="item.idStr"
                                        :label="item.title"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item>
                            <el-input class="disappear"></el-input>
                        </el-form-item>
                        <el-form-item label="参赛类型">
                            <el-select v-model="teamType" @change="changeTeamListByType">
                                <el-option
                                    v-for="item in teamTypes"
                                    :key="item.idStr"
                                    :value="item.idStr"
                                    :label="item.title"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="比赛状态">
                            <el-select v-model="room.gameStatus">
                                <el-option
                                    v-for="item in gameStatus"
                                    :key="item.idStr"
                                    :value="item.idStr"
                                    :label="item.title"
                                ></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" class="">
                        <el-col :span="10" class="noPadding">
                            <el-form-item label="主队">
                                <el-select
                                        v-model="hostTeam[0].idStr"
                                        filterable
                                        remote
                                        placeholder="请输入关键词"
                                        :remote-method="remoteTeamMethod"
                                        :loading="teamSearchLoading"
                                        @change="resetTeam"
                                        clearable
                                >
                                    <el-option
                                            v-for="item in tempTeamList"
                                            :key="item.idStr"
                                            :label="item.title"
                                            :value="item.idStr">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item>
                                <img  v-if="hostTeam[0].portrait" :src="hostTeam[0].portrait" class="teamIcon"/>
                                <img v-else="true" class="teamIcon" :src="defaultIcon" />
                            </el-form-item>
                            <el-form-item label="客队">
                                <el-select
                                        v-model="guestTeam[0].idStr"
                                        filterable
                                        remote
                                        placeholder="请输入关键词"
                                        :remote-method="remoteTeamMethod"
                                        :loading="teamSearchLoading"
                                        @change="resetTeam"
                                        clearable
                                >
                                    <el-option
                                            v-for="item in tempTeamList"
                                            :key="item.idStr"
                                            :label="item.title"
                                            :value="item.idStr">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item>
                                <img  v-if="guestTeam[0].portrait" :src="guestTeam[0].portrait" class="teamIcon"/>
                                <img v-else="true" class="teamIcon" :src="defaultIcon" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <!--<el-button icon="plus"></el-button>-->
                            <el-select
                                        v-model="hostTeam[1].idStr"
                                        filterable
                                        remote
                                        placeholder="请输入关键词"
                                        :remote-method="remoteTeamMethod"
                                        :loading="teamSearchLoading"
                                        @change="resetTeam"
                                        clearable
                            >
                                    <el-option
                                            v-for="item in tempTeamList"
                                            :key="item.idStr"
                                            :label="item.title"
                                            :value="item.idStr">
                                    </el-option>
                            </el-select>
                            <div class="margin-vertical-22">
                                <img  v-if="hostTeam[1].portrait" :src="hostTeam[1].portrait" class="teamIcon"/>
                                <img v-else="true" class="teamIcon" :src="defaultIcon" />
                            </div>
                            <el-select
                                    v-model="guestTeam[1].idStr"
                                    filterable
                                    remote
                                    placeholder="请输入关键词"
                                    :remote-method="remoteTeamMethod"
                                    :loading="teamSearchLoading"
                                    style="margin-top: 10px"
                                    @change="resetTeam"
                                    clearable
                            >
                                <el-option
                                        v-for="item in tempTeamList"
                                        :key="item.idStr"
                                        :label="item.title"
                                        :value="item.idStr">
                                </el-option>
                            </el-select>
                            <div class="margin-vertical-22">
                                <img  v-if="guestTeam[1].portrait" :src="hostTeam[1].portrait" class="teamIcon"/>
                                <img v-else="true" class="teamIcon" :src="defaultIcon" />
                            </div>
                        </el-col>
                        <el-col :span="2">
                            <el-button type="mini" icon="minus"></el-button>
                        </el-col>
                        <el-col :span="4" class="text-right noPadding">
                            <el-button size="small" type="success">参赛管理 >></el-button>
                        </el-col>
                    </el-col>
                    <el-col :span="24">
                        <el-col :span="10" class="noPadding">
                            <el-form-item label="图文主播">
                                <el-select
                                        v-model="room.textAnchor"
                                        multiple
                                        filterable
                                        remote
                                        placeholder="请输入关键词"
                                        :remote-method="remoteAnchorMethod"
                                        :loading="anchorSearchLoading">
                                    <el-option
                                            v-for="item in tempAnchors"
                                            :key="item.idStr"
                                            :label="item.title"
                                            :value="item.idStr">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="10" class="noPadding">
                            <el-form-item label="音频主播">
                                <el-select
                                        v-model="room.audioAnchor"
                                        multiple
                                        filterable
                                        remote
                                        placeholder="请输入关键词"
                                        :remote-method="remoteAnchorMethod"
                                        :loading="anchorSearchLoading">
                                    <el-option
                                            v-for="item in tempAnchors"
                                            :key="item.idStr"
                                            :label="item.title"
                                            :value="item.idStr">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="4" class="text-right noPadding">
                            <el-button size="small" type="success">主播管理 >></el-button>
                        </el-col>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="开播时间">
                            <el-date-picker
                                    v-model="room.beginTime"
                                    type="datetime"
                                    placeholder="选择日期时间"
                                    align="right"
                                    >
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" class="noPadding">
                        <el-col :span="12">
                            <el-form-item label="前瞻">
                                <el-input v-model="room.prospectId"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="战报">
                                <el-input v-model="room.battlefieldId"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="附注">
                            <el-input v-model="room.handWriting"></el-input>
                        </el-form-item>
                        <el-form-item label="视频">
                            <el-button disabled>添加视频源</el-button>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24" class="media-source">
                        <el-col :span="6" style="width: 80px">
                            <el-input
                                v-for="item in videos"
                                :value="item.title"
                                :key="item.title"
                                v-model="item.title"
                            ></el-input>
                        </el-col>
                        <el-col :span="18">
                            <el-input
                                    v-for="item in videos"
                                    :value="item.url"
                                    :key="item.url"
                                    v-model="item.url"
                            >
                            </el-input>
                        </el-col>
                    </el-col>
                <el-col :span="24" class="text-right">
                    <el-button @click="resetForm">重置</el-button>
                    <el-button @click="saveRoom" type="primary">保存</el-button>
                </el-col>
                </el-form>
            </el-col>
            <el-col :span="15">道具</el-col>
        </el-row>
    </section>
</template>
<script>
    import {roomUpate,roomGetEditRoom,roomAdd,roomGetByRoomId} from '../../api/api';
    export default {
        data(){
            var g = this.GLOBAL.defaultConfig;
            return {
                videos: [
                    {title: "视频源1", url: ""},
                    {title: "视频源2", url: ""},
                    {title: "腾讯", url: ""},
                    {title: "虎扑", url: ""},
                ],
                room: {
                    handWriting:'',
                    idStr: '',
                    title: '',
                    status: 0,
                    catalog: {
                        idStr: ''
                    },
                    hostTeam: [
                        {idStr: "", title: ""},
                        {idStr: "", title: ""},
                    ],
                    guestTeam: [
                        {idStr: "", title: ""},
                        {idStr: "", title: ""},
                    ],
                    beginTime: '',
                    textAnchor: [],
                    audioAnchor: [],
                    gameStatus: 0,
                    prospectId: "",
                    battlefieldId: "",
                },

                hostTeam: [
                    {idStr: "", title: ""},
                    {idStr: "", title: ""},
                ],
                guestTeam: [
                    {idStr: "", title: ""},
                    {idStr: "", title: ""},
                ],

                teamType: 0,

                teamList: [],
                tempTeamList: [],

                teamTypes: g.teamType,

                defaultIcon: g.defaultImg.icon,
                status: g.roomStatus,
                teams: [],
                catalogs: [],
                gameStatus: g.gameStatus,
                roomId:"",

                teamSearchLoading: false,
                tempTeams: [],
                tempAnchors: [],

                selectedTeams: [],

                teamListMap: {},

                teamSearchLoading: false,

                anchors: [],
                tempAnchors: [],
                anchorSearchLoading: false,

            };
        },
        methods: {
            remoteAnchorMethod: function (query) {
                if (query !== '') {
                    this.anchorSearchLoading = true;
                    setTimeout(() => {
                        this.anchorSearchLoading = false;
                        this.tempTeamList = this.anchors.filter(item => {
                            return item.title.toLowerCase()
                                .indexOf(query.toLowerCase()) > -1;
                        });
                    }, 200);
                } else {
                    this.tempAnchors = this.anchors;
                }
            },

            remoteTeamMethod: function (query) {
                if (query !== '') {
                    this.teamSearchLoading = true;
                    setTimeout(() => {
                        this.teamSearchLoading = false;
                        this.tempTeamList = this.teamList.filter(item => {
                            return item.title.toLowerCase()
                                .indexOf(query.toLowerCase()) > -1;
                        });
                    }, 200);
                } else {
                    this.tempTeamList = this.teamList;
                }
            },

            resetTeam: function () {
                setTimeout(()=>{
                    this.tempTeamList = this.teamList;
                },250)
                console.log(this.tempTeamList)
            },
            changeTeamListByType: function (val) {
                if (val == 0) {
                    this.teamList = this.teamListMap[val];
                }else {
                    this.teamList = this.teamListMap[val+ "-" +this.room.catalog.idStr];
                }
                this.tempTeamList =  this.teamList;
            },

            changeTeamListByRoomType: function (val) {
                if (this.teamType == 0) {
                    this.teamList = this.teamListMap[this.teamType];
                }else {
                    this.teamList = this.teamListMap[this.teamType + "-" +val];
                }
                this.tempTeamList =  this.teamList;
            },

            saveRoom: function () {
                let para = _.cloneDeep(this.room);
                var params = {};
                params.room = {
                    "title": para.title,
                    "status": para.status,
                    "gameStatus": para.gameStatus,
                    "beginTime": _.isNumber(para.beginTime) ? para.beginTime : ((new Date(para.beginTime)).getTime()),
                    "game_id": para.gameId,
                    "prospectId": para.prospectId,
                    "battlefieldId": para.battlefieldId,
                    "handWriting": para.handWriting,
                    "catalog": {
                        "id": para.catalog.idStr
                    }
                }
                params.videos = this.videos;
                params.hostTeam = new Array();
                params.guestTeam = new Array();
                for (var i=0; i<this.hostTeam.length; i++){
                    if (_.isEmpty(this.hostTeam[i].idStr)) {
                        continue;
                    }
                    params.hostTeam[i] = this.hostTeam[i].idStr;
                    params.guestTeam[i] = this.guestTeam[i].idStr;
                }
                params.textAnchor = para.textAnchor;
                params.anchorList = para.audioAnchor;
                if (_.isEmpty(this.room.idStr)){
                    roomAdd(params).then((res)=>{
                        if (this.checkResult(res, true, '添加成功')){
                            this.resetRoom();
                        }
                    })
                }else {
                    para.idStr ? ( params.room.id = para.idStr):"";
                    roomUpate(params).then((res)=>{
                        this.checkResult(res, true, '保存成功')
                    })
                }
            },
            resetForm: function () {

            },

            checkResult: function(res, type, message) {
                if (res.code == "1"){
                    if (type){
                        this.$message({
                            message: message ? message :"操作成功",
                            type: "success"
                        });
                    }
                    return true;
                }else {
                    this.$message({
                        message: "操作失败,失败信息: " + res.desc,
                        type: "error"
                    })
                }
                return false;
            },

            resetRoom: function () {
                this.room =  {
                    handWriting:'',
                        idStr: '',
                        title: '',
                        status: 0,
                        catalog: {
                        idStr: ''
                    },
                    hostTeam: [
                        {idStr: "", title: ""},
                        {idStr: "", title: ""},
                    ],
                        guestTeam: [
                        {idStr: "", title: ""},
                        {idStr: "", title: ""},
                    ],
                        beginTime: '',
                        textAnchor: [],
                        audioAnchor: [],
                        gameStatus: 0,
                        prospectId: "",
                        battlefieldId: "",
                };
                this.hostTeam = [
                    {idStr: "", title: ""},
                    {idStr: "", title: ""},
                ],
                this.guestTeam = [
                    {idStr: "", title: ""},
                    {idStr: "", title: ""},
                ],
                this.teamType = 0;
                this.room.catalog.idStr = this.catalogs[0].idStr;
                this.videos = [
                    {title: "视频源1", url: ""},
                    {title: "视频源2", url: ""},
                    {title: "腾讯", url: ""},
                    {title: "虎扑", url: ""},
                ];
            },

            initRoom: function () {
                if (!_.isUndefined(this.roomId) && !_.isEmpty(this.roomId)){
                    let para = {
                            roomId: this.roomId
                        }
                    roomGetEditRoom(para).then((res)=>{
                        if (this.checkResult(res)) {
                            this.teamListMap = res.data.listMap;
                            this.catalogs = res.data.catalog;
                            this.room = res.data.room;
                            this.teamType = this.room.guestTeam[0].type;
                            this.anchors = res.data.anchor;
                            this.tempAnchors = res.data.anchor;
                            this.tempTeamList = _.cloneDeep(this.teamListMap[this.teamType+"-"+this.room.catalog.idStr]);
                            this.teamList = _.cloneDeep(this.teamListMap[this.teamType+"-"+this.room.catalog.idStr]);
                            for (var i=0;i<this.room.hostTeam.length;i++){
                                this.hostTeam[i] = this.room.hostTeam[i];
                                this.guestTeam[i] = this.room.guestTeam[i];
                            }
                            this.videos = this.room.videos;
                            this.room.audioAnchor.forEach(function (val,index,arr) {
                                arr[index] = val.idStr;
                            })
                            this.room.textAnchor.forEach(function (val,index,arr) {
                                arr[index] = val.idStr;
                            })
                        }
                    });
                }else {
                    this.roomId = "";
                    roomGetByRoomId().then((res)=>{
                        if (this.checkResult(res)) {
                            this.teamListMap = res.data.listMap;
                            this.catalogs = res.data.catalog;
                            this.anchors = _.cloneDeep(res.data.anchor);
                            this.tempAnchors = _.cloneDeep(res.data.anchor);
                            this.tempTeamList = _.cloneDeep(this.teamListMap[0]);
                            this.teamList = _.cloneDeep(this.teamListMap[0]);
                            this.room.catalog.idStr = this.catalogs[0].idStr;
                        }
                    })
                }
            }
        },
        watch: {
              $route(){
                  this.roomId = this.$route.query.roomId
              },
              roomId: function (current, old) {
                  this.initRoom();
              },
        },
        mounted(){
            this.roomId = this.$route.query.roomId;
            this.initRoom();

        },
        components: {}
    }
</script>
<style>
    .media-source input  {
        margin-bottom: 22px;
    }

    .teamIcon {
        width: 100%;
        height: 100px;
    }

    .disappear {
        visibility: hidden;
    }
    .text-right {
        text-align: right;
    }
    .margin-vertical-22 {
        margin: 22px 0;
    }
</style>