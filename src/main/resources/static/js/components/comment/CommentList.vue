<template>
    <v-layout>
        <v-card tile class="mx-auto">
            <comment-item v-for="(item,index) in comments" :comment="item" :key="index"></comment-item>
            <comment-form :message-id="messageId"></comment-form>
        </v-card>
    </v-layout>
</template>

<script>
    import CommentItem from "./CommentItem.vue";
    import CommentForm from "./CommentForm.vue";
    export default {
        name: "CommentList",
        components: {CommentForm, CommentItem},
        props:["comments","messageId"]
    }
</script>

<style scoped>

</style>