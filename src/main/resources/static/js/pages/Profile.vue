<template>
    <v-container>
        <v-layout justify-space-around>
            <v-flex xs6="!$vuetify.breakpoint.xsOnly">
                <div class="text-center font-weight-bold"><h2>User Profile</h2></div>
                <v-layout row justify-space-between>
                    <v-flex class="px-1">
                        <v-img max-width="5rem" :src="profile.userpic"></v-img>
                    </v-flex>
                    <v-flex class="px-1">
                        <v-layout column>
                            <v-flex>Name: {{profile.name}}</v-flex>
                            <v-flex>Region: {{profile.locale}}</v-flex>
                            <v-flex>Last in: {{profile.lastVisit}}</v-flex>
                            <v-flex>Subscribers: {{profile.subscribers && profile.subscribers.length}}</v-flex>
                            <v-flex>Subscriptions: {{profile.subscriptions && profile.subscriptions.length}}</v-flex>
                        </v-layout>
                    </v-flex>
                </v-layout>
                <v-btn
                v-if="!isMyProfile"
                @click="changeSubscription">
                    {{isSubscribed}}
                </v-btn>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import profileAPI from "../api/profile";
    import {mapGetters} from "vuex";
    export default {
        name: "Profile",
        data(){
            return {
                profile:{}
            }
        },
        computed:{
            ...mapGetters(["myId"]),
            isSubscribed(){
                let subs=this.profile.subscribers;
                const isSubscribed= this.profile.subscribers;
                if(!isSubscribed)
                    return "Subscribe";
                for(let i=0;i<subs.length;i++){
                    if(subs[i].subscriber===this.myId)
                        return "Unsusbscribe";
                }
                return "Subscribe";
            },
            isMyProfile(){
                return !this.$route.params.id || this.$route.params.id===this.myId;
            }
        },
        watch:{
            '$route'(){
                this.updateProfile();
            }
        },
        methods:{
            async changeSubscription(){
                const data =await (await profileAPI.changeSubscription(this.profile.id)).json();
                this.profile=data;
            },
            async updateProfile(){
                const id=this.$route.params.id||this.myId;
                console.log(id)
                const data=await (await profileAPI.get(id)).json();
                this.profile=data;
                // this.$forceUpdate();
            }
        },
        beforeMount() {
            this.updateProfile();
        }
    }
</script>

<style scoped>

</style>