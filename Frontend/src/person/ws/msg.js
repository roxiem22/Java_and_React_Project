import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

let wsclient;
let messgs = [];

export function connect1(messageCallback, typingCallback) {
    const s = new SockJS('http://localhost:8099/ws', null, { withCredentials: true });
    wsclient = Stomp.over(s);

    wsclient.connect({}, function (frame) {
        wsclient.subscribe('/chat/message/', function (msg) {
            messgs.push(msg.body);
            if (messageCallback) {
                messageCallback();
            }
        });

        wsclient.subscribe('/chat/typing/', function (typingStatus) {
            const parsedTypingStatus = JSON.parse(typingStatus.body);

            if (typingCallback) {
                typingCallback(parsedTypingStatus);
            }
        });
    });
}

export function sendMessage(message, callback) {
    wsclient.send('/ws', {}, message);
    if (callback) {
        callback();
    }
}

export function sendTypingStatus(typingStatus) {
    wsclient.send('/typing', {}, typingStatus);
}

export function disconnect1() {
    wsclient.disconnect();
}

export { messgs };
