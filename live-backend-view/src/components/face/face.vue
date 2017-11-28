<template>
    <section>
        <el-tabs type="border-card" :class="[ifShow?'show':'hidden']">
            <el-tab-pane
                    v-for="item in faceLists"
                    :label="item.title"
                    :key="item.id"
            >
                <table>
                    <tr v-for="i in Math.ceil(item.quantity/15)">
                        <td v-for="j in 15" v-if="((i-1)*15 + j)<=item.quantity">
                            <img :src="item.baseUrl+((i-1)*15 + j)+'.gif'" @click="insertIcon(((i-1)*15 + j),item.baseUrl)" class="clickAble"/>
                        </td>
                    </tr>
                </table>
            </el-tab-pane>
        </el-tabs>
    </section>
</template>
<script>
    export default {
        name: 'face',
        data() {
            var g = this.GLOBAL.defaultConfig;
            return {
                faceLists: g.defaultImg.face,
            };
        },
        props: {
            ifShow: {
                type: Boolean
            }
        },
        mounted() {
        },
        methods: {
            insertIcon: function (filename,baseUrl) {
                var faceUrl = baseUrl + filename + '.gif';
                this.$emit("getFace",faceUrl);
            }
        }

    }
</script>
<style scoped="scoped">
    .inline {
        display: inline-block;
    }
    .clickAble {
        cursor: pointer;
    }
    .show {
        display: inline-block;
    }
    .hidden {
        display: none;
    }
</style>