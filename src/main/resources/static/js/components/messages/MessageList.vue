<template>
    <v-layout align-content-space-around justify-start column>
        <message-form :messages="messages" :redMessage="message"/>
        <message-row v-for="message in sortedMessages"
                     :message="message"
                     :messages="messages"
                     :key="message.id"
                     :editMessage="editMethod"
                     :deleteMessage="deleteMessage"
                     :deleteWs="deleteWs"
        />
    </v-layout>
</template>

<script>
    import MessageRow from "./MessageRow.vue";
    import MessageForm from "./MessageForm.vue";
    import {deleteMessage} from "../../util/ws";

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
            indexOf(arr,el){
                for(let i=0;i<arr.length;i++){
                    if(arr[i].id===el.id)
                        return i
                }
                return -1;
            },
            async deleteMessage(message){
                let res=await this.$resource('/message{/id}').remove({id:message.id});
                console.log(res)
                this.messages.splice(this.indexOf(this.messages,message),1)
            },
            deleteWs(message){
                deleteMessage(message)
            }
        }
    }
</script>

<style scoped>

</style>