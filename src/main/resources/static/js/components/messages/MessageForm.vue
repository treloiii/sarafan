<template>
    <v-layout>
        <v-text-field v-model="text"
                      label="Solo"
                      solo
                      dense
        />
        <v-btn class="ml-5" @click="submit">Send</v-btn>
    </v-layout>
</template>

<script>
    import {mapActions} from "vuex";
    export default {
        name: "MessageForm",
        data() {
            return {
                text:'',
                id:''
            }
        },
        props:['redMessage'],
        watch:{
            redMessage(newVal,oldVal){
                this.text=newVal.text;
                this.id=newVal.id
            }
        },
        methods:{
            ...mapActions(["updateMessageAction","addMessageAction"]),
            async submit(){
                const message={id:this.id,text:this.text}
                if(this.id!==''){
                    this.updateMessageAction(message);
                }else {
                    this.addMessageAction(message)
                }
                this.text='';
                this.id='';
            }
        }
    }
</script>

<style scoped>

</style>