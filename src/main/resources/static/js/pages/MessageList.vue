<template>
    <v-container>
        <v-layout align-content-space-around justify-start column>
            <message-form  :redMessage="message"/>
            <message-row v-for="message in sortedMessages"
                         :message="message"
                         :key="message.id"
                         :editMessage="editMethod"
            />
            <lazy-loader></lazy-loader>
        </v-layout>
    </v-container>
</template>

<script>
    import MessageRow from "../components/messages/MessageRow.vue";
    import MessageForm from "../components/messages/MessageForm.vue";
    import {mapGetters, mapState} from 'vuex'
    import LazyLoader from "../components/LazyLoader.vue";

    export default {
        name: "MessagesList.vue",
        components:{
            LazyLoader,
            MessageRow,
            MessageForm
        },
        data() {
            return{
                message:null
            }
        },
        computed:{
            ...mapGetters(['sortedMessages'])
        },
        methods:{
            editMethod(message){
                this.message=message;
            }
        }
    }
</script>

<style scoped>

</style>