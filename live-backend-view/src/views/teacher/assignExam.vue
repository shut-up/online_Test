<template>
    <section>
        <el-col :span="24" class="toolbar">
            <el-form :inline="true" :model="filters">
                <el-form-item label="大类">
                    <el-select v-model="filters.catalogId" placeholder="全部" clearable>
                        <el-option
                                v-for="item in catalogs"
                                :key="item.idStr"
                                :value="item.idStr"
                                :label="item.title"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="比赛状态" placeholder="全部">
                    <el-select v-model="filters.gameStatus" clearable>
                        <el-option
                                v-for="item in gameStatus"
                                :key="item.idStr"
                                :value="item.idStr"
                                :label="item.title"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="直播间状态">
                    <el-select v-model="filters.status" clearable>
                        <el-option
                                v-for="item in roomStatus"
                                :key="item.idStr"
                                :value="item.idStr"
                                :label="item.title"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="时间">
                    <el-date-picker
                            v-model="filters.timeRange"
                            type="datetimerange"
                            placeholder="开始和结束时间"
                            align="right"
                            clearable
                    >
                    </el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-input v-model="filters.searchTitle" placeholder="输入直播间名称或队名"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button icon="search" @click="searchList">搜索</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button icon="plus" @click="addRoom">新建直播间</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <el-table :data="rooms"
                  highlight-current-row
                  v-loading="listLoading"
                  @selection-change="selectChange"
                  style="width: 100%;"
                  ref="roomRef"
        >
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="idStr" label="直播ID" width="180">
                <template scope="scope">
                    <div class="blue" @click="editRoom(scope.$index, scope.row)">
                        {{ scope.row.idStr}}
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="catalog.title" label="类型" width="70">
            </el-table-column>
            <el-table-column prop="status" label="状态" width="65" :formatter="formatStatus">
            </el-table-column>
            <el-table-column prop="title" label="名称" min-width="200">
            </el-table-column>
            <el-table-column prop="hostTeam" label="对阵" width="200" :formatter="formatAgainst">
            </el-table-column>
            <el-table-column prop="textAnchor" label="图文主播" :formatter="formatAnchors" width="95">
            </el-table-column>
            <el-table-column prop="audioAnchor" label="音频主播" :formatter="formatAnchors" width="95">
            </el-table-column>
            <el-table-column prop="gameStatus" label="赛况" width="70" :formatter="formatGameStatus">
            </el-table-column>
            <el-table-column prop="prospectId" label="前瞻" width="70" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="battlefieldId" label="战报" width="70" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="handWriting" label="附注" width="70" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="beginTime" label="开始时间" width="160" :formatter="formatTime">
            </el-table-column>
            <el-table-column prop="endTime" label="结束时间" width="160" :formatter="formatTime">
            </el-table-column>
            <el-table-column label="操作" width="140">
                <template scope="scope">
                    <div class="moreContent" @click="goLive(scope.$index, scope.row)">进入直播间</div>
                </template>
            </el-table-column>
        </el-table>
        <el-col :span="24" class="toolbar">
            <el-form :inline="true"  v-model="operations">
                <el-form-item>
                    <el-select v-model="operations.oType">
                        <el-option key="1" value="1" label="操作"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-select v-model="operations.status" :disabled="this.selectedItems.length===0">
                        <el-option
                            v-for="item in status"
                            :key="item.idStr"
                            :value="item.idStr"
                            :label="item.title"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="updateRoomStatus" :disabled="this.selectedItems.length===0">确定</el-button>
                </el-form-item>
                <el-pagination layout="total,prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total" style="float:right;">
                </el-pagination>
            </el-form>
        </el-col>
    </section>
</template>
<script>
    import Util from '../../common/js/util';
    import {roomStatusUpate,roomGetList,roomGetSearch,roomGetAllCatalog,roomGetSize} from '../../api/api';
    export default {
        data(){
            var g = this.GLOBAL.defaultConfig;
            return {
                /** config **/
                page: 1,
                pageSize: 15,
                total: 0,

                filters: {
                    timeRange: [],
                    catalogId: "",
                    gameStatus: "",
                    status: "",
                    searchTitle: ""
                },
                operations: {
                    oType: "1",
                    status: "",
                },
                status: g.operations,
                catalogs: [

                ],
                gameStatus: g.gameStatus,
                roomStatus: g.roomStatus,
                rooms: [
                ],
                listLoading: false,
                selectedItems: [],
            };
        },
        methods:{
            /** testapi **/
            getCel: function (row, column, cell, event) {
            },

            /** formatter **/
            formatAgainst: function (row, column) {
                var ret = new Array();
                var left = new Array();
                var right = new Array();
                for (let i=0; i<row.hostTeam.length; i++){
                    left.push(row.hostTeam[i].title);
                    right.push(row.guestTeam[i].title);
                }
               return "["+left.join("&") + "]vs[" + right.join("&") + "]";
            },
            formatStatus: function (row, column) {
               return row[column.property] == 0 ? '显示' :
                   (row[column.property] == 1) ? '隐藏' : '推荐';
            },
            formatGameStatus: function (row, column) {
               return row[column.property] == 0 ? '未开'
                   : row[column.property] == 1 ? '赛中'
                       : '结束';
            },
            formatTime: function(row, column) {
               if (row[column.property] == 0 || row[column.property] == null) {
                   return "";
               }
               let date = new Date(row[column.property]);
               return Util.formatDate.format(date,'y-M-d h:m:s');
            },
            formatAnchors: function (row, column) {
                let ret = new Array();
                for (let i=0; i<row[column.property].length; i++){
                    ret.push(row[column.property][i].title)
                }
                return ret.join("&")
            },

            /** opera room **/
            searchList: function () {
                let para = _.cloneDeep(this.filters);
                if (!_.isEmpty(para.timeRange)){
                    para.beginTime = (new Date(para.timeRange[0])).getTime();
                    para.endTime = (new Date(para.timeRange[1])).getTime();
                }
                roomGetSearch(para).then((res)=>{
                    if (this.checkResult(res, true)){
                        this.rooms = res.data.list;
                        this.total = res.data.total;
                    }
                });
            },
            updateRoomStatus: function () {
                let para = {
                    status: this.operations.status,
                    ids: this.selectedItems.map(res=>res.idStr)
                }
                roomStatusUpate(para).then((res)=>{
                    if (this.checkResult(res, true)){
                        this.getRoomList();
                    }
                })
            },


            /** jump to other page **/
            addRoom: function () {
                this.$router.push({ path: '/editRoom'});
            },
            editRoom: function (index, row) {
                this.$router.push({ path: '/editRoom/?roomId=' + row.idStr})
            },
            goLive: function (index, row) {
                this.$router.push({ path: '/liveRoom/?roomId=' + row.idStr})
            },

            selectChange: function (items) {
                this.selectedItems = items;
            },

            checkResult: function (result, noSuccessMessage) {
                if (result.code == "1"){
                    if (noSuccessMessage){
                        this.$message({
                            message: "操作成功",
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

            //page
            handleCurrentChange: function (val) {
              this.page = val;
            },

            /** init **/
            getRoomSize: function () {
                roomGetSize().then((res)=>{
                    if (this.checkResult(res)){
                        this.total = res.data;
                    }
                })
            },
            getAllCatalogs: function () {
                roomGetAllCatalog().then((res)=>{
                    if (this.checkResult(res)){
                        this.catalogs = res.data;
                    }
                })
            },
            getRoomList: function () {
                let para = {
                  page: this.page,
                  pageSize: this.pageSize
                };
                this.listLoading = true;
                roomGetList(para).then((res)=>{
                    this.listLoading = false;
                    if (this.checkResult(res)){
                        this.rooms = res.data;
                    }
                })
            },
            initPage: function () {
                this.getAllCatalogs();
                this.getRoomList();
                this.getRoomSize();
            }
        },
        mounted(){
            this.initPage();
        },
        components:{}
    }
</script>
<style scoped>
    .moreContent {
        color: blue;
        cursor: pointer
    }
</style>