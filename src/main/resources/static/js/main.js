import Vue from 'vue'
import App from 'pages/App.vue'
import VueResource from 'vue-resource'
import "core-js/stable";
import "regenerator-runtime/runtime";
import {connect} from './util/ws'
import Vuetify from "vuetify";
import 'vuetify/dist/vuetify.min.css'


if(frontendData.profile)
    connect()

Vue.use(Vuetify)
Vue.use(VueResource)
new Vue({
    el:"#app",
    vuetify:new Vuetify(),
    render: a=>a(App)
})