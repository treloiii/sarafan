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
                if(data.objectType==="MESSAGE") {
                    let message = data.body;
                    let index = this.messages.findIndex(item => item.id === message.id);
                    switch (data.eventType) {
                        case "UPDATE":
                        case "CREATE":
                            if (index>-1) {
                                this.messages.splice(index, 1, message)
                            } else {
                                this.messages.push(message);
                            }
                            break;
                        case "REMOVE":
                            this.messages.splice(index, 1)
                            break;
                        default:
                            console.error(`UNKNOWN EVENT TYPE: ${data.eventType}`)
                    }
                }
                else {
                    console.error(`UNKNOWN OBJECT TYPE: ${data.objectType}`)
                }
            })
        }
    }
</script>

<style>
</style>