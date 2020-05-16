import Vue from 'vue'
import Vuex from 'vuex'
import API from "../api/messages";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        messages: frontendData.messages,
        profile: frontendData.profile
    },
    getters:{
        sortedMessages(state){
            return state.messages.sort((a,b)=>{
                return b.id-a.id;
            })
        }
    },
    mutations: {
        addMessageMutation(state,message){
            state.messages=[
                ...state.messages,
                message
            ]
        },
        updateMessageMutation(state,message){
            const updateIndex=state.messages.findIndex(el=>el.id===message.id);
            state.messages=[
                ...state.messages.slice(0,updateIndex),
                message,
                ...state.messages.slice(updateIndex+1)
            ]
        },
        removeMessageMutation(state,message){
            const deleteIndex=state.messages.findIndex(el=>el.id===message.id);
            if(deleteIndex>-1) {
                state.messages = [
                    ...state.messages.slice(0, deleteIndex),
                    ...state.messages.slice(deleteIndex + 1)
                ]
            }
        }
    },
    actions:{
        async addMessageAction({commit},message){
            await API.add(message);
            //commit("addMessageMutation",result); //state подсавится автоматически
        },
        async updateMessageAction({commit},message){
            await API.update(message);
        },
        async removeMessageAction({commit},message){
            await API.delete(message.id)
        }
    }
})