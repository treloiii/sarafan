<template>
    <v-list-item two-line>
        <v-list-item-avatar size="36">
            <v-img :src="comment.user.userpic"/>
        </v-list-item-avatar>
        <v-list-item-content>
            <v-list-item-subtitle>{{comment.user.name}}</v-list-item-subtitle>
            <v-list-item-title>{{comment.text}}</v-list-item-title>
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