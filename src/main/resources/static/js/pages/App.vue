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
                <messages-list/>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import MessagesList from "../components/messages/MessageList.vue";
    import {addHandler} from "../util/ws";
    import {mapState,mapMutations} from "vuex";

    export default {
        components:{
            MessagesList
        },
        computed:mapState(["profile"]),
        methods:mapMutations(["addMessageMutation","updateMessageMutation","removeMessageMutation"]),
        created() {
            addHandler(data=>{
                if(data.objectType==="MESSAGE") {
                    let message = data.body;
                    switch (data.eventType) {
                        case "UPDATE":
                            this.updateMessageMutation(message);
                            break;
                        case "CREATE":
                            this.addMessageMutation(message);
                            break;
                        case "REMOVE":
                            this.removeMessageMutation(message);
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