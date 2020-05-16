import Vue from 'vue'
import App from 'pages/App.vue'
import VueResource from 'vue-resource'
import "core-js/stable";
import "regenerator-runtime/runtime";
Vue.use(VueResource)
new Vue({
    el:"#app",
    render: a=>a(App)
})

/*

var messageApi=Vue.resource('/message{/id}')
function indexOf(arr,el){

}
Vue.component('message-form',{

})
*/
