<template>
    <form @submit.prevent="submit">
        <input type="text" name="text" placeholder="Message text" v-model="text">
        <button type="submit">Submit</button>
    </form>
</template>

<script>
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
            indexOf(arr,el){
                for(let i=0;i<arr.length;i++){
                    if(arr[i].id===el.id)
                        return i
                }
                return -1;
            },
            async submit(){
                let message={
                    text:this.text
                }
                if(this.id!==''){
                    console.log(this.id)
                    message.id=this.id;
                    let index=this.indexOf(this.messages,message)
                    let res=await this.$resource('/message{/id}').update({id:this.id},message)
                    res = await res.json();
                    console.log(index)
                    this.messages.splice(index,1,res);
                    this.text = "";
                }else {
                    let res = await this.$resource('/message{/id}').save({}, message);
                    res = await res.json();
                    this.messages.push(res);
                    this.text = "";
                }
            }
        }
    }
</script>

<style scoped>

</style>