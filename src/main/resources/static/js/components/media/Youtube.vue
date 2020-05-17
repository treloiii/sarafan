<template>
    <iframe width="560" height="315"
            :src="targetSrc"
            frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
            allowfullscreen
    >
    </iframe>
</template>

<script>
    export default {
        name: "Youtube",
        props:['src'],
        computed:{
            targetSrc(){
                let vidID=this.src.split("/").pop();
                return `https://www.youtube.com/embed/${vidID}`;
            }
        }
    }
</script>

<style scoped>

</style>