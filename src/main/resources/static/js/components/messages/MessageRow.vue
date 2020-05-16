<template>
    <div>
        <i>{{message.id}}</i>
        <b>{{message.text}}</b>
        <span>
            <button type="button" @click="edit">Edit</button>
        </span>
        <span>
            <button type="button" @click="remove">X</button>
        </span>
    </div>
</template>

<script>
    export default {
        name: "MessageRow",
        props:['message','editMessage','deleteMessage','messages'],
        methods:{
            edit:function(){
                this.editMessage(this.message);
            },
            async remove() {
                this.deleteMessage(this.message);
            }
        }
    }
</script>

<style scoped>

</style>