<template>
<section>
    <div>
        <img v-if="imgUrl" :src="imgUrl"/>
    </div>

    <face :ifShow="ifShow" @getFace="getFace"></face>
</section>

</template>
<script>
    import face from './face.vue';
    export default {
        data() {
            return {
                ifShow: true,
                imgUrl: ''
            };
        },
        methods: {
            getFace: function(faceUrl) {
                this.imgUrl  = faceUrl;
                this.ifShow = false;
            }
        },
        mounted(){

        },
        components: {face}
    }
</script>
<style></style>