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
                            <v-flex>Email: {{profile.email}}</v-flex>
                            <v-flex>Region: {{profile.locale}}</v-flex>
                            <v-flex>Last in: {{profile.lastVisit}}</v-flex>
                        </v-layout>
                    </v-flex>
                </v-layout>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "Profile",
        computed:mapState(["profile"])
    }
</script>

<style scoped>

</style>