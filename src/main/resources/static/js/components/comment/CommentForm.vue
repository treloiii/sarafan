<template>
    <v-layout>
        <v-text-field v-model="text"
                      label="Add comment"
                      solo
                      dense
                      @keyup.enter="submit"
        />
        <v-btn class="ml-5" @click="submit">Send</v-btn>
    </v-layout>
</template>

<script>
    import {mapActions} from "vuex";

    export default {
        name: "CommentForm",
        props:['messageId'],
        data(){
            return{
                text: "",

            }
        },
        methods:{
            ...mapActions(['updateCommentAction']),
            async submit() {
                this.updateCommentAction({
                    text:this.text,
                    message:{
                        id:this.messageId
                    }
                });
                this.text='';
            }
        }
    }
</script>

<style scoped>

</style>