import Stomp from "stompjs"
import SockJS from "sockjs-client"
let wsclient;
export function connect() {
    const s = new SockJS("http://localhost:8091/spring-demo3/websocket",null,{withCredentials:true});
    wsclient = Stomp.over(s);
    wsclient.connect({}, function (frame){
        const userId = localStorage.getItem("id");
        wsclient.subscribe('/notify/msg/'+userId.replace(/^"(.*)"$/, '$1'), function (msg){
            alert(msg)
        })
    })
}

export function disconnect() {
    wsclient.disconnect();
}