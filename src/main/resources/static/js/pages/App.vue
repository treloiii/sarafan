<template>
    <div>
        <div v-if="!profile">Please authorize via <a href="/login">Google</a></div>
        <div v-else>
            <div>Hello {{profile.name}}! &nbsp;<a href="/logout">Logout</a></div>
            <messages-list  :messages="messages"/>
        </div>
    </div>
</template>

<script>
    import MessagesList from "../components/messages/MessageList.vue";
    import {addHandler} from "../util/ws";
    import {indexOf} from "../util/util";

    export default {
        components:{
            MessagesList
        },
        data(){
            return {
                messages: frontendData.messages,
                profile: frontendData.profile
            }
        },
        created() {
            addHandler(data=>{
                let index=indexOf(this.messages,data);
                if(index>-1){
                    this.messages.splice(index,1,data)
                }
                else{
                    this.messages.push(data);
                }
            })
        }
    }
</script>

<style>

</style>