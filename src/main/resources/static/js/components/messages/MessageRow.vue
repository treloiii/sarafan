<template>
    <v-card class="mb-5">
        <v-card-title>
            <i>[{{message.id}}]&nbsp;</i>
            <b>{{message.text}}</b>
        </v-card-title>
        <media v-if="message.link" :message="message "></media>
        <v-card-actions>
            <v-btn type="button" @click="edit">Edit</v-btn>
            <v-btn dark type="button" @click="remove">Delete</v-btn>
        </v-card-actions>
        <comment-list :comments="message.comments" :message-id="message.id"></comment-list>
    </v-card>
</template>

<script>
    import {mapActions} from 'vuex';
    import Media from "../media/Media.vue";
    import CommentList from "../comment/CommentList.vue";
    export default {
        name: "MessageRow",
        components: {CommentList, Media},
        props:['message','editMessage'],
        methods:{
            ...mapActions(["removeMessageAction"]),
            edit(){
                this.editMessage(this.message);
            },
            async remove() {
                this.removeMessageAction(this.message)
            }
        }
    }
</script>

<style scoped>

</style>