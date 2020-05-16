<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>Sarafan</v-toolbar-title>
            <v-spacer></v-spacer>
            <div v-if="profile">Hello {{profile.name}}! &nbsp;</div>
            <v-btn v-if="profile" href="/logout">
               Logout
            </v-btn>
        </v-app-bar>
        <v-content>
            <v-container v-if="!profile">Please authorize via <a href="/login">Google</a></v-container>
            <v-container v-if="profile">
                <messages-list  :messages="messages"/>
            </v-container>
        </v-content>
    </v-app>
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
                if(typeof data==="number"){
                    let index=indexOf(this.messages,{id:data});
                    this.messages.splice(index,1)
                }else {
                    let index = indexOf(this.messages, data);
                    if (index > -1) {
                        this.messages.splice(index, 1, data)
                    } else {
                        this.messages.push(data);
                    }
                }
            })
        }
    }
</script>

<style>
</style>