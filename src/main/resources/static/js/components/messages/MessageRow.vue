<template>
    <v-card class="mb-5">
        <v-card-title>
            <v-list-item two-line>
                <v-list-item-avatar color="indigo" v-if="message.author&&message.author.userpic" size="36px">
                    <v-img :src="message.author.userpic"/>
                </v-list-item-avatar>
                <v-list-item-avatar color="indigo" v-else size="36px">
                    <v-img src="https://forum.develop-soft.ru/images/avatars/random/default-avatar.svg"/>
                </v-list-item-avatar>
                <v-list-item-content>
                    <v-list-item-subtitle>{{messageAuthor}}</v-list-item-subtitle>
                    <v-list-item-title>{{message.text}}</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
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
        computed:{
            messageAuthor(){
                return this.message.author?this.message.author.name:"anonim";
            }
        },
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