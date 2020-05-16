import App from 'pages/App.vue'
import Vue from 'vue'
import "core-js/stable";
import "regenerator-runtime/runtime";
import {connect} from './util/ws'
import Vuetify from "vuetify";
import 'vuetify/dist/vuetify.min.css'
import store from 'store/store'

if(frontendData.profile)
    connect()

Vue.use(Vuetify)
new Vue({
    el:"#app",
    store,
    vuetify:new Vuetify(),
    render: a=>a(App)
})