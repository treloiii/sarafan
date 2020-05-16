<template>
    <v-container>
        <v-layout align-content-space-around justify-start column>
            <message-form  :redMessage="message"/>
            <message-row v-for="message in sortedMessages"
                         :message="message"
                         :key="message.id"
                         :editMessage="editMethod"
            />
        </v-layout>
    </v-container>
</template>

<script>
    import MessageRow from "../components/messages/MessageRow.vue";
    import MessageForm from "../components/messages/MessageForm.vue";
    import {mapGetters, mapState} from 'vuex'

    export default {
        name: "MessagesList",
        components:{
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