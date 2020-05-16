<template>
    <v-layout>
        <v-text-field v-model="text"
                      label="Solo"
                      solo
                      dense
        />
<!--        <input type="text" name="text" placeholder="Message text" >-->
        <v-btn class="ml-5" @click="submit">Send</v-btn>
    </v-layout>
</template>

<script>
    import API from "../../api/messages";
    export default {
        name: "MessageForm",
        data() {
            return {
                text:'',
                id:''
            }
        },
        props:['messages','redMessage'],
        watch:{
            redMessage(newVal,oldVal){
                this.text=newVal.text;
                this.id=newVal.id
            }
        },
        methods:{
            async submit(){
                const message={id:this.id,text:this.text}
                if(this.id!==''){
                    API.update(message);
                }else {
                    API.add(message)
                }
                this.text='';
                this.id='';
            }
        }
    }
</script>

<style scoped>

</style>