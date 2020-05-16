import Vue from 'vue'
import App from 'pages/App.vue'
import VueResource from 'vue-resource'
import "core-js/stable";
import "regenerator-runtime/runtime";
import {connect} from './util/ws'

connect()
Vue.use(VueResource)
new Vue({
    el:"#app",
    render: a=>a(App)
})