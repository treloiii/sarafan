import Vue from 'vue'
import Vuex from 'vuex'
import messageAPI from "../api/messages";
import commentAPI from "../api/comment";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        messages,//:frontendData.messages,
        profile: frontendData.profile
    },
    getters:{
        sortedMessages(state){
            let sorted= state.messages.sort((a,b)=>{
                return b.id-a.id;
            })
            console.log(sorted[1])
            return sorted;
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
        },
        updateCommentMutation(state,comment){
            const messageIndex=state.messages.findIndex(el=>el.id===comment.message.id);
            let message =state.messages[messageIndex];
            console.log(message)
            state.messages=[
                ...state.messages.slice(0,messageIndex),
                {
                    ...message,
                    comments:[
                        ...message.comments,
                        comment
                    ]
                },
                ...state.messages.slice(messageIndex+1)
            ]
        },
    },
    actions:{
        async addMessageAction({commit},message){
            await messageAPI.add(message);
            //commit("addMessageMutation",result); //state подсавится автоматически
        },
        async updateMessageAction({commit},message){
            await messageAPI.update(message);
        },
        async removeMessageAction({commit},message){
            await messageAPI.delete(message.id)
        },
        async updateCommentAction({commit,state},comment){
            const response=await (await commentAPI.add(comment)).json();
            console.log(response)
            commit('updateCommentMutation',response);
        }
    }
})