<template>
    <v-layout align-content-space-around justify-start column>
        <message-form  :redMessage="message"/>
        <message-row v-for="message in sortedMessages"
                     :message="message"
                     :key="message.id"
                     :editMessage="editMethod"
        />
    </v-layout>
</template>

<script>
    import MessageRow from "./MessageRow.vue";
    import MessageForm from "./MessageForm.vue";
    import {mapGetters} from 'vuex'

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
        computed:mapGetters(['sortedMessages']),
        methods:{
            editMethod(message){
                this.message=message;
            }
        }
    }
</script>

<style scoped>

</style>