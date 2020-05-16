<template>
    <div>
        <message-form :messages="messages" :redMessage="message"/>
        <message-row v-for="message in messages"
                     :message="message"
                     :messages="messages"
                     :key="message.id"
                     :editMessage="editMethod"
                     :deleteMessage="deleteMessage"
        />
    </div>
</template>

<script>
    import MessageRow from "./MessageRow.vue";
    import MessageForm from "./MessageForm.vue";
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
            }
        }
    }
</script>

<style scoped>

</style>