<template>
    <v-card class="mb-5">
        <v-card-title>
            <i>[{{message.id}}]&nbsp;</i>
            <b>{{message.text}}</b>
        </v-card-title>
        <v-card-actions>
            <v-btn flat type="button" @click="edit">Edit</v-btn>
            <v-btn flat dark type="button" @click="removeWs">Delete</v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: "MessageRow",
        props:['message','editMessage','deleteMessage','messages','deleteWs'],
        methods:{
            edit:function(){
                this.editMessage(this.message);
            },
            async remove() {
                this.deleteMessage(this.message);
            },
            removeWs(){
                this.deleteWs(this.message);
            }
        }
    }
</script>

<style scoped>

</style>