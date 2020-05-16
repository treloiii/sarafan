import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

var stompClient=null;
const handlers=[];
export function connect() {
    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug=()=>{}
    stompClient.connect({}, frame=> {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/activity', message=> {
            // showGreeting(JSON.parse(greeting.body).content);
            handlers.forEach(handler=>handler(JSON.parse(message.body)));
        });
    });
}

export function addHandler(handler) {
    handlers.push(handler)
}
export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

export function sendMessage(message) {
    stompClient.send("/app/hello", {}, JSON.stringify(message));
}
export function deleteMessage(message) {
    stompClient.send("/app/delete",{},JSON.stringify(message))
}