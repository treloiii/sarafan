
var messageApi=Vue.resource('/message{/id}')
function indexOf(arr,el){
    for(let i=0;i<arr.length;i++){
        if(arr[i].id===el.id)
            return i
    }
    return -1;
}
Vue.component('message-form',{
    data() {
        return {
            text:'',
            id:''
        }
    },
    props:['messages','redMessage'],
    watch:{
      redMessage:function(newVal,oldVal){
          this.text=newVal.text;
          this.id=newVal.id
      }
    },
    template:'<form @submit.prevent="submit">' +
                '<input type="text" name="text" placeholder="Message text" v-model="text">'+
                '<button type="submit">Submit</button> '+
              '</form>',
    methods:{
        submit:async function(){
            let message={
                text:this.text
            }
            if(this.id!==''){
                console.log(this.id)
                message.id=this.id;
                let index=indexOf(this.messages,message)
                let res=await messageApi.update({id:this.id},message)
                res = await res.json();
                console.log(index)
                this.messages.splice(index,1,res);
                this.text = "";
            }else {
                let res = await messageApi.save({}, message);
                res = await res.json();
                this.messages.push(res);
                this.text = "";
            }
        }
    }
})

Vue.component('message-row',{
    props:['message','editMethod','messages'],
    template:'<div>' +
        '<i>{{message.id}}</i><b>{{message.text}}</b>' +
        '<span><button type="button" @click="edit">Edit</button></span>'+
        '<span><button type="button" @click="remove">X</button></span>'+
        '</div>',
    methods:{
        edit:function(){
            this.editMethod(this.message);
        },
        remove:async function () {
            let res=await messageApi.remove({id:this.message.id});
            console.log(res)
            this.messages.splice(indexOf(this.messages,this.message),1)
        }
    }
})

Vue.component('messages-list', {
    data() {
        return{
            message:null
        }
    },
    props:['messages'],
    template: '<div>' +
                '<message-row v-for="message in messages" :message="message" :messages="messages" :key="message.id" :editMethod="editMethod"/>' +
                '<message-form :messages="messages" :redMessage="message"/>'+
              '</div>',
    created:async function(){
        let res=await messageApi.get()
        res=await res.json();
        console.log(res);
        res.forEach(message=>{
            this.messages.push(message)
        });
    },
    methods:{
        editMethod:function(message){
            this.message=message;
        }
    }
})


var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages"/>',
    data: {
        messages: []
    }
})