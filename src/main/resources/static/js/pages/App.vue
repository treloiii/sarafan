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
    export default {
        components:{
            MessagesList
        },
        data(){
            return {
                messages: frontendData.messages,
                profile: frontendData.profile
            }
        }
    }
</script>

<style>

</style>