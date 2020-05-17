<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title text>Sarafan</v-toolbar-title>
            <v-btn @click="toMessages" v-if="profile" text :disabled="$route.path==='/'">
                Messages List
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn @click="toProfile" v-if="profile" :disabled="$route.path==='/profile'" text>
                Hello {{profile.name}}! &nbsp;
            </v-btn>
            <v-btn v-if="profile" href="/logout">
               Logout
            </v-btn>
        </v-app-bar>
        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>

<script>
    import {addHandler} from "../util/ws";
    import {mapState,mapMutations} from "vuex";

    export default {
        computed:mapState(["profile"]),
        methods:{
            ...mapMutations([
                "addMessageMutation",
                "updateMessageMutation",
                "removeMessageMutation",
                "updateCommentMutation"
            ]),
            toMessages(){
                this.$router.push("/")
            },
            toProfile(){
                this.$router.push("/profile")
            }
        },
        created() {
            addHandler(data=>{
                if(data.objectType==="MESSAGE") {
                    let message = data.body;
                    console.log(message)
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
                } else if(data.objectType==="COMMENT") {
                    let comment = data.body;
                    console.log(comment)
                    switch (data.eventType) {
                        case "CREATE":
                            this.updateCommentMutation(comment);
                            break;
                        default:
                            console.error(`UNKNOWN EVENT TYPE: ${data.eventType}`)
                    }
                }
                else {
                    console.error(`UNKNOWN OBJECT TYPE: ${data.objectType}`)
                }
            })
        },
        beforeMount() {
            if(!this.profile){
                this.$router.replace('/auth');
            }
        }
    }
</script>

<style>
</style>