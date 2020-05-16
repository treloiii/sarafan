<template>
    <v-card class="mb-5">
        <v-card-title>
            <i>[{{message.id}}]&nbsp;</i>
            <b>{{message.text}}</b>
        </v-card-title>
        <v-card-actions>
            <v-btn type="button" @click="edit">Edit</v-btn>
            <v-btn dark type="button" @click="remove">Delete</v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    import {mapActions} from 'vuex';
    export default {
        name: "MessageRow",
        props:['message','editMessage'],
        methods:{
            ...mapActions(["removeMessageAction"]),
            edit(){
                this.editMessage(this.message);
            },
            async remove() {
                this.removeMessageAction(this.message)
            }
        }
    }
</script>

<style scoped>

</style>