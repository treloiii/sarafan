import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)
const messages=Vue.resource('message{/id}');

export default {
    add:message=>messages.save({},message),
    update:message=>{
        let id=message.id;
        messages.update({id},message)
    },
    delete:id=>messages.remove({id})
}