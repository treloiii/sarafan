<template>
    <v-list-item two-line>
        <v-list-item-content>
            <v-list-item-title>{{comment.author}}</v-list-item-title>
            <v-list-item-subtitle>{{comment.text}}</v-list-item-subtitle>
        </v-list-item-content>

    </v-list-item>
</template>

<script>
    export default {
        name: "CommentItem",
        props:["comment"]
    }
</script>

<style scoped>

</style>