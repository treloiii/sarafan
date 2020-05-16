<template>
    <v-layout align-content-space-around justify-start column>
        <message-form :messages="messages" :redMessage="message"/>
        <message-row v-for="message in sortedMessages"
                     :message="message"
                     :messages="messages"
                     :key="message.id"
                     :editMessage="editMethod"
                     :deleteMessage="deleteMessage"
        />
    </v-layout>
</template>

<script>
    import MessageRow from "./MessageRow.vue";
    import MessageForm from "./MessageForm.vue";
    import API from "../../api/messages";

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
        props:['messages'],
        computed:{
            sortedMessages(){
                return this.messages.sort((a,b)=>{
                    return b.id-a.id;
                })
            }
        },
        methods:{
            editMethod(message){
                this.message=message;
            },
            async deleteMessage(message){
                await API.delete(message.id);
            }
        }
    }
</script>

<style scoped>

</style>